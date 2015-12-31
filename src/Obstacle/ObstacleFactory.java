package Obstacle;

// Use the class to get object of type Obstacle
public class ObstacleFactory {
    public Obstacle getObstacle(String type, int x, int y, int speed) {
        // Get missile object.
        if (type.equalsIgnoreCase("missile")) {
            return new Missile(x, y, speed);
        // Get wheel object.
        } else if (type.equalsIgnoreCase("spike")) {
            return new Spikes(x, y, speed);
        } else if (type.equalsIgnoreCase("flame")) {
            return new Flame(x, y, speed);
        } else {
            return null;
        }
    }
}
