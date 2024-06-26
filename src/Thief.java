public class Thief extends Character {
    private static Thief instance;

    private Thief(int[][] matrix, int startX, int startY) {
        super(matrix, startX, startY);
        matrix[startY][startX] = 6; // Imposta la posizione iniziale del ladro (arancione)
    }

    public static Thief getInstance(int[][] matrix, int startX, int startY) {
        if (instance == null) {
            instance = new Thief(matrix, startX, startY);
        }
        return instance;
    }

    public static void resetInstance() {
        instance = null;
    }

    @Override
    public void move(int dx, int dy) {
        int newX = x + dx;
        int newY = y + dy;
        if (canMove(newX, newY)) {
            if (matrix[newY][newX] == 7) { // Verifica se il ladro raggiunge l'uscita
                x = newX;
                y = newY;
            } else {
                matrix[y][x] = 0; // Supponiamo che il pavimento sia bianco (0)
                x = newX;
                y = newY;
                matrix[y][x] = 6; // Aggiorna la posizione del ladro nella matrice
            }
        }
    }
}
