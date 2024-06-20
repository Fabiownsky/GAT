import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        // Carica e analizza l'immagine
        try {
            String imagePath = "src/assets/lab50x50.png";
            int[][] matrix = ImageAnalyzer.analyzeImage(imagePath);
            Game game = new Game(matrix);

            // Avvia l'applicazione Swing
            SwingUtilities.invokeLater(() -> {
                GameGUI gameGUI = new GameGUI(game);
                gameGUI.createAndShowGui();
                GameGUI.updatePlayerName("Fabio Porcelli");
                GameGUI.updateSteps(0);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
