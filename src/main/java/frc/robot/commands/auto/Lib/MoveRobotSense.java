package frc.robot.commands.auto.Lib;


/**
 * SimpleDrive class
 * <p>
 * This class drives a motor 
 */
public class MoveRobotSense extends MoveRobot
{
    private final end_func fn_ptr;
    // An interface is an abstract class. All methods are not defined.
    // This interface defines a method that check for the end condition to terminate this command
    interface end_func {
        public boolean endCondition();
    }
    
    /**
     * This command moves the robot a certain distance following a trapezoidal speed profile 
     * or terminate early when condition is met.
     * <p>
     * 
     * @param type - 0, 1 or 2 for x, y, or w speed
     * @param dist - distance to move
     * @param startSpeed -  starting speed of robot
     * @param endSpeed - ending speed og robot
     * @param maxSpeed - max speed of robot
     * @param fn - function that defines early end condition
     */
    public MoveRobotSense(int type, double dist, double startSpeed, double endSpeed, double maxSpeed, end_func fn)
    {
        super(type, dist, startSpeed, endSpeed, maxSpeed);
        fn_ptr = fn;
    }

    @Override
    public boolean endCondition()
    {
        return fn_ptr.endCondition();
    }
}