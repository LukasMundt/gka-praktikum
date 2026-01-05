package haw.gka.praktikum.euler;

import haw.gka.praktikum.Edge;
import haw.gka.praktikum.GraphModel;
import haw.gka.praktikum.Node;

import java.util.List;

public class Euler {
    //TODO: fehlt hier noch was
    public static boolean checkEulerCircle(GraphModel original, List<Edge> candidate) {
        if (original.getEdges().size() != candidate.size()) {
            return false;
        }

        // get start node
        Edge firstEdge = candidate.get(0);
        Edge secondEdge = candidate.get(1);

        Node start = firstEdge.getEnd();
        if(secondEdge.getEnd().equals(start) || secondEdge.getStart().equals(start)) { // genauer ansehen
            start = firstEdge.getStart();
        }

        // get end node
        Edge lastEdge = candidate.get(candidate.size() - 1);
        Edge penultimateEdge = candidate.get(candidate.size() - 2);

        Node end = lastEdge.getStart();
        if(penultimateEdge.getEnd().equals(end) || penultimateEdge.getStart().equals(end)) {
            end = lastEdge.getEnd();
        }

        if(!start.equals(end)) {
            return false;
        }

        // check tour
        Node current = start;
        for(Edge edge : candidate) {
            if (!edge.getEnd().equals(current) && !edge.getStart().equals(current)) {
                return false;
            }
            current = edge.getOtherNode(current);
        }

        return true;
    }
}
