package Obstacle;

import java.awt.event.KeyEvent;

public class Bird extends Obstacle {
    private int speed;

    public Bird(int x, int y) {
        super(x, y);
        initObstacle();
    }

    @Override
    public void initObstacle() {
        loadImage("images/Bird.gif");
        getImageDimensions();
    }

    @Override
    public void move() {
        y += speed;
    }

    // accept user's input.
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP) {
            speed = -2;
        }

        if (key == KeyEvent.VK_DOWN) {
            speed = 2;
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP) {
            speed = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            speed = 0;
        }
    }
}