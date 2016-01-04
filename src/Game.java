import GameFunctions.GameOver;
import Obstacle.Bird;
import Obstacle.Observer;

import javax.swing.JFrame;

public class Game extends Observer {
    private Menu menu;
    private GameOver gameOver;
    private GameScreen window;
    private Bird bird;
    private JFrame frame;

    private Game() {
        frame = new JFrame();
        menu = new Menu(this);
        bird = Bird.getInstance();
        bird.attach(this);
        gameOver = new GameOver();

        frame.add(menu);
        frame.setSize(1024, 720);
        frame.setResizable(false);
        frame.setTitle("Dodger Bird");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void openGame() {
        menu.setVisible(false);
        menu = null;
        window = new GameScreen(bird, gameOver);
        frame.add(window);
        window.requestFocusInWindow();
    }

    private static Game game = new Game();

    public static Game getGameInstance() { return game; }

    public JFrame getFrame() {
        return frame;
    }

    @Override
    public void update() {
        window.setVisible(false);
        window = null;
        gameOver = new GameOver();
        frame.add(gameOver);
        gameOver.requestFocusInWindow();
    }
}