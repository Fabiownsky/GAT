import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeaderboardGUI {
    private static final String LEADERBOARD_FILE = "src/assets/leaderboard.txt";

    public void showLeaderboard(JFrame gameFrame) {
        JFrame frame = new JFrame("Leaderboard");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 400);
        frame.setLocationRelativeTo(null);

        List<Score> scores = loadScores();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Leaderboard");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);

        for (Score score : scores) {
            JLabel scoreLabel = new JLabel(score.getPlayerName() + ": " + score.getSteps());
            scoreLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(scoreLabel);
        }

        frame.add(panel);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (gameFrame != null) {
                    gameFrame.dispose();
                }
            }
        });
    }

    public static void addScore(String playerName, int steps) {
        List<Score> scores = loadScores();
        scores.add(new Score(playerName, steps));
        Collections.sort(scores);
        saveScores(scores);
    }

    private static List<Score> loadScores() {
        List<Score> scores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(LEADERBOARD_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    scores.add(new Score(parts[0], Integer.parseInt(parts[1])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scores;
    }

    private static void saveScores(List<Score> scores) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LEADERBOARD_FILE))) {
            for (int i = 0; i < Math.min(scores.size(), 10); i++) {
                Score score = scores.get(i);
                writer.write(score.getPlayerName() + "," + score.getSteps());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
