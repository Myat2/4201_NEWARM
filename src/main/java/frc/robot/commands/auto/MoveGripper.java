package frc.robot.commands.auto;

import frc.robot.Globals;
//RobotContainer import
import frc.robot.commands.auto.Lib.MoveArm;

public class MoveGripper extends MoveArm{

    public MoveGripper(double x, double y,double maxSpeed){
        super(x, y, maxSpeed);
        SetTargetPos(x-Globals.arm_offset_y, y-Globals.arm_offset_z+Globals.gripper_offset);
        
    }
     /**
     * Runs before execute
     */
    
}