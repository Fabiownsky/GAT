public class Thief extends Character {

    public Thief(int[][] matrix, int startX, int startY) {
        super(matrix, startX, startY);
        matrix[startY][startX] = 6; // Imposta la posizione iniziale del ladro (arancione)
    }

    @Override
    public void move(int dx, int dy) {
        int newX = x + dx;
        int newY = y + dy;
        if (canMove(newX, newY)) {
            matrix[y][x] = 0; // Supponiamo che il pavimento sia bianco (0)
            x = newX;
            y = newY;
            matrix[y][x] = 6; // Aggiorna la posizione del ladro nella matrice
        }
    }
}
