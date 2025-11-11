package haw.gka.praktikum;

import java.util.HashSet;
import java.util.Set;

public class GraphConverter {
    public static GraphModel getUndirectedGraphModel(GraphModel graph) {
        Set<Edge> undirectedEdges = new HashSet<>();
        for (Edge edge : graph.getEdges()) {
            if(!edge.isDirected()) {
                undirectedEdges.add(edge);
            } else {
                Edge newEdge;
                if (edge.isWeighted()) {
                    newEdge = new Edge(edge.getStart(), edge.getEnd(), false, edge.getWeight());
                } else {
                    newEdge = new Edge(edge.getStart(), edge.getEnd(), false);
                }
                undirectedEdges.add(newEdge);
            }
        }
        return new GraphModel(graph.getNodes(), undirectedEdges);
    }

    public static GraphModel getDirectedGraphModel(GraphModel graph) {
        Set<Edge> directedEdges = new HashSet<>();
        for (Edge edge : graph.getEdges()) {
            if(edge.isDirected()) {
                directedEdges.add(edge);
            } else if (edge.isWeighted()) {
                directedEdges.add(new Edge(edge.getStart(), edge.getEnd(), true, edge.getWeight()));
                directedEdges.add(new Edge(edge.getEnd(), edge.getStart(), true, edge.getWeight()));
            } else {
                directedEdges.add(new Edge(edge.getStart(), edge.getEnd(), true));
                directedEdges.add(new Edge(edge.getEnd(), edge.getStart(), true));
            }
        }
        return new GraphModel(graph.getNodes(), directedEdges);
    }


}
