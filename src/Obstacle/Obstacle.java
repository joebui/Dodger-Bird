package Obstacle;

import java.awt.*;
import javax.swing.ImageIcon;

public abstract class Obstacle {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean vis;
    protected Image image;

    public Obstacle(int x, int y) {

        this.x = x;
        this.y = y;
        vis = true;
    }

    public abstract void move();

    protected void loadImage(String imageName) {

        Image bird = new ImageIcon(imageName).getImage();
        ImageIcon scaled = new ImageIcon(bird.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        image = scaled.getImage();
    }

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
        return vis;
    }

    public void setVisible(Boolean visible) {
        vis = visible;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rectangle getBound() {
        return new Rectangle(x, y, width, height);
    }
}