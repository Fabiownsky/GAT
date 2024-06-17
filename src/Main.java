import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        // Carica e analizza l'immagine
        try {
            String imagePath = "src/assets/Sprite-0001.png";
            int[][] matrix = ImageAnalyzer.analyzeImage(imagePath);
            GridDisplay.setMatrix(matrix);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Avvia l'applicazione Swing
        SwingUtilities.invokeLater(GridDisplay::createAndShowGui);
    }
}
