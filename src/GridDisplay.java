import javax.swing.*;
import java.awt.*;

public class GridDisplay extends JPanel implements Observer {
    private Game game;
    public static final int PADDING = 20; // Padding costante
    private static JLabel playerNameLabel;
    private static JLabel stepsLabel;


    public GridDisplay(Game game) {
        this.game = game;
        game.addObserver(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int[][] matrix = game.getMatrix();
        int cellSize = 10; // Dimensione delle celle
        int spacing = 2;   // Spaziatura tra le celle

        if (matrix != null) {
            for (int y = 0; y < matrix.length; y++) {
                for (int x = 0; x < matrix[y].length; x++) {
                    g.setColor(getColor(matrix[y][x]));
                    g.fillRect(PADDING + x * (cellSize + spacing), PADDING + y * (cellSize + spacing), cellSize, cellSize);
                }
            }
            // Disegna il ladro
            g.setColor(Color.ORANGE);
            g.fillRect(PADDING + game.getThiefX() * (cellSize + spacing), PADDING + game.getThiefY() * (cellSize + spacing), cellSize, cellSize);
        }
    }

    private Color getColor(int value) {
        switch (value) {
            case 0: return Color.WHITE;  // white (pavimento)
            case 1: return Color.BLACK;  // black (muro)
            case 2: return Color.RED;    // red
            case 3: return Color.YELLOW; // yellow
            case 4: return Color.GREEN;  // green
            case 5: return Color.BLUE;   // blue
            case 6: return Color.ORANGE; // orange
            case 7: return new Color(139, 69, 19); // brown
            default: return Color.GRAY;  // colore non specificato
        }
    }


    @Override
    public void update() {
        repaint();
    }
}
