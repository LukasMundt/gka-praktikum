package haw.gka.praktikum;

import haw.gka.praktikum.LogResources.LogResources;

import java.util.*;

public class Prim {
    private final GraphModel _inputGraph;
    private final GraphModel _resultGraph;
    private final PriorityQueue<PrioritizedNode> _nodePriorityQueue;

    private Prim(GraphModel inputGraph, GraphModel resultGraph) {
        _inputGraph = inputGraph;
        _resultGraph = resultGraph;
        _nodePriorityQueue = new PriorityQueue<>();
    }

    public static GraphModel getMinimalSpanningTree(GraphModel inputGraph) {
        Node firstNode = inputGraph.getNodes().iterator().next();
        return Prim.getMinimalSpanningTree(inputGraph, firstNode);
    }

    public static GraphModel getMinimalSpanningTree(GraphModel inputGraph, Node firstNode) {
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
        // todo: optimieren
        prim._nodePriorityQueue.addAll(prim.getUnincludedNeighborNodesOfAWithPriority(firstNode));
        prim._nodePriorityQueue.addAll(prim.getUnincludedAndUnpriorizedNodesPrioritized(fallbackPriority));


        //prim.printPrioQueue();

        prim.executeAlgorithm();

        LogResources.stopTask("Prim");
        return prim._resultGraph;
    }

    private void executeAlgorithm() {
        // check if working + make stable if nodes are not connected to the
        while (!_resultGraph.getNodes().equals(_inputGraph.getNodes())) {
            PrioritizedNode prioNode = _nodePriorityQueue.poll();
            _nodePriorityQueue.remove(prioNode);

            _resultGraph.addNodes(prioNode.getNode());
            if (prioNode.getConnectingEdge() != null) {
                _resultGraph.addEdges(prioNode.getConnectingEdge());
            }


            // alle neu erreichbaren Knoten, die noch nicht im Graphen enthalten sind, priorisiert der Queue hinzufügen
            List<PrioritizedNode> prioritizedNodes = this.getUnincludedNeighborNodesOfAWithPriority(prioNode.getNode());
            _nodePriorityQueue.addAll(prioritizedNodes);
            // this.printPrioQueue();
        }
    }

    private List<PrioritizedNode> getUnincludedNeighborNodesOfAWithPriority(Node a) {
        Set<Edge> edgesToNeighbors = _inputGraph.getAdjacency().get(a);

        if (edgesToNeighbors == null) {
            return new ArrayList<>();
        }

        return edgesToNeighbors.stream()
                .filter(edge -> edge.isOtherNodeReachableFromA(a))
                .map(edge -> new PrioritizedNode(edge.getOtherNode(a), edge, edge.getWeight()))
                .filter(prioritizedNode -> !_resultGraph.getNodes().contains(prioritizedNode.getNode()))
                .toList();
    }

    private List<PrioritizedNode> getUnincludedAndUnpriorizedNodesPrioritized(float fallbackPriority) {
        return _inputGraph
                .getNodes()
                .stream()
                .filter(node -> !_resultGraph.getNodes().contains(node))
                //.filter(node -> !_nodePriorityQueue.contains(new PrioritizedNode(node, null, 0)))
                .map(node -> new PrioritizedNode(node, null, fallbackPriority))
                .toList();
    }

    private void printPrioQueue() {
        System.out.println("---------------------------------------------");
        System.out.println("PrioQueue:");
        _nodePriorityQueue.forEach(prioNode -> {
            System.out.println(prioNode.getNode().toString()+" - "+prioNode.getPriority());
        });
        System.out.println("---------------------------------------------");
    }
}
