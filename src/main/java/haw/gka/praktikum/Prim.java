package haw.gka.praktikum;

import haw.gka.praktikum.LogResources.LogResources;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementierung des Prim-Algorithmus zur Berechnung eines minimalen Spannbaums (MST).
 * <p>
 * Der Algorithmus startet von einem Knoten und erweitert den Spannbaum iterativ,
 * indem immer die Kante mit dem geringsten Gewicht hinzugefügt wird, die einen
 * neuen Knoten mit dem bestehenden Baum verbindet.
 * </p>
 */
public class Prim {
    /** Graph, für den der MST ermittelt werden soll */
    private final GraphModel _inputGraph;
    /** Ergebnis-Graph, hier wird der minimale Spannbaum aufgebaut */
    private final GraphModel _resultGraph;
    /** Prioritäten-Warteschlange der abzuarbeitenden Knoten */
    private final PriorityQueue<PrioritizedNode> _nodePriorityQueue;

    /**
     * Privater Konstruktor zur Initialisierung der Prim-Instanz.
     *
     * @param inputGraph  Der Eingangsgraph, für den der MST berechnet werden soll
     * @param resultGraph Der Graph, in dem der resultierende MST gespeichert wird
     */
    private Prim(GraphModel inputGraph, GraphModel resultGraph) {
        _inputGraph = inputGraph;
        _resultGraph = resultGraph;
        _nodePriorityQueue = new PriorityQueue<>();
    }

    /**
     * Berechnet den minimalen Spannbaum für den gegebenen Graphen.
     * Der Startknoten wird automatisch als erster Knoten des Eingangsgraphen gewählt.
     *
     * @param inputGraph Nicht leerer Graph, für den der MST berechnet werden soll
     * @return GraphModel mit dem minimalen Spannbaum
     * @throws IllegalArgumentException wenn der Eingangsgraph leer ist
     */
    public static GraphModel getMinimalSpanningTree(GraphModel inputGraph) {
        if (inputGraph == null) throw new IllegalArgumentException("Input graph is null");
        if (inputGraph.getNodes().isEmpty()) throw new IllegalArgumentException("Input graph is empty (not null)");

        Node firstNode = inputGraph.getNodes().iterator().next();
        return Prim.getMinimalSpanningTree(inputGraph, firstNode);
    }

    /**
     * Berechnet den minimalen Spannbaum für den gegebenen Graphen,
     * beginnend mit dem spezifizierten Startknoten.
     *
     * @param inputGraph Der Eingangsgraph (not null)
     * @param firstNode  Der Knoten, mit dem der Algorithmus startet (not null, muss in Graphen enthalten sein)
     * @return GraphModel mit dem minimalen Spannbaum
     * @throws IllegalArgumentException wenn inputGraph ist null oder leer, first node ist null oder nicht in inputGraph enthalten
     */
    public static GraphModel getMinimalSpanningTree(GraphModel inputGraph, Node firstNode) {
        if (inputGraph == null) throw new IllegalArgumentException("Input graph is null");
        if (inputGraph.getNodes().isEmpty()) throw new IllegalArgumentException("Input graph is empty (not null)");
        if (firstNode == null) throw new IllegalArgumentException("First node is null");
        if (inputGraph.getNodes().contains(firstNode)) throw new IllegalArgumentException("First node is not contained in graph");

        // Resourcen logging starten
        LogResources.startTask("Prim");

        // Ersten Knoten dem neuen Graphen hinzufügen
        System.out.println("Starte mit Knoten: "+firstNode.toString());
        GraphModel resultGraph = new GraphModel();
        resultGraph.addNodes(firstNode);

        // Fallback Priorität für Knoten berechnen, die noch nicht erreichbar sind
        float fallbackPriority = inputGraph
                .getEdges()
                .stream()
                .map(Edge::getWeight)
                .max(Float::compare)
                .orElse(0f) + 100;

        // Instanz erzeugen, die den Prim-Algorithmus durchführt und Daten speichert
        Prim prim = new Prim(inputGraph, resultGraph);

        // alle Nodes zu priority queue hinzufügen (Nachbarn inkl. Prio und noch nicht erreichbare Knoten mit Fallback Priorität)
        prim._nodePriorityQueue.addAll(prim.getUnincludedNeighborNodesOfAWithPriority(firstNode));
        prim._nodePriorityQueue.addAll(prim.getUnincludedNodesWithFallbackPriority(fallbackPriority));

        // Prim algorithmus ausführen
        prim.executeAlgorithm();

        // Resourcen logging beenden
        LogResources.stopTask("Prim");
        return prim._resultGraph;
    }

