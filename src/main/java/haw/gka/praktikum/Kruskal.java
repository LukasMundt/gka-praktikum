package haw.gka.praktikum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * Algorithmus von Kruskal:
 * Eingabe: Eine Menge E der Kanten mit ihren Längen.
 * Ausgabe: Eine Teilmenge F der Kantenmenge E
 * <p>
 * - Nummeriere die Kanten e1...e|E| nach steigender Länge. Setze F := {}
 * - Für i := 1,..., |E|:
 * Falls F vereinigt mit {ei} nicht die Kantenmenge eines Kreises in G
 * enthält, setze F := F vereinigt mit {ei}
 */
public class Kruskal {
    /**
     * aus der Aufgabe:
     * - Algorithmus implementieren und testen
     * - Laufzeit ermitteln (kurz gewinnt)
     * - Ergebnis: minimaler Spannbaum und sein Gesamtgewicht
     * - JUnit-Tests, die Alg. mit Graphen aus Generator testen (umfassend)
     */

    //in: gewichtete Kanten resp. Graph; kommen am Ende aus Generator
    public List<Edge> searchSpanningTree(GraphModel graph) {
        //check if null TODO

        //Edges aus GraphModell holen
        HashSet<Edge> edges = graph.getEdges();

        //Kanten sortieren lassen
        List<Edge> sortedEdges = sortEdges(edges);

        List<Edge> minSpanningTree = null;
        //Kanten suchen, bis alle Knoten erreicht wurden
        for (int i = 0; i <= sortedEdges.size(); i++) {
            // check if Node is unvisited (?) TODO
            //if not in F, put Kante in F

        }

        return minSpanningTree;
    }

    //sortiert die Kanten der Eingabe nach steigender Länge (größer
    // werdender float Wert), nummeriert sie danach
    //dafür Edges comparable machen
    public List<Edge> sortEdges(HashSet<Edge> edges) {
        //check if null

        //von HashSet (Menge) zu Liste (sortierbar)
        List<Edge> sortedEdges = new ArrayList<>(edges);

        //da Edges comparable gemacht wurde, geht hier sort()
        Collections.sort(sortedEdges);

        return sortedEdges;
    }


    float totalWeight;

    //summiert Gewichte der Kanten aus Minimalgerüst
    public float getTotalWeight(List<Edge> minSpanningTree) {
        //check if null

        //getWeight of each Edge in minSpanningTree


        for (int i = 0; i <= minSpanningTree.size(); i++) {
            //totalWeight += edgeWeight;
        }
        return totalWeight;
    }

}
