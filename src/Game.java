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
    private Graph graph; // Rappresentazione del grafo del labirinto
    private int exitX, exitY; // Coordinate dell'uscita

    public Game(int[][] matrix) {
        this.matrix = matrix;
        this.steps = 0;
        this.gameEnded = false;

        // Trova la posizione iniziale del ladro (arancione), della guardia (blu) e dell'uscita (marrone)
        int thiefX = -1, thiefY = -1, guardX = -1, guardY = -1;
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length; x++) {
                if (matrix[y][x] == 6) { // 6 è il codice per il colore arancione (ladro)
                    thiefX = x;
                    thiefY = y;
                } else if (matrix[y][x] == 5) { // 5 è il codice per il colore blu (guardia)
                    guardX = x;
                    guardY = y;
                } else if (matrix[y][x] == 7) { // 7 è il codice per il colore marrone (uscita)
                    exitX = x;
                    exitY = y;
                }
            }
        }

        this.graph = new Graph(matrix);
        this.thief = Thief.getInstance(matrix, thiefX, thiefY);
        this.guard = new Guard(matrix, guardX, guardY, graph, exitX, exitY);
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
        if (!gameEnded) {
            int newX = thief.getX() + dx;
            int newY = thief.getY() + dy;
            if (canMove(newX, newY)) {
                thief.move(dx, dy);
                steps++;
                notifyObservers();
                checkEndGame(); // Controlla la fine del gioco dopo il movimento del ladro
                if (!gameEnded) {
                    guard.move(20); // 20% di probabilità di muoversi verso l'uscita
                    notifyObservers();
                    checkEndGame(); // Controlla la fine del gioco dopo il movimento della guardia
                }
            }
        }
    }

    public void moveGuard() {
        if (!gameEnded) {
            guard.move(20); // 20% di probabilità di muoversi verso l'uscita
            notifyObservers();
            checkEndGame();
        }
    }

    private void checkEndGame() {
        // Condizioni di fine partita
        if (thief.getX() == exitX && thief.getY() == exitY) { // Verifica se il ladro è sulla cella marrone
            endGame(true);
        } else if (thief.getX() == guard.getX() && thief.getY() == guard.getY()) {
            endGame(false);
        } else if (isAdjacent(thief.getX(), thief.getY(), guard.getX(), guard.getY())) {
            endGame(false);
        } else if (matrix[guard.getY()][guard.getX()] == 7) {
            endGame(false);
        }
    }

    private boolean isAdjacent(int x1, int y1, int x2, int y2) {
        return (Math.abs(x1 - x2) == 1 && y1 == y2) || (Math.abs(y1 - y2) == 1 && x1 == x2);
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
        new LeaderboardGUI().showLeaderboard(gameFrame);
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

    // Metodo per verificare se la guardia può muoversi
    private boolean canMove(int x, int y) {
        return x >= 0 && x < matrix[0].length && y >= 0 && y < matrix.length && matrix[y][x] != 1;
    }
}
