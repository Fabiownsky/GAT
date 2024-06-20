import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameGUI {
    private JFrame frame;
    private Game game;
    private static JLabel playerNameLabel;
    private static JLabel stepsLabel;

    public GameGUI(Game game) {
        this.game = game;
    }

    public void createAndShowGui() {
        frame = new JFrame("Grid Display");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        GridDisplay gridPanel = new GridDisplay(game);
        frame.add(gridPanel, BorderLayout.CENTER);

        JPanel legendPanel = createLegendPanel();
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

    private static JPanel createLegendPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel legendTitle = new JLabel("LEGENDA");
        legendTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        legendTitle.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(legendTitle);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Spazio tra il titolo e le voci

        panel.add(createLegendItem("Guardia - Uscita", Color.RED));
        panel.add(createLegendItem("Guardia - Casuale", Color.YELLOW));
        panel.add(createLegendItem("Guardia - Opposta", Color.GREEN));
        panel.add(createLegendItem("Guardia", Color.BLUE));
        panel.add(createLegendItem("Ladro", Color.ORANGE));
        panel.add(createLegendItem("Uscita", new Color(139, 69, 19)));

        panel.add(Box.createVerticalGlue()); // Spazio flessibile per spingere in basso i campi del giocatore

        JPanel playerInfoPanel = new JPanel();
        playerInfoPanel.setLayout(new BoxLayout(playerInfoPanel, BoxLayout.Y_AXIS));
        playerInfoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerNameLabel = new JLabel("Nome giocatore");
        playerNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        stepsLabel = new JLabel("Passi: 0");
        stepsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerInfoPanel.add(playerNameLabel);
        playerInfoPanel.add(stepsLabel);

        panel.add(playerInfoPanel);
        panel.add(Box.createVerticalGlue()); // Spazio flessibile per spingere gli elementi al centro

        return panel;
    }

    private static JPanel createLegendItem(String name, Color color) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setMaximumSize(new Dimension(200, 30)); // Dimensione massima per rendere compatto
        JLabel label = new JLabel(name);
        label.setPreferredSize(new Dimension(150, 20));
        JPanel colorPanel = new JPanel();
        colorPanel.setBackground(color);
        colorPanel.setPreferredSize(new Dimension(20, 20));
        panel.add(colorPanel);
        panel.add(Box.createRigidArea(new Dimension(10, 0))); // Spazio tra il colore e il testo
        panel.add(label);
        return panel;
    }

    public static void updatePlayerName(String playerName) {
        playerNameLabel.setText(playerName);
    }

    public static void updateSteps(int steps) {
        stepsLabel.setText("Passi: " + steps);
    }

}
