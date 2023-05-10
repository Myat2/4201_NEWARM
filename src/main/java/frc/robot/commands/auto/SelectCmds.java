package frc.robot.commands.auto;

import java.util.Map;
import edu.wpi.first.wpilibj2.command.SelectCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.commands.auto.Lib.MoveRobot;
// import the commands
import frc.robot.commands.auto.TestMove.MoveBack;
import frc.robot.commands.auto.TestMove.MoveLeft;
import frc.robot.commands.auto.TestMove.MoveRight;


/**
 * SelectCmds class
 * <p>
 * This class uses SelectCommand to select 1 of 3 commands to execute
 */
public class SelectCmds extends SequentialCommandGroup
{

    static public int selectCmd123() {
        if (RobotContainer.m_sensor.getIRDistance()<30)
            return 1;
        else if (RobotContainer.m_sensor.getIRDistance()<50)
            return 2;
        else
            return 3;
    }

    // Use limit switch to select
    static public boolean selectCmd12_SW() {
        return RobotContainer.m_sensor.getSwitch();
    }
    // use IR to select
    static public boolean selectCmd12_IR() {
        return RobotContainer.m_sensor.getIRDistance()>30?true:false;
    }
	public SelectCmds()
    {

        super(
             new MoveRobot(1, 0.5, 0, 0, 0.4),  

            //Use this when there are only two options
            //Select one of two commands
            //new ConditionalCommand( new MoveLeft(), new MoveRight(),  MoveTest::selectCmd12_R ) 
            
            //Select one of many commands
            //Selection command in selectCmd123
            new SelectCommand(
                Map.ofEntries(
                    Map.entry(1, new MoveBack()),
                    Map.entry(2, new MoveLeft()),
                    Map.entry(3, new MoveRight()) ),
                    SelectCmds::selectCmd123
            ) 
        );
    }
}
