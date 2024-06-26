import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainMenu {
    private JFrame frame;

    public MainMenu() {
        frame = new JFrame("Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10));

        JButton playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerName = JOptionPane.showInputDialog(frame, "Enter your name:");
                if (playerName != null && !playerName.trim().isEmpty()) {
                    try {
                        Thief.resetInstance(); // Resetta l'istanza del ladro all'inizio di una nuova partita
                        int[][] matrix = loadMatrix();
                        Game game = new Game(matrix);
                        game.setPlayerName(playerName);
                        GameGUI gameGUI = new GameGUI(game);
                        game.setGameFrame(gameGUI.createAndShowGui());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        panel.add(playButton);

        JButton leaderboardButton = new JButton("Leaderboard");
        leaderboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LeaderboardGUI().showLeaderboard(null);
            }
        });
        panel.add(leaderboardButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private int[][] loadMatrix() throws IOException {
        String imagePath = "src/assets/lab50x50.png"; // Imposta qui il percorso corretto dell'immagine
        return ImageAnalyzer.analyzeImage(imagePath);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenu::new);
    }
}
