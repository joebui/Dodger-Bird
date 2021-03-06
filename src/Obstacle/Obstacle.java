package Obstacle;

import java.awt.*;
import javax.swing.ImageIcon;

public abstract class Obstacle {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean isVisible;
    protected Image image;
    protected int speed;
    public Obstacle(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        isVisible = true;
    }

    public abstract void move();

    public abstract void initObstacle();

    protected abstract void loadImage(String imageName);

    protected void getImageDimensions() {
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

    public Rectangle getBound() {
        return new Rectangle(x, y, width, height);
    }
}