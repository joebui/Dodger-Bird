package GameFunctions;

import Obstacle.Bird;
import Obstacle.Observer;

public class GameTimer extends Observer {
    private int time;
    private int minute;
    private int second;
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

    public int getSecond() {
        return second;
    }

    private void increaseTime() {
        time++;
        // Get the minute part.
        minute = time / 60;
        // Get the second part.
        second = time % 60;
    }

    @Override
    public void update() {
        if (!this.bird.isVisible()) {
            // Stop the stop watch.
            timerThread.stop();
        }
    }
}
