public abstract class Character {
    protected int[][] matrix;
    protected int x, y;

    public Character(int[][] matrix, int startX, int startY) {
        this.matrix = matrix;
        this.x = startX;
        this.y = startY;
    }

    protected boolean canMove(int x, int y) {
        return x >= 0 && x < matrix[0].length && y >= 0 && y < matrix.length && matrix[y][x] != 1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int[][] getMatrix() {
        return matrix;
    }
}
