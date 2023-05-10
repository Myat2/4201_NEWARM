package frc.robot.commands.auto;

import frc.robot.Globals;
import frc.robot.commands.auto.Lib.MoveRobot;


public class AlignPicker extends MoveRobot {
    //Grab the subsystem instance from RobotContainer

    public AlignPicker(){
        super(0, 0, 0, 0, 0.4 );
        m_startSpeed= 0;  
    }
     /**
     * Runs before execute
     */
    @Override
    public void initialize()
    {   
        super.m_dist = ((Globals.curItemX -400) * Globals.convertPxToM);
       
        super.initialize();
    }
}