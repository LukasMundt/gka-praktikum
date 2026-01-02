package haw.gka.praktikum.euler;

import haw.gka.praktikum.Edge;
import haw.gka.praktikum.GraphModel;

import java.io.IOException;
import java.util.HashSet;

public class Prechecks {

    GraphModel graph;

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
        return true;
    }
}
