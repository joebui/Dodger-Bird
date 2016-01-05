package BirdMemento;

public class Memento {
    private int x, y, speed;
    private boolean isVisible;

    public Memento(int x, int y, int speed, boolean isVisible) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.isVisible = isVisible;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isVisible() {
        return isVisible;
    }
}
