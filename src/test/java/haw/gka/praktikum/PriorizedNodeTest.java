package haw.gka.praktikum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PriorizedNodeTest {

    /**
     * Testet die Getter-Methoden der PrioritizedNode-Klasse.
     */
    @Test
    public void testGetter() {
        Node a = Node.getNode("a");
        Node b = Node.getNode("b");
        Edge edge = new Edge(a,b,false,1);

        PrioritizedNode prioNode1 = new PrioritizedNode(a,null, 0);
        PrioritizedNode prioNode2 = new PrioritizedNode(b,edge, edge.getWeight());

        assertEquals(a, prioNode1.getNode());
        assertEquals(b, prioNode2.getNode());

        assertNull(prioNode1.getConnectingEdge());
        assertEquals(edge, prioNode2.getConnectingEdge());

        assertEquals(0, prioNode1.getPriority());
        assertEquals(edge.getWeight(), prioNode2.getPriority());
    }

    /**
     * Testet, dass Equals nur auf Basis von Knoten gleichheit auch mit unterschiedlichen Kanten und Prio true liefert
     */
    @Test
    public void testEquals() {
        Node a = Node.getNode("a");
        Node b = Node.getNode("b");
        Edge edge = new Edge(a,b,false,1);

        PrioritizedNode prioNode1 = new PrioritizedNode(a,null, 0);
        PrioritizedNode prioNode2 = new PrioritizedNode(a,edge, edge.getWeight());
        PrioritizedNode prioNode3 = new PrioritizedNode(b,null, 0);

        assertEquals(prioNode1, prioNode2);
        assertNotEquals(prioNode1, prioNode3);
    }

    /**
     * Testet, dass hashCode nur auf Basis von Knoten (auch mit unterschiedlichen Kanten und Prio) gleichen Wert liefert
     */
    @Test
    public void testHashCode() {
        Node a = Node.getNode("a");
        Node b = Node.getNode("b");
        Edge edge = new Edge(a,b,false,1);

        PrioritizedNode prioNode1 = new PrioritizedNode(a,null, 0);
        PrioritizedNode prioNode2 = new PrioritizedNode(a,edge, edge.getWeight());

        assertEquals(prioNode1.hashCode(), prioNode2.hashCode());
    }

    /**
     * Testet, dass natürliche Ordnung auf Basis von Prio entsteht
     */
    @Test
    public void testCompareTo() {
        Node a = Node.getNode("a");

        PrioritizedNode prioNode1 = new PrioritizedNode(a,null, 0);
        PrioritizedNode prioNode2 = new PrioritizedNode(a,null, 1);

        assertEquals(Float.compare(0,1), prioNode1.compareTo(prioNode2));
        assertEquals(Float.compare(1,0), prioNode2.compareTo(prioNode1));
    }
}
