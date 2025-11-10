package haw.gka.praktikum;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GraphConverterTest {
    /**
     *     b
     *    ^ ^
     *   /   \
     *  a     c
     */
    @Test
    public void testDirectedToUndirected() {
        GraphModel graphModel = new GraphModel();
        graphModel.addNode("a");
        graphModel.addNode("b");
        graphModel.addNode("c");
        graphModel.addEdges(new Edge(Node.getNode("a"), Node.getNode("b"), true));
        graphModel.addEdges(new Edge(Node.getNode("c"), Node.getNode("b"), true));

        GraphModel converted = GraphConverter.getUndirectedGraphModel(graphModel);

        List<Node> result = BreadthFirstSearch.search(converted, Node.getNode("b"), Node.getNode("c"));
        assertFalse(result.isEmpty());
        for (Edge edge : converted.getEdges()) {
            assertFalse(edge.isDirected());
        }
    }

    /**
     *  a
     *  |
     *  b
     */
    @Test
    public void testUndirectedToDirected() {
        GraphModel graphModel = new GraphModel();
        graphModel.addNode("a");
        graphModel.addNode("b");
        graphModel.addEdges(new Edge(Node.getNode("a"), Node.getNode("b"), false));

        GraphModel converted = GraphConverter.getDirectedGraphModel(graphModel);

        List<Node> result = BreadthFirstSearch.search(converted, Node.getNode("a"), Node.getNode("b"));
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());

        assertEquals(2, converted.getEdges().size());
        for (Edge edge : converted.getEdges()) {
            assertTrue(edge.isDirected());
        }
    }
}
