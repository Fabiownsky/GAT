public class Score implements Comparable<Score> {
    private String playerName;
    private int steps;

    public Score(String playerName, int steps) {
        this.playerName = playerName;
        this.steps = steps;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getSteps() {
        return steps;
    }

    @Override
    public int compareTo(Score other) {
        return Integer.compare(this.steps, other.steps);
    }
}
