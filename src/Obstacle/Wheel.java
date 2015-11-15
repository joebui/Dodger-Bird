package Obstacle;

public class Wheel extends Obstacle {
    public Wheel(int x, int y) {
        super(x, y);
        initWheel();
    }

    @Override
    public void move() {
        x -= 3;

        if (x < 10) {
            vis = false;
        }
    }

    private void initWheel() {
        loadImage("images/Obstacle.gif");
        getImageDimensions();
    }
}
