package haw.gka.praktikum;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class BreadthFirstSearchTest {
    /**
     * Testfall: Zwei voneinander getrennte Komponenten.
     *
     *  a   c
     *  |   |
     *  b   d
     *
     * Erwartung:
     * BFS findet keinen Pfad zwischen a und c → Ergebnisliste ist leer.
     */
    @Test
    public void testNoConnection() {
        GraphModel graphModel = new GraphModel();
        graphModel.addNode("a");
        graphModel.addNode("b");
        graphModel.addNode("c");
        graphModel.addNode("d");
        graphModel.addEdges(new Edge(Node.getNode("a"), Node.getNode("b"), false));
        graphModel.addEdges(new Edge(Node.getNode("c"), Node.getNode("d"), false));

        List<Node> result = BreadthFirstSearch.search(graphModel, Node.getNode("a"), Node.getNode("c"));
        assertTrue(result.isEmpty());
    }

    /**
     * Testfall: Gerichteter Graph, in dem der Zielknoten nicht erreichbar ist.
     *
     *     b
     *    ^ ^
     *   /   \
     *  a     c
     *
     * Richtung der Kanten: a→b und c→b
     *
     * Erwartung:
     * Von b aus gelangt man nicht zu c → Ergebnisliste ist leer.
     */
    @Test
    public void testConnectionDirected() {
        GraphModel graphModel = new GraphModel();
        graphModel.addNode("a");
        graphModel.addNode("b");
        graphModel.addNode("c");
        graphModel.addEdges(new Edge(Node.getNode("a"), Node.getNode("b"), true));
        graphModel.addEdges(new Edge(Node.getNode("c"), Node.getNode("b"), true));

        List<Node> result = BreadthFirstSearch.search(graphModel, Node.getNode("b"), Node.getNode("c"));
        assertTrue(result.isEmpty());
    }

    /**
     *  a -- b
     *  |    |
     *  d -- c
     */
    @Test
    public void testFindsShortestPathUndirected() {
        GraphModel graphModel = new GraphModel();
        graphModel.addNode("a");
        graphModel.addNode("b");
        graphModel.addNode("c");
        graphModel.addNode("d");
        graphModel.addEdges(
                new Edge(Node.getNode("a"), Node.getNode("b"), false),
                new Edge(Node.getNode("b"), Node.getNode("c"), false),
                new Edge(Node.getNode("c"), Node.getNode("d"), false),
                new Edge(Node.getNode("a"), Node.getNode("d"), false)
        );

        List<Node> result = BreadthFirstSearch.search(graphModel, Node.getNode("a"), Node.getNode("d"));
        assertEquals(2, result.size());
        assertEquals(Node.getNode("a"), result.get(0));
        assertEquals(Node.getNode("d"), result.get(1));
    }

    @Test
    public void testEmptyGraph() {
        GraphModel graphModel = new GraphModel();
        List<Node> result = BreadthFirstSearch.search(graphModel, Node.getNode("a"), Node.getNode("d"));
        assertTrue(result.isEmpty());
    }

    @Test
    public void testStartIsGoal(){
        GraphModel graphModel = new GraphModel();
        graphModel.addNode("a");

        List<Node> result = BreadthFirstSearch.search(graphModel, Node.getNode("a"), Node.getNode("a"));
        assertEquals(1, result.size());
        assertEquals(Node.getNode("a"), result.getFirst());
    }

    @Test
    public void testLongChainGraph() {
        GraphModel graphModel = new GraphModel();
        for (char c = 'a'; c <= 'z'; c++) {
            graphModel.addNode(String.valueOf(c));
        }
        for (char c = 'a'; c < 'z'; c++) {
            graphModel.addEdges(new Edge(Node.getNode(String.valueOf(c)), Node.getNode(String.valueOf((char)(c + 1))), false));
        }

        List<Node> result = BreadthFirstSearch.search(graphModel, Node.getNode("a"), Node.getNode("z"));
        assertEquals(26, result.size());
        assertEquals(Node.getNode("a"), result.getFirst());
        assertEquals(Node.getNode("z"), result.getLast());
    }

    /**
     * Testfall: Einer der beiden Knoten (Start oder Ziel) existiert nicht im Graphen.
     *
     * Erwartung:
     * Für beide Varianten (Start existiert nicht oder Ziel existiert nicht)
     * darf kein Pfad existieren → Ergebnisliste ist leer.
     */
    @Test
    public void testNonExistingNodes() {
        GraphModel graphModel = new GraphModel();
        graphModel.addNode("a");
        graphModel.addNode("b");

        // Ziel existiert nicht
        List<Node> result = BreadthFirstSearch.search(graphModel, Node.getNode("a"), Node.getNode("z"));
        assertTrue(result.isEmpty());

        // Start existiert nicht
        List<Node> result2 = BreadthFirstSearch.search(graphModel, Node.getNode("z"), Node.getNode("a"));
        assertTrue(result2.isEmpty());

        // Start und Ziel existieren nicht
        List<Node> result3 = BreadthFirstSearch.search(graphModel, Node.getNode("z"), Node.getNode("y"));
        assertTrue(result3.isEmpty());
    }
}
