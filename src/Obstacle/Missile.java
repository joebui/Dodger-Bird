package Obstacle;

import javax.swing.*;
import java.awt.*;

public class Missile extends Obstacle {
    public Missile(int x, int y, int speed) {
        super(x, y, speed);
        initObstacle();
    }

    @Override
    public void initObstacle() {
        loadImage("images/Rocket.gif");
        getImageDimensions();
    }

    @Override
    public void move() {
        x -= speed; //x = x-5

        // Destroy when going out of the border.
        if (x < 1) {
            isVisible = false;
        }
    }

    @Override
    protected void loadImage(String imageName) {
        Image bird = new ImageIcon(imageName).getImage();
        // Scale image.
        ImageIcon scaled = new ImageIcon(bird.getScaledInstance(60, 60, Image.SCALE_SMOOTH));
        image = scaled.getImage();
    }
}