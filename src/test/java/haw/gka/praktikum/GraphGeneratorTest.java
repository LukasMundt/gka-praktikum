package haw.gka.praktikum;

import org.junit.jupiter.api.Test;

import java.util.Set;

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

        assertEquals(3, genGraph.getEdges().size());
        assertNotEquals(2, genGraph.getEdges().size());
        assertNotEquals(4, genGraph.getEdges().size());
        assertEquals(3, genGraph.getNodes().size());
        assertNotEquals(4, genGraph.getNodes().size());

        assertEquals(30, genGraph2.getNodes().size());
        assertEquals(5, genGraph2.getEdges().size());

        assertEquals(3000, genGraph3.getNodes().size());
        assertEquals(5000, genGraph3.getEdges().size());
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

    @Test
    void testVollstaendig() {
        GraphModel genGraph = generator.generateGraph(4, 6);
        assertEquals(6, genGraph.getEdges().size());

        GraphModel genGraph2 = generator.generateGraph(30, 435, false, true);
        assertEquals(30, genGraph2.getNodes().size());
        assertEquals(435, genGraph2.getEdges().size());
    }

    @Test
    void testConntected() {
        GraphModel genGraph1 = generator.generateGraph(30, 304, false, true);
        assertEquals(30, genGraph1.getNodes().size());
        assertEquals(304, genGraph1.getEdges().size());
        assertTrue(genGraph1.isGraphConnected());

        GraphModel genGraph2 = generator.generateGraph(30, 306, false, true);
        assertEquals(30, genGraph2.getNodes().size());
        assertEquals(306, genGraph2.getEdges().size());
        assertTrue(genGraph2.isGraphConnected());
    }

    @Test
    void testAllNodeDegreesEven() {
        GraphModel genGraph = generator.generateGraph(30, 306, false, true);
        assertEquals(306, genGraph.getEdges().size());

        for(Set<Edge> edgesOfNode : genGraph.getAdjacency().values()) {
            int degree = edgesOfNode.size();

            for (Edge edge : edgesOfNode) {
                if(edge.getStart().equals(edge.getEnd())) {
                    degree++;
                }
            }
            assertFalse(degree % 2 != 0);
        }
    }

}
