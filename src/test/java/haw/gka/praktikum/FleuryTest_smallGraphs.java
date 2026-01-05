package haw.gka.praktikum;

import haw.gka.praktikum.euler.Fleury;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static haw.gka.praktikum.euler.Euler.checkEulerCircle;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * - JUnit-Tests, die Alg. mit kleinen Graphen aus Dateien
 * testen (umfassend)
 * - pos. und neg. Tests
 */

public class FleuryTest_smallGraphs {

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


    @Test
    void testSearchEulerCircle_Error_GraphIsDirected() throws IOException {
        GraphModel directed = graphReader.readGraph(path_directed);


        assertThrows(IllegalArgumentException.class,
                () -> Fleury.search(directed));
    }

    @Test
    void testSearchEulerCircle_Error_graphIsTooSmall() throws IOException {
        GraphModel tooSmall = graphReader.readGraph(path_too_small);

        assertThrows(IllegalArgumentException.class,
                () -> Fleury.search(tooSmall));
    }

    @Test
    void testSearchEulerCircle_Error_GraphIsUnconnected() throws IOException {
        GraphModel unconnected = graphReader.readGraph(path_evenDegrees_unconnected);

        assertThrows(IllegalArgumentException.class,
                () -> Fleury.search(unconnected));
    }

    @Test
    void testSearchEulerCircle_Error_GraphHasSoloNodes() throws IOException {
        GraphModel unconnected_solo =
                graphReader.readGraph(path_unconnected_solo);

        assertThrows(IllegalArgumentException.class,
                () -> Fleury.search(unconnected_solo));
    }

    @Test
    void testSearchEulerCircle_Error_GraphHasOddDegrees() throws IOException {
        GraphModel oddDegrees = graphReader.readGraph(path_unevenDegrees);

        assertThrows(IllegalArgumentException.class,
                () -> Fleury.search(oddDegrees));
    }

    @Test
    void testThrowsIOException() throws IOException {
        assertThrows(IllegalArgumentException.class,
                () -> Fleury.search(null));
    }

    /**
     * Die folgenden Tests testen den tatsächlichen Algorithmus und sollen
     * Eulerkreise finden
     *
     * @throws IOException
     */
    @Test
    void testFindsEulerCircle() throws IOException {
        GraphModel graph = graphReader.readGraph(path_eulercircle);

        Fleury fleury = Fleury.search(graph);
        List<Edge> actual_eulerkreis = fleury.getEulerCircleTour();

        assertTrue(checkEulerCircle(graph, actual_eulerkreis));
    }

    @Test
    void testFindsEulerCircle_minimum() throws IOException {
        GraphModel graph = graphReader.readGraph(path_euler_minimum);

        Fleury fleury = Fleury.search(graph);
        List<Edge> actual_eulerkreis = fleury.getEulerCircleTour();

        assertTrue(checkEulerCircle(graph, actual_eulerkreis));
    }

    @Test
    void testFindsEulerCircle_AlreadySorted() throws IOException {
        GraphModel graph = graphReader.readGraph(path_euler_sorted);

        Fleury fleury = Fleury.search(graph);
        List<Edge> actual_eulerkreis = fleury.getEulerCircleTour();

        assertTrue(checkEulerCircle(graph, actual_eulerkreis));
    }

    @Test
    void testFindsEulerCircle_Nodenames_Weights() throws IOException {
        GraphModel graph = graphReader.readGraph(path_euler_cities);

        Fleury fleury = Fleury.search(graph);
        List<Edge> actual_eulerkreis = fleury.getEulerCircleTour();

        assertTrue(checkEulerCircle(graph, actual_eulerkreis));
    }
    //TODO noch weitere Randfälle?
}
