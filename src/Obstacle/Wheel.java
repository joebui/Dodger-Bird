package Obstacle;

public class Wheel extends Obstacle {
    public Wheel(int x, int y) {
        super(x, y);
        initObstacle();
    }

    @Override
    public void move() {
        x -= 3;

        // Destroy when going out of the border.
        if (x < 1) {
            isVisible = false;
        }
    }

    @Override
    public void initObstacle() {
        loadImage("images/Obstacle.gif");
        getImageDimensions();
    }
}
