import javax.swing.*;
import java.io.IOException;
import static java.lang.Thread.sleep;

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
        SwingUtilities.invokeLater(() -> {
            GridDisplay.createAndShowGui();
            // Esempio di aggiornamento delle informazioni del giocatore
            GridDisplay.updatePlayerName("Fabio Porcelli");
            GridDisplay.updateSteps(0);
        });


        for(int i = 0; i < 100; i++) {
            try {
                sleep(1000);
                GridDisplay.updateSteps(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            GridDisplay.updateSteps(i);
        }
    }
}
