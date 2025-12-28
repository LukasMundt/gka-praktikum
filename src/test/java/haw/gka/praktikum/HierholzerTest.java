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
public class HierholzerTest {
    Hierholzer hierholzer = new Hierholzer();
    String eulerkreis;
    String keinEulerkreisUngeradeGrade;
    String keinEulerkreisGeradeGrade;
    String unzusammenhängend;
    String gerichtet;

    GraphIn graphReader;
    GraphModel expected;

    @BeforeEach
    void setup() throws IOException {
        eulerkreis = "src/test/java/resources/Eulerkreis.gka";
        keinEulerkreisUngeradeGrade = "src/test/java/resources/undirected.gka";
        keinEulerkreisGeradeGrade = "src/test/java/resources/keinEulerkreis" +
                ".gka"; //ist auch unzusammenhängend
        unzusammenhängend = "src/test/java/resources/TestFileSolo.gka"; //hat
        // Soloknoten
        gerichtet = "src/test/java/resources/directed.gka";
        graphReader = new GraphIn();
        expected = new GraphModel();
    }

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

        // assertThrows(NullPointerException.class,
        //        () ->  //enter method on(graph));
    }


}
