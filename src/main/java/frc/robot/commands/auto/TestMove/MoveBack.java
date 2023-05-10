package frc.robot.commands.auto.TestMove;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.auto.Lib.MoveRobot;


/**
 * DriveMotor class
 * <p>
 * This class creates the inline auto command to drive the motor
 */
public class MoveBack extends SequentialCommandGroup
{
    public MoveBack()
    {
        super(
            new MoveRobot(2, 1.2, 0, 0, 1.4),
            new WaitCommand(0.1),
            new MoveRobot(2, -1.2, 0, 0, 1.4),
            new WaitCommand(0.1)
            );
    }
}
