package haw.gka.praktikum;

import java.util.ArrayList;
import java.util.Set;

/**
* Implementation of the BFS algorithm
 **/
public class BreadthFirstSearch {
    public static void search(GraphModel graph, Node start, Node end) {
        graph.indexNode(start, 0);

        ArrayList<Node> nodesToHandle = new ArrayList<>();
        nodesToHandle.add(start);

        while (!nodesToHandle.isEmpty()) {
            Node current = nodesToHandle.getFirst();
            nodesToHandle.removeFirst();
            int index = graph.getIndexOfNode(current);
            if (index == -1) {
                throw new RuntimeException("Node not found: " + current.getName());
            }

            if(current.equals(end)){
                System.out.println("Ziel gefunden. Beteiligte Knoten: "+(graph.getIndexOfNode(end)+1));
                return;
            }

            Set<Node> neighbors = graph.getUnindexedNeighbors(current);
            for (Node node : neighbors) {
                graph.indexNode(node, index+1);
                nodesToHandle.add(node);
                System.out.println("Found node: " + node.getName()+"; marked with:" +(index+1));
            }
        }
    }
}
