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

    @Override
    public void move(int dx, int dy) {
        int newX = x + dx;
        int newY = y + dy;
        if (canMove(newX, newY)) {
            if (matrix[newY][newX] == 7) { // 7 è il codice per il colore marrone (uscita)
                // Ladro raggiunge l'uscita
                return;
            }
            matrix[y][x] = 0; // Supponiamo che il pavimento sia bianco (0)
            x = newX;
            y = newY;
            matrix[y][x] = 6; // Aggiorna la posizione del ladro nella matrice
        }
    }

    // Metodo per reimpostare l'istanza per i test o nuovi giochi
    public static void resetInstance() {
        instance = null;
    }
}
