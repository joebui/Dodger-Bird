package GameFunctions;

import BirdMemento.*;
import Obstacle.Observer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import java.io.File;

public class Game extends Observer {
    private Menu menu;
    private GameOver gameOver;
    private CareTaker careTaker;
    private GameScreen window;
    private Database database;
    private HighScore highScore;
    private Bird bird;
    private Clip c;
    private JFrame frame;

    private Game() {
        database = new Database();
        frame = new JFrame();
        menu = new Menu(this);
        bird = Bird.getInstance();
        bird.attach(this);
        careTaker = new CareTaker();
        careTaker.add(bird.saveToMemento());
        gameOver = new GameOver(this);

        // Display main menu.
        frame.add(menu);
        frame.setSize(1024, 720);
        frame.setResizable(false);
        frame.setTitle("Dodger Bird");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        music();
    }

    private static Game game = new Game();

    public static Game getGameInstance() { return game; }

    public void openMainMenu() {
        // Remove game over screen and open main menu.
        bird.getFromMemento(careTaker.get());
        gameOver.setVisible(false);
        gameOver = null;
        menu.setVisible(true);
        frame.add(menu);
        menu.requestFocusInWindow();
    }

    public void openGame() {
        // Start background music.
        c.loop(Clip.LOOP_CONTINUOUSLY);
        // Remove main menu and open game screen.
        menu.setVisible(false);
        gameOver = new GameOver(this);
        bird.initObstacle();
        window = new GameScreen(bird, gameOver);
        frame.add(window);
        window.requestFocusInWindow();
    }

    @Override
    public void update() {
        try {
            // Play die sound
            c.stop();
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("sounds/die.wav"));
            clip.open(inputStream);
            clip.start();
        } catch (Exception e) {
            System.out.println(e);
        }

        // Open game over screen.
        window.setVisible(false);
        gameOver.displayTime();
        frame.add(gameOver);
        gameOver.requestFocusInWindow();
        database.addToDatabase(gameOver.askPlayerName(), gameOver.getTime());
        window = null;
    }

    public void openHighScores() {
        // Open high scores screen.
        menu.setVisible(false);
        highScore = new HighScore(database, game);
        frame.add(highScore);
    }

    public void backToMenuFromHighScore() {
        highScore.setVisible(false);
        highScore = null;
        frame.add(menu);
        menu.setVisible(true);
        menu.requestFocusInWindow();
    }

    public JFrame getFrame() {
        return frame;
    }

    private synchronized void music() {
        try {
            c = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("sounds/BackgroundMusic.wav"));
            c.open(inputStream);
            // Play background music endlessly.
        } catch(Exception error)  {
            System.out.println(error.getMessage());
        }
    }
}