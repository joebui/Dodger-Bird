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
    private ArrayList<Spikes> spikes;
    private ArrayList<Flame> flames;
    private Thread firingSpikes;
    private Thread firingMissiles;
    private Thread firingFlames;
    private ObstacleFactory factory;
    private GameTimer gameTimer;
    private int pastMin;
    private int spikeSpeed;
    private int missileSpeed;
    private int x;
    private boolean isPause;
    
    public Window() {
        // keyboard listener.
        pastMin = 0;
        spikeSpeed = 3;
        missileSpeed = 5;
        x = 1024;
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                bird.keyPressed(e);

                int key = e.getExtendedKeyCode();
                // Pause game.
                if (key == KeyEvent.VK_ESCAPE) {
                    if (!isPause)
                        pause();
                    else
                        resume();
                }
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
        spikes = new ArrayList<>();
        flames = new ArrayList<>();
        factory = new ObstacleFactory();
        gameTimer = new GameTimer(bird);
        fireMissile();
        fireSpike();
        fireFlame();
        Timer timer = new Timer(10, this);
        timer.start();
        music();
    }

    // Display the objects on screen.
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // Add scrolling background image.
        if (x < 0)
            x = 1024;

        g.drawImage(new ImageIcon("images/background.png").getImage(), x - 1024, 0, 1024, 720, this);
        g.drawImage(new ImageIcon("images/background.png").getImage(), x, 0, 1024, 720, this);
        if (!isPause)
            x--;

        if (bird.isVisible()) {
            // Display timer on screen.
            g.setFont(new Font("Times New Roman", Font.BOLD, 20));
            StringBuilder builder = new StringBuilder();
            builder.append("Time - ");
            builder.append(gameTimer.getMinute());
            builder.append(":");
            builder.append(gameTimer.getSecond());
            g.drawString(String.valueOf(builder.toString()), 460, 20);

            // Display obstacles and bird.
            g2d.drawImage(bird.getImage(), bird.getX(), bird.getY(), this);

            for (Missile m : missiles) {
                g2d.drawImage(m.getImage(), m.getX(), m.getY(), this);
            }

            for (Spikes w : spikes) {
                g2d.drawImage(w.getImage(), w.getX(), w.getY(), this);
            }

            for (Flame f : flames) {
                g2d.drawImage(f.getImage(), f.getX(), f.getY(), this);
            }
        }

        Toolkit.getDefaultToolkit().sync();
    }

    // This method will be called recursively to update the object.
    @Override
    public void actionPerformed(ActionEvent e) {
        if (bird.isVisible() && !isPause) {
            updateMissiles();
            updateBird();
            updateSpike();
            updateFlame();
            checkCollision();
        }

        if (gameTimer.getMinute() > pastMin) {
            spikeSpeed += 1;
            missileSpeed += 1;
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

    private void updateSpike() {
        for (int i = 0; i < spikes.size(); i++) {
            Spikes w = spikes.get(i);
            if (w.isVisible()) {
                w.move();
            } else {
                spikes.remove(i);
            }
        }
    }

    private void updateFlame() {
        for (int i = 0; i < flames.size(); i++) {
            Flame w = flames.get(i);
            if (w.isVisible()) {
                w.move();
            } else {
                flames.remove(i);
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
                break;
            }
        }

        for (Spikes w : spikes) {
            Rectangle wr = w.getBound();
            if (birdBound.intersects(wr)) {
                // Make bird disappear and notify other objects in game.
                bird.setVisible(false);
                stopSpawning();
                break;
            }
        }

        for (Flame f : flames) {
            Rectangle fr = f.getBound();
            if (birdBound.intersects(fr)) {
                // Make bird disappear and notify other objects in game.
                bird.setVisible(false);
                stopSpawning();
                break;
            }
        }
    }

    private void fireMissile() {
        firingMissiles = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                    while (true) {
                        sleep(10000);
                        // Play rocket shooting sound.
                        rocketSound();
                        // Fire missile
                        Obstacle missile = factory.getObstacle("missile", 1024, bird.getY() + bird.getHeight() / 8, missileSpeed);
                        missiles.add((Missile) missile);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        };

        firingMissiles.start();
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

    private void fireSpike() {
        firingSpikes = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                    while (true) {
                            // Spawn wheel
                        Obstacle wheel = factory.getObstacle("spike", 1000, new Random().nextInt(610) + 10, spikeSpeed);
                        spikes.add((Spikes) wheel);
                        sleep(1000);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        };

        firingSpikes.start();
    }

    private void fireFlame() {
        firingFlames = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                    while (true) {
                        // Spawn wheel
                        Obstacle flame = factory.getObstacle("flame", 1000, bird.getY() + bird.getHeight() / 8, spikeSpeed);
                        flames.add((Flame) flame);
                        sleep(1000);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        };

        firingFlames.start();
    }

    private void stopSpawning() {
        // Stop the thread firing missiles and spawning wheels.
        missiles.clear();
        spikes.clear();
        flames.clear();
        firingMissiles.stop();
        firingSpikes.stop();
        firingFlames.stop();
    }

    private void pause() {
        isPause = true;
        firingMissiles.suspend();
        firingSpikes.suspend();
        firingFlames.suspend();
        gameTimer.pause();
    }

    private void resume() {
        isPause = false;
        firingMissiles.resume();
        firingSpikes.resume();
        firingFlames.resume();
        gameTimer.resume();
    }
}