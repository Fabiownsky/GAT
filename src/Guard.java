public class Guard extends Character {

    public Guard(int[][] matrix, int startX, int startY) {
        super(matrix, startX, startY);
        matrix[startY][startX] = 5; // Imposta la posizione iniziale della guardia (blu)
    }

    @Override
    public void move(int dx, int dy) {
        int newX = x + dx;
        int newY = y + dy;
        if (canMove(newX, newY)) {
            matrix[y][x] = 0; // Supponiamo che il pavimento sia bianco (0)
            x = newX;
            y = newY;
            matrix[y][x] = 5; // Aggiorna la posizione della guardia nella matrice
        }
    }
}
