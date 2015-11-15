package Obstacle;

public class Missile extends Obstacle {
    public Missile(int x, int y) {
        super(x, y);
        initMissile();
    }

    private void initMissile() {

        loadImage("images/Rocket.gif");
        getImageDimensions();
    }

    @Override
    public void move() {
        x -= 5;

        if (x < 10) {
            vis = false;
        }
    }
}