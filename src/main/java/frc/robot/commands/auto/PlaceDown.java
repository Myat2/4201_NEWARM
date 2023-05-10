package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.auto.Lib.MoveArm;
// import the commands


/**
 * DriveMotor class
 * <p>
 * This class creates the inline auto command to place objects down
 */
public class PlaceDown extends SequentialCommandGroup
{
    
    public PlaceDown()
    {
      
      super(
     
      new MoveArm(0.35,0,0.5),
      new Gripper(1,60),
      new WaitCommand(1),
      new MoveArm(0.35,0,0.5)
      //new Gripper(0,60)
      
      
      );
      
    }
}