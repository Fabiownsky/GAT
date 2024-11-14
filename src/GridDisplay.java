import javax.swing.*;
import java.awt.*;

//Classe che gestisce la visualizzazione della griglia di gioco
public class GridDisplay extends JPanel implements Observer {
    private Game game;
    public static final int PADDING = 20; // Padding di default
    private JLabel playerNameLabel;
    private JLabel stepsLabel;
    private StepCounter stepCounter; // Riferimento al contatore dei passi
    private static boolean isColorblindMode = true; // Modalità daltonica (true di default)

    public GridDisplay(Game game) {
        this.game = game;
        //Si aggiunge alla lista degli Observers
        game.addObserver(this);

        // Inizializza i componenti GUI
        this.setLayout(new BorderLayout());

        //Aggiunge la legenda
        JPanel legendPanel = createLegendPanel();
        this.add(legendPanel, BorderLayout.EAST);

        updatePlayerName(game.getPlayerName()); // Aggiorna il nome del giocatore

        // Inizializza StepCounter passando l'etichetta stepsLabel
        stepCounter = StepCounter.getInstance();
        stepCounter.setStepsLabel(stepsLabel);
        game.addObserver(stepCounter); // Aggiunge StepCounter come Observer di Game
    }

    public static void setColorblindMode(boolean isColorblind) {
        isColorblindMode = isColorblind;
    }

    //Metodo richiamato ogni volta che repaint() viene invocato
    @Override
    protected void paintComponent(Graphics g) {
        //Inizializzazione del
        super.paintComponent(g);
        //Prende la matrice aggiornata
        int[][] matrix = game.getMatrix();
        int cellSize = 10; // Dimensione delle celle
        int spacing = 2;   // Spaziatura tra le celle
        Font originalFont = g.getFont();
        Font smallFont = originalFont.deriveFont(8f); // Font di dimensione 8

        if (matrix != null) {
            for (int y = 0; y < matrix.length; y++) {
                for (int x = 0; x < matrix[y].length; x++) {
                    int value = matrix[y][x];
                    g.setColor(getColor(value));
                    //Crea il rettangolo riempito col colore appropriato
                    g.fillRect(PADDING + x * (cellSize + spacing), PADDING + y * (cellSize + spacing), cellSize, cellSize);

                    if (isColorblindMode && value >= 2 && value <= 7) { // Solo per i colori specificati in modalità daltonica
                        g.setFont(smallFont); // Imposta il font piccolo
                        drawCenteredString(g, getLetter(value), PADDING + x * (cellSize + spacing), PADDING + y * (cellSize + spacing), cellSize, cellSize);
                        g.setFont(originalFont); // Ripristina il font originale
                    }
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
            case 5: return Color.BLUE;   // blue (guardia)
            case 6: return Color.ORANGE; // orange (ladro)
            case 7: return new Color(139, 69, 19); // brown
            default: return Color.GRAY;  // colore non specificato
        }
    }

    private String getLetter(int value) {
        switch (value) {
            case 2: return "R"; // red
            case 3: return "Y"; // yellow
            case 4: return "G"; // green
            case 5: return "P"; // blue (poliziotto)
            case 6: return "T"; // orange (ladro)
            case 7: return "B"; // brown
            default: return "";
        }
    }

    //Metodo per aggiungere le lettere all'interno delle figure se è abilitata la modalità colorBlind
    private void drawCenteredString(Graphics g, String text, int x, int y, int width, int height) {
        FontMetrics metrics = g.getFontMetrics();
        int centerX = x + (width - metrics.stringWidth(text)) / 2;
        int centerY = y + ((height - metrics.getHeight()) / 2) + metrics.getAscent();

        // Disegna il contorno bianco
        g.setColor(Color.WHITE);
        g.drawString(text, centerX - 1, centerY);
        g.drawString(text, centerX + 1, centerY);
        g.drawString(text, centerX, centerY - 1);
        g.drawString(text, centerX, centerY + 1);

        // Disegna il testo nero sopra il contorno
        g.setColor(Color.BLACK);
        g.drawString(text, centerX, centerY);
    }

    private JPanel createLegendPanel() {
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

    //Crea un singolo elemento della legenda
    private JPanel createLegendItem(String name, Color color) {
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
    //Modifica l'etichetta del nome del giocatore nel pannello laterale
    public void updatePlayerName(String playerName) {
        if (playerNameLabel != null) {
            playerNameLabel.setText(playerName);
        }
    }

    //Quando viene notificato un cambiamento dello stato di game (tramite Observer) viene invocato repaint()
    @Override
    public void update() {
        repaint();
    }
}
