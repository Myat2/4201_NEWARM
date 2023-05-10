package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.Globals;
import frc.robot.commands.auto.Lib.MoveArm;

public class ViewingPosition extends ParallelCommandGroup{
  public ViewingPosition(){
    super(
      // Lifts Arm
      new MoveArm(0.25,0.37, 0.4),
      new MoveCamera(Globals.PickingCameraAngle)
    );
  }
}
