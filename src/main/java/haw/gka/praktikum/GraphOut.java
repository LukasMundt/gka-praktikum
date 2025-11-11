package haw.gka.praktikum;

import haw.gka.praktikum.LogResources.LogResources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

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

        LogResources.startTask("Writing Graph to " + path);

        //try with ressources, um sicherzustellen, dass Writer geschlossen wird
        try (PrintWriter writer = new PrintWriter(path)) {
            writeGraphs(g.getEdges(), writer);
            writeSingleNodes(g.getSingleNodes(), writer);
        } catch (IOException e) {
            System.err.println("Fehler beim Schreiben der Datei: " + e.getMessage());
            throw e;
        }

        LogResources.stopTask("Writing Graph to " + path);

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

    //Hilfsmethode, die Knoten ohne Kanten ausgibt
    private void writeSingleNodes(Set<Node> nodes, PrintWriter writer){
        for (Node node : nodes) {
            writer.println(node.getName() + ";");
        }
    }

}
