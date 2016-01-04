import GameFunctions.*;
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

public class GameScreen extends JPanel implements ActionListener {
    private Bird bird;
    private ArrayList<Missile> missiles;
    private ArrayList<Spikes> spikes;
    private ArrayList<Flame> flames;
    private Thread firingSpikes;
    private Thread firingMissiles;
    private Thread firingFlames;
    private ObstacleFactory factory;
    private GameTimer gameTimer;
    private GameOver gameOver;
    private int pastSecond;
    private int spikeSpeed;
    private int missileSpeed;
    private int x;
    private boolean isPause;
    
    public GameScreen(Bird bird, GameOver gameOver) {
        // Get the only available Bird object.
        this.bird = bird;
        this.gameOver = gameOver;
        // keyboard listener.
        pastSecond = 30;
        spikeSpeed = 3;
        missileSpeed = 5;
        x = 1024;

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (!isPause)
                    bird.keyPressed(e);

                int key = e.getExtendedKeyCode();
                // Pause game.
                if (key == KeyEvent.VK_ESCAPE) {
                    if (!isPause) {
                        pause();
                        JOptionPane.showMessageDialog(null, "Game is paused. Press Enter then Esc to resume");
                    } else {
                        resume();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (!isPause)
                    bird.keyReleased(e);
            }
        });

        setBackground(Color.BLACK);

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

            Missile[] missile = missiles.toArray(new Missile[missiles.size()]);
            for (Missile m : missile) {
                g2d.drawImage(m.getImage(), m.getX(), m.getY(), this);
            }

            Spikes[] spike = spikes.toArray(new Spikes[spikes.size()]);
            for (Spikes w : spike) {
                g2d.drawImage(w.getImage(), w.getX(), w.getY(), this);
            }

            Flame[] flame = flames.toArray(new Flame[flames.size()]);
            for (Flame f : flame) {
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

            gameOver.updateTime(gameTimer.getMinute(), gameTimer.getSecond());
        } else {

        }

        if (gameTimer.getCountSecond() > pastSecond) {
            spikeSpeed += 1;
            missileSpeed += 1;
            pastSecond += 30;
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
                    while (true) {
                        sleep(1);
                        if (!isPause && gameTimer.getTime() % 10 == 0 && gameTimer.getTime() != 0) {
                            // Play rocket shooting sound.
                            rocketSound();
                            // Fire missile
                            Obstacle missile = factory.getObstacle("missile", 1024, bird.getY() + bird.getHeight() / 8, missileSpeed);
                            missiles.add((Missile) missile);
                            sleep(1000);
                        }
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
                        // Spawn spike
                        if (!isPause) {
                            Obstacle wheel = factory.getObstacle("spike", 1000, new Random().nextInt(610) + 10, spikeSpeed);
                            spikes.add((Spikes) wheel);
                        }
                        sleep(2000);
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
                    sleep(1500);
                    while (true) {
                        // Spawn flame
                        if (!isPause) {
                            Obstacle flame = factory.getObstacle("flame", 1000, bird.getY() + bird.getHeight() / 8, spikeSpeed);
                            flames.add((Flame) flame);
                        }
                        sleep(2000);
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
        gameTimer.pause();
    }

    private void resume() {
        isPause = false;
        gameTimer.resume();
    }
}