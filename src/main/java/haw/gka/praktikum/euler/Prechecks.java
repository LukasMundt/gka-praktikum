package haw.gka.praktikum.euler;

import haw.gka.praktikum.Edge;
import haw.gka.praktikum.GraphModel;

import java.util.Set;

/**
 * Klasse, die prüft, ob ein gegebener Graph die Vorraussetzungen erfüllt, um
 * Eulerkreise zu enthalten
 */
public class Prechecks {

    GraphModel graph;

    /**
     * Prüft Voraussetzungen, damit ein Graph überhaupt einen Eulerkreis
     * enthalten kann:
     * - graph ungleich null
     * - graph ungerichtet
     * - graph hat mindestens 3 Kanten
     * - graph hat gerade Knotengrade
     *
     * @param graph
     * @throws IllegalArgumentException
     */
    public static void checkEulerRequirements(GraphModel graph) throws IllegalArgumentException {
        checkDirected(graph);
        checkGraphIsBigEnough(graph);
        checkEvenNodeDegrees(graph);
        checkConnected(graph);
    }

    /**
     * Prüft, dass der Graph keine gerichteten Kanten enthält.
     * @param graph Graph, der überprüft werden soll
     */
    public static void checkDirected(GraphModel graph) {
        if (graph.getDirectionOfGraph()) {
            throw new IllegalArgumentException("Übergebener Graph ist gerichtet, damit " +
                    "kann ich nicht arbeiten.");
        }
    }

    /**
     * Prüft, ob der Graph groß genug ist.
     * @param graph Graph, der überprüft werden soll
     */
    public static void checkGraphIsBigEnough(GraphModel graph) {

        if (graph.getEdges().size() <= 2) {
            throw new IllegalArgumentException("Übergebener Graph muss mindestens 3 Kanten" +
                    " enthalten für Eulerkreis.");
        }
    }

    /**
     * Prüft, ob alle Knotengrade gerade sind.
     * @param graph Graph, der überprüft werden soll
     */
    public static void checkEvenNodeDegrees(GraphModel graph) {
        for(Set<Edge> edgesOfNode : graph.getAdjacency().values()) {
            int degree = edgesOfNode.size();

            for (Edge edge : edgesOfNode) {
                if(edge.getStart().equals(edge.getEnd())) {
                    degree++;
                }
            }
            if(degree % 2 != 0){
                throw new IllegalArgumentException("Nicht alle Knoten des Graphen" +
                        " haben einen geraden Knotengrad.");
            }
        }
    }

    /**
     * Prüft, ob der Graph zusammenhängend ist.
     * @param graph Graph, der überprüft werden soll
     */
    public static void checkConnected(GraphModel graph) {
        if (!graph.isGraphConnected()) {
            throw new IllegalArgumentException("Der Graph ist nicht zusammenhängend.");
        }
    }
}
