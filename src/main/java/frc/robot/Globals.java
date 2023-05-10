package frc.robot;

import edu.wpi.first.wpilibj.geometry.Pose2d;

//Put all global variables here
public class Globals
{
    static public int menuItem;

    static public final int DNUM = 4;
    static public int debug[] = new int[DNUM];
    static public String[] debugNames = new String[] {"debug0", "debug1", "debug2", "debug3"};



    public static double convertPxToM = 0.0006225;
    public static double camera_offset = 0.12;
    public static double camera_mount_offset = 0.015;
    public static double arm_offset_y = 0.13; // 0.125
    public static double arm_offset_z = 0.25;
    public static double gripper_offset = 0.16;

    public static double CokeRatio = 0.8; // actual is 0.805 O.G 0.79 (Tarun Code)
    public static double camera_mount_offset_x = 0.015;//  actual is 1.5cm
    public static int curItemType = 0;
    public static double curItemX;
    public static double curItemY;
    public static int    cvMode=1;
    public static int[][] VisionObjects = new int[3][3];
    public static double totalItemCnt;
    public static Pose2d m_posB;

    public static int PickingCameraAngle = 275;

    public static Pose2d GetPose2d() {
        return m_posB;
    }
    public static boolean OpenHouseLoopCondition(){
        double objects[] = RobotContainer.m_vision.getObjects();
        for (int id=0; id<4; id++) {
            if(objects[id*3]>0){
                Globals.curItemType = id;
                Globals.curItemY = objects[Globals.curItemType*3+2];
                Globals.curItemX = objects[Globals.curItemType*3+1];
                return false;
            }
        }
        return true;
    }
}