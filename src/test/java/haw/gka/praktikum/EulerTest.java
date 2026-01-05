package haw.gka.praktikum;

import haw.gka.praktikum.euler.Euler;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EulerTest {

    @Test
    public void test_euler_circle() {
        GraphModel graph = new GraphModel();

        Node a = Node.getNode("a");
        Node b = Node.getNode("b");
        Node c = Node.getNode("c");
        Node d = Node.getNode("d");

        Edge ab = new Edge(a, b, false);
        Edge bc = new Edge(b, c, false);
        Edge cd = new Edge(c, d, false);
        Edge da = new Edge(d, a, false);

        graph.addNodes(a, b, c, d);
        graph.addEdges(ab, bc, cd, da);

        List<Edge> tour = new LinkedList<>();
        tour.add(ab);
        tour.add(bc);
        tour.add(cd);
        tour.add(da);

        assertTrue(Euler.checkEulerCircle(graph, tour));
    }

    @Test
    public void test_euler_circle_negative() {
        GraphModel graph = new GraphModel();

        Node a = Node.getNode("a");
        Node b = Node.getNode("b");
        Node c = Node.getNode("c");
        Node d = Node.getNode("d");

        Edge ab = new Edge(a, b, false);
        Edge bc = new Edge(b, c, false);
        Edge cd = new Edge(c, d, false);

        graph.addNodes(a, b, c, d);
        graph.addEdges(ab, bc, cd);

        List<Edge> tour = new LinkedList<>();
        tour.add(ab);
        tour.add(bc);
        tour.add(cd);

        assertFalse(Euler.checkEulerCircle(graph, tour));
    }

}
