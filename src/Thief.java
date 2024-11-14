public class Thief extends Character {
    private static Thief instance;

    //Costruttore privato perché Thief è un Singleton
    private Thief(int[][] matrix, int startX, int startY) {
        super(matrix, startX, startY);
        matrix[startY][startX] = 6; // Imposta la posizione iniziale del ladro (arancione)
    }

    //Quando non so ancora se la istanza è stata creata
    public static Thief getInstance(int[][] matrix, int startX, int startY) {
        if (instance == null) {
            instance = new Thief(matrix, startX, startY);
        }
        return instance;
    }

    //Sono sicuro che la istanza sia già stata creata
    public static Thief getInstance() {
        if (instance == null) {
            throw new IllegalStateException("L'istanza del ladro non è stata ancora creata.");
        }
        return instance;
    }

    public static void resetInstance() {
        instance = null;
    }

    //Metodo che permette di capire come aggiornare la matrice a seconda del movimento del ladro
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
