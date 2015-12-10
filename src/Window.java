import GameFunctions.GameTimer;
import Obstacle.Bird;
import Obstacle.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class Window extends JPanel implements ActionListener {
    private Bird bird;
    private ArrayList<Missile> missiles;
    private ArrayList<Spikes> wheels;
    private Thread firingWheels;
    private Thread firingMissiles;
    private ObstacleFactory factory;
    private GameTimer gameTimer;
    private int pastMin;
    private int spikeSpeed;
    private int missleSpeed;
    public Window() {
        // keyboard listener.
        pastMin = 0;
        spikeSpeed = 3;
        missleSpeed = 5;
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                bird.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                bird.keyReleased(e);
            }
        });

        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);

        // Get the only available Bird object.
        bird = Bird.getInstance();
        missiles = new ArrayList<>();
        wheels = new ArrayList<>();
        factory = new ObstacleFactory();
        gameTimer = new GameTimer(bird);
        fireMissile();
        fireWheel();
        Timer timer = new Timer(10, this);
        timer.start();
        music();
    }

    // Display the objects on screen.
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Add background image.
        g.drawImage(new ImageIcon("images/background.png").getImage(), 0, 0, 1024, 720, this);
        // Display timer on screen.
        if (bird.isVisible()) {
            g.setFont(new Font("Times New Roman", Font.BOLD, 20));
            StringBuilder builder = new StringBuilder();
            builder.append("Time - ");
            builder.append(gameTimer.getMinute());
            builder.append(":");
            builder.append(gameTimer.getSecond());
            g.drawString(String.valueOf(builder.toString()), 460, 20);
        }

        Graphics2D g2d = (Graphics2D) g;

        if (bird.isVisible()) {
            g2d.drawImage(bird.getImage(), bird.getX(), bird.getY(), this);

            for (Missile m : missiles) {
                g2d.drawImage(m.getImage(), m.getX(), m.getY(), this);
            }

            for (Spikes w : wheels) {
                g2d.drawImage(w.getImage(), w.getX(), w.getY(), this);
            }
        }

        Toolkit.getDefaultToolkit().sync();
    }

    // This method will be called recursively to update the object.
    @Override
    public void actionPerformed(ActionEvent e) {
        updateMissiles();
        updateBird();
        updateWheel();
        checkCollision();
        if (gameTimer.getMinute() > pastMin) {
            spikeSpeed += 1;
            missleSpeed += 1;
            pastMin = gameTimer.getMinute();
        }

        repaint();
    }

    private void updateMissiles() {
        for (int i = 0; i < missiles.size(); i++) {
            Missile m = missiles.get(i);
            if (m.isVisible()) {
                m.move();
            } else {
                missiles.remove(i);
            }
        }
    }

    private void updateWheel() {
        for (int i = 0; i < wheels.size(); i++) {
            Spikes w = wheels.get(i);
            if (w.isVisible()) {
                w.move();
            } else {
                wheels.remove(i);
            }
        }
    }

    private void updateBird() {
        bird.move();
    }

    public void checkCollision() {
        Rectangle birdBound = bird.getBound();

        for (Missile m : missiles) {
            Rectangle mr = m.getBound();
            if (birdBound.intersects(mr)) {
                // Make bird disappear and notify other objects in game.
                bird.setVisible(false);
                stopSpawning();
            }
        }

        for (Spikes w : wheels) {
            Rectangle wr = w.getBound();
            if (birdBound.intersects(wr)) {
                // Make bird disappear and notify other objects in game.
                bird.setVisible(false);
                stopSpawning();
            }
        }
    }

    private void fireMissile() {
        firingWheels = new Thread() {
            @Override
            public void run() {
<<<<<<< HEAD
                try {
                    sleep(1000);
                    while (true) {
                            sleep(10000);
                            // Play rocket shooting sound.
                            rocketSound();
                            // Fire missile
                            Obstacle missile = factory.getObstacle("missile", 1024, bird.getY() + bird.getHeight() / 2);
                            missiles.add((Missile) missile);
=======
                while (true) {
                    try {
                        sleep(10000);
                        // Play rocket shooting sound.
                        rocketSound();
                        // Fire missile
                        Obstacle missile = factory.getObstacle("missile", 1024, bird.getY() + bird.getHeight() / 2, missleSpeed);
                        missiles.add((Missile) missile);
                    } catch (Exception e) {
                        System.out.println(e);
>>>>>>> Phuc
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        };

        firingWheels.start();
    }

    private synchronized void rocketSound() {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("sounds/RocketShoot.wav"));
            clip.open(inputStream);
            clip.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private synchronized void music() {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("sounds/BackgroundMusic.wav"));
            clip.open(inputStream);
            // Play background music endlessly.
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch(Exception error)  {
            System.out.println(error.getMessage());
        }
    }

    private void fireWheel() {
        firingMissiles = new Thread() {
            @Override
            public void run() {
<<<<<<< HEAD
                try {
                    sleep(1000);
                    while (true) {
                            // Spawn wheel
                            Obstacle wheel = factory.getObstacle("wheel", 1024, new Random().nextInt(610) + 10);
                            wheels.add((Spikes) wheel);
                            sleep(1000);
=======
                while (true) {
                    try {
                        // Spawn wheel
                        Obstacle wheel = factory.getObstacle("wheel", 1024, new Random().nextInt(610) + 10, spikeSpeed);
                        wheels.add((Spikes) wheel);
                        sleep(1000);
                    } catch (Exception e) {
                        System.out.println(e);
>>>>>>> Phuc
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        };

        firingMissiles.start();
    }

    private void stopSpawning() {
        // Stop the thread firing missiles and spawning wheels.
        firingMissiles.stop();
        firingWheels.stop();
    }
}