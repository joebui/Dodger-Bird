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
        x -= 5;

        // Destroy when going out of the border.
        if (x < 1) {
            isVisible = false;
        }
    }
}