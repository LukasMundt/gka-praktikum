package haw.gka.praktikum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Disjoint Data Set, Datenstruktur für Union-Find resp. Kruskal-Algorithmus;
 * übernommen aus
 * <a href="https://www.geeksforgeeksorg/dsa/kruskals-minimum-spanning-tree-algorithm-greedy-algo-2/"/a>
 * und erweitert / auf Graphenstruktur angepasst
 */
public class DSU {
    private final Map<Node, Node> parent = new HashMap<>();

    /**
     * Jeder Knoten wird initial als sein eigener Parent gesetzt
     * Jeder Knoten ist eigenes Set und sein eigener Repräsentant
     *
     * @param nodes alle Knoten eines Graphs
     */
    public DSU(List<Node> nodes) {
        for (Node node : nodes) {
            parent.put(node, node);
        }
    }

//
//    public DSU(int n) {
//        parent = new int[n];
//        rank = new int[n];
//        for (int i = 0; i < n; i++) {
//            parent[i] = i;
//            rank[i] = 1;
//        }
//    }

    /**
     * Repräsentant (Parent von sich selbst) des Sets ist immer root, daher
     * rekursives Suchen nach root
     *
     * @param node ein beliebiger Knoten
     * @return den Knoten, der die Wurzel des Sets ist
     */
    public Node find(Node node) {
        //wenn Knoten nicht eigener Parent, weitermachen
        if (parent.get(node) != node) {
            node = parent.get(node);
            //Pfadkomprimierung zur Optimierung, Laufzeiten vergleichen TODO
            // parent.put(node, find(parent.get(node)));
        }
        return parent.get(node);
    }

    /**
     * Sucht die Repräsentanten der beiden Elemente / deren Set und setzt
     * eins in das Set des anderen
     *
     * @param startNode Knoten aus Menge 1
     * @param endNode   Knoten aus Menge 2
     */
    public boolean union(Node startNode, Node endNode) {
        //Root / Parent der Knoten finden
        Node startRoot = find(startNode);
        Node endRoot = find(endNode);

        //wenn nicht identisch, Knoten in einer Menge vereinigen
        if (startRoot != endRoot) {
            parent.put(endRoot, startRoot);
            return true;
        }
        return false; //Knoten bereits in gleicher Menge
    }
}
