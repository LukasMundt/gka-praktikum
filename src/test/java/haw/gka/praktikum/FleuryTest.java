package haw.gka.praktikum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * - JUnit-Tests, die Alg. mit Graphen a) aus Dateien und b) aus Generator
 * testen (umfassend)
 * - jeder Schritt, den der Alg macht -> eine Methode
 * - pos. und neg. Tests
 */
public class FleuryTest {
    Fleury fleury = new Fleury();
    String eulerkreis;
    GraphIn graphReader;
    GraphModel expected;

    @BeforeEach
    void setup() throws IOException {
        eulerkreis = "src/test/java/resources/Eulerkreis.gka";
        //s. für weitere Dateien HierholzerTest!
        graphReader = new GraphIn();
        expected = new GraphModel();
    }

    //TODO prüfen, ob FLeury (noch) andere VOraussetzungen hat, hier von
    // Hierholzer kopiert
    @Test
    void testGraphIsUndirected() {
    }

    @Test
    void testGraphIsConnected() {
    }

    @Test
    void testGraphHasEvenGrades() {
    }

    @Test
    void testThrowsNullPointerException() {
        GraphModel graph = null;

        //assertThrows(NullPointerException.class,
        //      () -> fleury //enter method on(graph));
    }

}
