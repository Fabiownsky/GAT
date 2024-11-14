import java.util.List;
import java.util.Random;

//Classe che implementa la Strategia di movimento di Default (senza potenziamenti)
public class DefaultMoveStrategy implements MovementStrategy {

    @Override
    public void move(Guard guard) {
        //Prende la probabilità di seguire la direzione verso l'uscita
        int probability = guard.getMoveProbability(); // probabilità movimento verso uscita della guardia
        Random random = new Random();
        int chance = random.nextInt(100);

        //Se rientra nella probabilità, segue la direzione ottimale
        if (chance < probability) {
            List<Integer> path = guard.getGraph().shortestPath(guard.getX(), guard.getY(), guard.getExitX(), guard.getExitY());
            if (path.size() > 1) {
                int next = path.get(1);
                int newX = next % guard.getMatrix()[0].length;
                int newY = next / guard.getMatrix()[0].length;
                guard.moveTo(newX, newY);
            }
        } else {
            // Altrimenti fa un movimento casuale
            int[][] directions = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
            int[] dir = directions[random.nextInt(directions.length)];
            int newX = guard.getX() + dir[0];
            int newY = guard.getY() + dir[1];
            if (guard.canMove(newX, newY)) {
                guard.moveTo(newX, newY);
            }
        }
    }

}
