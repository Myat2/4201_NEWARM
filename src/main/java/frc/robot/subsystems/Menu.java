package frc.robot.subsystems;

import java.util.Map;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SelectCommand;
//WPI imports
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Globals;
import frc.robot.commands.auto.Lib.MoveArm;
import frc.robot.commands.auto.Lib.MoveRobot;
import frc.robot.commands.auto.TestMove.MoveBack;
import frc.robot.commands.tele.OI;

public class Menu extends SubsystemBase
{

    private final OI m_oi;

    // Shuffleboard
    private final ShuffleboardTab tab = Shuffleboard.getTab("Menu");
    private final NetworkTableEntry D_button = tab.add("button", -1).getEntry();
    private final NetworkTableEntry D_menu = tab.add("menu", "?").getEntry();
    private NetworkTableEntry D_debug[] = new NetworkTableEntry[Globals.DNUM];
   

    int menuNum=0;
    private final String[] menuName;

    public Menu(final OI oi) {

        for (int i=0; i<Globals.DNUM; i++) {
            D_debug[i] = tab.add(Globals.debugNames[i], -1).getEntry();
        }
        m_oi = oi;
        m_oi.buttonStart.whenPressed(             
            new SelectCommand(
                Map.ofEntries(
                    Map.entry(menuNum++, new MoveArm(0.2, 0.0, 0.5)),
                    Map.entry(menuNum++, new MoveArm(0.2, 0.2, 0.5)),
                    Map.entry(menuNum++, new MoveArm(0.4, 0.2, 0.5)),
                    Map.entry(menuNum++, new MoveArm(0.4, 0.0, 0.5)),
                    Map.entry(menuNum++, new MoveRobot(2, Math.PI/6, 0, 0, Math.PI)),
                    Map.entry(menuNum++, new MoveRobot(2, -Math.PI/6, 0, 0, Math.PI))
                ), ()->Globals.menuItem
            ) 
        );

        menuName = new String[] {
            "task1",
            "task2",
            "task3",
            "task4",
            "task5",
            "task6" 
        };

        //A-up button, Y-down button
        m_oi.buttonA.whenPressed( ()->{Globals.menuItem--;Globals.menuItem=(Globals.menuItem+menuNum)%menuNum;});
        m_oi.buttonY.whenPressed( ()->{Globals.menuItem++;Globals.menuItem%=menuNum;});

        //These commands are for easy testing
        //Can be used for core programming tasks
        tab.add("Task1", new MoveArm(0.2, 0.0, 0.5));
        tab.add("Task2", new MoveArm(0.2, 0.2, 0.5));
        tab.add("Task3", new MoveArm(0.4, 0.2, 0.5));
        tab.add("Task4", new MoveArm(0.4, 0.0, 0.5));
        tab.add("Y4m", new MoveRobot(1, 4, 0, 0, 0.4));
        tab.add("X-4m", new MoveRobot(0, -4, 0, 0, 0.4));
        tab.add("w180", new MoveRobot(2, Math.PI, 0, 0, Math.PI/3));
        tab.add("w-180", new MoveRobot(2, -Math.PI, 0, 0, Math.PI/3));
        tab.add("MoveBack", new MoveBack());
    
    }


    @Override
    public void periodic()
    {
      
        D_menu.setString( menuName[Globals.menuItem]);
        D_button.setNumber(m_oi.getDriveButtons());
        for (int i=0; i<Globals.DNUM; i++) {
            D_debug[i].setNumber(Globals.debug[i]);
        }

    }
}