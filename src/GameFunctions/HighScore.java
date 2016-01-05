package GameFunctions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class HighScore extends JPanel {

    public HighScore(Database database, Game game) {
        ArrayList<JLabel> names = new ArrayList<>();
        ArrayList<JLabel> times = new ArrayList<>();

        database.listTopTenResults(names, times);
        JLabel title = new JLabel("HIGH SCORE");

        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 45, 45));
        title.setFont(new Font("Magneto", Font.BOLD, 40));
        title.setForeground(Color.WHITE);
        p1.add(title);
        p1.setBackground(Color.BLACK);

        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel a = new JPanel(new GridLayout(names.size() + 1, 2, 100, 5));
        JLabel n  = new JLabel("Name"); n.setForeground(Color.WHITE); n.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel t = new JLabel("Time"); t.setForeground(Color.WHITE); t.setFont(new Font("Arial", Font.BOLD, 16));
        a.add(n); a.add(t);
        for (int i = 0; i < names.size(); i++) {
            a.add(names.get(i));
            a.add(times.get(i));
        }
        a.setBackground(Color.BLACK);
        p2.setBackground(Color.BLACK);
        p2.add(a, BorderLayout.CENTER);

        JPanel p3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 45, 60));
        JButton back = new JButton("Back to main menu");
        back.setBackground(Color.RED);
        back.setFont(new Font("Arial", Font.BOLD, 30));
        back.setForeground(Color.WHITE);
        p3.add(back);
        p3.setBackground(Color.BLACK);

        back.addActionListener(e -> game.backToMenuFromHighScore());

        JPanel main = new JPanel(new GridLayout(3, 0));
        main.add(p1); main.add(p2); main.add(p3);
        add(main);
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setFocusable(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(new ImageIcon("images/background.png").getImage(), 0, 0, 1024, 720, this);
    }
}
