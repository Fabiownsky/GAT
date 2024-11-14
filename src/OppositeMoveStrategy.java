//Questa classe gestisce l'algoritmo del movimento opposto al Ladro
public class OppositeMoveStrategy implements MovementStrategy {

    @Override
    public void move(Guard guard) {

        //Prende l'ultima direzione del Ladro nel gioco e va nella direzione opposta
        Game.Direction lastThiefDirection = guard.getGame().getLastThiefDirection();
        int[] chosenDirection;
        //Creo i casi delle direzioni opposte a quelle del ladro
        int[][] oppositeDirections = {
                {0, 1},  // Opposto di UP
                {0, -1}, // Opposto di DOWN
                {1, 0},  // Opposto di LEFT
                {-1, 0}  // Opposto di RIGHT
        };

        switch (lastThiefDirection) {
            case UP:
                chosenDirection = oppositeDirections[0];
                break;
            case DOWN:
                chosenDirection = oppositeDirections[1];
                break;
            case LEFT:
                chosenDirection = oppositeDirections[2];
                break;
            case RIGHT:
                chosenDirection = oppositeDirections[3];
                break;
            default:
                return;
        }

        //Aggiorno la nuova posizione della guardia
        int newX = guard.getX() + chosenDirection[0];
        int newY = guard.getY() + chosenDirection[1];

            if (guard.canMove(newX, newY)) {
                guard.moveTo(newX, newY);
            }
    }
}
