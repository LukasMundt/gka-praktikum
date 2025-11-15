package haw.gka.praktikum;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GraphModelTest {
    /**
     * Testfall: Hinzufügen von Knoten.
     *
     * Erwartung:
     *  - Sowohl das Hinzufügen über addNode("a") als auch über addNodes(Node) funktioniert.
     */
    @Test
    public void testAddingNodes() {
        GraphModel graph = new GraphModel();

        //using addNode()
        Node a = graph.addNode("a");
        Node b = graph.addNode("b");

        //using addNodes()
        Node k = new Node("k");
        Node l = new Node("l");
        graph.addNodes(k, l);

        Node z = new Node("z");

        assertEquals(4, graph.getNodes().size());
        assertTrue(graph.getNodes().contains(a));
        assertTrue(graph.getNodes().contains(b));
        assertTrue(graph.getNodes().contains(k));
        assertFalse(graph.getNodes().contains(z));
    }

    /**
     * Testfall: Hinzufügen einer Kante.
     *
     * Erwartung:
     *  - Beide beteiligten Knoten sind im Graph vorhanden.
     *  - Genau eine Kante wurde hinzugefügt.
     */
    @Test
    public void testAddingEdges() {
        GraphModel graph = new GraphModel();
        Node a = graph.addNode("a");
        Node b = graph.addNode("b");

        Edge ab = new Edge(a, b, true);
        graph.addEdges(ab);

        assertEquals(2, graph.getNodes().size());
        assertEquals(1, graph.getEdges().size());
        assertTrue(graph.getEdges().contains(ab));
    }

    /**
     * Testfall: Indizieren eines Knotens.
     *
     * Erwartung:
     *  - Der Index wird korrekt gespeichert.
     *  - Der Lookup über getIndexOfNode() funktioniert.
     */
    @Test
    public void testIndexNode() {
        GraphModel graph = new GraphModel();
        Node a = Node.getNode("a");
        graph.addNodes(a);
        graph.addNode("b");
        graph.addEdges(new Edge(a, Node.getNode("b"), true));

        graph.indexNode(a, 5);

        assertEquals(5, graph.getIndexOfNode(a));
    }

    /**
     * Testfall: Abfrage des Index für nicht indizierte Knoten.
     *
     * Szenario:
     *  - Ein Knoten ("a") wird angelegt, aber nicht indiziert.
     *  - Ein weiterer Knoten ("b") wird NICHT zum Graphen hinzugefügt,
     *    ist also für den Graphen völlig unbekannt.
     *
     * Erwartung:
     *  - Für beide Knoten soll getIndexOfNode() den Wert -1 liefern.
     *    (-1 signalisiert: Knoten ist nicht indiziert bzw. nicht bekannt)
     */
    @Test
    public void testGetIndexForUnindexedNode(){
        GraphModel graph = new GraphModel();
        Node a = graph.addNode("a");

        assertEquals(-1, graph.getIndexOfNode(a));
        assertEquals(-1, graph.getIndexOfNode(Node.getNode("b")));
    }

    /**
     * Testfall: Ermitteln unindizierter Nachbarn.
     *
     * Ablauf:
     *  - a → b (gerichtet)
     *  - Zuerst ist keiner der beiden indiziert.
     *
     * Erwartung:
     *  - b hat keine unindizierten Nachbarn (a ist nicht erreichbar, da gerichtete Kante).
     *  - Nach dem Indizieren von a hat a genau einen unindizierten Nachbarn (b).
     */
    @Test
    public void testGetUnindexedNeighbors(){
        GraphModel graph = new GraphModel();
        Node a = Node.getNode("a");
        Node b = Node.getNode("b");
        graph.addNodes(a, b);
        graph.addEdges(new Edge(a, b, true));

        assertEquals(0, graph.getUnindexedNeighbors(b).size());

        graph.indexNode(a, 5);

        assertEquals(5, graph.getIndexOfNode(a));
        assertEquals(1, graph.getUnindexedNeighbors(a).size());
    }

    /**
     * Testfall: Reverse-Nachbarn.
     *
     * Testgraph:
     *  a → b
     *
     * Erwartung:
     *  - Reverse-Nachbarn von b: {a}
     *  - Reverse-Nachbarn von a: {}
     */
    @Test
    public void testGetReversedNeighbors(){
        GraphModel graph = new GraphModel();
        Node a = Node.getNode("a");
        Node b = Node.getNode("b");
        Node c = Node.getNode("c");
        graph.addNodes(a, b, c);
        graph.addEdges(new Edge(a, b, true));

        assertEquals(1, graph.getReverseNeighbors(b).size());
        assertEquals(0, graph.getReverseNeighbors(a).size());
        assertTrue(graph.getReverseNeighbors(c).isEmpty());
    }

    /**
     * Testfall: Reverse-Nachbarn mit Indexfilterung.
     *
     * Testgraph:
     *  a → b
     *
     * Erwartung:
     *  - Wenn a den Index 5 besitzt, dann liefert getReverseNeighborWithIndex(b, 5) → a.
     */
    @Test
    public void testGetReversedNeighborsWithIndex(){
        GraphModel graph = new GraphModel();
        Node a = Node.getNode("a");
        Node b = Node.getNode("b");
        graph.addNodes(a, b);
        graph.addEdges(new Edge(a, b, true));

        graph.indexNode(a, 5);

        assertEquals(a, graph.getReverseNeighborWithIndex(b, 5));
    }
}
