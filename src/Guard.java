import java.util.List;
import java.util.Random;

public class Guard extends Character {
    private final Random random = new Random();
    private final Graph graph;
    private final int exitX, exitY;
    private Game game; // Aggiungi un riferimento al gioco

    public Guard(int[][] matrix, int startX, int startY, Graph graph, int exitX, int exitY, Game game) {
        super(matrix, startX, startY);
        this.graph = graph;
        this.exitX = exitX;
        this.exitY = exitY;
        this.game = game;
    }

    @Override
    public void move(int dx, int dy) {
        int newX = x + dx;
        int newY = y + dy;
        if (canMove(newX, newY)) {
            moveTo(newX, newY);
        }
    }

    public void move(int k) {
        int chance = random.nextInt(100);
        if (chance < k) {
            // Usa Dijkstra per trovare il percorso più breve verso l'uscita
            List<Integer> path = graph.shortestPath(x, y, exitX, exitY);
            if (path.size() > 1) {
                int next = path.get(1);
                int newX = next % matrix[0].length;
                int newY = next / matrix[0].length;
                moveTo(newX, newY);
            }
        } else {
            // Muoviti casualmente
            int[][] directions = { {0, -1}, {0, 1}, {-1, 0}, {1, 0} };
            int[] dir = directions[random.nextInt(directions.length)];
            int newX = x + dir[0];
            int newY = y + dir[1];
            if (canMove(newX, newY)) {
                moveTo(newX, newY);
            }
        }
    }

    private void moveTo(int newX, int newY) {
        if (newX == exitX && newY == exitY) {
            // La guardia ha raggiunto l'uscita, termina il gioco
            game.endGame(false);
            return;
        }

        matrix[y][x] = 0; // Supponiamo che il pavimento sia bianco (0)
        x = newX;
        y = newY;
        matrix[y][x] = 5; // Aggiorna la posizione della guardia nella matrice
    }
}
