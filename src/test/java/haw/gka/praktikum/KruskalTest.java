package haw.gka.praktikum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * - JUnit-Tests, die Alg. mit Graphen aus Generator testen (umfassend)
 * - jede Methode aus Kruskal
 * - pos. und neg. Tests
 * // TODO Tests gegen Generator und negative Tests
 */
public class KruskalTest {
    Kruskal kruskal = new Kruskal();
    String testFileKruskal;

    GraphIn graphReader;
    GraphModel expected;

    HashSet<Edge> edges;

    //Beispiel aus Vorlesung
    Node nodeR = Node.getNode("r");
    Node nodeS = Node.getNode("s");
    Node nodeT = Node.getNode("t");
    Node nodeU = Node.getNode("u");
    Node nodeV = Node.getNode("v");

    //Kanten bereits nach Gewicht und Alphabet sortiert in Liste schreiben
    Edge e1 = new Edge(nodeR, nodeS, false, true, 2.0f, null);
    Edge e2 = new Edge(nodeV, nodeR, false, true, 2.0f, null);
    Edge e3 = new Edge(nodeS, nodeT, false, true, 3.0f, null);
    Edge e4 = new Edge(nodeS, nodeV, false, true, 3.0f, null);
    Edge e5 = new Edge(nodeV, nodeT, false, true, 4.0f, null);
    Edge e6 = new Edge(nodeU, nodeV, false, true, 5.0f, null);
    Edge e7 = new Edge(nodeT, nodeU, false, true, 6.0f, null);

    @BeforeEach
    void setup() throws IOException {
        testFileKruskal = "src/test/java/resources/Test_Kruskal.gka";
        graphReader = new GraphIn();
        expected = new GraphModel();
    }

    @Test
    void testSearchSpanningTree() throws IOException {
        // minSpanningTree: Menge mit: rs, rv, st, uv
        List<Edge> expectedSpanningTree = new ArrayList<>();
        expectedSpanningTree.add(e1); //rs
        expectedSpanningTree.add(e2); //rv
        expectedSpanningTree.add(e3); //st
        expectedSpanningTree.add(e6); //uv

        GraphModel actualGraph = graphReader.readGraph(testFileKruskal);

        List<Edge> actualSpanningTree = kruskal.searchSpanningTree(actualGraph);

        assertEquals(expectedSpanningTree, actualSpanningTree, "Inhalte " +
                "müssen gleich sein");
    }


    @Test
    void testSortEdges() throws IOException {
        // sortedEdges from testFileKruskal:
        // [vr: 2.0, rs: 2.0, sv: 3.0, st: 3.0, vt: 4.0, uv: 5.0, tu: 6.0]

        List<Edge> expectedSortedEdges = new ArrayList<>();
        expectedSortedEdges.add(e1); // rs
        expectedSortedEdges.add(e2); // rv
        expectedSortedEdges.add(e3); //sv
        expectedSortedEdges.add(e4); // st
        expectedSortedEdges.add(e5); // vt
        expectedSortedEdges.add(e6); // uv
        expectedSortedEdges.add(e7); // tu

        //actual use of method
        GraphModel graph = graphReader.readGraph(testFileKruskal);
        HashSet<Edge> edges = graph.getEdges();
        List<Edge> actualSortedEdges = kruskal.sortEdges(edges);

        assertNotNull(actualSortedEdges, "Ergebnis darf nicht null sein");
        assertEquals(expectedSortedEdges, actualSortedEdges, "Die " +
                "Kantengewichte sollen von klein nach groß sortiert sein.");

    }

    @Test
    void testGetTotalWeight() {
        float expectedWeight = 12.0f;
        // Input: r -- s : 2; r -- v : 2; s -- t : 3; u -- v : 5

        List<Edge> actualSpanningTree = new ArrayList<>();
        actualSpanningTree.add(e1); //rs
        actualSpanningTree.add(e2); //rv
        actualSpanningTree.add(e4); //st
        actualSpanningTree.add(e6); //uv

        float actualWeight = kruskal.getTotalWeight(actualSpanningTree);

        assertEquals(expectedWeight, actualWeight, "Gewicht muss " +
                "übereinstimmen");
    }

    @Test
    void testThrowsNullPointerException() {
        GraphModel graph = null;
        HashSet<Edge> edges = null;
        List<Edge> minSpanningTree = null;

        assertThrows(NullPointerException.class,
                () -> kruskal.searchSpanningTree(graph));
        assertThrows(NullPointerException.class,
                () -> kruskal.sortEdges(edges));
        assertThrows(NullPointerException.class,
                () -> kruskal.getTotalWeight(minSpanningTree));
    }

}
