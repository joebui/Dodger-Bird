import GameFunctions.Game;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Game ex = Game.getGameInstance();
            ex.getFrame().setVisible(true);
        });
    }
}
