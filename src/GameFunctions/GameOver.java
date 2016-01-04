package GameFunctions;

import javax.swing.*;
import java.awt.*;

public class GameOver extends JPanel {
    private JLabel title, time;
    private JButton back;
    private int m, s;

    public GameOver() {
        title = new JLabel("GAME OVER");
        time = new JLabel("Time: " + m + ":" + s);
        back = new JButton("Back to menu");

        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 45, 45));
        title.setFont(new Font("Magneto", Font.BOLD, 40));
        p1.add(title);
        p1.setBackground(Color.BLACK);

        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 45, 45));
        time.setFont(new Font("Magneto", Font.BOLD, 40));
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

    public void updateTime(int m, int s) {
        this.m = m;
        this.s = s;
    }
}
