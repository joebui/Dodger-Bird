package Obstacle;

public class Wheel extends Obstacle {
    public Wheel(int x, int y) {
        super(x, y);
        initWheel();
    }

    @Override
    public void move() {
        // speed of the wheel.
        x -= 3;

        // wheel goes over the window border.
        if (x < 10) {
            vis = false;
        }
    }

    private void initWheel() {
        loadImage("images/Obstacle.gif");
        getImageDimensions();
    }
}
