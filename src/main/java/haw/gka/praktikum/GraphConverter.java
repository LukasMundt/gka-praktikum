package haw.gka.praktikum;

import java.util.Set;
import java.util.stream.Collectors;

public class GraphConverter {
    public static GraphModel getUndirectedGraphModel(GraphModel graph) {
        Set<Edge> undirectedEdges = graph.getEdges().stream()
                .map(Edge::getUndirected)
                .collect(Collectors.toSet());
        return new GraphModel(graph.getNodes(), undirectedEdges);
    }
}
