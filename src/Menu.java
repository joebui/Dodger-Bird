import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        JPanel t = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        title.setFont(new Font("Magneto", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        t.add(title);
        t.setBackground(Color.GREEN);
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        play.setBackground(Color.RED);
        play.setFont(new Font("Arial", Font.BOLD, 20));
        play.setForeground(Color.WHITE);
        p.add(play);
        p.setBackground(Color.GREEN);
        JPanel h = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        highScores.setBackground(Color.BLACK);
        highScores.setFont(new Font("Arial", Font.BOLD, 14));
        highScores.setForeground(Color.WHITE);
        h.add(highScores);
        h.setBackground(Color.GREEN);
        JPanel tu = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        howto.setBackground(Color.BLACK);
        howto.setFont(new Font("Arial", Font.BOLD, 14));
        howto.setForeground(Color.WHITE);
        tu.add(howto);
        tu.setBackground(Color.GREEN);
        JPanel e = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        exit.setBackground(Color.BLACK);
        exit.setFont(new Font("Arial", Font.BOLD, 14));
        exit.setForeground(Color.WHITE);
        e.add(exit);
        e.setBackground(Color.GREEN);

        JPanel main = new JPanel(new GridLayout(5, 0));
        main.add(t);
        main.add(p);
        main.add(h);
        main.add(tu);
        main.add(e);
        add(main);

        play.addActionListener(e1 -> g.openGame());
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(new ImageIcon("images/background.png").getImage(), 0, 0, 500, 430, this);
    }
}
