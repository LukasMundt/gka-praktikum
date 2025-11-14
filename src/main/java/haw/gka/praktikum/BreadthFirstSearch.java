package haw.gka.praktikum;

import haw.gka.praktikum.LogResources.LogResources;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
* Implementation of the BFS algorithm
 **/
public class BreadthFirstSearch {
    /**
     * Sucht in dem gegebenen Graphen den kürzesten Weg zwischen dem Start- und dem Zielknoten
     *
     * @param graphModel Der zu traversierende Graph
     * @param start Der Startpunkt
     * @param end Das Ziel, zu dem der kürzeste Weg gesucht werden soll
     * @return Liste der an dem kürzesten Weg beteiligten Knoten. (Von Start zum Ziel)
     * keine Verbindung -> leere Liste
     * Start ist Ziel -> Liste der Laenge 1 (Start)
     */
    public static List<Node> search(GraphModel graphModel, Node start, Node end) {
        LogResources.startTask("BreadthFirstSearch");
        // Graph kopieren um den eingegebenen Graphen noch an anderen Stellen unverändert verwenden zu können
        GraphModel graph = new GraphModel(graphModel.getNodes(), graphModel.getEdges());

        if(start.equals(end)){
            // wenn der Start das Ziel ist wird der Start in der Liste zurückgegeben
            List<Node> resultList = new ArrayList<>();
            resultList.add(start);
            return resultList;
        } else if(graph.getNodes().isEmpty()){
            // wenn der Graph keine Knoten hat
            return new ArrayList<>();
        } else if(!graph.getNodes().contains(start)) {
            // wenn der Startknoten nicht im Graphen vorkommt
            System.out.println("Start-Node not found in graph: " + start.getName());
            return new ArrayList<>();
        }

        // Start mit 0 indizieren und fügt ihn der Queue hinzu
        graph.indexNode(start, 0);
        ArrayList<Node> nodesToHandle = new ArrayList<>();
        nodesToHandle.add(start);
        Node current = null;

        while (!nodesToHandle.isEmpty()) {
            // nimmt den ersten Knoten aus der Queue und markiert ihn als aktuellen
            current = nodesToHandle.getFirst();
            nodesToHandle.removeFirst();

            // holt den Index des aktuellen Knoten
            int index = graph.getIndexOfNode(current);
            if (index == -1) {
                throw new RuntimeException("Node not found: " + current.getName());
            }

            // beendet, wenn Ziel gefunden
            if(current.equals(end)){
                break;
            }

            // holt alle noch nicht indizierten Nachbarn, indiziert sie mit n+1 und fügt sie der queue hinzu
            Set<Node> neighbors = graph.getUnindexedNeighbors(current);
            for (Node node : neighbors) {
                graph.indexNode(node, index+1);
                nodesToHandle.add(node);
                System.out.println("Found node: " + node.getName()+"; marked with:" +(index+1));
            }
        }

        if(current.equals(end)){
            // ziel wurde gefunden
            System.out.println("Ziel gefunden. Benutze Kanten: "+(graph.getIndexOfNode(end)));
        } else {
            // Ziel wurde nicht gefunden -> gibt leere Liste zurück
            System.out.println("Ziel ist nicht vom Start erreichbar.");
            return new ArrayList<>();
        }

        // suche den kürzesten Weg aus dem traversierten Graphen raus
        List<Node> result = findPathFromTraversedGraph(graph, start, end);
        LogResources.stopTask("BreadthFirstSearch");
        return result;
    }

    /**
     * Sucht in einem traversierten Graphen den tatsächlich kürzesten Weg vom Ende wieder zum Start zurück.
     *
     * @param graph Der traversierte Graph
     * @param start Der Startpunkt
     * @param end Das Ziel, zu dem der kürzeste Weg gesucht werden soll (hier starten wir von hier aus)
     * @return Liste der an dem kürzesten Weg beteiligten Knoten. (Von Start zum Ziel)
     */
    private static List<Node> findPathFromTraversedGraph(GraphModel graph, Node start, Node end) {
        List<Node> resultList = new ArrayList<>();

        // Ziel-Knoten wurde nicht indiziert -> Ziel wurde nicht gefunden
        if(graph.getIndexOfNode(end) == -1){
            return resultList;
        }

        // Ziel-Knoten dem Weg hinzufügen und als aktuellen Knoten wählen
        resultList.add(end);
        int index = graph.getIndexOfNode(end);
        Node current = end;

        // Suche der jeweiligen Nachbarn des aktuellen Knoten mit Index (n-1) -> neuer aktueller Knoten und vorne an Weg angefügt
        while (index != 1) {
            System.out.printf("\nSuche Nachbarn mit Index %d von %s.\n", index-1, current.getName());
            current = graph.getReverseNeighborWithIndex(current, index-1);
            resultList.addFirst(current);
            index--;
        }

        // Start der Liste hinzufügen
        resultList.addFirst(start);

        return resultList;
    }
}
