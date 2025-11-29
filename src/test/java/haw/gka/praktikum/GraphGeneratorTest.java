package haw.gka.praktikum;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GraphGeneratorTest {

    GraphGenerator generator = new GraphGenerator();

    /**
     * Test für die korrekte Erstellung einer Anzahl Knoten und Kanten
     */
    @Test
    void testGraphGeneration() {


        GraphModel genGraph = generator.generateGraph(3, 3);
        GraphModel genGraph2 = generator.generateGraph(30, 5);
        GraphModel genGraph3 = generator.generateGraph(3000, 5000);

        assertEquals(genGraph.getEdges().size(), 3);
        assertNotEquals(genGraph.getEdges().size(), 2);
        assertNotEquals(genGraph.getEdges().size(), 4);
        assertEquals(genGraph.getNodes().size(), 3);
        assertNotEquals(genGraph.getNodes().size(), 4);

        assertEquals(genGraph2.getNodes().size(), 30);
        assertEquals(genGraph2.getEdges().size(), 5);

        assertEquals(genGraph3.getNodes().size(), 3000);
        assertEquals(genGraph3.getEdges().size(), 5000);


    }

    /**
     * Stellt sicher, dass ungültige Eingaben einen Fehler erzeugen
     */
    @Test
    void testThrow() {

        assertThrows(IllegalArgumentException.class,
                () -> generator.generateGraph(-4, 3));
        assertThrows(IllegalArgumentException.class,
                () -> generator.generateGraph(0, 3));
        assertThrows(IllegalArgumentException.class,
                () -> generator.generateGraph(2, 3));

    }

}
