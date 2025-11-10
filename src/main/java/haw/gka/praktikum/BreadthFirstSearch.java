package haw.gka.praktikum;

import haw.gka.praktikum.LogResources.LogResources;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
* Implementation of the BFS algorithm
 **/
public class BreadthFirstSearch {
    public static List<Node> search(GraphModel graphModel, Node start, Node end) {
        LogResources.startTask("BreadthFirstSearch");
        GraphModel graph = new GraphModel(graphModel.getNodes(), graphModel.getEdges());

        if(start.equals(end)){
            List<Node> resultList = new ArrayList<>();
            resultList.add(start);
            return resultList;
        }
        graph.indexNode(start, 0);

        ArrayList<Node> nodesToHandle = new ArrayList<>();
        nodesToHandle.add(start);
        Node current = null;

        while (!nodesToHandle.isEmpty()) {
            current = nodesToHandle.getFirst();
            nodesToHandle.removeFirst();
            int index = graph.getIndexOfNode(current);
            if (index == -1) {
                throw new RuntimeException("Node not found: " + current.getName());
            }

            if(current.equals(end)){
                break;
            }

            Set<Node> neighbors = graph.getUnindexedNeighbors(current);
            for (Node node : neighbors) {
                graph.indexNode(node, index+1);
                nodesToHandle.add(node);
                System.out.println("Found node: " + node.getName()+"; marked with:" +(index+1));
            }
        }

        if(current.equals(end)){
            System.out.println("Ziel gefunden. Benutze Kanten: "+(graph.getIndexOfNode(end)));
        } else {
            System.out.println("Ziel ist nicht vom Start erreichbar.");
            return new ArrayList<>();
        }


        List<Node> result = findPathFromTraversedGraph(graph, start, end);
        LogResources.stopTask("BreadthFirstSearch");
        return result;
    }

    private static List<Node> findPathFromTraversedGraph(GraphModel graph, Node start, Node end) {
        List<Node> resultList = new ArrayList<>();

        if(graph.getIndexOfNode(end) == -1){
            return resultList;
        }

        resultList.add(end);

        int index = graph.getIndexOfNode(end);
        Node current = end;

        while (index != 1) {
            System.out.printf("\nSuche Nachbarn mit Index %d von %s.\n", index-1, current.getName());
            current = graph.getReverseNeighborWithIndex(current, index-1);
            resultList.addFirst(current);
            index--;
        }

        resultList.addFirst(start);

        for (Node node : resultList) {
            if(!node.equals(end)){
                System.out.print(node.getName()+" -> ");
            } else {
                System.out.print(node.getName());
            }
        }

        return resultList;
    }
}
