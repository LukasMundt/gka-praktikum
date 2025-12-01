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
        GraphVisualizer.displayGraph(graph, null);
    }

    /**
     * Anzeige des übergebenen Graphen.
     *
     * @param graph der zu visualisierende Graph
     * @param title Der Titel, wird nur angezeigt wenn != null, null erlaubt
     */
    public static void displayGraph(GraphModel graph, String title) {
        System.setProperty("org.graphstream.ui", "javafx");

        Graph graphViz = new MultiGraph(title != null ? title : "GKA Praktikum");
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

        if(title != null) {
            GraphVisualizer.displayTitle(graphViz, title);
        }

        // fenster anzeigen
        graphViz.display();
    }

    /**
     * Fügt den übergebenen Titel dem übergebenen Graphen hinzu.
     *
     * @param graphViz Der Graph, in dem der Titel angezeigt werden soll
     * @param title Der Titel, der angezeigt werden soll.
     */
    private static void displayTitle(Graph graphViz, String title) {
        if(graphViz == null) throw new IllegalArgumentException("graphViz cannot be null");
        if(title == null) throw new IllegalArgumentException("title cannot be null");

        Node titleNode = graphViz.addNode("GRAPHTITLE");
        titleNode.setAttribute("ui.label", title); // Setze den Graphennamen als Label
        titleNode.setAttribute("layout.frozen"); // Verhindert, dass der Layout-Algorithmus ihn verschiebt

        // Style für den Titel-Knoten (groß, kein Füllfarbe/Rand)
        graphViz.setAttribute("ui.stylesheet",
                "node { fill-color: #1E88E5; size: 20px; text-alignment: above; text-size: 14px; } " +
                        "edge { fill-color: #999; size: 2px; } " +
                        // NEUER STYLE FÜR DEN TITEL-KNOTEN
                        "node#GRAPHTITLE { " +
                        "   fill-mode: none; " +             // Keine Füllfarbe
                        "   size: 0px; " +                   // Unsichtbare Größe
                        "   text-size: 24px; " +             // Große Schrift
                        "   text-color: #000000; " +         // Schwarze Schrift
                        "   text-alignment: left; " +        // Text links ausrichten
                        "   text-style: bold; " +            // Fett
                        "   text-padding: 0px, 0px; " +      // Kein Padding
                        "}");

        // Setze die anfängliche Position des Titel-Knotens (z.B. links oben)
        // ACHTUNG: Die Koordinaten sind relativ zur Zeichenfläche.
        titleNode.setAttribute("xy", 0, 10);
    }
}
