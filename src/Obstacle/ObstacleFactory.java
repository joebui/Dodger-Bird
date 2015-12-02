package Obstacle;

// Use the class to get object of type Obstacle
public class ObstacleFactory {
    public Obstacle getObstacle(String type, int x, int y) {
        // Get missile object.
        if (type.equalsIgnoreCase("missile")) {
            return new Missile(x, y);
        // Get wheel object.
        } else if (type.equalsIgnoreCase("wheel")) {
            return new Wheel(x, y);
        } else {
            return null;
        }
    }
}
