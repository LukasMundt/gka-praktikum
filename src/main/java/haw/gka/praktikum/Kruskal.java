package haw.gka.praktikum;

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

    //in: gewichtete Kanten; kommen am Ende aus Generator
    private final HashSet<Edge> _edges = new HashSet<>();

    public void searchSpanningTree(HashSet<Edge> _edges) {

        //call: sortEdges(), return: sortedList

        //return: Menge von Edges (Minimalgerüst)

    }

    //sortiert die Kanten der Eingabe nach steigender Länge (größer
    // werdender float Wert), nummeriert sie danach
    public void sortEdges() {


        //return: sortedList (klein nach groß) TODO wirklich Liste?
    }


    float totalWeight;

    //summiert Gewichte der Kanten aus MInimalgerüst
    public float getTotalWeight(List sortedList) {
        for (int i = 0; i <= sortedList.length(); i++) {
            //edgeWeight += edgeWeight;
        }
        return totalWeight;
    }

}
