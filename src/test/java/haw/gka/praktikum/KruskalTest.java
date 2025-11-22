package haw.gka.praktikum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * - JUnit-Tests, die Alg. mit Graphen aus Generator testen (umfassend)
 * - jede Methode aus Kruskal
 * - pos. und neg. Tests
 *
 */
public class KruskalTest {

    String testFileKruskal;

    GraphIn graphReader;
    GraphModel expected;

    @BeforeEach
    void setup() throws IOException {
        testFileKruskal = "src/test/java/resources/Test_Kruskal.gka";
        graphReader = new GraphIn();
        expected = new GraphModel();
    }

    @Test
    void testSearchSpanningTree() throws IOException {
        expected = graphReader.readGraph(testFileKruskal);

        // minSpanningTree: Menge mit: rs, rv, st, uv
    }

    @Test
    void testSortEdges() {
        // sortedEdges from testFileKruskal:
        // e1 = rs, e2 = rv, e3 = st, e4 = sv, e5 = tv, e6 = uv, e7 = tu
    }

    @Test
    void testGetTotalWeight() {
        float expectedWeight = 12;

        // Input für getToTalWeight:
        // r -- s : 2; r -- v : 2; s -- t : 3; u -- v : 5

        float actualWeight = getTotalWeight();

        assertEquals(expectedWeight, actualWeight, "Gewicht muss " +
                "übereinstimmen");
    }
}
