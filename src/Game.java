import javax.swing.JFrame;

public class Game extends JFrame {
    public Game() {
        add(new Window());

        setSize(1024, 720);
        setResizable(false);

        setTitle("Dodger Bird");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}