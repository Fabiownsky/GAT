import java.util.List;

public class MoveToExitStrategy implements MovementStrategy {
    @Override
    public void move(Guard guard) {
        Graph graph = guard.getGraph();
        int exitX = guard.getExitX();
        int exitY = guard.getExitY();

        //Creo il percorso ottimale
        List<Integer> path = graph.shortestPath(guard.getX(), guard.getY(), exitX, exitY);

        if (path.size() > 1) { // Il primo elemento è la posizione attuale
            int next = path.get(1); //Identificatore della cella nella prossima posizione del tipo y*cols+x
            int newX = next % guard.getMatrix()[0].length; //A partire dall'identificatore trovo la colonna
            int newY = next / guard.getMatrix()[0].length; //A partire dall'identificatore trovo la riga
            guard.moveTo(newX, newY);
        }
    }
}
