package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.RobotContainer;
import frc.robot.commands.auto.Lib.MoveArm;
import frc.robot.subsystems.Arm;






/**
 * DriveMotor class
 * <p>
 * This class creates the inline auto command to drive the motor
 */
public class AlignRobotAndPickItem extends SequentialCommandGroup
{   
    
    double temp;

    private final static Arm m_arm = RobotContainer.m_arm;

	public AlignRobotAndPickItem() 
    {
         
        super(
            
        new AlignPicker(),
        new InstantCommand(()-> m_arm.setCameraAngle(220)),
        new PickItem(),
        new MoveArm(0.33,0.265, 0.5)

        );
    }
    
}
