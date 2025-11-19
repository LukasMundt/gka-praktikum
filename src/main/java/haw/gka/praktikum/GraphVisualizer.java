package haw.gka.praktikum;

import org.graphstream.graph.Edge;
import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
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

    /**
     * Anzeige des übergebenen Graphen.
     *
     * @param graph der zu visualisierende Graph
     */
    public static void displayGraph(GraphModel graph) {
        System.setProperty("org.graphstream.ui", "javafx");

        Graph graphViz = new MultiGraph("GKA Praktikum");
        graphViz.setAttribute("ui.layout", "springbox");

        // Knoten hinzufügen
        graph.getNodes().forEach(node -> {
            Node n = graphViz.addNode(node.getName());
            n.setAttribute("ui.label", node.getName());
        });


        // Kanten hinzufügen
        graph.getEdges().forEach(edge -> {
            String idFrom = edge.getStart().getName();
            String idTo = edge.getEnd().getName();
            try {
                // Kantenlabel setzt sich aus Start/Ziel und optional Gewicht zusammen
                String id = idFrom + idTo;
                String label = "";
                if(edge.getName() != null) {
                    id += edge.getName();
                    label = edge.getName();
                }
                if(edge.isWeighted()){ //
                    id += edge.getWeight();
                    label += (!label.isEmpty() ?" (":"(")+((edge.getWeight() % 1 == 0)?Integer.toString((int)edge.getWeight()):edge.getWeight())+")";
                }
                Edge e = graphViz.addEdge(id, idFrom, idTo, edge.isDirected());
                e.setAttribute("ui.label", label);
                if(edge.isWeighted()){
                    e.setAttribute("weight", edge.getWeight());
                }

            } catch (EdgeRejectedException exception) {
                System.out.println(exception.getMessage());
            }
        });

        // styles für die Darstellung
        graphViz.setAttribute("ui.stylesheet",
                "node { fill-color: #1E88E5; size: 20px; text-alignment: above; text-size: 14px; } " +
                        "edge { fill-color: #999; size: 2px; }");

        // fenster anzeigen
        graphViz.display();
    }
}
