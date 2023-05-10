package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Globals;
import frc.robot.RobotContainer;
import frc.robot.commands.auto.Lib.MoveArm;
import frc.robot.subsystems.Arm;



/**
 * DriveMotor class
 * <p>
 * This class creates the inline auto command to drive the motor
 */
public class ViewPickupBin extends SequentialCommandGroup
{   
    
    private final static Arm m_arm = RobotContainer.m_arm;

	public ViewPickupBin() 
    {
         
        super(
        
        new MoveArm(0.24,0.1, 0.5),
        new InstantCommand(()-> m_arm.setCameraAngle(260)),
        new InstantCommand(() -> Globals.cvMode = 1),
        new WaitCommand(3),
        new InstantCommand(() -> Globals.cvMode = -1)       //Set vision to no processing 
        );
    }
   
}
