package Obstacle;

import BirdMemento.Bird;

// Observer to notify other objects about bird's status.
public abstract class Observer {
    protected Bird bird;
    public abstract void update();
}
