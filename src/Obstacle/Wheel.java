package Obstacle;

public class Wheel extends Obstacle {
    public Wheel(int x, int y) {
        super(x, y);
        initObstacle();
    }

    @Override
    public void move() {
        // speed of the wheel.
        x -= 3;

        // wheel goes over the window border.
        if (x < 10) {
            isVisible = false;
        }
    }

    @Override
    public void initObstacle() {
        loadImage("images/Obstacle.gif");
        getImageDimensions();
    }
}
