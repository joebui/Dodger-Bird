import javax.swing.JFrame;

public class Game extends JFrame {
    private Menu menu;

    private Game() {
        menu = new Menu(this);
        add(menu);

        setSize(500, 430);
        setResizable(false);

        setTitle("Dodger Bird");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void openGame() {
        setSize(1024, 720);
        setLocationRelativeTo(null);
        menu.setVisible(false);
        menu = null;
        Window window = new Window();
        add(window);
        window.requestFocusInWindow();
    }

    private static Game game = new Game();

    public static Game getGameInstance() { return game; }
}