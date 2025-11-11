package haw.gka.praktikum;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GraphModelTest {
    @Test
    public void testAddingNodes() {
        GraphModel graph = new GraphModel();
        graph.addNode("a");
        graph.addNodes(Node.getNode("b"));

        assertEquals(2, graph.getNodes().size());
    }

    @Test
    public void testAddingEdges() {
        GraphModel graph = new GraphModel();
        graph.addNode("a");
        graph.addNode("b");
        graph.addEdges(new Edge(Node.getNode("a"), Node.getNode("b"), true));

        assertEquals(2, graph.getNodes().size());
        assertEquals(1, graph.getEdges().size());
    }

    @Test
    public void testIndexNode() {
        GraphModel graph = new GraphModel();
        Node a = Node.getNode("a");
        graph.addNodes(a);
        graph.addNode("b");
        graph.addEdges(new Edge(a, Node.getNode("b"), true));

        assertEquals(2, graph.getNodes().size());
        assertEquals(1, graph.getEdges().size());

        graph.indexNode(a, 5);

        assertEquals(5, graph.getIndexOfNode(a));
    }

    @Test
    public void testGetUnindexedNeighbors(){
        GraphModel graph = new GraphModel();
        Node a = Node.getNode("a");
        Node b = Node.getNode("b");
        graph.addNodes(a, b);
        graph.addEdges(new Edge(a, b, true));

        assertEquals(0, graph.getUnindexedNeighbors(b).size());

        graph.indexNode(a, 5);

        assertEquals(5, graph.getIndexOfNode(a));
        assertEquals(1, graph.getUnindexedNeighbors(a).size());
    }
}
