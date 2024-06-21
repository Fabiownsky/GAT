import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Observer> observers = new ArrayList<>();
    private int[][] matrix;
    private int thiefX, thiefY;
    private int steps;
    private String playerName; // Aggiungi il nome del giocatore

    public Game(int[][] matrix) {
        this.matrix = matrix;
        this.steps = 0;
        // Trova la posizione iniziale del ladro (arancione)
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length; x++) {
                if (matrix[y][x] == 6) { // 6 è il codice per il colore arancione (ladro)
                    this.thiefX = x;
                    this.thiefY = y;
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
        int newX = thiefX + dx;
        int newY = thiefY + dy;
        if (canMove(newX, newY)) {
            // Ripristina il colore della posizione precedente
            matrix[thiefY][thiefX] = 0; // Supponiamo che il pavimento sia bianco (0)
            // Aggiorna la posizione del ladro
            thiefX = newX;
            thiefY = newY;
            // Imposta il nuovo colore del ladro nella matrice
            matrix[thiefY][thiefX] = 6;
            steps++;
            notifyObservers();
        }
    }

    private boolean canMove(int x, int y) {
        return matrix[y][x] != 1; // Verifica che la nuova posizione non sia un muro
    }

    // Getters per la posizione del ladro e il numero di passi
    public int getThiefX() {
        return thiefX;
    }

    public int getThiefY() {
        return thiefY;
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
