package frc.robot.subsystems;

import java.util.Map;

import com.studica.frc.Servo;
import com.studica.frc.TitanQuad;
import com.studica.frc.TitanQuadEncoder;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Arm extends SubsystemBase {
    private final TitanQuad motor3;         //shoulder
    // private final TitanQuadEncoder encoder3;
    private Encoder encoder3;
    private PIDController pidController3;
    private double pid_dT = Constants.PID_DT_ARM;
    double pidInput3;
    double pidOutput3;
    double default_P = 0.2;
    double default_D = 0.001;
    double default_I = 0.00;
    double pidInPipe[];
    final int PIPESIZE = 3;


    private final Servo servo1;
    private final Servo servo2;
    private final Servo servo3;
    private final double a1 = 0.24; //upper arm length
    private final double a2 = 0.33; //lower arm
    private final double default_x = a2; //Power on arm-tip default position
    private final double default_y = a1;
    private final double reset_A = 133;  //Power on arm angle A value
    private double m_x, m_y;     //current arm tip position
    private Translation2d m_pos; // current arm tip position
    private double m_A, m_B;
    //private double gripper_yoffset = 0.2;
    //private double arm_base_yoffset = 0.15;

    //The offset is required as motor cannot be mounted perfectly.
    //Also a preferred offset is required due to limited range of servo motor movement
    //For example, studica servo range is 0-300 deg.
    //The offset allows us to choose the most effect mounting position for servo.

    private double offset0 = 120;  //For making software adjustment to servo 
                                  //Upper arm power on angle. (up right)
    private double offset1 = 0;

    //gearing of servo motor to joint
    private double ratio0 = 2.0;
    private double ratio1 = 2.0;

    private static boolean firstTime = true;

    // Good for debugging
    // Shuffleboard
    private final ShuffleboardTab tab = Shuffleboard.getTab("Arm");
    private final NetworkTableEntry D_arm_A = tab.add("arm_A_deg", 0).getEntry();
    private final NetworkTableEntry D_enc3 = tab.add("enc3", 0).getEntry();
    private final NetworkTableEntry D_in3 = tab.add("in3", 0).getEntry();
    private final NetworkTableEntry D_out3 = tab.add("out3", 0).getEntry();
    private final NetworkTableEntry D_offset0 = tab.addPersistent("offset0", offset0).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", -100, "max", +200)).getEntry();
    private final NetworkTableEntry D_offset1 = tab.addPersistent("offset1", offset1).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", -100, "max", +200)).getEntry();
    //private final NetworkTableEntry D_offset0 = tab.addPersistent("offset0", 0).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", -500, "max", +500)).getEntry();
    //private final NetworkTableEntry D_offset1 = tab.addPersistent("offset1", 0).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", -500, "max", +500)).getEntry();

    private final NetworkTableEntry D_posX = tab.add("posX", 0).getEntry();
    private final NetworkTableEntry D_posY = tab.add("posY", 0).getEntry();
    private final NetworkTableEntry D_slider_P = tab.add("pid P", default_P).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0.01, "max", 0.4)) .getEntry();
    private final NetworkTableEntry D_slider_D = tab.add("pid D", default_D).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0.0, "max", 0.1)) .getEntry();
    private final NetworkTableEntry D_slider_I = tab.add("pid I", default_I).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0.0, "max", 0.01)) .getEntry();
    private final NetworkTableEntry D_sliderX = tab.add("setX", default_x).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0.05, "max", 0.5)) .getEntry();
    private final NetworkTableEntry D_sliderY = tab.add("setY", default_y).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", -0.2, "max", 0.5)) .getEntry();
    
   
    public Arm () {
        //Motors and encoders
        motor3 = new TitanQuad(Constants.TITAN_ID, 3);         //shoulder
        motor3.setInverted(false);   //Positive is CW. Need to reverse

        int enc = 3;
        encoder3 = new Encoder(enc*2, enc*2+1, false, Encoder.EncodingType.k4X);
        encoder3.setDistancePerPulse(Constants.KENCODERANGLE);
        // encoder3 = new TitanQuadEncoder(motor3, 3, Constants.KENCODERANGLE);
        encoder3.reset();

        pidController3 = new PIDController(default_P,default_I,default_D, pid_dT); 

        pidInPipe = new double[PIPESIZE];
        // pidController3.setIntegratorRange(-0.2, 0.2);

        servo1 = new Servo(Constants.SERVO_1);  //elbow
        servo2 = new Servo(Constants.SERVO_2);  //gripper
        servo3 = new Servo(Constants.SERVO_3);  //camera

    }
    
    public void initialize() {
        pidController3.reset();
        if (firstTime == true) {
            //First time enabled. Init the encoder reference
            encoder3.reset();
            setArmAngleAB(offset0, 90);
            firstTime  = false;
        }
        else {
            //Set m_A based on current encoder value. This will prevent the arm from jerking
            m_A = offset0 - encoder3.getDistance()/ratio0;
            setArmPosGivenAngle(m_A, m_B);
        }
        //setArmPos(m_x, m_y);
        setGripperAngle(150);
    }


    /**
     * Get slider-x value
     * <p>
     * 
     * @return return slider value
     */
    public double getSliderX( ) {
        return D_sliderX.getDouble(0.04);
    }

    /**
     * Get slider-y value
     * <p>
     * 
     * @return return slider value
     */
    public double getSliderY( ) {
        return D_sliderY.getDouble(0.0);
    }
    public double getSliderOffset0( ) {
        return D_offset0.getDouble(0.0);
    }
    public double getSliderOffset1( ) {
        return D_offset1.getDouble(0.0);
    }

   
    /**
     * Sets the servo3 angle (Camera)
     * <p>
     * 
     * @param degrees degree to set the servo to, range 0° - 300°
     */
    public void setCameraAngle(final double degrees) {
        servo3.setAngle(degrees);
    }

        /**
     * Gets the servo3 angle (Camera)
     * <p>
     * 
     * @param degrees degree to set the servo to, range 0° - 300°
     */
    public double getCameraAngle() {
        return servo3.getAngle();
    }




    /**
     * Sets the servo2 angle (gripper)
     * <p>
     * 
     * @param degrees degree to set the servo to, range 0° - 300°
     */
    public void setGripperAngle(final double degrees) {
        servo2.setAngle(degrees);
    }
    /**
     * Returns the servo2 angle (Gripper)
     * <p>
     */
    public double getGripperAngle() {
        return servo2.getAngle();
    }
    /**
     * Get the arm tip x position
     * <p>
     * 
     * @param : none
     */
    public double getArmPosX( ) {
        return m_x;
    }

    public double getArmPosY( ) {
        return m_y;
    }
    /**
     * Sets the arm A, B angles
     * <p>
     * 
     * @param A - shoulder angle
     * @param B - elbow angle
     * 
     */
    public void setArmAngleAB(double A, double B) {
        m_A = A;
        m_B = B;
        pidInput3 = m_A - offset0;
        servo1.setAngle(m_B*ratio1 + offset1);    
        setArmPosGivenAngle(A, B);
    }
    public void setArmPosGivenAngle(double A, double B) {
        // elbow position
        double x0 = a1 * Math.cos(Math.toRadians(A));
        double y0 = a1 * Math.sin(Math.toRadians(A));
        // wrist position
        m_x = x0 + a2 * Math.cos(Math.toRadians(A-B));
        m_y = y0 + a2 * Math.sin(Math.toRadians(A-B));
    }
    public Translation2d getArmPos(){
        return new Translation2d(m_x, m_y);
    } 

    public void setArmPos(Translation2d pos) {
        setArmPos(pos.getX(), pos.getY());
    }
    public void LimitArmXY() {
        double a = a2;
        double c = a1;
        double dist = Math.sqrt(m_x*m_x + m_y*m_y);
        double maxDist = a+c;
        if (dist>=(maxDist-0.02)) {
            dist = maxDist-0.02;
            double angle = Math.atan2(m_y, m_x);
            m_x = Math.cos(angle) * dist;
            m_y = Math.sin(angle) * dist;
        }

    }
    /**
     * Sets the arm tip (x,y) position
     * <p>
     * 
     * @param pos (x,y) position of arm tip
     */
    public void setArmPos(double x, double y ) {

        //Refer to https://www.alanzucconi.com/2018/05/02/ik-2d-1/
        //The angle convention A & B follows the website

        // arm tip  cannot be physically in the area around origin
        if ( (y<0.1) && (x<0.1)  ) {
            //x = 0.1;
        }
        m_x = x;
        m_y = y;
        LimitArmXY();

        double a = a2;
        double c = a1;
        double b = Math.sqrt(x*x+y*y);
        double alpha = Math.acos( (b*b + c*c - a*a)/(2*b*c) );
        double beta = Math.acos( (a*a + c*c - b*b)/(2*a*c) );

        // A is DC motor angle wrt horizon
        // When A is zero, arm-c is horizontal.
        // B is servo1 angle wrt arm-c (BA)
        // When B is zero, arm-a is opened parallel  to arm-c
        m_B = Math.PI - beta;    
        m_A = alpha + Math.atan2(y,x);

        //servo0 and servo1 might be mounted clockwise or anti clockwise.
        //offset0 and offset1 are used to adjust the zero the arm position.
        //This makes it easier to mount and tune the arm.
        m_A = (Math.toDegrees(m_A) ); 
        m_B = (Math.toDegrees(m_B) ); 

        pidInput3 = (m_A - offset0);
        pidInPipe[PIPESIZE-1] = pidInput3;
        servo1.setAngle(m_B*ratio1 + offset1);    //

        //D_debug1.setDouble(A);
        //D_debug2.setDouble(B);

    }

    public void setArmAInc(double dA ) {
        m_A+=dA;
        pidInput3 = m_A - offset0;
    }
    public void setArmPosInc(double dx, double dy ) {
        m_x += dx;
        m_y += dy;
        setArmPos(m_x, m_y);
    }
    public void doPID( ){
        pidOutput3 = pidController3.calculate(pidInput3, encoder3.getDistance()/ratio0);
    }
    /**
     * Code that runs once every robot loop
     */
    @Override
    public void periodic()
    {
        if (!Constants.PID_THREAD_ARM ) {
            double in = pidInPipe[0];
            for (int i=0; i<PIPESIZE-1; i++) {
                pidInPipe[i] = pidInPipe[i+1];
            }
            pidOutput3 = pidController3.calculate(-encoder3.getDistance()/ratio0, in);
        // pidOutput3 = pidController3.calculate(encoder3.getEncoderDistance(), pidInput3);
        }
        motor3.set(pidOutput3);
        offset0 = D_offset0.getDouble(offset0);
        offset1 = D_offset1.getDouble(offset1);

        //Unnecessary display should be removed during contest
        
        pidController3.setP(D_slider_P.getDouble(default_P));
        pidController3.setD(D_slider_D.getDouble(default_D));
        pidController3.setI(D_slider_I.getDouble(default_I));
        D_arm_A.setDouble(m_A);
        D_enc3.setDouble(-encoder3.getDistance()/ratio0);
        D_in3.setDouble(pidInput3);
        D_out3.setDouble(pidOutput3);
        D_posX.setDouble(m_x);
        D_posY.setDouble(m_y);

    }
}
