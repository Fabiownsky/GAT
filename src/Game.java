import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Game {
    private List<Observer> observers = new ArrayList<>();
    private int[][] matrix;
    private Thief thief;
    private Guard guard;
    private int steps;
    private String playerName; // Aggiungi il nome del giocatore
    private boolean gameEnded; // Indica se il gioco è terminato
    private JFrame gameFrame; // Riferimento alla finestra di gioco

    public Game(int[][] matrix) {
        this.matrix = matrix;
        this.steps = 0;
        this.gameEnded = false;
        // Trova la posizione iniziale del ladro (arancione) e della guardia (blu)
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length; x++) {
                if (matrix[y][x] == 6) { // 6 è il codice per il colore arancione (ladro)
                    this.thief = new Thief(matrix, x, y);
                }
                if (matrix[y][x] == 5) { // 5 è il codice per il colore blu (guardia)
                    this.guard = new Guard(matrix, x, y);
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
        if (!gameEnded && matrix[thief.getY() + dy][thief.getX() + dx] == 7) {
            endGame(true);
        } else {
            thief.move(dx, dy);
            steps++;
            notifyObservers();
            checkEndGame();
        }
    }

    public void moveGuard(int dx, int dy) {
        if (!gameEnded) {
            guard.move(dx, dy);
            notifyObservers();
            checkEndGame();
        }
    }

    private void checkEndGame() {
        // Condizioni di fine partita
        if (matrix[thief.getY()][thief.getX()] == 7) { // 7 è il codice per il colore marrone (uscita)
            endGame(true);
        } else if (thief.getX() == guard.getX() && thief.getY() == guard.getY()) {
            endGame(false);
        } else if (matrix[guard.getY()][guard.getX()] == 7) {
            endGame(false);
        }
    }

    public void endGame(boolean thiefWins) {
        gameEnded = true;
        if (thiefWins) {
            LeaderboardGUI.addScore(playerName, steps);
            JOptionPane.showMessageDialog(null, "Congratulazioni " + playerName + "! Hai vinto!");
        } else {
            JOptionPane.showMessageDialog(null, "Game Over! Il ladro è stato catturato o la guardia è uscita.");
        }
        // Mostra la leaderboard
        new LeaderboardGUI().showLeaderboard();
        // Chiude la finestra di gioco
        if (gameFrame != null) {
            gameFrame.dispose();
        }
    }

    // Getters per la posizione del ladro e il numero di passi
    public int getThiefX() {
        return thief.getX();
    }

    public int getThiefY() {
        return thief.getY();
    }

    public int getGuardX() {
        return guard.getX();
    }

    public int getGuardY() {
        return guard.getY();
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

    // Setter per il riferimento alla finestra di gioco
    public void setGameFrame(JFrame gameFrame) {
        this.gameFrame = gameFrame;
    }
}
