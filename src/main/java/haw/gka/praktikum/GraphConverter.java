package haw.gka.praktikum;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class GraphConverter {
    public static GraphModel getUndirectedGraphModel(GraphModel graph) {
        Set<Edge> undirectedEdges = graph.getEdges().stream()
                .map(Edge::getUndirected)
                .collect(Collectors.toSet());
        return new GraphModel(graph.getNodes(), undirectedEdges);
    }

    public static GraphModel getDirectedGraphModel(GraphModel graph) {
        Set<Edge> directedEdges = new HashSet<>();
        for (Edge edge : graph.getEdges()) {
            if (edge.isWeighted()) {
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
