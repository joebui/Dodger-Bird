package BirdMemento;

import Obstacle.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class Bird {
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean isVisible;
    private Image image;
    private int speed;
    private ArrayList<Observer> observer = new ArrayList<>();

    // Bird class can't be instantiated outside.
    private Bird() {
        x = 40;
        y = 60;
        isVisible = true;
    }

    // Instantiate the only Bird object.
    private static Bird instance = new Bird();

    public static Bird getInstance() {
        return instance;
    }

    public void initObstacle() {
        Image bird;
        Random r = new Random();
        int ranNum = r.nextInt(3);

        // Assign a random bird image.
        if (ranNum == 0)
            bird = new ImageIcon("images/bird1.gif").getImage();
        else if (ranNum == 1)
            bird = new ImageIcon("images/bird2.gif").getImage();
        else
            bird = new ImageIcon("images/bird3.gif").getImage();

        ImageIcon scaled = new ImageIcon(bird.getScaledInstance(60, 50, Image.SCALE_SMOOTH));
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
        if (!visible)
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
        observer.forEach(Observer::update);
    }

    public Memento saveToMemento() {
        return new Memento(x, y, speed, isVisible);
    }

    public void getFromMemento(Memento memento) {
        x = memento.getX();
        y = memento.getY();
        speed = memento.getSpeed();
        isVisible = memento.isVisible();
    }

    // Accept user's input.
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        // Go up.
        if (key == KeyEvent.VK_UP) {
            speed = -3;
        }

        // Go down.
        if (key == KeyEvent.VK_DOWN) {
            speed = 3;
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