import Obstacle.Bird;
import Obstacle.Missile;
import Obstacle.Wheel;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Window extends JPanel implements ActionListener {
    private Bird bird;
    private ArrayList<Missile> missiles;
    private ArrayList<Wheel> wheels;

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
        bird = new Bird(40, 60);
        missiles = new ArrayList<>();
        wheels = new ArrayList<>();

        fireMissile();
        fireWheel();
        Timer timer = new Timer(10, this);
        timer.start();
    }

    // display the objects on screen.
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
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

    // this method will be called recursively to update the object.
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
                m.setVisible(false);
                bird.setVisible(false);
            }
        }

        for (Wheel w : wheels) {
            Rectangle wr = w.getBound();
            if (birdBound.intersects(wr)) {
                w.setVisible(false);
                bird.setVisible(false);
            }
        }
    }

    private void fireMissile() {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        missiles.add(new Missile(600, bird.getY() + bird.getHeight() / 2));
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private void fireWheel() {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        wheels.add(new Wheel(600, new Random().nextInt(450) + 10));
                        sleep(800);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}