    /**
     * Führt die Hauptschleife des Prim-Algorithmus aus.
     */
    private void executeAlgorithm() {
        // solange nicht alle Knoten aus inputGraph in Mst enthalten
        while (!_resultGraph.getNodes().equals(_inputGraph.getNodes())) {
            PrioritizedNode prioNode = _nodePriorityQueue.poll();

            // abbrechen, wenn keine Knoten mehr in Warteschlange
            if (prioNode == null) break;

            // Wenn schon benutzt -> ignorieren (durch poll schon gelöscht)
            if (_resultGraph.getNodes().contains(prioNode.getNode())) {
                continue; // nächste PQ-Eintrag
            }

            // Knoten dem Graphen hinzufügen + wenn vorhanden Kante hinzufügen
            _resultGraph.addNodes(prioNode.getNode());
            if (prioNode.getConnectingEdge() != null) {
                _resultGraph.addEdges(prioNode.getConnectingEdge());
            }

            // alle neu erreichbaren Knoten, die noch nicht im Graphen enthalten sind, priorisiert der Queue hinzufügen
            List<PrioritizedNode> prioritizedNodes = this.getUnincludedNeighborNodesOfAWithPriority(prioNode.getNode());
            _nodePriorityQueue.addAll(prioritizedNodes);
        }
    }


    /**
     * Ermittelt alle Nachbarknoten des Knotens a, die noch nicht im Ergebnisgraphen enthalten sind,
     * und gibt sie mit ihrer Priorität (Kantengewicht) zurück.
     *
     * @param a Der Knoten, dessen Nachbarn ermittelt werden sollen
     * @return Liste von priorisierten Nachbarknoten, die noch nicht im Spannbaum enthalten sind
     */
    private List<PrioritizedNode> getUnincludedNeighborNodesOfAWithPriority(Node a) {
        Set<Edge> edgesToNeighbors = _inputGraph.getAdjacency().get(a);

        // wenn keine mit a verbundenen Kanten gefunden -> leere Antwort
        if (edgesToNeighbors == null) {
            return new ArrayList<>();
        }

        return edgesToNeighbors.stream()
                .filter(edge -> edge.isOtherNodeReachableFromA(a))
                .map(edge -> new PrioritizedNode(edge.getOtherNode(a), edge, edge.getWeight())) // auf PriorizedNode mappen
                .filter(prioritizedNode -> !_resultGraph.getNodes().contains(prioritizedNode.getNode())) // filter: noch nicht in Graph enthalten
                .collect(Collectors.toList());
    }

    /**
     * Erstellt eine Liste aller Knoten des Eingangsgraphen, die noch nicht im Ergebnisgraphen
     * enthalten sind, und weist ihnen eine Fallback-Priorität zu.
     * <p>
     * Diese Methode wird verwendet, um nicht zusammenhängende Komponenten des Graphen
     * zu behandeln. Die Fallback-Priorität ist größer als alle tatsächlichen Kantengewichte.
     * </p>
     *
     * @param fallbackPriority Die Priorität, die nicht erreichbaren Knoten zugewiesen wird
     * @return Liste von priorisierten Knoten mit Fallback-Priorität
     */
    private List<PrioritizedNode> getUnincludedNodesWithFallbackPriority(float fallbackPriority) {
        return _inputGraph
                .getNodes()
                .stream()
                .filter(node -> !_resultGraph.getNodes().contains(node)) // filtert alle Knoten heraus, die bereits im Ziel-Graphen enthalten sind
                .map(node -> new PrioritizedNode(node, null, fallbackPriority)) // mapped jeden Knoten auf einen priorisierten Knoten
                .collect(Collectors.toList());
    }

    /**
     * Gibt den aktuellen Inhalt der Priority Queue zu Debug-Zwecken auf der Konsole aus.
     * Zeigt für jeden Knoten in der Queue seinen Namen und seine Priorität an.
     */
    private void printPrioQueue() {
        System.out.println("---------------------------------------------");
        System.out.println("PrioQueue:");
        _nodePriorityQueue.forEach(prioNode -> {
            System.out.println(prioNode.getNode().toString()+" - "+prioNode.getPriority());
        });
        System.out.println("---------------------------------------------");
    }
}
