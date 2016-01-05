package GameFunctions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;


public class Menu extends JPanel {
    private JLabel title;
    private JButton play;
    private JButton highScores;
    private JButton howto;
    private JButton exit;

    public Menu(Game g) {
        title = new JLabel("DODGER BIRD");
        play = new JButton("PLAY");
        highScores = new JButton("High Scores");
        howto = new JButton("How-to");
        exit = new JButton("Exit");

        JPanel t = new JPanel(new FlowLayout(FlowLayout.CENTER, 45, 45));
        title.setFont(new Font("Magneto", Font.BOLD, 40));
        title.setForeground(Color.WHITE);
        t.add(title);
        t.setBackground(Color.BLACK);
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        play.setBackground(Color.GREEN);
        play.setFont(new Font("Arial", Font.BOLD, 30));
        play.setForeground(Color.WHITE);
        p.add(play);
        p.setBackground(Color.BLACK);
        JPanel h = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        highScores.setBackground(Color.ORANGE);
        highScores.setFont(new Font("Arial", Font.BOLD, 30));
        highScores.setForeground(Color.WHITE);
        h.add(highScores);
        h.setBackground(Color.BLACK);
        JPanel tu = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        howto.setBackground(Color.BLUE);
        howto.setFont(new Font("Arial", Font.BOLD, 30));
        howto.setForeground(Color.WHITE);
        tu.add(howto);
        tu.setBackground(Color.BLACK);
        JPanel e = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        exit.setBackground(Color.RED);
        exit.setFont(new Font("Arial", Font.BOLD, 30));
        exit.setForeground(Color.WHITE);
        e.add(exit);
        e.setBackground(Color.BLACK);

        JPanel main = new JPanel(new GridLayout(5, 0));
        main.add(t);
        main.add(p);
        main.add(h);
        main.add(tu);
        main.add(e);
        add(main);

        play.addActionListener(e1 -> g.openGame());
        howto.addActionListener(arg0 ->
                JOptionPane.showMessageDialog(null, "Use the UP and DOWN arrow keys to move the bird.\n" +
                "Try to avoid obstacle and be aware of missiles. You only have 1 life.\n\n" +
                "The game will be endless until you die.", "How to play Dodger Bird",
                JOptionPane.INFORMATION_MESSAGE));

        highScores.addActionListener(e1 -> g.openHighScores());
        exit.addActionListener(e4 -> System.exit(0));
        setFocusable(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(new ImageIcon("images/background.png").getImage(), 0, 0, 1024, 720, this);
    }
}
