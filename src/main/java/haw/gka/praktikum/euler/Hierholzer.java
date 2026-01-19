package haw.gka.praktikum.euler;

import haw.gka.praktikum.Edge;
import haw.gka.praktikum.GraphModel;
import haw.gka.praktikum.LogResources.LogResources;
import haw.gka.praktikum.Node;

import java.util.*;

/**
 * Implementierung des Hierholzer-Algorithmus
 */
public class Hierholzer {

    /**
     *
     * @param graph
     * @return
     */
    public static GraphModel searchEulerCircle(GraphModel graph) {
        //Start Logging
        LogResources.startTask("Running Hierholzer on graph");

        if (graph == null || graph.getEdges().isEmpty()) {
            throw new IllegalArgumentException("Graph is empty");
        }

        //Algorithmus ausführen und
        List<Edge> edgesCandidate = executeHierholzer(graph);

        //final prüfen, ob Eulerkreis enthalten
        if (!Euler.checkEulerCircle(graph, edgesCandidate)) {
            System.err.println("Es wurde kein gültiger Eulerkreis gefunden!");
            return null;
        }

        //Ergebnis-Liste in ein GraphModel umwandeln
        GraphModel resultGraph = new GraphModel();
        HashSet<Edge> usedEdges = new HashSet<>();

        for (Edge e : edgesCandidate) {
            resultGraph.addEdge(e.getStart(), e.getEnd(), e.isDirected(),
                    e.isWeighted(), e.getWeight(), e.getName());
        }

        //Stop Logging
        LogResources.stopTask("Running Hierholzer on graph");

        return resultGraph;
    }

    /**
     * Methode, die den eigentlichen Hierholzer-Algorithmus ausführt
     *
     * @param graph
     * @return
     */
    public static List<Edge> executeHierholzer(GraphModel graph) {

        if (graph == null) {
            throw new IllegalArgumentException("graphModel cannot be null");
        }
        Prechecks.checkEulerRequirements(graph);

        // Hilfsstrukturen
        Stack<Node> nodeStack = new Stack<>();
        Stack<Edge> edgeStack = new Stack<>();
        LinkedList<Edge> finalEdgeList = new LinkedList<>();
        HashSet<Edge> usedEdges = new HashSet<>();

        //Iterator pro Knoten statt Adjazenz-Matrix, damit sehr langsam
        // (prüft alle Kanten, ob benutzt, statt zu wissen, wo es weitergeht)
        Map<Node, Iterator<Edge>> edgeIterators = new HashMap<>();
        for (Node node : graph.getNodes()) {
            edgeIterators.put(node, graph.getAdjacency().getOrDefault(node, Collections.emptySet()).iterator());
        }

        Node startNode = graph.getEdges().iterator().next().getStart();
        nodeStack.push(startNode);

        while (!nodeStack.isEmpty()) {
            Node currentNode = nodeStack.peek();
            Iterator<Edge> it = edgeIterators.get(currentNode);

            Edge nextEdge = null;

            while (it != null && it.hasNext()) {
                Edge e = it.next();
                if (!usedEdges.contains(e)) {
                    nextEdge = e;
                    break;
                }
            }

            if (nextEdge != null) {
                //Kante markieren und weiterziehen
                usedEdges.add(nextEdge);

                //Nachbarknoten holen und STack hinzufügen
                Node neighbor = nextEdge.getOtherNode(currentNode);
                nodeStack.push(neighbor);
                edgeStack.push(nextEdge);

            } else {
                // alle Kanten abgearbeitet? Knoten fertig, aus Stack entfernen
                // aber nur, wenn da noch was drin ist
                nodeStack.pop();
                if (!edgeStack.isEmpty()) {
                    finalEdgeList.addFirst(edgeStack.pop());
                }
            }
        }

        return finalEdgeList;
    }

}
