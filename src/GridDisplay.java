import javax.swing.*;
import java.awt.*;

public class GridDisplay extends JPanel {

    private static int[][] matrix;
    private static final int PADDING = 20; // Padding costante

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
            case 2: return new Color(128, 0, 128); // viola (RGB personalizzato)
            case 3: return Color.YELLOW; // giallo
            case 4: return Color.GREEN;  // verde
            case 5: return Color.RED;    // rosso
            default: return Color.GRAY;  // colore non specificato
        }
    }

    public static void createAndShowGui() {
        JFrame frame = new JFrame("Grid Display");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridDisplay panel = new GridDisplay();
        frame.add(panel);

        if (matrix != null) {
            int cellSize = 10; // Dimensione delle celle
            int spacing = 2;   // Spaziatura tra le celle
            int width = matrix[0].length * (cellSize + spacing) - spacing + 2 * PADDING; // Calcola larghezza con padding
            int height = matrix.length * (cellSize + spacing) - spacing + 2 * PADDING;   // Calcola altezza con padding
            frame.setSize(width + 16, height + 39); // Adatta dimensioni per evitare il taglio
        } else {
            frame.setSize(600, 600); // Dimensione predefinita
        }

        frame.setVisible(true);
    }
}
