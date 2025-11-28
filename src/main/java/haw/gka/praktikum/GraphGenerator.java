package haw.gka.praktikum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Generator für einen ungerichteten Graphen mit einer vorgegebenen Anzahl von
 * Knoten und Kanten mit beliebigen, aber unterschiedlichen, nicht-negativen
 * Kantengewichten.
 */
public class GraphGenerator {

    /**
     * Konstruktor für den Generator
     *
     * @throws IllegalArgumentException
     */
    public GraphGenerator() throws IllegalArgumentException {
    }

    /**
     * Die Methode generiert gemäß der übergebenen Parameter einen
     * ungerichteten Graphen mit zufällig gewählten Gewichten.
     *
     * @param nodesNr int Anzahl der Knoten
     * @param edgesNr int Anzahl der Kanten
     * @return GraphModel der erstellte Graph
     */
    public GraphModel generateGraph(int nodesNr, int edgesNr) {

        //Kantenmenge eines vollständigen Graphens bestimmen als obere Grenze
        int maxEdges = (nodesNr * (nodesNr - 1)) / 2;

        //ungültige Eingaben abfangen
        if (nodesNr < 1 || edgesNr < 0 || nodesNr < edgesNr || edgesNr > maxEdges) {
            throw new IllegalArgumentException("die Knoten- und/oder " +
                    "Kantenanzahl ist nicht sinnvoll gewählt");
        }

        //Graph generieren, in den die Nodes und Edges gespeichert werden
        GraphModel generatedGraph = new GraphModel();

        // nodesNr Nodes anlegen
        for (int i = 1; i <= nodesNr; i++) {
            // brauchen Bezeichnung
            String newNode = "n_" + i;

            //dem Graphen hinzufügen
            generatedGraph.addNode(newNode);
        }

        // mit diesen Nodes insgesamt edgesNr Edges bilden
        // (auch: vollständiger Graph)

        //von HashSet (Menge) zu Liste (sortierbar)
        List<Node> allNodes = new ArrayList<>(generatedGraph.getNodes());
        //Zähler für bereits erstellte Kanten
        int j = 0;
        while (j < edgesNr) {
            //zufällige Auswahl aus Knoten-Liste
            int rIndexA = new Random().nextInt(allNodes.size());
            int rIndexB = new Random().nextInt(allNodes.size());

            Node nodeA = allNodes.get(rIndexA);
            Node nodeB = allNodes.get(rIndexB);

            //Zufallsgewicht generieren lassen
            float genWeight = generateFloat();

            Edge ab;
            //prüfen, ob die ausgewählten Knoten gleich sind
            if (nodeA != nodeB) {
                //Kante AB zusammenstellen mit
                // folgenden Parametern:
                ab = new Edge(nodeA, nodeB, false, true, genWeight, null);

                //prüfen, ob Kante (oder gedrehtes Äquivalent) bereits
                // vorhanden ist (auch mit anderem Gewicht)
                if (!generatedGraph.hasEdgeBetween(nodeA, nodeB)) {
                    generatedGraph.addEdges(ab);
                    j++;
                }
            }
        }
        return generatedGraph;
    }

    /**
     * Hilfsmethode, um einen zufälligen FLoat zu generieren und auf eine
     * Zahl zwischen 1.0 und 10.0 umzurechnen (auf zwei Nachkommastellen
     * gerundet)
     *
     * @return float zufällig generierter und formatierter Wert
     */
    public float generateFloat() {
        Random random = new Random();
        //erzeugt float zw. 0.0 und 1.0, * 10, da ganzzahlige Darstellung für
        // Kanten-Gewicht geeigneter
        float randomFloat = (random.nextFloat() * 10);

        //round, um Nachkommastellen loszuwerden
        return Math.round(randomFloat);
    }
}
