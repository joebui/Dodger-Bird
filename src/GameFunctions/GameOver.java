package GameFunctions;

import javax.swing.*;
import java.awt.*;

public class GameOver extends JPanel {
    private JButton back;
    private int t;

    public GameOver(Game game) {
        back = new JButton("Back to menu");
        back.addActionListener(e -> game.openMainMenu());
    }

    public void updateTime(int time) {
        t = time;
    }

    public void displayTime() {
        JLabel title = new JLabel("YOU FAILED TO SURVIVE");
        JLabel time = new JLabel();

        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 45, 45));
        title.setFont(new Font("Magneto", Font.BOLD, 40));
        title.setForeground(Color.WHITE);
        p1.add(title);
        p1.setBackground(Color.BLACK);

        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 45, 45));
        time.setText("Time: " + t / 60 + ":" + t % 60);
        time.setFont(new Font("Arial", Font.BOLD, 40));
        time.setForeground(Color.WHITE);
        p2.add(time);
        p2.setBackground(Color.BLACK);

        JPanel p3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 45, 45));
        back.setBackground(Color.RED);
        back.setFont(new Font("Arial", Font.BOLD, 30));
        back.setForeground(Color.WHITE);
        p3.add(back);
        p3.setBackground(Color.BLACK);

        setLayout(new GridLayout(3, 0));
        add(p1); add(p2); add(p3);
    }

    public String askPlayerName() {
        return JOptionPane.showInputDialog("Enter your name", "player");
    }

    public int getTime() {
        return t;
    }
}
