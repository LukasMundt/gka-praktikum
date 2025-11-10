package haw.gka.praktikum;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;

/**
 * Methods to write the .gka files
 *
 */

public class GraphOut {
    //Methode, die gegebenes Graphmodel in Datei schreibt
    public void writeFile(GraphModel g, String path) throws IOException {
        if (g == null) {
            System.err.println("Das GraphModel ist null und kann nicht in eine Datei gespeichert werden.");
            return;
        }

        //try with ressources, um sicherzustellen, dass Writer geschlossen wird
        try (PrintWriter writer = new PrintWriter(path)) {
            writeGraphs(g.getEdges(), writer);
        } catch (IOException e) {
            System.err.println("Fehler beim Schreiben der Datei: " + e.getMessage());
            throw e;
        }

        System.out.println("Die .gka Datei wird nach " + path + " gespeichert.");
    }

    //Hilfsmethode, die Graphen (eigentlich: Kanten, diese beinhalten Knoteninfos) formatiert
    // und ausgibt
    private void writeGraphs(HashSet<Edge> edges, PrintWriter writer) {
        for (Edge edge : edges) {
            //Kantenzeichen auswählen je nach (un)gerichtetem Graph
            String s = edge.isDirected() ? " -> " : " -- ";
            //Graph zusammensetzen aus zwei Knoten und Kante
            String graph = edge.getStart().getName() + s + edge.getEnd().getName();
            //Gewicht, falls vorhanden
            if (edge.isWeighted() && edge.getWeight() != 0) {
                graph += " : " + edge.getWeight();
            }
            writer.println(graph + ";");
        }
    }

}
