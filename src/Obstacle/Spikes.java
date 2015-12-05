package Obstacle;

import javax.swing.*;
import java.awt.*;

public class Spikes extends Obstacle {
    public Spikes(int x, int y) {
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
        loadImage("images/wallOfSpikes.gif");
        getImageDimensions();
    }

    @Override
    protected void loadImage(String imageName) {
        Image bird = new ImageIcon(imageName).getImage();
        // Scale image.
        ImageIcon scaled = new ImageIcon(bird.getScaledInstance(50, 90, Image.SCALE_SMOOTH));
        image = scaled.getImage();
    }
}
