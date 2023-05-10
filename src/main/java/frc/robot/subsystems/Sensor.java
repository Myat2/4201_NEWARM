package frc.robot.subsystems;

//Java imports

//Vendor imports

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
//WPI imports
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Sensor extends SubsystemBase
{
    //Creates all necessary hardware interface here for sensors
    //For servo testing also????

    // Sensors
    private final DigitalInput input0, input1;
    private final AnalogInput IRsensor0;
    //private final Cobra cobra;
    private int i;


    // Good for debugging
    // Shuffleboard
    private final ShuffleboardTab tab = Shuffleboard.getTab("Sensors");
    private final NetworkTableEntry D_input0Disp = tab.add("input0", false).getEntry();
    private final NetworkTableEntry D_input1Disp = tab.add("input1", false).getEntry();
    private final NetworkTableEntry D_cntDisp = tab.add("cntDisp", 0).getEntry();
    private final NetworkTableEntry D_IRsensor = tab.add("IRsen", 0).getEntry();

    //Subsystem for sensors
    //This is just an example.
    public Sensor() {
        input0 = new DigitalInput(Constants.INPUT0);
        input1 = new DigitalInput(Constants.INPUT1);
        IRsensor0 = new AnalogInput(0);
        //cobra = new Cobra();
    }

    /**
     * Get the switch status
     * <p>
     * 
     * @return true or false
     */
    public Boolean getSwitch() {
        return input0.get();
    }



    /**
     * Call for the raw ADC value
     * <p>
     * 
     * @param channel range 0 - 3 (matches what is on the adc)
     * @return value between 0 and 2047 (11-bit)
     */
    public int getCobraRawValue(final int channel) {
        return 0;
    }


    /**
     * Call for the distance measured by the Sharp IR Sensor
     * <p>
     * 
     * @return value between 0 - 100 (valid data range is 10cm - 80cm)
     */
    public double getIRDistance() {
        return (Math.pow(IRsensor0.getAverageVoltage(), -1.2045)) * 27.726;
    }
   
    /**
     * Code that runs once every robot loop
     */
    @Override
    public void periodic()
    {
        //Display on shuffleboard
        //These display is good for debugging but may slow system down.
        //Good to remove unnecessary display during competition
        i++;
        D_input0Disp.setBoolean(input0.get());
        D_input1Disp.setBoolean(input1.get());
        D_cntDisp.setNumber(i);
        D_IRsensor.setNumber(getIRDistance());
        // System.out.println("Start Time: " + Timer.getFPGATimestamp() + "s");
        // System.out.println("Cobra 0: " + cobra.getRawValue(0));
        // System.out.println("Cobra 1: " + cobra.getRawValue(1));
        // System.out.println("Cobra 2: " + cobra.getRawValue(2));
        // System.out.println("Cobra 3: " + cobra.getRawValue(3));
        // System.out.println("End Time: " + Timer.getFPGATimestamp() + "s");
        
    }
}