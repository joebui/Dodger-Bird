package Obstacle;

public class Missile extends Obstacle {
    public Missile(int x, int y) {
        super(x, y);
        initObstacle();
    }

    @Override
    public void initObstacle() {
        loadImage("images/Rocket.gif");
        getImageDimensions();
    }

    @Override
    public void move() {
        // speed of missile.
        x -= 5;

        // missile goes over the window border.
        if (x < 10) {
            isVisible = false;
        }
    }
}