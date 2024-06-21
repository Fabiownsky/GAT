import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeaderboardGUI {
    private JFrame frame;
    private static final String LEADERBOARD_FILE = "src/assets/leaderboard.txt";

    public void showLeaderboard() {
        frame = new JFrame("Leaderboard");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        List<PlayerScore> leaderboard = loadLeaderboard();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(leaderboard.size(), 1));

        for (PlayerScore score : leaderboard) {
            panel.add(new JLabel(score.getName() + ": " + score.getScore()));
        }

        frame.add(new JScrollPane(panel), BorderLayout.CENTER);
        frame.setSize(300, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private List<PlayerScore> loadLeaderboard() {
        List<PlayerScore> leaderboard = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(LEADERBOARD_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    int score = Integer.parseInt(parts[1].trim());
                    leaderboard.add(new PlayerScore(name, score));
                }
            }
            Collections.sort(leaderboard);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return leaderboard;
    }

    public static void addScore(String name, int score) {
        List<PlayerScore> leaderboard = new LeaderboardGUI().loadLeaderboard();
        leaderboard.add(new PlayerScore(name, score));
        Collections.sort(leaderboard);
        if (leaderboard.size() > 10) {
            leaderboard = leaderboard.subList(0, 10);
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LEADERBOARD_FILE))) {
            for (PlayerScore playerScore : leaderboard) {
                writer.write(playerScore.getName() + "," + playerScore.getScore());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
