package haw.gka.praktikum;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

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

    @Test
    public void testEmptyGraph() {
        GraphModel graphModel = new GraphModel();
        GraphModel converted = GraphConverter.getUndirectedGraphModel(graphModel);
        assertEquals(0, converted.getEdges().size());
        assertEquals(0, converted.getNodes().size());

        GraphModel converted2 = GraphConverter.getDirectedGraphModel(graphModel);
        assertEquals(0, converted2.getEdges().size());
        assertEquals(0, converted2.getNodes().size());
    }

    /**
     *     b
     *    / ^
     *   /   \
     *  a     c
     */
    @Test
    public void testMixedGraphToDirected() {
        GraphModel graphModel = new GraphModel();
        graphModel.addNode("a");
        graphModel.addNode("b");
        graphModel.addNode("c");
        graphModel.addEdges(new Edge(Node.getNode("a"), Node.getNode("b"), false));
        graphModel.addEdges(new Edge(Node.getNode("c"), Node.getNode("b"), true));

        GraphModel converted = GraphConverter.getDirectedGraphModel(graphModel);

        assertEquals(3, converted.getEdges().size());
        for (Edge edge : converted.getEdges()) {
            assertTrue(edge.isDirected());
        }
    }

    /**
     *     b
     *    / ^
     *   /   \
     *  a     c
     */
    @Test
    public void testMixedGraphToUndirected() {
        GraphModel graphModel = new GraphModel();
        graphModel.addNode("a");
        graphModel.addNode("b");
        graphModel.addNode("c");
        graphModel.addEdges(new Edge(Node.getNode("a"), Node.getNode("b"), false));
        graphModel.addEdges(new Edge(Node.getNode("c"), Node.getNode("b"), true));

        GraphModel converted = GraphConverter.getUndirectedGraphModel(graphModel);

        assertEquals(2, converted.getEdges().size());
        for (Edge edge : converted.getEdges()) {
            assertFalse(edge.isDirected());
        }
    }

    @Test
    public void testDirectedGraphToUndirectedWithDoubleEdges() {
        GraphModel graphModel = new GraphModel();
        graphModel.addNode("a");
        graphModel.addNode("b");
        graphModel.addEdges(new Edge(Node.getNode("a"), Node.getNode("b"), true));
        graphModel.addEdges(new Edge(Node.getNode("b"), Node.getNode("a"), true));

        GraphModel converted = GraphConverter.getUndirectedGraphModel(graphModel);

        assertEquals(1, converted.getEdges().size());
        for (Edge edge : converted.getEdges()) {
            assertFalse(edge.isDirected());
        }
    }
}
