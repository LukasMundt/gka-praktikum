package haw.gka.praktikum.euler;

import haw.gka.praktikum.Edge;
import haw.gka.praktikum.GraphModel;

import java.io.IOException;
import java.util.HashSet;
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
     * - graph hat gerade Knotengrade TODO!
     *
     * @param graph
     * @return boolean true, wenn Graph alle Reqs erfüllt
     * @throws IOException
     */
    public static boolean checkEulerRequirements(GraphModel graph) throws IOException {
        //check if null
        if (graph == null) {
            throw new IOException("Übergebener Graph ist null, damit kann ich" +
                    " nicht arbeiten.");
        }

        //check if directed
        if (graph.getDirectionOfGraph()) {
            throw new IOException("Übergebener Graph ist gerichtet, damit " +
                    "kann ich nicht arbeiten.");
        }

        HashSet<Edge> allEdges = graph.getEdges();
        //check if size is big enough
        if (allEdges.size() <= 2) {
            throw new IOException("Übergebener Graph muss mindestens 3 Kanten" +
                    " enthalten für Eulerkreis.");
        }

        //TODO Test für gerade Knotengrade -> müsste so passen
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

        return true;
    }
}
