import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameGUI {
    private JFrame frame;
    private Game game;

    public GameGUI(Game game) {
        this.game = game;
    }

    public void createAndShowGui() {
        frame = new JFrame("Grid Display");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        GridDisplay gridPanel = new GridDisplay(game);
        frame.add(gridPanel, BorderLayout.CENTER);

        JPanel legendPanel = gridPanel.createLegendPanel();
        frame.add(legendPanel, BorderLayout.EAST);

        if (game.getMatrix() != null) {
            int cellSize = 10; // Dimensione delle celle
            int spacing = 2;   // Spaziatura tra le celle
            int width = game.getMatrix()[0].length * (cellSize + spacing) - spacing + 2 * GridDisplay.PADDING; // Calcola larghezza con padding
            int height = game.getMatrix().length * (cellSize + spacing) - spacing + 2 * GridDisplay.PADDING;   // Calcola altezza con padding
            frame.setSize(width + 250, height + 50); // Aggiungi spazio per la legenda
        } else {
            frame.setSize(800, 600); // Dimensione predefinita
        }

        frame.setVisible(true);
        gridPanel.updatePlayerName(game.getPlayerName());

        // Imposta il riferimento alla finestra di gioco
        game.setGameFrame(frame);

        // Aggiungi listener per l'input da tastiera
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        game.moveThief(0, -1);
                        break;
                    case KeyEvent.VK_DOWN:
                        game.moveThief(0, 1);
                        break;
                    case KeyEvent.VK_LEFT:
                        game.moveThief(-1, 0);
                        break;
                    case KeyEvent.VK_RIGHT:
                        game.moveThief(1, 0);
                        break;
                }
            }
        });
    }
}
