package frc.robot.Astar;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;

//All units are in metre and radian
//Positions are in Pose2d
public class Layout {
    // Dimension of layout in real unit
    public static final double x_size_m = 2.250;
    public static final double y_size_m = 4.500;
    public static final double tile_size_m = 0.025;
    
    // Robot start position. 
    public static final Pose2d startPos = new Pose2d(0.340, 0.340, new Rotation2d(-Math.PI/2) );

    //List all fixed walls in layout here
    public static final double walls_m[][] = {
        //Boundary
        {0,         0,         x_size_m,     0   }, 
        {x_size_m,  0,         x_size_m,     y_size_m}, 
        {x_size_m,  y_size_m,  0,            y_size_m}, 
        {0,         y_size_m,  0,            0   }, 
        // Other walls

   
    };


    //List all fixed rectangular obstacles in layout here
    public static final double obs_m[][] = {
        //x0    y0      xSize   ySize   Angle
        {1.400, 1.100,  0.300,  0.420,  0 },   //pickup bin
        {1.380, 3.000,  0.300,  0.420,  -Math.PI/4 },   //pickup bin

      
    };
    public static final double obsRound_m[][] = {
        //x,    y,      diameter
        {0.5, 1.6, 0.5}, //red
        {0.200, 4.370 , 0.300}, //green
        {1.100, 4.330,  0.300}  //blue
        // {0.980,  3.910,  0.300} //red
        // {0.290,  4.370,  0.300}, //green
        // {1.860,  3.830,  0.300}  //blue
      
    };
    public static final Pose2d TestPickupBinPos = new Pose2d(0.78, 1.21,  new Rotation2d(-Math.PI/4) );
    public static final Pose2d PickupBinPos = new Pose2d(0.68, 1.11,  new Rotation2d(-Math.PI/2) );
    public static final Pose2d PickupBinPos2 = new Pose2d(0.92, 3.5,  new Rotation2d(-Math.PI*3/4) );
    public static final Pose2d workOrderPos = new Pose2d(1.1, 0.25,  new Rotation2d(-Math.PI/2) );

    //These are initial positions for robots to go to place medicine cube
    //Each row corresponds to a room (room-0, room-1 etc)
    //Robot needs to make final adjustment to align with stand
    public static final Pose2d medCubeStandPos[] = {
        //x, y, angle
        //Robot stops 250mm from stand. To be adjusted
        new Pose2d(1.546-0.185, 50+250,  new Rotation2d(-Math.PI/2)), //Room med cube stand.
        new Pose2d(2.546-0.185, 50+250,  new Rotation2d(-Math.PI/2)), //Room med cube stand.
        new Pose2d(3.546-0.185, 50+250,  new Rotation2d(-Math.PI/2)), //Room med cube stand.
        new Pose2d(3.546+0.185, 2.038-50-250,  new Rotation2d(Math.PI/2)), //Room med cube stand.
        new Pose2d(2.546+0.185, 2.038-50-250,  new Rotation2d(Math.PI/2)), //Room med cube stand.
    };

    //These are initial positions for robots to go to retrieve hazmat cube
    public static final Pose2d hazMatStandPos[] = {
        //x, y, angle
        new Pose2d(0.0, 0.0,  new Rotation2d(0)),
    };

    //These are initial room gurney positions for robots to go to 
    public static final Pose2d roomGurneyPos[] = {
        //x, y, angle
        new Pose2d(0.0, 0.0,  new Rotation2d(0)),
    };

    // Initial Position for robot to go to for dispensary
    public static final Pose2d dispensaryPos = new Pose2d(0.525, 2.038-0.5,  new Rotation2d(Math.PI/2) );

    // Position for robot to go to for reading work order
    // public static final int workOrderPos[] = {950, 2038-500, 90};

    // Initial Position for robot to go to for disposing hazmat
    public static final Pose2d HazMatBinPos = new Pose2d(0.50, 0.98, new Rotation2d(Math.PI) );

    public Layout() {


    }

    /**
   * Convert a point in A* cell to meter
   *
   * @param pt (int metre)
   * @return pt (in cell size)
   */
    static public Translation2d Convert_cell_m(Translation2d pt) {
        Translation2d pt_m = new Translation2d(pt.getX()*tile_size_m, pt.getY()*tile_size_m);
        return pt_m;
    }

    /**
   * Convert from metre to grid cell index
   *
   * @param m length in metre
   * @return Grid cell index
   */
    static public int Convert_m_cell (double m) {
        return (int) Math.round(m/tile_size_m);
    }
    
    public double [][] getWalls() {
        return walls_m;
    }

    public double [][] getObs() {
        return obs_m;
    }

    public double [][] getObsRound() {
        return obsRound_m;
    }
}
