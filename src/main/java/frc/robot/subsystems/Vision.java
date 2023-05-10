package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Globals;
import frc.robot.RobotContainer;

public class Vision extends SubsystemBase{
    double[] defaultValue = new double[12];
    private final ShuffleboardTab tab = Shuffleboard.getTab("Vision");
    private NetworkTableInstance inst = NetworkTableInstance.getDefault();
    private NetworkTable visionTable = inst.getTable("Shuffleboard/Vision");

    private final NetworkTableEntry D_Debug = tab.add("Debug", "None").getEntry();



    public final NetworkTableEntry D_targetXArm = tab.add("targetXArm", 0).getEntry();
    private final static Arm m_arm = RobotContainer.m_arm;

    private final NetworkTableEntry D_totalItemCnt = tab.add("TotalItemCnt", 0).getEntry();
    private final NetworkTableEntry D_currentItem = tab.add("CurrentItem", 0).getEntry();
    private final NetworkTableEntry D_currentItemX = tab.add("CurrentItemX", 0).getEntry();
    private final NetworkTableEntry D_currentItemY = tab.add("CurrentItemY", 0).getEntry();
    private final NetworkTableEntry D_AddedArmX = tab.add("AddedArmX", 0).getEntry();
    private final NetworkTableEntry D_AddedRobotX = tab.add("AddedRobotX", 0).getEntry();
    private final NetworkTableEntry D_cvMode = tab.add("cvMode", 0).getEntry();

    // private final NetworkTableEntry D_T1 = tab.add("T1_full", 0).getEntry();
    // private final NetworkTableEntry D_T2 = tab.add("T2_full", 0).getEntry();
    //private final NetworkTableEntry D_Array = tab.addDoubleArray("array", array1).get;
    public Vision(){

        m_arm.setCameraAngle(280); // Look down
    }

    public double [] getLine(){
      double[] line = new double[3];

      line[0] = (SmartDashboard.getNumber("Bl_X",0));
      line[1] = (SmartDashboard.getNumber("Bl_Y",0));
      line[2] = (SmartDashboard.getNumber("Bl_W",0));
      return line;
    }

    

    public double getVerticalLine(){
      return SmartDashboard.getNumber("VerticalLine",0);
    }

    public void getOmega(){
        //Globals.cW = getLine(2);
    }
    
    public double getResolution(int wh){
      double[] dimension = new double[2];

      dimension[0] = 800;//(SmartDashboard.getNumber("imW",0));
      dimension[1] = 600;//(SmartDashboard.getNumber("imH",0));
      return dimension[wh];
    }

    
    public void getWOBItems(){
      double[] defaultValue = new double[1];
      // reads the array passed to the networktable
      double[] WOB = visionTable.getEntry("WOB").getDoubleArray(defaultValue);

      // stores the data in Globals
      // int[][] Targets = new int[3][3];
      // for (int i = 0; i >= Red.length; i++){
      //   Targets[0][i] = (int)(Red[i]);
      //   Targets[1][i] = (int)(Green[i]);
      //   Targets[2][i] = (int)(Blue[i]);
      // }
      //Globals.Targets = Targets;
    }

        
    public double[] getObjects(){
      /*
       * 0 - Dettol Count
       * 1,2 - Dettol X,Y
       * 3 - Jagabee Count
       * 4,5 - Jagabee X,Y 
       * 6 - Coke Count
       * 7,8 - Coke X,Y 
       */
     
      double[] objects = visionTable.getEntry("objects").getDoubleArray(defaultValue);
      
      return objects;
  }
    @Override
    public void periodic()
    {
        //Globals.cW = getLine(2);
        //D_cW.setNumber(Globals.cW);
        //D_targetX.setNumber(getLine(0) - 345);
        D_totalItemCnt.setNumber(Globals.totalItemCnt);
        D_currentItem.setNumber(Globals.curItemType);
        D_currentItemX.setNumber(Globals.curItemX);
        D_currentItemY.setNumber(Globals.curItemY);
        D_AddedRobotX.setNumber(((Globals.curItemX -400) * Globals.convertPxToM));
        D_AddedArmX.setNumber(m_arm.getArmPosX() + Globals.camera_offset - (Globals.curItemY - getResolution(1)/2) * Globals.convertPxToM);
        D_cvMode.setNumber(Globals.cvMode);

        double[] vision_f = visionTable.getEntry("objects").getDoubleArray(defaultValue);

        int[] vision = new int[12];
        for (int i=0; i<vision_f.length; i++) {
          vision[i] = (int)vision_f[i];
        }
        Globals.totalItemCnt = vision[0] + vision[3] + vision[6] + vision[9];
        
        String result = String.format("J=%d: %d, %d D=%d: %d, %d C=%d: %d, %d", 
        vision[0], vision[1], vision[2], vision[3], vision[4], vision[5],vision[6], vision[7], vision[8]);
        D_Debug.setString(result);
    }
}