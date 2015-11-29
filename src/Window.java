import GameFunctions.GameTimer;
import Obstacle.Bird;
import Obstacle.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Window extends JPanel implements ActionListener {
    private Bird bird;
    private ArrayList<Missile> missiles;
    private ArrayList<Wheel> wheels;
    private Thread firingWheels;
    private Thread firingMissiles;
    private ObstacleFactory factory;
    private GameTimer gameTimer;

    public Window() {
        // keyboard listener.
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

            for (Wheel w : wheels) {
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
            Wheel w = wheels.get(i);

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

        for (Wheel w : wheels) {
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
                while (true) {
                    try {
                        // Get missile object.
                        Obstacle missile = factory.getObstacle("missile", 1024, bird.getY() + bird.getHeight() / 2);
                        missiles.add((Missile) missile);
                        sleep(10000);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        };

        firingWheels.start();
    }

    private void fireWheel() {
        firingMissiles = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        // Get wheel object.
                        Obstacle wheel = factory.getObstacle("wheel", 1024, new Random().nextInt(610) + 10);
                        wheels.add((Wheel) wheel);
                        sleep(800);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
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