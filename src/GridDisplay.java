import javax.swing.*;
import java.awt.*;

public class GridDisplay extends JPanel {

    private static int[][] matrix;
    private static final int PADDING = 20; // Padding costante
    private static JLabel playerNameLabel;
    private static JLabel stepsLabel;

    public static void setMatrix(int[][] matrix) {
        GridDisplay.matrix = matrix;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int cellSize = 10; // Dimensione delle celle
        int spacing = 2;   // Spaziatura tra le celle

        if (matrix != null) {
            for (int y = 0; y < matrix.length; y++) {
                for (int x = 0; x < matrix[y].length; x++) {
                    g.setColor(getColor(matrix[y][x]));
                    g.fillRect(PADDING + x * (cellSize + spacing), PADDING + y * (cellSize + spacing), cellSize, cellSize);
                }
            }
        }
    }

    private Color getColor(int value) {
        switch (value) {
            case 0: return Color.WHITE;  // bianco
            case 1: return Color.BLACK;  // nero
            case 2: return new Color(128, 0, 128); // viola (ladro)
            case 3: return Color.YELLOW; // giallo (guardia va in una direzione casuale per 10 secondi)
            case 4: return Color.GREEN;  // verde (guardia va nella direzione opposta al ladro per 10 secondi)
            case 5: return Color.RED;    // rosso (guardia va nella direzione che porta all’uscita)
            default: return Color.GRAY;  // colore non specificato
        }
    }

    public static void createAndShowGui() {
        JFrame frame = new JFrame("Grid Display");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        GridDisplay gridPanel = new GridDisplay();
        frame.add(gridPanel, BorderLayout.CENTER);

        JPanel legendPanel = createLegendPanel();
        frame.add(legendPanel, BorderLayout.EAST);

        if (matrix != null) {
            int cellSize = 10; // Dimensione delle celle
            int spacing = 2;   // Spaziatura tra le celle
            int width = matrix[0].length * (cellSize + spacing) - spacing + 2 * PADDING; // Calcola larghezza con padding
            int height = matrix.length * (cellSize + spacing) - spacing + 2 * PADDING;   // Calcola altezza con padding
            frame.setSize(width + 250, height + 50); // Aggiungi spazio per la legenda
        } else {
            frame.setSize(800, 600); // Dimensione predefinita
        }

        frame.setVisible(true);
    }

    private static JPanel createLegendPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel legendTitle = new JLabel("LEGENDA");
        legendTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        legendTitle.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(legendTitle);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Spazio tra il titolo e le voci

        panel.add(createLegendItem("Ladro", new Color(128, 0, 128)));
        panel.add(createLegendItem("Guardia (casuale 10s)", Color.YELLOW));
        panel.add(createLegendItem("Guardia (opposta 10s)", Color.GREEN));
        panel.add(createLegendItem("Guardia (verso uscita)", Color.RED));

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
