package haw.gka.praktikum;

import haw.gka.praktikum.LogResources.LogResources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * Implementation des Kruskal-Algorithmus, gibt minimalen Spannbaum und
 * dessen Gesamtgewicht aus
 * - Laufzeit ermitteln (kurz gewinnt) TODO
 * - JUnit-Tests, die Alg. mit Graphen aus Generator testen (umfassend) TODO
 *
 */
public class Kruskal {

    /**
     * Implementierung des Kruskal-Algorithmus:
     * Eingabe: Eine Menge E der Kanten mit ihren Längen.
     * Ausgabe: Eine Teilmenge F der Kantenmenge E
     * Nummeriere / sortiere die Kanten e1...e|E| nach steigender Länge.
     * Setze F := {}
     * Für i := 1,..., |E|:
     * Falls F vereinigt mit {ei} nicht die Kantenmenge eines Kreises in G
     * enthält, setze F := F vereinigt mit {ei}
     * Berechnet den minimalen Spannbaum eines gewichteten, ungerichteten
     * Graphs.
     *
     * @param graph Eine Instanz graph der GraphModel-Klasse, entweder aus
     *              einer .gka Datei ausgelesen oder vom GraphGenerator erzeugt
     * @return eine Liste von Kanten, die den minimalen Spannbaum von
     * graph enthält
     * @throws NullPointerException wenn übergebenes GraphModell null ist
     */
    public List<Edge> searchSpanningTree(GraphModel graph) throws NullPointerException {
        LogResources.startTask("Running Kruskal on graph");

        //check if null
        if (graph == null) {
            throw new NullPointerException("Graph darf nicht null sein.");
        }

        //Edges aus übergebenem Graph holen
        HashSet<Edge> edges = graph.getEdges();

        //Kanten sortieren lassen, Hilfsmethode dieser Klasse
        List<Edge> sortedEdges = sortEdges(edges);

        //alle Knoten des Graphen ausgeben
        List<Node> nodesList = new ArrayList<>(graph.getNodes());

        //Initialisierung der DSU-Logik
        DSU dsu = new DSU(nodesList);

        //Spannbaum suchen
        List<Edge> minSpanningTree = new ArrayList<>(); //entspricht F

        //Kanten von billig nach teuer durchgehen,
        // bis alle Knoten erreicht wurden
        for (int i = 0; i < sortedEdges.size(); i++) {

            Edge currentEdge = sortedEdges.get(i);
            Node startNode = currentEdge.getStart();
            Node endNode = currentEdge.getEnd();

            //find() gibt Repräsentanten / root der Mengen zurück, zu denen
            // die Knoten gehören
            Node rootStart = dsu.find(startNode);
            Node rootEnd = dsu.find(endNode);

            //Kante darf keinen Kreis bilden (Start == Ende), die beiden
            // Mengen nicht zu einem solchen zusammenschließen
            if (rootStart != rootEnd) {
                //kein Kreis: put currentEdge in F
                minSpanningTree.add(currentEdge);

                //Sets der beiden Knoten vereinigen (DSU-Logik)
                dsu.union(startNode, endNode);

                //Abbruch, wenn alle Kanten gefunden wurden (die keinen Kreis
                // bilden)
                if (minSpanningTree.size() == nodesList.size() - 1) {
                    break;
                }
            } //else bildet Kreis, dann fällt Kante raus
        }
        //Stop Logging
        LogResources.stopTask("Running Kruskal on graph");

        return minSpanningTree;
    }

    /**
     * Hilfsmethode, sortiert die übergebene Menge von Kanten in aufsteigender
     * Reihenfolge basierend auf ihrem Gewicht (sekundär alphabetisch).
     *
     * @param edges eine Menge Kanten, die aus einem Graphen extrahiert wurden
     * @return eine nach aufsteigendem Gewicht sortierte Liste an Kanten
     * @throws NullPointerException, wenn übergebene Liste null ist
     */
    public List<Edge> sortEdges(HashSet<Edge> edges) throws NullPointerException {
        //check if null
        if (edges == null) {
            throw new NullPointerException("Die übergebenen Kanten dürfen " +
                    "nicht null sein.");
        }

        //von HashSet (Menge) zu Liste (sortierbar)
        List<Edge> sortedEdges = new ArrayList<>(edges);
        System.out.println(sortedEdges);

        //da Edges comparable gemacht wurde, geht hier sort()
        //compareTo() in Edge vergleicht Gewicht!
        Collections.sort(sortedEdges);

        return sortedEdges;
    }

    /**
     * Summiert Gewichte der Kanten aus Minimalgerüst
     *
     * @param minSpanningTree ein mit searchSpanningTree ermittelter Spannbaum
     * @return das Gesamtgewicht als Float
     * @throws NullPointerException wenn übergebener Spannbaum null ist
     */
    public float getTotalWeight(List<Edge> minSpanningTree) throws NullPointerException {
        //check if null
        if (minSpanningTree == null) {
            throw new NullPointerException("der Spannbaum darf nicht null " +
                    "sein");
        }
        float totalWeight = 0.0f;

        //getWeight of each Edge in minSpanningTree
        for (int i = 0; i < minSpanningTree.size(); i++) {
            Edge currentEdge = minSpanningTree.get(i);
            totalWeight += currentEdge.getWeight();
        }
        return totalWeight;
    }

}
