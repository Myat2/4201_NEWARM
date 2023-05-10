package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import frc.robot.Globals;
//RobotContainer import
import frc.robot.RobotContainer;
import frc.robot.commands.auto.Lib.MoveArm;
//Subsystem imports
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Vision;

public class MoveArmToPick extends MoveArm {
    private final static Vision m_vision = RobotContainer.m_vision;
    private final static Arm m_arm = RobotContainer.m_arm;

    
    private double pickUpHeight = 0.00;
    private int m_type;
    public MoveArmToPick(int type){
        super(0, 0, 0.2);
        m_type = type;
    }
     /**
     * Runs before execute
     */
    @Override
    public void initialize() {
        
        double x, y;
        if (m_type==0) {
            x = m_arm.getArmPosX() + Globals.camera_offset - (Globals.curItemY - m_vision.getResolution(1)/2) * Globals.convertPxToM;
            y = m_arm.getArmPosY();
        }
        else {
            x = m_arm.getArmPosX();   
            y = pickUpHeight - Globals.arm_offset_z+ Globals.gripper_offset;
        }
            
        SetTargetPos(x, y);
        super.initialize();
    }
}