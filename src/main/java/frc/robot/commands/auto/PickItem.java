package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// import the commands

//WPI imports


/**
 * DriveMotor class
 * <p>
 * This class creates the inline auto command to pick up objects
 */
public class PickItem extends SequentialCommandGroup
{
    
    public PickItem()
    {
      
      super(
        new Gripper(1, 60),
        new MoveArmToPick(0),
        new MoveArmToPick(1),
        new Gripper(0, 60), 
        new MoveGripper(0.4,0.3,0.5)
      

      );
      
    }
}