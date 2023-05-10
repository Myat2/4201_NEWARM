package frc.robot.Astar;

import java.util.ArrayList;

public class Tile extends Node {

    private int x, y;
    public static int TILE_SIZE = 4;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.setObsValue(0.0);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public void calculateNeighbours(Network network, boolean diagFlag) {
        
        Grid grid = (Grid) network;

        ArrayList<Node> nodes = new ArrayList<>();

        int minX = 0;
        int minY = 0;
        int maxX = grid.getxSize() - 1;
        int maxY = grid.getySize() - 1;

        if (x > minX) {
            nodes.add(grid.find(x - 1, y)); //west
        }

        if (x < maxX) {
            nodes.add(grid.find(x + 1, y)); //east
        }

        if (y > minY) {
            nodes.add(grid.find(x, y - 1)); //north
        }

        if (y < maxY) {
            nodes.add(grid.find(x, y + 1)); //south
        }
        if (diagFlag == true) {
            if (x > minX && y > minY) {
                nodes.add(grid.find(x - 1, y - 1)); // northwest
            }

            if (x < maxX && y < maxY) {
                nodes.add(grid.find(x + 1, y + 1)); // southeast
            }

            if (x < maxX && y > minY) {
                nodes.add(grid.find(x + 1, y - 1)); // northeast
            }

            if (x > minY && y < maxY) {
                nodes.add(grid.find(x - 1, y + 1)); // southwest
            }
        }
        setNeighbours(nodes);

    }

    @Override
    public double heuristic(Node dest) {
        return distanceTo(dest);
    }

    @Override
    public double distanceTo(Node dest) {
        Tile d = (Tile) dest;
        double dx = x-d.x;
        double dy = y-d.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
    @Override
    public double dirTo(Node dest) {
        Tile d = (Tile) dest;
        int dx = d.x - x;
        int dy = d.y - y;
        if (dx==0 && dy==1) return Math.PI/2;
        if (dx==1 && dy==1) return Math.PI/4;
        if (dx==1 && dy==0) return 0;
        if (dx==1 && dy==-1) return -Math.PI/4;
        if (dx==0 && dy==-1) return -Math.PI/2;
        if (dx==-1 && dy==-1) return -Math.PI*3/4;
        if (dx==-1 && dy==0) return Math.PI;
        if (dx==-1 && dy==1) return Math.PI*3/4;
        return 0;
        //return Math.atan2(dy, dx);

    }

}
