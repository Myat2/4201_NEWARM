package frc.robot.commands.auto;

import frc.robot.RobotContainer;
import frc.robot.commands.auto.Lib.MoveArm;
import frc.robot.commands.auto.Lib.MovetoB;
// import the commands
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Arm;

public class OpenHouseLoop extends SequentialCommandGroup {

  private final static Arm m_arm = RobotContainer.m_arm;
  public OpenHouseLoop(){
    super(  
      
        new AlignRobotAndPickItem(),
        new InstantCommand(()-> m_arm.setCameraAngle(280)),
        new MovetoB(new Pose2d(0.96, 1.5, new Rotation2d(0))),
        new PlaceDown(),
        new MoveArm(0.33, 0.24, 0.5),
        new MovetoB(new Pose2d(0.96, 1.1, new Rotation2d(0))),
        new ViewPickupBin()
    );
  }
}