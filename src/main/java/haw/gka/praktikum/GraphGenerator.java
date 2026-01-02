package haw.gka.praktikum;

import haw.gka.praktikum.LogResources.LogResources;

import java.util.*;

/**
 * Generator für einen ungerichteten Graphen mit einer vorgegebenen Anzahl von
 * Knoten und Kanten mit beliebigen, aber unterschiedlichen, nicht-negativen
 * Kantengewichten.
 */
public class GraphGenerator {

    private final Random _random = new Random();

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
    //TODO 3. Parameter übergeben für "alle Knotengrade even" (even == true) und
    // Code um Funktionalität erweitern
    public GraphModel generateGraph(int nodesNr, int edgesNr) {
        LogResources.startTask("Generate Graph");
        //Kantenmenge eines vollständigen Graphens bestimmen als obere Grenze
        long maxEdges = ((long) nodesNr * ((long) nodesNr - 1)) / 2;
        System.out.println("maxEdges: " + maxEdges);

        //ungültige Eingaben abfangen
        if (nodesNr < 1 || edgesNr < 0 || edgesNr > maxEdges) {
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

        // Adjazenzliste
        HashMap<Integer, HashSet<Integer>> usedIndexes = new HashMap<>();

        while (j < edgesNr) {
            // zufällige Auswahl aus Knoten-Liste
            int rIndexA = _random.nextInt(nodesNr);
            Set<Integer> usedIndexesA = usedIndexes.getOrDefault(rIndexA, new HashSet<>());

            int rIndexB = _random.nextInt(nodesNr);

            // Generierung von Zufallszahlen solange Kante schon existiert oder Start gleich Ziel
            while (usedIndexesA.contains(rIndexB) || rIndexA == rIndexB) {
                rIndexB = _random.nextInt(nodesNr);
            }

            // Knoten generieren
            Node nodeA = allNodes.get(rIndexA);
            Node nodeB = allNodes.get(rIndexB);

            //Zufallsgewicht generieren lassen
            float genWeight = generateFloat();

            // Kante AB zusammenstellen mit folgenden Parametern:
            Edge ab = new Edge(nodeA, nodeB, false, true, genWeight, null);

            // Kante hinzufügen
            generatedGraph.addEdges(ab);

            // Kante in lokale Adjazenzliste eintragen
            usedIndexes.computeIfAbsent(rIndexA, k -> new HashSet<>()).add(rIndexB);
            usedIndexes.computeIfAbsent(rIndexB, k -> new HashSet<>()).add(rIndexA);

            // Index generierter Kanten hochzählen
            j++;
        }
        LogResources.stopTask("Generate Graph");
        System.out.println("Edges count: " + generatedGraph.getEdges().size());
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
        //erzeugt float zw. 0.0 und 1.0, * 10, da ganzzahlige Darstellung für
        // Kanten-Gewicht geeigneter
        float randomFloat = (_random.nextFloat() * 10);

        //round, um Nachkommastellen loszuwerden
        return Math.round(randomFloat);
    }
}
