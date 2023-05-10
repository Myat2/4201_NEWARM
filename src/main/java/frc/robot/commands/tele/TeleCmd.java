package frc.robot.commands.tele;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.OmniDrive;

//This command will be run during teleop mode
public class TeleCmd extends CommandBase
{
    /**
     * Bring in Subsystem and Gamepad code
     */
    private final OmniDrive m_omnidrive;
    private final Arm m_arm;
    private final OI m_oi;

    /**
     * Constructor
     */
    public TeleCmd(OmniDrive omnidrive, OI oi, Arm arm)
    {
        m_omnidrive = RobotContainer.m_omnidrive;
        m_oi = RobotContainer.m_oi;
        m_arm = RobotContainer.m_arm;
        addRequirements(m_omnidrive); //add the drive subsystem as a requirement 
		//addRequirements(m_menu); 
    }

    /**
     * Code here will run once when the command is called for the first time
     */
    @Override
    public void initialize()
    {

    }

    /**
     * Code here will run continously every robot loop until the command is stopped
     */
    @Override
    public void execute()
    {
        /**
         * Get Joystick data
         */
        //Right stick for X-Y control
        //Left stick for W (rotational) control
        double x = m_oi.getRightDriveX();
        double y = -m_oi.getRightDriveY();//Down is positive. Need to negate
        double w = -m_oi.getLeftDriveX(); //X-positive is CW. Need to negate

        //Get other buttons?

        //Add code here to control servo motor etc.

        //m_omnidrive.setMotorOut012(s0,s1,s2);
        // m_arm.setGripperAngle( x*300);
        // m_arm.setCameraAngle( y*300);
        //m_arm.setServoAngle0( m_arm.getSliderOffset0());
        //m_arm.setServoAngle1( m_arm.getSliderOffset1());
//        Translation2d pos = new Translation2d(0.25, 0.25); //90,90
 //     Translation2d pos = new Translation2d(0.25, 0); //60,120
 //Translation2d pos = new Translation2d(0.25/Math.sqrt(2)+0.25, 0.25/Math.sqrt(2));//45,45
        //m_arm.setArmPos(m_arm.getSliderX(), m_arm.getSliderY());
        // m_omnidrive.setRobotSpeedXYW(x*0.4, y*0.4, w*Math.PI/2);
        m_arm.setArmPosInc(x * 0.001, y * 0.001);
        // m_arm.setArmAInc(x * 0.02);

    }

    /**
     * When the comamnd is stopped or interrupted this code is run
     * <p>
     * Good place to stop motors in case of an error
     */
    @Override
    public void end(boolean interrupted)
    {
        m_omnidrive.setMotorOut012(0, 0, 0);
    }

    /**
     * Check to see if command is finished
     */
    @Override
    public boolean isFinished()
    {
        return false;
    }
}