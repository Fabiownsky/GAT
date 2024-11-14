import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

//Classe che gestisce la logica del gioco, le interazioni e i movimenti tra Guardia e Ladro e notificatore agli osservatori
public class Game implements Observable {
    private List<Observer> observers = new ArrayList<>(); //ArrayList per memorizzare gli Observer
    private int[][] matrix; //Matrice del labirinto
    private Thief thief;
    private Guard guard;
    private String playerName; // Aggiungi il nome del giocatore
    private boolean gameEnded; // Indica se il gioco è terminato
    private JFrame gameFrame; // Riferimento alla finestra di gioco
    private Graph graph; // Rappresentazione del grafo del labirinto
    private int exitX, exitY; // Coordinate dell'uscita
    private Direction lastThiefDirection; //Ultima direzione del ladro
    private StepCounter stepCounter;

    //Enumerazione delle direzioni possibili del ladro
    public enum Direction {
        UP, DOWN, LEFT, RIGHT;
    }

    public Game(int[][] matrix) {
        Thief.resetInstance(); // Resetta l'istanza del ladro all'inizio di ogni partita
        this.matrix = matrix;
        this.gameEnded = false;
        this.stepCounter = StepCounter.getInstance();
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

        this.graph = new Graph(matrix); //Inizializza il grafo basato sulla griglia matrix
        this.thief = Thief.getInstance(matrix, thiefX, thiefY);
        this.guard = new Guard(matrix, guardX, guardY, graph, exitX, exitY, this); // Passa il riferimento del gioco
    }

    //Implementa il metodo addObserver per aggiungere un Observer
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    //Implementa il metodo removeObserver per rimuovere un Observer
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    //Implementa il metodo per notificare tutti gli Observer in attesa tramite update()
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    //Metodo per la gestione del movimento del ladro
    public void moveThief(int dx, int dy) {
        if (!gameEnded) {
            //Calcola le nuove coordinate
            int newX = thief.getX() + dx;
            int newY = thief.getY() + dy;
            if (canMove(newX, newY)) {
                int cellColor = matrix[newY][newX];

                //Salva l'ultima direzione del ladro
                if (dx == 1 && dy == 0) {
                    lastThiefDirection = Direction.RIGHT;
                } else if (dx == -1 && dy == 0) {
                    lastThiefDirection = Direction.LEFT;
                } else if (dx == 0 && dy == 1) {
                    lastThiefDirection = Direction.DOWN;
                } else if (dx == 0 && dy == -1) {
                    lastThiefDirection = Direction.UP;
                }

                thief.move(dx, dy);
                //Modifica la strategia in base alla cella attravesata dal Ladro
                // Applica potenziamenti in base al colore della cella
                if (cellColor == 2) { // Cella rossa
                    guard.setStrategy(new MoveToExitStrategy());
                } else if (cellColor == 3) { // Cella gialla
                    guard.setStrategy(new RandomMoveStrategy(), 10); // 10 turni di movimento casuale
                } else if (cellColor == 4) { // Cella verde
                    guard.setStrategy(new OppositeMoveStrategy(), 10); // 10 turni di movimento opposto
                }

                //Controlla se il gioco è terminato
                checkEndGame();
                //Se non è finita, procede al movimento della guardia, notificare gli observer e ricontrollare la fine della partita
                if (!gameEnded) {
                    guard.move();
                    notifyObservers();
                    checkEndGame();
                }
            }
        }
    }

    //Metodo per controllare se le condizioni di fine partita sono rispettate, ed indicando se il Ladro ha vinto (thiefWins = true) o ha perso (thiefWins = false)
    private void checkEndGame() {
        // Condizioni di fine partita
        if (thief.getX() == exitX && thief.getY() == exitY) { // Verifica se il ladro è sulla cella marrone
            endGame(true);
        } else if (thief.getX() == guard.getX() && thief.getY() == guard.getY()) {
            endGame(false);
        } else if (isAdjacent(thief.getX(), thief.getY(), guard.getX(), guard.getY())) {
            endGame(false);
        } else if (guard.getX() == exitX && guard.getY() == exitY) { // Verifica se la guardia è sulla cella marrone
            endGame(false);
        }
    }


    private boolean isAdjacent(int x1, int y1, int x2, int y2) {
        return (Math.abs(x1 - x2) == 1 && y1 == y2) || (Math.abs(y1 - y2) == 1 && x1 == x2);
    }

    //Metodo che viene chiamato una volta che la partita è terminata
    public void endGame(boolean thiefWins) {
        gameEnded = true;
        if (thiefWins) {
            //Mostra il messaggio di congratulazioni nel caso di vittoria e aggiunge il punteggio
            LeaderboardGUI.addScore(playerName, stepCounter.getSteps());
            JOptionPane.showMessageDialog(null, "Congratulazioni " + playerName + "! Hai vinto!");
        } else {
            //Mostra messaggio di sconfitta nel caso di sconfitta
            JOptionPane.showMessageDialog(null, "Game Over! Il ladro è stato catturato o la guardia è uscita.");
        }
        // Mostra la leaderboard
        new LeaderboardGUI().showLeaderboard(gameFrame);
    }

    // Getters per la posizione del ladro
    public int getThiefX() {
        return thief.getX();
    }

    public int getThiefY() {
        return thief.getY();
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public Direction getLastThiefDirection() {
        return lastThiefDirection;
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
