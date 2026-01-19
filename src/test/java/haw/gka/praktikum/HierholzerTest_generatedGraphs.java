package haw.gka.praktikum;

import haw.gka.praktikum.euler.Euler;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static haw.gka.praktikum.euler.Hierholzer.searchEulerCircle;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * - JUnit-Tests, die Alg. mit großen generierten Graphen testen (umfassend)
 * - pos. und neg. Tests
 */
public class HierholzerTest_generatedGraphs {
    GraphGenerator generator = new GraphGenerator();

    //erweiterter Generator mit kleinem, überschaubarem Graph testen
    @Test
    void test_modifiedGenerator_evenDegrees() throws IOException {
        GraphModel genGraph = generator.generateGraph(8, 12, true, true);

        GraphModel actual_eulerkreis = searchEulerCircle(genGraph);

        assertTrue(Euler.checkEulerCircle(actual_eulerkreis));
    }

    /**
     * ab hier die vorgegebenen Werte für die generierten Graphen aus der
     * Aufgabe
     *
     * @throws IOException
     */
    @Test
    void test_100edges_32nodes() throws IOException {
        GraphModel genGraph = generator.generateGraph(32, 100, true, true);

        GraphModel actual_eulerkreis = searchEulerCircle(genGraph);

        assertTrue(Euler.checkEulerCircle(actual_eulerkreis));
    }

    @Test
    void test_300edges_64nodes() throws IOException {
        GraphModel genGraph = generator.generateGraph(64, 300, true, true);

        GraphModel actual_eulerkreis = searchEulerCircle(genGraph);

//        assertTrue(Euler.checkEulerCircle(actual_eulerkreis));
    }

    @Test
    void test_1000edges_128nodes() throws IOException {
        GraphModel genGraph = generator.generateGraph(128, 1000, true, true);

        GraphModel actual_eulerkreis = searchEulerCircle(genGraph);

//        assertTrue(Euler.checkEulerCircle(actual_eulerkreis));
    }

    @Test
    void test_3000edges_256nodes() throws IOException {
        GraphModel genGraph = generator.generateGraph(256, 3000, true, true);

        GraphModel actual_eulerkreis = searchEulerCircle(genGraph);

//        assertTrue(Euler.checkEulerCircle(actual_eulerkreis));
    }

    @Test
    void test_10_000edges_512nodes() throws IOException {
        GraphModel genGraph = generator.generateGraph(512, 10_000, true, true);

        GraphModel actual_eulerkreis = searchEulerCircle(genGraph);

//        assertTrue(Euler.checkEulerCircle(actual_eulerkreis));
    }

    @Test
    void test_30_000edges_1024nodes() throws IOException {
        GraphModel genGraph = generator.generateGraph(1024, 30_000, true, true);

        GraphModel actual_eulerkreis = searchEulerCircle(genGraph);

//        assertTrue(Euler.checkEulerCircle(actual_eulerkreis));
    }

    @Test
    void test_100_000edges_2048nodes() throws IOException {
        GraphModel genGraph = generator.generateGraph(2048, 100_000, true, true);

        GraphModel actual_eulerkreis = searchEulerCircle(genGraph);

//        assertTrue(Euler.checkEulerCircle(actual_eulerkreis));
    }

    @Test
    void test_300_000edges_4096nodes() throws IOException {
        GraphModel genGraph = generator.generateGraph(4096, 300_000, true, true);

        GraphModel actual_eulerkreis = searchEulerCircle(genGraph);

//        assertTrue(Euler.checkEulerCircle(actual_eulerkreis));
    }

    @Test
    void test_1_000_000edges_8192nodes() throws IOException {
        GraphModel genGraph = generator.generateGraph(8192, 1_000_000, true, true);

        GraphModel actual_eulerkreis = searchEulerCircle(genGraph);

//        assertTrue(Euler.checkEulerCircle(actual_eulerkreis));
    }

    @Test
    void test_3_000_000edges_16_384nodes() throws IOException {
        GraphModel genGraph = generator.generateGraph(16_384, 3_000_000, true, true);

        GraphModel actual_eulerkreis = searchEulerCircle(genGraph);

//        assertTrue(Euler.checkEulerCircle(actual_eulerkreis));
    }

    @Test
    void test_10_000_000edges_32_768nodes() throws IOException {
        GraphModel genGraph = generator.generateGraph(32_768, 10_000_000, true, true);

        GraphModel actual_eulerkreis = searchEulerCircle(genGraph);

//        assertTrue(Euler.checkEulerCircle(actual_eulerkreis));
    }
}
