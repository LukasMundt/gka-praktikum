package haw.gka.praktikum;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EdgeTest {
    @Test
    public void testUndirectedNeighbourTest(){
        Node a = new Node("start");
        Node b = new Node("end");
        Edge edge = new Edge(a, b, false);

        assertTrue(edge.isBReachableFromA(a, b));
        assertTrue(edge.isBReachableFromA(b, a));

        assertTrue(edge.isOtherNodeReachableFromA(a));
        assertTrue(edge.isOtherNodeReachableFromA(b));

        assertTrue(edge.isAReachableFromOtherNode(a));
        assertTrue(edge.isAReachableFromOtherNode(b));
    }

    @Test
    public void testDirectedNeighbourTest(){
        Node a = new Node("start");
        Node b = new Node("end");
        Edge edge = new Edge(a, b, true);

        assertTrue(edge.isBReachableFromA(a, b));
        assertFalse(edge.isBReachableFromA(b, a));

        assertTrue(edge.isOtherNodeReachableFromA(a));
        assertFalse(edge.isOtherNodeReachableFromA(b));

        assertTrue(edge.isAReachableFromOtherNode(b));
        assertFalse(edge.isAReachableFromOtherNode(a));
    }

    @Test
    public void testGetOtherNodeTest(){
        Node a = new Node("start");
        Node b = new Node("end");
        Edge edge = new Edge(a, b, false);

        assertEquals(b, edge.getOtherNode(a));
        assertEquals(a, edge.getOtherNode(b));

        // directed
        Node aDir = new Node("start");
        Node bDir = new Node("end");
        Edge edgeDir = new Edge(aDir, bDir, true);

        assertEquals(bDir, edgeDir.getOtherNode(aDir));
        assertEquals(aDir, edgeDir.getOtherNode(bDir));
    }
}
