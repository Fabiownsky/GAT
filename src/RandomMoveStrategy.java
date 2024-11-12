import java.util.Random;

public class RandomMoveStrategy implements MovementStrategy {
    private final Random random = new Random();

    @Override
    public void move(Guard guard) {
        int[][] directions = { {0, -1}, {0, 1}, {-1, 0}, {1, 0} }; // {su, giù, sinistra, destra}
        int[] dir = directions[random.nextInt(directions.length)];
        int newX = guard.getX() + dir[0];
        int newY = guard.getY() + dir[1];

        if (guard.canMove(newX, newY)) {
            guard.moveTo(newX, newY);
        }
    }
}
