import javax.swing.JFrame;

public class Game extends JFrame {

    public Game() {
        initUI();
    }

    private void initUI() {

        add(new Window());

        setSize(600, 500);
        setResizable(false);

        setTitle("Moving sprite");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}