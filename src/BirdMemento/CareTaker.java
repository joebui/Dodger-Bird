package BirdMemento;

public class CareTaker {
    private Memento memento;

    // Add a state.
    public void add(Memento state) {
        memento = state;
    }

    // Get a state.
    public Memento get() {
        return memento;
    }
}
