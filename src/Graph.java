import java.util.*;

//Grafo basato sulla matrice di gioco dove ogni cella è rappresentato da un nodo del grafo
public class Graph {
    private final int[][] matrix;
    private final int rows, cols;
    private final List<List<Node>> adjList; //Lista di adiacenza del grafo

    public Graph(int[][] matrix) {
        this.matrix = matrix;
        this.rows = matrix.length;
        this.cols = matrix[0].length;
        this.adjList = new ArrayList<>();

        //Riempe la lista di adiacenza vuota per tutti i nodi possibili
        for (int i = 0; i < rows * cols; i++) {
            adjList.add(new ArrayList<>());
        }
        buildGraph();
    }

    //Metodo per costruire il grafo
    private void buildGraph() {
        //Per ogni cella
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                if (matrix[y][x] != 1) { // Se non è un muro
                    int node = y * cols + x; //Identificativo del nodo
                    addEdges(node, x, y);
                }
            }
        }
    }

    //Metodo che aggiunge i vicini del nodo corrente nella lista di adiacenza
    private void addEdges(int node, int x, int y) {
        int[][] directions = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        //Per ogni direzione
        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];
            if (isValid(newX, newY) && matrix[newY][newX] != 1) {
                int neighbor = newY * cols + newX;
                adjList.get(node).add(new Node(neighbor, 1)); // Peso dell'arco 1
            }
        }
    }

    //Metodo per controllare che non si sia usciti dalla matrice
    private boolean isValid(int x, int y) {
        return x >= 0 && x < cols && y >= 0 && y < rows;
    }

    //Metodo che implemente l'algoritmo di Dijkstra per trovare il percorso più breve tra due node
    public List<Integer> shortestPath(int startX, int startY, int endX, int endY) {
        // Calcola gli indici unidimensionali dei nodi di partenza e di fine
        int start = startY * cols + startX;
        int end = endY * cols + endX;

        // Array per memorizzare le distanze minime dai nodi
        int[] dist = new int[rows * cols];
        Arrays.fill(dist, Integer.MAX_VALUE);
        // Distanza nodo iniziale a 0
        dist[start] = 0;

        // Crea coda di priorità per gestire i nodi
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.weight));
        pq.add(new Node(start, 0));

        //Array prev per memorizzare i nodi precedenti nel percorso
        int[] prev = new int[rows * cols];
        Arrays.fill(prev, -1);

        //Ciclo principale dell'Algoritmo di Dijkstra, che continua finché la coda di priorità non è vuota
        while (!pq.isEmpty()) {
            //Estrae il nodo con la distanza minore
            Node current = pq.poll();
            int u = current.vertex;

            //Interrompe se ha raggiunto l'ultimo nodo
            if (u == end) break;

            //Esamina ogni nodo vicino del nodo corrente
            for (Node neighbor : adjList.get(u)) {
                int v = neighbor.vertex;
                int weight = neighbor.weight;

                //Se trova una distanza minore, aggiorna la distanza, imposta il predecessore e aggiunge il vicino nella coda di priorità
                if (dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    prev[v] = u;
                    pq.add(new Node(v, dist[v]));
                }
            }
        }

        //Costruisce il percorso più breve partendo dal nodo finale e seguendo i predecessori
        List<Integer> path = new ArrayList<>();
        for (int at = end; at != -1; at = prev[at]) {
            path.add(at);
        }
        //Inverte la lista per ottenere il percorso corretto
        Collections.reverse(path);

        //Restituisce il percorso o una lista vuota se il percorso non esiste
        return path.size() > 1 ? path : Collections.emptyList();
    }

    //Classe interna per rappresentare il nodo di un grafo
    private static class Node {
        int vertex; //Identificatore nodo
        int weight; //Peso dell'arco

        Node(int vertex, int weight) {
            this.vertex = vertex;
            this.weight = weight;
        }
    }
}
