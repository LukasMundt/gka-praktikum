package haw.gka.praktikum;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class BreadthFirstSearchTest {
    /**
     *  a   c
     *  |   |
     *  b   d
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
     *     b
     *    ^ ^
     *   /   \
     *  a     c
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

    @Test
    public void testNonExistingNodes() {
        GraphModel graphModel = new GraphModel();
        graphModel.addNode("a");
        graphModel.addNode("b");

        List<Node> result = BreadthFirstSearch.search(graphModel, Node.getNode("a"), Node.getNode("z"));
        assertTrue(result.isEmpty());
    }
}
