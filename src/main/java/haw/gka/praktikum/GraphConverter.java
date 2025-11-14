package haw.gka.praktikum;

import java.util.HashSet;
import java.util.Set;

/**
 * Dienstklasse zur Konvertierung eines gegebenen {@link GraphModel}
 * in ein gerichtet oder ungerichtet interpretiertes Graphmodell.
 *
 * <p>
 * Diese Klasse erstellt neue Mengen von Kanten und verwendet die
 * vorhandenen Knoten. Ursprüngliche Kanten werden dabei kopiert,
 * wenn Eigenschaften (z. B. Gerichtetheit) verändert werden müssen.
 * </p>
 */
public class GraphConverter {
    /**
     * Erzeugt ein neues {@link GraphModel}, in dem alle Kanten ungerichtet sind.
     *
     * <p>
     * Für jede Kante im ursprünglichen Graphen gilt:
     * <ul>
     *     <li>Ungerichtete Kanten bleiben unverändert.</li>
     *     <li>Gerichtete Kanten werden in eine neue ungerichtete Kante umgewandelt.</li>
     *     <li>Gewichtete Kanten behalten ihr Gewicht.</li>
     * </ul>
     * </p>
     *
     * @param graph Das Ursprungsgraph-Modell
     * @return Ein neues GraphModel mit ausschließlich ungerichteten Kanten
     */
    public static GraphModel getUndirectedGraphModel(GraphModel graph) {
        Set<Edge> undirectedEdges = new HashSet<>();
        for (Edge edge : graph.getEdges()) {
            if(!edge.isDirected()) {
                // ungerichtete Kante bleibt erhalten
                undirectedEdges.add(edge);
            } else {
                // gerichtete Kante wird kopiert und ungerichtet gemacht.
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

    /**
     * Erzeugt ein neues {@link GraphModel}, in dem alle Kanten gerichtet sind.
     *
     * <p>
     * Für jede Kante im ursprünglichen Graphen gilt:
     * <ul>
     *     <li>Bereits gerichtete Kanten bleiben unverändert.</li>
     *     <li>Ungerichtete Kanten werden in zwei gerichtete Kanten umgewandelt,
     *         eine in jede Richtung.</li>
     *     <li>Gewichte ungerichteter Kanten werden auf beide gerichteten Kanten übertragen.</li>
     * </ul>
     * </p>
     *
     * @param graph Das Ursprungsgraph-Modell
     * @return Ein neues GraphModel mit ausschließlich gerichteten Kanten
     */
    public static GraphModel getDirectedGraphModel(GraphModel graph) {
        Set<Edge> directedEdges = new HashSet<>();
        for (Edge edge : graph.getEdges()) {
            if(edge.isDirected()) {
                // gerichtete Kante bleibt bestehen
                directedEdges.add(edge);
            } else if (edge.isWeighted()) {
                // ungerichtete und gewichtet -> zwei gerichtete Kanten mit Gewicht
                directedEdges.add(new Edge(edge.getStart(), edge.getEnd(), true, edge.getWeight()));
                directedEdges.add(new Edge(edge.getEnd(), edge.getStart(), true, edge.getWeight()));
            } else {
                // ungerichtet und ungewichtet -> zwei gerichtete Kanten
                directedEdges.add(new Edge(edge.getStart(), edge.getEnd(), true));
                directedEdges.add(new Edge(edge.getEnd(), edge.getStart(), true));
            }
        }
        return new GraphModel(graph.getNodes(), directedEdges);
    }


}
