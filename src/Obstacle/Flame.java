package Obstacle;

import javax.swing.*;
import java.awt.*;

public class Flame extends Obstacle {
    private Thread sprites;

    public Flame(int x, int y, int speed) {
        super(x, y, speed);
        initObstacle();
    }

    @Override
    public void move() {
        x -= speed;

        // Destroy when going out of the border.
        if (x < 0) {
            isVisible = false;
        }
    }

    @Override
    public void initObstacle() {
        // Create flame sprite.
        sprites = new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        loadImage("images/flame-sprite/flame1.png");
                        getImageDimensions();
                        sleep(100);
                        loadImage("images/flame-sprite/flame2.png");
                        getImageDimensions();
                        sleep(100);
                        loadImage("images/flame-sprite/flame3.png");
                        getImageDimensions();
                        sleep(100);
                        loadImage("images/flame-sprite/flame4.png");
                        getImageDimensions();
                        sleep(100);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        sprites.start();
    }

    @Override
    protected void loadImage(String imageName) {
        Image bird = new ImageIcon(imageName).getImage();
        // Scale image.
        ImageIcon scaled = new ImageIcon(bird.getScaledInstance(60, 90, Image.SCALE_SMOOTH));
        image = scaled.getImage();
    }

    public Thread getSprites() {
        return sprites;
    }
}
