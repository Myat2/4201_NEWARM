package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Globals;
import frc.robot.RobotContainer;
import frc.robot.Astar.Layout;
import frc.robot.commands.auto.Lib.MoveArm;
import frc.robot.commands.auto.Lib.MovetoB;
import frc.robot.commands.auto.TestMove.MoveBack;
import frc.robot.subsystems.Arm;


// import the commands

/**
 * Auto mode main class
 * <p>
 * This class creates the auto command to drive the robot during autonomous mode
 */
public class AutoMainCmd extends SequentialCommandGroup
{   

    int count = 0;
    public static int testPos[] = {960, 1100,  -90};
	public AutoMainCmd()
    {
    //    super( 
    //         new ViewPickupBin(),
    //         new LoopCmd(new MoveBack(), ()->Globals.totalItemCnt==0) 
    //    );

        // super (
        //     new MovetoB(Layout.workOrderPos),
        //     new MovetoB(Layout.dispensaryPos),
        //     new MovetoB(Layout.medCubeStandPos[0]),
        //     new MovetoB(Layout.medCubeStandPos[1]),
        //     new MovetoB(Layout.startPos)
        //     );

        super (
            // new ViewItem()
            new MoveArm(0.33,0.24,0.4),
            new WaitCommand(2),
            new MoveArm(0.43,0.24,0.4),
            new WaitCommand(2),
            new MoveArm(0.43,0.14,0.4),
            new WaitCommand(2),
            new MoveArm(0.33,0.14,0.4),
            new WaitCommand(2),
            new MoveArm(0.33,0.24,0.4)
            
            // new MovetoB(Layout.PickupBinPos),
            // new MovetoB(Layout.TestPickupBinPos),
            // new MovetoB(Layout.startPos)

            // new MovetoB(Layout.PickupBinPos2),
            // new MovetoB(()-> RobotContainer.m_Grid.findGotoPos(0.5, 1.6, 0.5)),
            // new MovetoB(()-> RobotContainer.m_Grid.findGotoPos(0.2, 4.32, 0.5)),
            // new MovetoB(()-> RobotContainer.m_Grid.findGotoPos(2.0, 1.85, 0.5)),
            // new MovetoB(Layout.startPos)
        );
        // super(
        //     //new MovetoB(new Pose2d(0.96, 1.1, new Rotation2d(0))),
        //     new InstantCommand( ()->RobotContainer.m_omnidrive.setOdometry(testPos)),
        //     new ViewPickupBin(),
        //     new LoopCmd(new OpenHouseLoop(), ()->Globals.OpenHouseLoopCondition())

        // );
    }
    /**
     * Code here will run once when the command is called for the first time
     */
    @Override
    public void initialize()
    {
        //Must be initialised before super.initialise
        super.initialize();
        //Initialise other stuff here
    }
}
