package haw.gka.praktikum;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * - JUnit-Tests, die Alg. mit kleinen Graphen aus Dateien
 * testen (umfassend)
 * - pos. und neg. Tests
 */

//Input und erwarteter Output, Vergleich
public class HierholzerTest_smallGraphs {
    Hierholzer hierholzer = new Hierholzer();

    GraphIn graphReader = new GraphIn();

    String path_eulerkreis = "src/test/java/resources/Eulerkreis.gka";
    String path_keinEulerkreisUngeradeGrade = "src/test/java/resources" +
            "/undirected.gka";
    String path_keinEulerkreisGeradeGrade = "src/test/java/resources" +
            "/keinEulerkreis" +
            ".gka"; //ist auch unzusammenhängend
    String path_unzusammenhängend = "src/test/java/resources/TestFileSolo.gka";
    //hat Soloknoten
    String path_gerichtet = "src/test/java/resources/directed.gka";


    @Test
    void testSearchEulerCircle_Error_Directed() throws IOException {
        //wirf einen Fehler, wenn du einen gerichteteten Graphen bekommst
        GraphModel gerichtet = graphReader.readGraph(path_gerichtet);

        //nachgucken, wie Fehler geworfen werden
        assertThrows(IOException.class, (Executable) hierholzer.searchEulerCircle(gerichtet));
    }

    //Test für directed replizieren für unconnected und uneven Knotengrade
    // und Solo-Knoten
    @Test
    void testGraphIsUnconnected() {
    }

    @Test
    void testGraphHasUnevenGrades() {
    }

    @Test
    void testThrowsIOException() throws IOException {
        GraphModel graph_is_null = null;

        assertThrows(IOException.class,
                (Executable) hierholzer.searchEulerCircle(graph_is_null));
    }

    //TODO utils Methode, die prüft, ob gegebener Graph ein Eulerkreis ist,
    // gibt true false zurück
    @Test
    void testFindsEulerCircle_Small() throws IOException {
        GraphModel candidate = graphReader.readGraph(path_eulerkreis);

        GraphModel actual_eulerkreis = hierholzer.searchEulerCircle(candidate);
        //TODO mit utils Methode prüfen, ob EUlerkreis korrekt gebaut wurde
        // assertTrue(checkEulerkreis(actual_eulerkreis));
    }

    //weitere Tests mit Randfällen für weitere Tests
    @Test
    void testFindsEulerCircle_Big() {
    }

    // Test mit Kantengewichten
    // Test mit Städtenamen
}
