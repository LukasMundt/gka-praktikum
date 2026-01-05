package haw.gka.praktikum;

import haw.gka.praktikum.LogResources.LogResources;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class PrimTest {

    GraphIn reader;
    GraphModel graph;

    @BeforeEach
    void setup() {
        reader = new GraphIn();
    }

    /** Beispiel aus der Vorlesung */
    @Test
    void testPrimSimpleGraph() throws IOException {
        GraphModel g = new GraphModel();

        Node r = Node.getNode("r");
        Node s = Node.getNode("s");
        Node t = Node.getNode("t");
        Node u = Node.getNode("u");
        Node v = Node.getNode("v");

        g.addNodes(r, s, t, u, v);

        Edge e1 = new Edge(r, s, false, true, 2, null);
        Edge e2 = new Edge(v, r, false, true, 2, null);
        Edge e3 = new Edge(s, t, false, true, 3, null);
        Edge e4 = new Edge(s, v, false, true, 3, null);
        Edge e5 = new Edge(v, t, false, true, 4, null);
        Edge e6 = new Edge(u, v, false, true, 5, null);
        Edge e7 = new Edge(t, u, false, true, 6, null);

        g.addEdges(e1, e2, e3, e4, e5, e6, e7);

        GraphModel mst = Prim.getMinimalSpanningTree(g);

        HashSet<Edge> expectedEdges = new HashSet<>(List.of(e1, e2, e3, e6));

        assertEquals(g.getNodes(), mst.getNodes(), "Alle Knoten müssen enthalten sein");
        assertEquals(expectedEdges, mst.getEdges(), "MST-Kanten müssen korrekt sein");
        assertEquals(12, mst.getTotalWeight());
    }

    /** GRAPH MIT MEHREREN KOMPONENTEN → Prim liefert Forest */
    @Test
    void testPrimWithDisconnectedGraph() {
        GraphModel g = new GraphModel();

        Node a = Node.getNode("a");
        Node b = Node.getNode("b");
        Node c = Node.getNode("c");

        Node x = Node.getNode("x");
        Node y = Node.getNode("y");

        g.addNodes(a, b, c, x, y);

        g.addEdges(
                new Edge(a, b, false, true, 1, null),
                new Edge(b, c, false, true, 2, null),
                new Edge(x, y, false, true, 3, null)
        );

        GraphModel mst = Prim.getMinimalSpanningTree(g);

        // Zwei Teilbäume: {a,b,c} und {x,y}
        assertEquals(5, mst.getNodes().size(), "Alle Knoten müssen enthalten sein");
        assertEquals(3, mst.getEdges().size(), "Forest muss 2 Kanten für ABC und 1 Kante für XY enthalten");
        assertEquals(6, mst.getTotalWeight(), "Der Graph muss ein Gesamtgewicht von 6 haben.");
    }

    /** Hat jeder Knoten in Ausgangs- und Ergebnisgraph gleiche Erreichbarkeit */
    @Test
    void testPrimEveryNodeReachable() {
        GraphModel g;
        try {
            g = (new GraphIn()).readGraph("src/test/java/resources/graph03.gka");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertNotNull(g);

        GraphModel mst = Prim.getMinimalSpanningTree(g);

        long before = System.nanoTime();

        Optional<Node> node = g.getNodes().stream().findFirst();
        assertTrue(node.isPresent());

        GraphModel tempGraph = new GraphModel(new HashSet<>(g.getNodes()), new HashSet<>(g.getEdges()));
        GraphModel traversedGraph = BreadthFirstSearch.traverseGraph(
                tempGraph,
                node.get(),
                null,
                false
                );

        GraphModel traversedMst = BreadthFirstSearch.traverseGraph(
                new GraphModel(new HashSet<>(mst.getNodes()), new HashSet<>(mst.getEdges())),
                node.get(),
                null,
                false
        );


        for (Node n : g.getNodes()) {
            int indexGraph = traversedGraph.getIndexOfNode(n);
            int indexMst = traversedMst.getIndexOfNode(n);

            assertEquals(indexGraph == -1, indexMst == -1);
            assertEquals(indexGraph >= 1, indexMst >= 1);
        }

        long after = System.nanoTime();
        System.out.println("Test hat gedauert: "+((after - before)/1_000_000)+"ms");
    }

    /** leerer Graph */
    @Test
    void testPrimOnEmptyGraph() {
        GraphModel g = new GraphModel();

        assertThrows(IllegalArgumentException.class,
                () -> Prim.getMinimalSpanningTree(g),
                "Prim muss bei leerem Graph fehlschlagen");
    }

    /** Graph mit nur einem Knoten */
    @Test
    void testPrimOnSingleNode() {
        GraphModel g = new GraphModel();
        Node a = Node.getNode("a");
        g.addNodes(a);

        GraphModel mst = Prim.getMinimalSpanningTree(g);

        assertEquals(1, mst.getNodes().size());
        assertEquals(0, mst.getEdges().size());
    }

    @Test
    void tetPrimWithSelfLoop() {
        GraphModel g = new GraphModel();
        Node a = Node.getNode("a");
        g.addNodes(a);
        g.addEdges(new Edge(a,a,false,10));

        GraphModel mst = Prim.getMinimalSpanningTree(g);

        assertEquals(1, mst.getNodes().size());
        assertEquals(0, mst.getEdges().size());
    }

    // ------------------------------------------------------------
    // 7. PERFORMANCE-TEST (optional)
    // ------------------------------------------------------------
//    @Test
//    void testPrimPerformance() {
//        GraphGenerator generator = new GraphGenerator();
//        GraphModel g = generator.generateGraph(20000, 21000);
//
//        long start = System.currentTimeMillis();
//        GraphModel mst = Prim.getMinimalSpanningTree(g);
//        long duration = System.currentTimeMillis() - start;
//
//        System.out.println("Prim duration: " + duration + "ms");
//
//        assertTrue(duration < 2000, "Prim sollte bei 5000/7000 in <2s laufen");
//        assertEquals(g.getNodes(), mst.getNodes());
//
//        Kruskal k = new Kruskal();
//        k.searchSpanningTree(g);
//    }
}
