package Obstacle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

// Apply Singleton pattern to Bird class.
public class Bird {
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean isVisible;
    private Image image;
    private int speed;
    private ArrayList<Observer> observer = new ArrayList<>();

    // Bird class won't be instantiated.
    private Bird(int x, int y) {
        this.x = x;
        this.y = y;
        isVisible = true;
        initObstacle();
    }

    // Instantiate the only Bird object.
    private static Bird instance = new Bird(40, 60);

    public static Bird getInstance() {
        return instance;
    }

    public void initObstacle() {
        Image bird = new ImageIcon("images/Bird.gif").getImage();
        ImageIcon scaled = new ImageIcon(bird.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        image = scaled.getImage();
        getImageDimensions();
    }

    public void move() {
        y += speed;

        // Prevent the bird from going out of the screen border.
        if (y < 10) {
            y = 10;
        } else if (y > 620) {
            y = 620;
        }
    }

    private void getImageDimensions() {
        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        this.isVisible = visible;
        // Notify other object about the new status.
        notifyAllObserver();
    }

    public int getHeight() {
        return height;
    }

    public Rectangle getBound() {
        return new Rectangle(x, y, width, height);
    }

    public void attach(Observer o) {
        observer.add(o);
    }

    public void notifyAllObserver() {
        // Update the connected objects.
        for (Observer o : observer) {
            o.update();
        }
    }

    // Accept user's input.
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        // Go up.
        if (key == KeyEvent.VK_UP) {
            speed = -2;
        }

        // Go down.
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