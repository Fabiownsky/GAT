public abstract class Character {
    protected int x;
    protected int y;
    protected int[][] matrix;

    public Character(int[][] matrix, int startX, int startY) {
        this.matrix = matrix;
        this.x = startX;
        this.y = startY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract void move(int dx, int dy);

    protected boolean canMove(int newX, int newY) {
        return matrix[newY][newX] != 1; // Verifica che la nuova posizione non sia un muro
    }
}
