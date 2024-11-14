import java.util.List;
import java.util.Random;

public class Guard extends Character {
    private final Graph graph;
    private final int exitX, exitY;
    private Game game;
    private MovementStrategy strategy;
    private int powerUpTurnsLeft = 0; // Conteggio dei turni per i potenziamenti
    private int moveProbability = 20; // Imposta qui la probabilità di default - 20%

    public Guard(int[][] matrix, int startX, int startY, Graph graph, int exitX, int exitY, Game game) {
        super(matrix, startX, startY);
        this.graph = graph;
        this.exitX = exitX;
        this.exitY = exitY;
        this.game = game;
        this.strategy = new DefaultMoveStrategy(); // Imposta la strategia di default
    }

    //Setter per impostare la difficoltà
    public void setMoveProbability(int probability) {
        this.moveProbability = probability;
    }

    // Imposta una strategia con durata specifica
    public void setStrategy(MovementStrategy strategy, int turns) {
        this.strategy = strategy;
        this.powerUpTurnsLeft = turns;
    }

    // Imposta una strategia permanente (ad es. verso l'uscita)
    public void setStrategy(MovementStrategy strategy) {
        this.strategy = strategy;
        this.powerUpTurnsLeft = -1; // -1 indica durata infinita o finché non viene preso un altro potenziamento
    }

    // Metodo per gestire quale strategia usare e
    public void move() {
        if (powerUpTurnsLeft == 0) {
            strategy = new DefaultMoveStrategy(); // Ritorna alla strategia di default
        } else if (powerUpTurnsLeft > 0) {
            powerUpTurnsLeft--; // Decrementa i turni rimanenti
        }
        strategy.move(this); // Muove la guardia usando la strategia corrente
    }

    // Metodo per muovere la guardia a una nuova posizione e controllo di fine partita
    public void moveTo(int newX, int newY) {
        if (newX == exitX && newY == exitY) {
            // La guardia ha raggiunto l'uscita, termina il gioco
            game.endGame(false);
            return;
        }
        matrix[y][x] = 0; // Aggiorna la vecchia posizione della guardia (0 = pavimento)
        x = newX;
        y = newY;
        matrix[y][x] = 5; // Aggiorna la nuova posizione della guardia
    }

    public Graph getGraph() {
        return this.graph;
    }

    public int getExitX() {
        return this.exitX;
    }

    public int getExitY() {
        return this.exitY;
    }

    public Game getGame(){
        return this.game;
    }

    public int getMoveProbability() {
        return this.moveProbability;
    }
}
