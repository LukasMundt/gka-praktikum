package haw.gka.praktikum.euler;

import haw.gka.praktikum.Edge;
import haw.gka.praktikum.GraphModel;
import haw.gka.praktikum.LogResources.LogResources;
import haw.gka.praktikum.Node;

import java.util.*;

public class Fleury {

    private GraphModel originalGraph;
    private List<Edge> eulerCircle;
    private GraphModel eulerCircleGraph;

    /**
     * @param originalGraph Graph, in dem der Eulerkreis gesucht werden soll
     * @param eulerCircle Liste der Kanten des Eulerkreises
     * @param eulerCircleGraph Graph, der den Eulerkreis enthält
     */
    private Fleury(GraphModel originalGraph, List<Edge> eulerCircle, GraphModel eulerCircleGraph) {
        this.originalGraph = originalGraph;
        this.eulerCircle = eulerCircle;
        this.eulerCircleGraph = eulerCircleGraph;
    }

    /**
     * Führt den Fleury-Algorithmus zur Suche nach einem Eulerkreis aus. An der zurückgegebenen
     * Fleury-Instanz kann das Resultat abgerufen werden.
     *
     * @param graphModel Graph, in dem Eulerkreis gesucht werden soll
     * @return Fleury-Objekt
     * @throws IllegalArgumentException
     */
    public static Fleury search(GraphModel graphModel) {
        // prechecks
        if(graphModel == null) {
            throw new IllegalArgumentException("graphModel cannot be null");
        }
        Prechecks.checkEulerRequirements(graphModel);

        LogResources.startTask("Running Fleury on graph");

        // Graphen kopieren
        GraphModel oldGraph = new GraphModel(new HashSet<>(graphModel.getNodes()), new HashSet<>(graphModel.getEdges()));

        List<Edge> eulerCircle = new ArrayList<>();
        GraphModel eulerCircleGraph = new GraphModel();

        // ersten Knoten wählen
        Node currentNode = graphModel.getNodes().iterator().next();

        while (!oldGraph.getEdges().isEmpty()) {
            // Inzidente Kanten
            Set<Edge> incidentEdges = oldGraph.getAdjacency().get(currentNode);

            // Wählen einer Kante, nicht-Brücken priorisiert
            Edge selectedEdge = null;

            for (Edge e : incidentEdges) {
                if (!oldGraph.isEdgeABridge(e)) {
                    selectedEdge = e;
                    break;
                }
            }
            // wenn alle Brücken sind -> irgendeine nehmen
            if (selectedEdge == null) {
                selectedEdge = incidentEdges.iterator().next();
            }

            // Kante markieren
            eulerCircle.add(selectedEdge);
            eulerCircleGraph.addEdges(selectedEdge);

            // Kante aus dem alten Graphen entfernen
            oldGraph.removeEdge(selectedEdge);
            if (oldGraph.getAdjacency().getOrDefault(currentNode, new HashSet<>()).isEmpty()) {
                oldGraph.removeNode(currentNode);
            }

            // neuen Knoten festlegen
            currentNode = selectedEdge.getOtherNode(currentNode);
//            System.out.println("currentNode: " + currentNode);
        }

        LogResources.stopTask("Running Fleury on graph");

        return new Fleury(graphModel, eulerCircle, eulerCircleGraph);
    }

    /**
     * Gibt den Eulerkreis als Liste von Kanten zurück.
     *
     * @return Liste von Kanten
     */
    public List<Edge> getEulerCircleTour() {
        return eulerCircle;
    }

    /**
     * Gibt den Eulerkreis als Graph zurück.
     *
     * @return Eulerkreis als Graph
     */
    public GraphModel getEulerCircleGraph() {
        return eulerCircleGraph;
    }
}
