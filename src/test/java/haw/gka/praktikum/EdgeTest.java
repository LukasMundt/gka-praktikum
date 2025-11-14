package haw.gka.praktikum;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EdgeTest {
    /**
     * Testfall: Ungerichtete Kante (Nachbarn)
     *
     * Erwartung:
     *  - Beide Richtungen sind erlaubt.
     *  - Beide Knoten sind wechselseitig erreichbar.
     *  - getOtherNode darf in beide Richtungen funktionieren.
     */
    @Test
    public void testUndirectedNeighbourTest(){
        Node a = new Node("start");
        Node b = new Node("end");
        Edge edge = new Edge(a, b, false);

        assertTrue(edge.isBReachableFromA(a, b));
        assertTrue(edge.isBReachableFromA(b, a));

        assertTrue(edge.isOtherNodeReachableFromA(a));
        assertTrue(edge.isOtherNodeReachableFromA(b));

        assertTrue(edge.isAReachableFromOtherNode(a));
        assertTrue(edge.isAReachableFromOtherNode(b));
    }

    /**
     * Testfall: Gerichtete Kante (Nachbarn)
     *
     * Kante: a --> b
     *
     * Erwartung:
     *  - Nur von a nach b erreichbar.
     *  - Von a aus ist der andere Knoten (b) erreichbar
     *  - Nur B ist von A aus erreichbar
     */
    @Test
    public void testDirectedNeighbourTest(){
        Node a = new Node("start");
        Node b = new Node("end");
        Edge edge = new Edge(a, b, true);

        assertTrue(edge.isBReachableFromA(a, b));
        assertFalse(edge.isBReachableFromA(b, a));

        assertTrue(edge.isOtherNodeReachableFromA(a));
        assertFalse(edge.isOtherNodeReachableFromA(b));

        assertTrue(edge.isAReachableFromOtherNode(b));
        assertFalse(edge.isAReachableFromOtherNode(a));
    }

    /**
     * Testfall: Anderen Knoten der Kante ausgeben
     *
     * Erwartung:
     * Für ungerichtete und gerichtete Kanten wird stets der jeweils andere Knoten zurückgegeben.
     */
    @Test
    public void testGetOtherNodeTest(){
        Node a = new Node("start");
        Node b = new Node("end");
        Edge edge = new Edge(a, b, false);

        assertEquals(b, edge.getOtherNode(a));
        assertEquals(a, edge.getOtherNode(b));

        // directed
        Node aDir = new Node("start");
        Node bDir = new Node("end");
        Edge edgeDir = new Edge(aDir, bDir, true);

        assertEquals(bDir, edgeDir.getOtherNode(aDir));
        assertEquals(aDir, edgeDir.getOtherNode(bDir));
    }

    /**
     * Testfall: Ungerichtete Kanten mit gleichen Start- und Endknoten vergleichen.
     *
     * Erwartung:
     *  - Die Kanten gelten als gleich.
     *  - Der Hashcode muss ebenfalls identisch sein.
     */
    @Test
    public void testEquals_UndirectedSameNodes() {
        Node a = new Node("a");
        Node b = new Node("b");

        Edge e1 = new Edge(a, b, false);
        Edge e2 = new Edge(a, b, false);

        assertEquals(e1, e2);
        assertEquals(e1.hashCode(), e2.hashCode());
    }

    /**
     * Testfall: Ungerichtete Kanten, aber Start- und Endknoten wurden vertauscht.
     *
     * Erwartung:
     *  - Da ungerichtet, müssen beide Kanten dennoch gleich sein.
     *  - Die Reihenfolge spielt keine Rolle.
     */
    @Test
    public void testEquals_UndirectedSwappedNodes() {
        Node a = new Node("a");
        Node b = new Node("b");

        Edge e1 = new Edge(a, b, false);
        Edge e2 = new Edge(b, a, false);  // Reihenfolge vertauscht

        assertEquals(e1, e2);
        assertEquals(e1.hashCode(), e2.hashCode());
    }

    /**
     * Testfall: Ungerichtete Kanten mit unterschiedlichen Endknoten.
     *
     * Erwartung:
     *  - Unterschiedliche Knoten → Kanten sind nicht gleich.
     */
    @Test
    public void testNotEquals_UndirectedDifferentNodes() {
        Node a = new Node("a");
        Node b = new Node("b");
        Node c = new Node("c");

        Edge e1 = new Edge(a, b, false);
        Edge e2 = new Edge(a, c, false);

        assertNotEquals(e1, e2);
    }

    /**
     * Testfall: Gerichtete Kanten mit identischer Richtung und identischen Knoten.
     *
     * Erwartung:
     *  - e1 und e2 sind gleich
     *  - Hashcodes müssen übereinstimmen
     */
    @Test
    public void testEquals_DirectedSameDirection() {
        Node a = new Node("a");
        Node b = new Node("b");

        Edge e1 = new Edge(a, b, true);
        Edge e2 = new Edge(a, b, true);

        assertEquals(e1, e2);
        assertEquals(e1.hashCode(), e2.hashCode());
    }

    /**
     * Testfall: Gerichtete Kanten, aber Richtung vertauscht (a->b vs. b->a).
     *
     * Erwartung:
     *  - Gerichtete Kanten sind NICHT gleich, wenn die Richtung unterschiedlich ist.
     */
    @Test
    public void testNotEquals_DirectedOppositeDirection() {
        Node a = new Node("a");
        Node b = new Node("b");

        Edge e1 = new Edge(a, b, true);
        Edge e2 = new Edge(b, a, true);  // andere Richtung

        assertNotEquals(e1, e2);
    }

    /**
     * Testfall: Eine gerichtete und eine ungerichtete Kante mit gleichen Knoten.
     *
     * Erwartung:
     *  - Unterschiedlicher Directed-Status → nicht gleich.
     */
    @Test
    public void testNotEquals_DirectedVsUndirected() {
        Node a = new Node("a");
        Node b = new Node("b");

        Edge e1 = new Edge(a, b, true);
        Edge e2 = new Edge(a, b, false);

        assertNotEquals(e1, e2);
    }

    /**
     * Testfall: Gleiches Gewicht und gleiche Knoten.
     *
     * Erwartung:
     *  - Kanten sind gleich.
     */
    @Test
    public void testEquals_WeightedSameWeight() {
        Node a = new Node("a");
        Node b = new Node("b");

        Edge e1 = new Edge(a, b, false, true, 5.5f, null);
        Edge e2 = new Edge(a, b, false, true, 5.5f, null);

        assertEquals(e1, e2);
    }

    /**
     * Testfall: Unterschiedliche Gewichte.
     *
     * Erwartung:
     *  - Gewicht ist unterschiedlich → Kanten nicht gleich.
     */
    @Test
    public void testNotEquals_WeightedDifferentWeight() {
        Node a = new Node("a");
        Node b = new Node("b");

        Edge e1 = new Edge(a, b, false, true, 3.0f, null);
        Edge e2 = new Edge(a, b, false, true, 7.0f, null);

        assertNotEquals(e1, e2);
    }

    /**
     * Testfall: Kanten mit identischem Namen.
     *
     * Erwartung:
     *  - Beide Kanten sind gleich.
     */
    @Test
    public void testEquals_WithNameSameName() {
        Node a = new Node("a");
        Node b = new Node("b");

        Edge e1 = new Edge(a, b, false, true, 1.0f, "edge1");
        Edge e2 = new Edge(a, b, false, true, 1.0f, "edge1");

        assertEquals(e1, e2);
    }

    /**
     * Testfall: Unterschiedliche Namen.
     *
     * Erwartung:
     *  - Kanten sind NICHT gleich.
     */
    @Test
    public void testNotEquals_WithNameDifferentName() {
        Node a = new Node("a");
        Node b = new Node("b");

        Edge e1 = new Edge(a, b, false, true, 1.0f, "A");
        Edge e2 = new Edge(a, b, false, true, 1.0f, "B");

        assertNotEquals(e1, e2);
    }

    /**
     * Testfall: Ein Name null, der andere nicht.
     *
     * Erwartung:
     *  - Nicht gleich.
     */
    @Test
    public void testNotEquals_NameNullOnOneSide() {
        Node a = new Node("a");
        Node b = new Node("b");

        Edge e1 = new Edge(a, b, false, true, 1.0f, null);
        Edge e2 = new Edge(a, b, false, true, 1.0f, "name");

        assertNotEquals(e1, e2);
    }

    /**
     * Testfall: Eine von vielen möglichen ungleichen Kombinationen:
     * Unterschied in directed/weighted/name etc.
     *
     * Erwartung:
     *  - Schon ein abweichendes Attribut → nicht gleich.
     */
    @Test
    public void testNotEquals_DifferentDirectedWeightedNameCombinations() {
        Node a = new Node("a");
        Node b = new Node("b");

        Edge e1 = new Edge(a, b, false, true, 5.0f, "x");
        Edge e2 = new Edge(a, b, true, true, 5.0f, "x");  // anderer isDirected

        assertNotEquals(e1, e2);
    }

    /**
     * Testfall: Gleichheit mit sich selber.
     *
     * Erwartung:
     *  - Ein Objekt muss immer gleich zu sich selbst sein.
     */
    @Test
    public void testEquals_SelfComparison() {
        Node a = new Node("a");
        Node b = new Node("b");

        Edge e = new Edge(a, b, false);

        assertEquals(e, e);
    }

    /**
     * Testfall: Vergleich gegen null oder andere Klassen.
     *
     * Erwartung:
     *  - equals(null) → false
     *  - equals(object anderer Klasse) → false
     */
    @Test
    public void testNotEquals_ComparedToNullOrOtherClass() {
        Node a = new Node("a");
        Node b = new Node("b");

        Edge e = new Edge(a, b, false);

        assertNotEquals(null, e);
        assertNotEquals("notAnEdge", e);
    }
}
