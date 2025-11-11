package haw.gka.praktikum;

import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;

public class GraphVisualizer {

    public static void displayGraph(GraphModel graph) {
        System.setProperty("org.graphstream.ui", "javafx");

        Graph graphViz = new MultiGraph("GKA Praktikum");

        graph.getNodes().forEach(node -> {
            graphViz.addNode(node.getName());
        });


        graph.getEdges().forEach(edge -> {
            String idFrom = edge.getStart().getName();
            String idTo = edge.getEnd().getName();
            try {
                String label = idFrom + idTo;
                if(edge.isWeighted()){
                    label = label + " : "+edge.getWeight();
                }
                graphViz.addEdge(label, idFrom, idTo, edge.isDirected());
            } catch (EdgeRejectedException exception) {
                System.out.println(exception.getMessage());
            }
        });

        // add ui labels
        graphViz.nodes().forEach(n -> n.setAttribute("ui.label", n.getId()));
        graphViz.edges().forEach(edge -> edge.setAttribute("ui.label", edge.getId()));

        graphViz.setAttribute("ui.stylesheet",
                "node { fill-color: #1E88E5; size: 20px; text-alignment: above; text-size: 14px; } " +
                        "edge { fill-color: #999; size: 2px; }");

        graphViz.display();
    }
}
