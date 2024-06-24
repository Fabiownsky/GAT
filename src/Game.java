import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Observer> observers = new ArrayList<>();
    private int[][] matrix;
    private Thief thief;
    private int steps;
    private String playerName; // Aggiungi il nome del giocatore

    public Game(int[][] matrix) {
        this.matrix = matrix;
        this.steps = 0;
        // Trova la posizione iniziale del ladro (arancione)
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length; x++) {
                if (matrix[y][x] == 6) { // 6 è il codice per il colore arancione (ladro)
                    this.thief = new Thief(matrix, x, y);
                    break;
                }
            }
        }
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public void moveThief(int dx, int dy) {
        thief.move(dx, dy);
        steps++;
        notifyObservers();
    }

    // Getters per la posizione del ladro e il numero di passi
    public int getThiefX() {
        return thief.getX();
    }

    public int getThiefY() {
        return thief.getY();
    }

    public int getSteps() {
        return steps;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    // Getter e setter per il nome del giocatore
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void endGame() {
        LeaderboardGUI.addScore(playerName, steps);
    }
}
