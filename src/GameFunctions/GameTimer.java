package GameFunctions;

import BirdMemento.Bird;
import Obstacle.Observer;

public class GameTimer extends Observer {
    private int time;
    private int minute;
    private int second;
    private int countSecond;
    private Thread timerThread;

    public GameTimer(Bird bird) {
        this.bird = bird;
        this.bird.attach(this);
        // Start thread to begin the stop watch.
        timerThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                    while (true) {
                        // Increase number of seconds.
                        increaseTime();
                        countSecond++;
                        sleep(1000);
                    }
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
        };
        timerThread.start();
    }

    public int getMinute() {
        return minute;
    }

    public int getCountSecond() {
        return countSecond;
    }

    public int getSecond() {
        return second;
    }

    public int getTime() { return time; }

    private void increaseTime() {
        time++;
        // Get the minute part.
        minute = time / 60;
        // Get the second part.
        second = time % 60;
    }

    @Override
    public void update() {
        // Stop the stop watch.
        timerThread.stop();
    }

    public void pause() {
        timerThread.suspend();
    }

    public void resume() {
        timerThread.resume();
    }

    public void gameOver() {

    }
}
