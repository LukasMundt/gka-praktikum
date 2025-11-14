package haw.gka.praktikum;

import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;

/**
 * Diese Klasse visualisiert einen gegebenen {@link GraphModel} mittels GraphStream.
 * <p>
 * Es wird ein {@link MultiGraph} erzeugt, damit mehrere Kanten zwischen denselben
 * Knoten (z. B. bei gewichteten oder parallelen Kanten) unterstützt werden.
 * Die Darstellung erfolgt über ein JavaFX-Fenster.
 * </p>
 */
public class GraphVisualizer {

    public static void displayGraph(GraphModel graph) {
        System.setProperty("org.graphstream.ui", "javafx");

        Graph graphViz = new MultiGraph("GKA Praktikum");

        // Knoten hinzufügen
        graph.getNodes().forEach(node -> {
            graphViz.addNode(node.getName());
        });


        // Kanten hinzufügen
        graph.getEdges().forEach(edge -> {
            String idFrom = edge.getStart().getName();
            String idTo = edge.getEnd().getName();
            try {
                // Kantenlabel setzt sich aus Start/Ziel und optional Gewicht zusammen
                String label = idFrom + idTo;
                if(edge.isWeighted()){
                    label = label + " : "+edge.getWeight();
                }
                graphViz.addEdge(label, idFrom, idTo, edge.isDirected());
            } catch (EdgeRejectedException exception) {
                System.out.println(exception.getMessage());
            }
        });

        // Labels hinzufügen (Namen von Kanten und Knoten werden angezeigt)
        graphViz.nodes().forEach(n -> n.setAttribute("ui.label", n.getId()));
        graphViz.edges().forEach(edge -> edge.setAttribute("ui.label", edge.getId()));

        // styles für die Darstellung
        graphViz.setAttribute("ui.stylesheet",
                "node { fill-color: #1E88E5; size: 20px; text-alignment: above; text-size: 14px; } " +
                        "edge { fill-color: #999; size: 2px; }");

        // fenster anzeigen
        graphViz.display();
    }
}
