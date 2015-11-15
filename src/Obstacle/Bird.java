package Obstacle;

import java.awt.event.KeyEvent;

public class Bird extends Obstacle {
    private int dy;

    public Bird(int x, int y) {
        super(x, y);

        initBird();
    }

    private void initBird() {
        loadImage("images/Bird.gif");
        getImageDimensions();
    }

    @Override
    public void move() {
        y += dy;
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP) {
            dy = -2;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 2;
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }
}