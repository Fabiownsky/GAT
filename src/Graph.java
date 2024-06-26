import java.util.*;

public class Graph {
    private final int[][] matrix;
    private final int rows, cols;
    private final List<List<Node>> adjList;

    public Graph(int[][] matrix) {
        this.matrix = matrix;
        this.rows = matrix.length;
        this.cols = matrix[0].length;
        this.adjList = new ArrayList<>();

        for (int i = 0; i < rows * cols; i++) {
            adjList.add(new ArrayList<>());
        }
        buildGraph();
    }

    private void buildGraph() {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                if (matrix[y][x] != 1) { // Non è un muro
                    int node = y * cols + x;
                    addEdges(node, x, y);
                }
            }
        }
    }

    private void addEdges(int node, int x, int y) {
        int[][] directions = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];
            if (isValid(newX, newY) && matrix[newY][newX] != 1) {
                int neighbor = newY * cols + newX;
                adjList.get(node).add(new Node(neighbor, 1)); // Peso dell'arco 1
            }
        }
    }

    private boolean isValid(int x, int y) {
        return x >= 0 && x < cols && y >= 0 && y < rows;
    }

    public List<Integer> shortestPath(int startX, int startY, int endX, int endY) {
        int start = startY * cols + startX;
        int end = endY * cols + endX;

        int[] dist = new int[rows * cols];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.weight));
        pq.add(new Node(start, 0));

        int[] prev = new int[rows * cols];
        Arrays.fill(prev, -1);

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int u = current.vertex;

            if (u == end) break;

            for (Node neighbor : adjList.get(u)) {
                int v = neighbor.vertex;
                int weight = neighbor.weight;

                if (dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    prev[v] = u;
                    pq.add(new Node(v, dist[v]));
                }
            }
        }

        List<Integer> path = new ArrayList<>();
        for (int at = end; at != -1; at = prev[at]) {
            path.add(at);
        }
        Collections.reverse(path);
        return path.size() > 1 ? path : Collections.emptyList();
    }

    private static class Node {
        int vertex;
        int weight;

        Node(int vertex, int weight) {
            this.vertex = vertex;
            this.weight = weight;
        }
    }
}
