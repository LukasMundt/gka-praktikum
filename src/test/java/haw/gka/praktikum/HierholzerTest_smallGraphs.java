package haw.gka.praktikum;

import haw.gka.praktikum.euler.Hierholzer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static haw.gka.praktikum.euler.Euler.checkEulerCircle;
import static haw.gka.praktikum.euler.Hierholzer.executeHierholzer;
import static haw.gka.praktikum.euler.Hierholzer.searchEulerCircle;
import static org.junit.jupiter.api.Assertions.*;


/**
 * - JUnit-Tests, die Alg. mit kleinen Graphen aus Dateien
 * testen (umfassend)
 * - pos. und neg. Tests
 */

public class HierholzerTest_smallGraphs {

    GraphIn graphReader = new GraphIn();
    //folgende Pfade führen zu Graphen mit Eulerkreis
    String path_eulercircle = "src/test/java/resources/euler/Eulerkreis.gka";
    String path_euler_cities = "src/test/java/resources/euler/cities.gka";
    String path_euler_sorted = "src/test/java/resources/euler" +
            "/Euler_already_sorted.gka";
    String path_euler_minimum = "src/test/java/resources/euler/minimum.gka";

    //folgende Pfade führen zu Graphen ohne Eulerkreis
    String path_unevenDegrees = "src/test/java/resources" +
            "/undirected.gka";
    String path_evenDegrees_unconnected = "src/test/java/resources/euler" +
            "/keinEulerkreis.gka";
    String path_unconnected_solo = "src/test/java/resources/euler" +
            "/TestFileSolo.gka";
    String path_directed = "src/test/java/resources/euler/directed.gka";
    String path_too_small = "src/test/java/resources/euler/smallGraph.gka";

    //Problem 1: Algorithmus in Hilfsmethode ausgelagert, Test muss angepasst
    // werden für korrekten Aufruf
    //Problem 2: falsche Exception geprüft
    @Test
    void testSearchEulerCircle_Error_GraphIsDirected() throws IllegalArgumentException, IOException {
        GraphModel directed = graphReader.readGraph(path_directed);


        assertThrows(IllegalArgumentException.class,
                () -> executeHierholzer(directed));
    }

    @Test
    void testSearchEulerCircle_Error_graphIsTooSmall() throws IllegalArgumentException, IOException {
        GraphModel tooSmall = graphReader.readGraph(path_too_small);

        assertThrows(IllegalArgumentException.class,
                () -> executeHierholzer(tooSmall));
    }

    @Test
    void testSearchEulerCircle_Error_GraphIsUnconnected() throws IllegalArgumentException, IOException {
        GraphModel unconnected = graphReader.readGraph(path_evenDegrees_unconnected);

        assertThrows(IllegalArgumentException.class,
                () -> searchEulerCircle(unconnected));
    }

    @Test
    void testSearchEulerCircle_Error_GraphHasSoloNodes() throws IllegalArgumentException, IOException {
        GraphModel unconnected_solo =
                graphReader.readGraph(path_unconnected_solo);

        assertThrows(IllegalArgumentException.class,
                () -> searchEulerCircle(unconnected_solo));
    }

    @Test
    void testSearchEulerCircle_Error_GraphHasUnevenDegrees() throws IllegalArgumentException, IOException {
        GraphModel unevenDegrees = graphReader.readGraph(path_unevenDegrees);

        assertThrows(IllegalArgumentException.class,
                () -> searchEulerCircle(unevenDegrees));
    }

    @Test
    void testThrowsIOException() throws IllegalArgumentException, IOException {
        GraphModel graph_is_null = null;

        assertThrows(IllegalArgumentException.class,
                () -> searchEulerCircle(graph_is_null));
    }

    /**
     * Die folgenden Tests testen den tatsächlichen Algorithmus und sollen
     * Eulerkreise finden
     *
     * @throws IOException
     */
    @Test
    void testFindsEulerCircle_Small() throws IOException {
        GraphModel candidate = graphReader.readGraph(path_eulercircle);

        List<Edge> edgeList = Hierholzer.executeHierholzer(candidate);

        GraphModel actual_eulerkreis = searchEulerCircle(candidate);

        assertTrue(checkEulerCircle(actual_eulerkreis, edgeList));
    }

    @Test
    void testFindsEulerCircle_AlreadySorted() throws IOException {
        GraphModel candidate = graphReader.readGraph(path_euler_sorted);

        List<Edge> edgeList = Hierholzer.executeHierholzer(candidate);

        GraphModel actual_eulerkreis = searchEulerCircle(candidate);

        assertTrue(checkEulerCircle(actual_eulerkreis, edgeList));
    }

    @Test
    void testFindsEulerCircle_Nodenames_Weights() throws IOException {
        GraphModel candidate = graphReader.readGraph(path_euler_cities);

        List<Edge> edgeList = Hierholzer.executeHierholzer(candidate);

        GraphModel actual_eulerkreis = searchEulerCircle(candidate);

        assertTrue(checkEulerCircle(actual_eulerkreis, edgeList));
    }

    //diesen Test nachträglich hinzugefügt, um auch die Methode
    // searchEulerCircle zu testen
    @Test
    void testSearchEulerCircle_ReturnsCorrectGraph() throws IOException {
        GraphModel original = graphReader.readGraph(path_eulercircle);
        GraphModel result = searchEulerCircle(original);

        assertNotNull(result);
        assertEquals(original.getEdges().size(), result.getEdges().size(),
                "Der Ergebnisgraph muss die gleiche Anzahl an Kanten haben");
    }

}
