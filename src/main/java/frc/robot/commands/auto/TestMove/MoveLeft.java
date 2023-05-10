package frc.robot.commands.auto.TestMove;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.auto.Lib.MoveRobot;


/**
 * DriveMotor class
 * <p>
 * This class creates the inline auto command to drive the motor
 */
public class MoveLeft extends SequentialCommandGroup
{
    public MoveLeft()
    {
        super(
            new MoveRobot(0, -0.1, 0, 0.0, 0.5),  
            new MoveRobot(1, -0.1, 0, 0.0, 0.5),
            new MoveRobot(0, 0.1, 0, 0.0, 0.5),  
            new MoveRobot(1, 0.1, 0, 0.0, 0.5)
            );
    }
}
