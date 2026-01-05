package haw.gka.praktikum.euler;

import haw.gka.praktikum.Edge;
import haw.gka.praktikum.GraphModel;
import haw.gka.praktikum.LogResources.LogResources;
import haw.gka.praktikum.Node;

import java.util.HashSet;

public class Hierholzer {

    public static GraphModel searchEulerCircle(GraphModel graph) {
        //Start Logging
        LogResources.startTask("Running Hierholzer on graph");

        if(graph == null) {
            throw new IllegalArgumentException("graphModel cannot be null");
        }
        Prechecks.checkEulerRequirements(graph);

        //Kanten aus Graph holen und Vergleichsset anlegen
        HashSet<Edge> allEdges = graph.getEdges();
        HashSet<Edge> usedEdges = new HashSet<>();

        //einen Knoten als Start aus dem graph wählen, EndNode deklarieren
        Edge firstEdge = allEdges.iterator().next();
        Node startNode = firstEdge.getStart();
        Node currentNode = startNode;
        Node endNode = null;

        GraphModel eulerCircle = null;
        //Suche wiederholen, bis alle Kanten verbraucht
        //zuerst mit containsAll geprüft, das ist aber langsam;
        // HashSet enthält keine Duplikate, also size vergleichen
        // TODO und Startpunkt wieder erreicht wird (das in der Schleife
        //  prüfen? sonst ist direkt am Anfang start gleich endNode
        while (usedEdges.size() < allEdges.size()) {
            //Unterkreis konstruieren, der Eulerkreis ist


            // checken, ob Eulerkreis vorliegt
            if (!checkEulerCircle(eulerCircle)) {
                System.out.println("es wurde kein Eulerkreis gefunden");
                return null;
            }

            //am ersten Punkt von K mit Grad > 0 einen weiteren Unterkreis suchen
            // checkEulerCircle!


        }

        //Stop Logging
        LogResources.stopTask("Running Hierholzer on graph");


        return eulerCircle;
    }


}
