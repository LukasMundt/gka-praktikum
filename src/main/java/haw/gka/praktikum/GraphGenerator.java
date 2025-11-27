package haw.gka.praktikum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Überlegen Sie sich dazu eine allgemeine Konstruktion eines ungerichteten Graphen
 * für eine vorgegebene Anzahl von Knoten und Kanten mit beliebigen, aber unter-
 * schiedlichen, nicht-negativen Kantengewichten.
 */
//TODO fertig kommentieren
public class GraphGenerator {
//Input: x Knoten, y Kanten
    // Output: GraphModel mit x Knoten, y Kanten
    // Kanten sind gewichtet, nicht gerichtet

    private final int nodesNr;
    private final int edgesNr;


    public GraphGenerator(int nodesNr, int edgesNr) throws IllegalArgumentException {
        if (nodesNr < 1 || edgesNr < 0) {
            throw new IllegalArgumentException();
        }

        this.nodesNr = nodesNr;
        this.edgesNr = edgesNr;
    }


    public GraphModel generateGraph() {
        GraphModel generatedGraph = new GraphModel();
        // nodesNr Nodes anlegen
        for (int i = 1; i <= nodesNr; i++) {
            // brauchen random Bezeichnung
            String newNode = generateString();

            //dem Graphen hinzufügen
            generatedGraph.addNode(newNode);
        }
        //for dev
        System.out.println("es wurden folgende Knoten generiert: " + generatedGraph.getNodes().toString());

        // mit diesen Nodes insgesamt y Edges bilden (auch: vollständiger Graph)
        // Muster:
        // Edge generatedEdge = new Edge(NodeA, NodeB, false, true, randomFloat);
        //von HashSet (Menge) zu Liste (sortierbar)
        List<Node> allNodes = new ArrayList<>(generatedGraph.getNodes());

        for (int j = 1; j <= edgesNr; j++) {
            int rIndexA = new Random().nextInt(allNodes.size() - 1);
            int rIndexB = new Random().nextInt(allNodes.size() - 1);

            Node nodeA = allNodes.get(rIndexA);
            Node nodeB = allNodes.get(rIndexB);

            float genWeight = generateFloat();

            Edge ab = null;
            Edge ba = null;
            //prüfen, ob die Knoten gleich sind
            if (nodeA != nodeB) {
                //Kante AB und gespiegelte Kopie BA zusammenstellen mit
                // folgenden Parametern:
                ab = new Edge(nodeA, nodeB, false, true, genWeight, null);
                ba = new Edge(nodeB, nodeA, false, true, genWeight, null);

                //prüfen, ob Kante (oder gedrehtes Äquivalent) bereits
                // vorhanden ist
                if (!generatedGraph.getEdges().contains(ab) || !generatedGraph.getEdges().contains(ba)) {
                    generatedGraph.addEdges(ab);
                }
            }
        }
        //for dev
        System.out.println(generatedGraph.getEdges());

        return generatedGraph;
    }

    /**
     * Erstmal ein QuickFix für die KnotenNamen,
     * TODO eleganter wäre natürlich erst a-z, dann aa-zz etc. und für
     * beliebige Größe
     *
     * @return String ein generierter String von fester Länge (default 3)
     */
    private String generateString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 3; // 26³ Möglichkeiten (17.576)
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);

        //für die oben festgelegte Stringlänge random chars a-z wählen und zu
        // String zusammenbauen
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }

    public float generateFloat() {
        Random random = new Random();
        float randomFloat = (random.nextFloat() * 10);

        return Math.round(randomFloat);
    }
}
