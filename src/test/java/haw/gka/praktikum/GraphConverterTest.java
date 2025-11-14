package haw.gka.praktikum;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class GraphConverterTest {
    /**
     *     b
     *    ^ ^
     *   /   \
     *  a     c
     */
    @Test
    public void testDirectedToUndirected() {
        GraphModel graphModel = new GraphModel();
        graphModel.addNode("a");
        graphModel.addNode("b");
        graphModel.addNode("c");
        graphModel.addEdges(new Edge(Node.getNode("a"), Node.getNode("b"), true));
        graphModel.addEdges(new Edge(Node.getNode("c"), Node.getNode("b"), true));

        GraphModel converted = GraphConverter.getUndirectedGraphModel(graphModel);

        List<Node> result = BreadthFirstSearch.search(converted, Node.getNode("b"), Node.getNode("c"));
        assertFalse(result.isEmpty());
        this.assertEdgesDirectionValue(converted.getEdges(), false);
    }

    /**
     *  a
     *  |
     *  b
     */
    @Test
    public void testUndirectedToDirected() {
        GraphModel graphModel = new GraphModel();
        graphModel.addNode("a");
        graphModel.addNode("b");
        graphModel.addEdges(new Edge(Node.getNode("a"), Node.getNode("b"), false));

        GraphModel converted = GraphConverter.getDirectedGraphModel(graphModel);

        List<Node> result = BreadthFirstSearch.search(converted, Node.getNode("a"), Node.getNode("b"));
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());

        assertEquals(2, converted.getEdges().size());
        this.assertEdgesDirectionValue(converted.getEdges(), true);
    }

    /**
     * Testet das Verhalten bei einem komplett leeren Graphen.
     *
     * Erwartung:
     * - Keine Knoten, keine Kanten, weder in gerichteter noch ungerichteter Umwandlung.
     */
    @Test
    public void testEmptyGraph() {
        GraphModel graphModel = new GraphModel();
        GraphModel converted = GraphConverter.getUndirectedGraphModel(graphModel);
        assertEquals(0, converted.getEdges().size());
        assertEquals(0, converted.getNodes().size());

        GraphModel converted2 = GraphConverter.getDirectedGraphModel(graphModel);
        assertEquals(0, converted2.getEdges().size());
        assertEquals(0, converted2.getNodes().size());
    }

    /**
     * Testet die Konvertierung eines gemischten Graphen (gerichtet + ungerichtet)
     * in einen vollständig gerichteten Graphen.
     *
     * <pre>
     *     b
     *    / ^
     *   /   \
     *  a     c
     * </pre>
     *
     * Erwartung:
     * - Ungerichtete Kante a–b wird zu zwei gerichteten Kanten.
     * - Gerichtete Kante c→b bleibt erhalten.
     * - Insgesamt 3 gerichtete Kanten.
     */
    @Test
    public void testMixedGraphToDirected() {
        GraphModel graphModel = new GraphModel();
        graphModel.addNode("a");
        graphModel.addNode("b");
        graphModel.addNode("c");
        graphModel.addEdges(new Edge(Node.getNode("a"), Node.getNode("b"), false));
        graphModel.addEdges(new Edge(Node.getNode("c"), Node.getNode("b"), true));

        GraphModel converted = GraphConverter.getDirectedGraphModel(graphModel);

        assertEquals(3, converted.getEdges().size());
        this.assertEdgesDirectionValue(converted.getEdges(), true);
    }

    /**
     * Testet die Konvertierung eines gemischten Graphen (gerichtet + ungerichtet)
     * in einen vollständig ungerichteten Graphen.
     *
     * <pre>
     *     b
     *    / ^
     *   /   \
     *  a     c
     * </pre>
     *
     * Erwartung:
     * - Gerichtete Kante c→b wird ungerichtet.
     * - Ungerichtete Kante bleibt ungerichtet.
     * - Insgesamt 2 ungerichtete Kanten.
     */
    @Test
    public void testMixedGraphToUndirected() {
        GraphModel graphModel = new GraphModel();
        graphModel.addNode("a");
        graphModel.addNode("b");
        graphModel.addNode("c");

        // eine ungerichtete und eine gerichtete Kante
        graphModel.addEdges(new Edge(Node.getNode("a"), Node.getNode("b"), false));
        graphModel.addEdges(new Edge(Node.getNode("c"), Node.getNode("b"), true));

        GraphModel converted = GraphConverter.getUndirectedGraphModel(graphModel);

        assertEquals(2, converted.getEdges().size());
        this.assertEdgesDirectionValue(converted.getEdges(), false);
    }

    /**
     * Testet, ob zwei entgegengesetzte gerichtete Kanten
     * korrekt zu einer einzigen ungerichteten Kante zusammengeführt werden.
     *
     * <pre>
     *  a → b
     *  a ← b
     * </pre>
     *
     * Erwartung:
     * - Nach der Umwandlung existiert nur eine ungerichtete Kante.
     */
    @Test
    public void testDirectedGraphToUndirectedWithDoubleEdges() {
        GraphModel graphModel = new GraphModel();
        graphModel.addNode("a");
        graphModel.addNode("b");

        // zwei entgegengesetzte gerichtete Kanten
        graphModel.addEdges(new Edge(Node.getNode("a"), Node.getNode("b"), true));
        graphModel.addEdges(new Edge(Node.getNode("b"), Node.getNode("a"), true));

        GraphModel converted = GraphConverter.getUndirectedGraphModel(graphModel);

        // beide Richtungen sollen zu einer einzigen ungerichteten Kante werden
        assertEquals(1, converted.getEdges().size());

        this.assertEdgesDirectionValue(converted.getEdges(), false);
    }

    /**
     * Testet die Konvertierung einer ungerichteten Schleifen-Kante (a -- a)
     * in gerichtete Kanten.
     *
     * Eine Schleife (Loop) hat Start- und Endknoten identisch. Bei der
     * Umwandlung von ungerichtet zu gerichtet darf daher nur **eine einzige**
     * gerichtete Kante entstehen (a -> a), da die „Gegenrichtung“ identisch wäre
     * und somit keine neue Information liefert.
     *
     * Erwartung:
     * - Der Graph enthält genau eine Kante.
     */
    @Test
    public void testUndirectedLoopToSameNodeToDirectedEdges() {
        GraphModel graphModel = new GraphModel();
        Node a = graphModel.addNode("a");

        graphModel.addEdges(new Edge(a, a, false));

        // Es darf nur eine Schleifen-Kante existieren
        assertEquals(1, graphModel.getEdges().size());
    }

    /**
     * Prüft, ob alle Kanten in der übergebenen Edge-Menge den erwarteten
     * Richtungswert besitzen (gerichtet oder ungerichtet).
     *
     * @param edges                Die zu überprüfenden Kanten.
     * @param expectedDirectedValue Erwarteter Wert von {@code isDirected()}.
     *                              true  -> alle Kanten müssen gerichtet sein
     *                              false -> alle Kanten müssen ungerichtet sein
     */
    private void assertEdgesDirectionValue(Set<Edge> edges, boolean expectedDirectedValue) {
        for (Edge edge : edges) {
            assertEquals(expectedDirectedValue, edge.isDirected());
        }
    }
}
