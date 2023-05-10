package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
//import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Globals;



/**
 * LoopCmd class
 * <p>
 * This class creates the Repeat command features
 */
public class LoopCmd extends CommandBase
{
    protected boolean m_endFlag;
    protected CommandBase m_cmd;
    protected SequentialCommandGroup cmd;
    private final end_func fn_ptr;

    // An interface is an abstract class. All methods are not defined.
    // This interface defines a method that check for the end condition to terminate this command
    interface end_func {
        public boolean endCondition();
    }

    /***
     * Sets one of the speeds of the robot
     * <p>  
     * @param cmdToRun - SequentialCommandGroup
     * @param fn - function that defines end condition for the loop
     */
	public LoopCmd(CommandBase cmdToRun, end_func fn)
    {
        m_cmd = cmdToRun;
        fn_ptr = fn;

    }

    public LoopCmd(SequentialCommandGroup cmdToRun) {
        m_cmd = cmdToRun;
        fn_ptr = null;
        
    }
    @Override
    public void initialize()
    {
        m_endFlag = false;

        Globals.debug[0]=Globals.debug[1]=Globals.debug[2]=0;
    }
    @Override
    public void execute()
    {

        if (m_cmd.isScheduled() == false) {
            //End condition for loopCmd
            if (fn_ptr.endCondition()) {
                m_endFlag = true;
            }
            else {
                //schedule command
                m_cmd.schedule(false);
                Globals.debug[0]++;
            }
        }

    }

    @Override
    public boolean isFinished()
    {
        if (m_endFlag)
            Globals.debug[2] = -1;
        return m_endFlag;
    }
    @Override
    public void end(boolean interrupted)
    {
        Globals.debug[1] = -1;
    }
}
