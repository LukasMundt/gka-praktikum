package haw.gka.praktikum;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Repräsentiert ein Graphmodell mit Knoten und Kanten.
 * <p>
 * Der Graph kann gerichtete oder ungerichtete, gewichtete oder ungewichtete
 * Kanten enthalten. Zusätzlich bietet die Klasse Funktionen, die für eine
 * Breitensuche (BFS) nützlich sind, z. B. das Ermitteln von Nachbarn.
 * </p>
 */
public class GraphModel {
    /**
     * Menge aller Knoten im Graphen
     */
    private final HashSet<Node> _nodes;
    /**
     * Menge aller Kanten im Graphen
     */
    private final HashSet<Edge> _edges;
    /**
     * Indizes zu allen Knoten im Graphen
     */
    private final HashMap<Node, Integer> _indexedNodes;
    /**
     * Adjazenz-Map - Speichert zu jedem Knoten die mit ihm verbundenen Kanten
     */
    private final Map<Node, Set<Edge>> _adjacency;

    /**
     * Erzeugt ein leeres Graphmodell.
     */
    public GraphModel() {
        _nodes = new HashSet<>();
        _edges = new HashSet<>();
        _indexedNodes = new HashMap<>();
        _adjacency = new HashMap<>();
    }

    /**
     * Erzeugt ein Graphmodell mit vorhandenen Knoten und Kanten.
     *
     * @param nodes Menge der Knoten (kann null sein → leere Menge)
     * @param edges Menge der Kanten (kann null sein → leere Menge)
     */
    public GraphModel(Set<Node> nodes, Set<Edge> edges) {
        _nodes = (nodes == null) ? new HashSet<>() : new HashSet<>(nodes);
        _edges = (edges == null) ? new HashSet<>() : new HashSet<>(edges);
        _indexedNodes = new HashMap<>();
        _adjacency = new HashMap<>();

        // Jede Kante der Adjazenz-Map hinzufügen
        if (edges != null) {
            edges.forEach(edge -> {
                _adjacency.computeIfAbsent(edge.getStart(), k -> new HashSet<>()).add(edge);
                _adjacency.computeIfAbsent(edge.getEnd(), k -> new HashSet<>()).add(edge);
            });
        }
    }

    /**
     * Fügt einen oder mehrere Knoten zum Graphen hinzu.
     *
     * @param nodes Knoten, die hinzugefügt werden sollen
     * @throws NullPointerException wenn einer der Knoten null ist
     */
    public void addNodes(Node... nodes) {
        for (Node node : nodes) {
            if (node == null) {
                throw new NullPointerException("node is null");
            }
            _nodes.add(node);
        }
    }

    /**
     * Fügt einen oder mehrere Kanten zum Graphen hinzu.
     *
     * @param edges Kanten, die hinzugefügt werden sollen
     * @throws NullPointerException wenn eine der Kanten null ist
     */
    public void addEdges(Edge... edges) {
        for (Edge edge : edges) {
            if (edge == null) {
                throw new NullPointerException("edge is null");
            }
            _edges.add(edge);

            // versucht die Knoten der Kante hinzuzufügen -> danach auf jeden Fall enthalten
            _nodes.add(edge.getStart());
            _nodes.add(edge.getEnd());

            // Fügt Kante der Adjazenz-Map hinzu
            _adjacency.computeIfAbsent(edge.getStart(), k -> new HashSet<>()).add(edge);
            _adjacency.computeIfAbsent(edge.getEnd(), k -> new HashSet<>()).add(edge);
        }
    }

    /**
     * Erzeugt einen neuen Knoten anhand eines Strings und fügt ihn dem Graphen hinzu.
     *
     * @param line Name des Knotens
     * @return Gibt den hinzugefügten Knoten zurück
     * @throws NullPointerException wenn der Name null ist
     */
    public Node addNode(String line) {
        if (line == null) {
            throw new NullPointerException("node is null");
        }
        Node tempNode = Node.getNode(line);
        _nodes.add(tempNode);
        _adjacency.computeIfAbsent(tempNode, k -> new HashSet<>());
        return tempNode;
    }

    /**
     * Erzeugt eine Kante mit den angegebenen Eigenschaften und fügt sie dem Graphen hinzu.
     *
     * @param start      Startknoten
     * @param end        Endknoten
     * @param isDirected true → gerichtete Kante
     * @param isWeighted true → gewichtete Kante (Gewicht wird genutzt)
     * @param weight     Gewicht der Kante (nur relevant bei isWeighted)
     * @param edgeName   Name der Kante (wenn kein Name, dann null)
     */
    public void addEdge(Node start, Node end, boolean isDirected, boolean isWeighted, float weight, String edgeName) {
        Edge edge = new Edge(start, end, isDirected, isWeighted, weight, edgeName);
        _edges.add(edge);

        // versucht die Knoten der Kante hinzuzufügen -> danach auf jeden Fall enthalten
        _nodes.add(start);
        _nodes.add(end);

        // Fügt Kante der Adjazenz-Map hinzu
        _adjacency.computeIfAbsent(edge.getStart(), k -> new HashSet<>()).add(edge);
        _adjacency.computeIfAbsent(edge.getEnd(), k -> new HashSet<>()).add(edge);
    }

    /**
     * Weist einem Knoten einen Index (Ebene) zu.
     *
     * @param node  Knoten
     * @param index Index
     * @throws IllegalArgumentException wenn der Knoten nicht im Graphen enthalten ist
     */
    public void indexNode(Node node, int index) {
        if (!_nodes.contains(node)) {
            throw new IllegalArgumentException("node is not contained in the graph");
        } else if (_indexedNodes.containsKey(node)) {
            System.err.println("Node already indexed: " + node.getName() + "; Resetting index.");
        }
        _indexedNodes.put(node, index);
    }

    /**
     * Gibt den Index eines Knotens zurück.
     *
     * @param node Knoten
     * @return BFS-Index oder -1, wenn der Knoten keinen Index besitzt
     */
    public int getIndexOfNode(Node node) {
        if (node == null) {
            throw new IllegalArgumentException("node is null");
        } else if (_indexedNodes.containsKey(node)) {
            return _indexedNodes.get(node);
        }
        // wenn der Knoten nicht indiziert ist, wird -1 zurückgegeben
        return -1;
    }

    /**
     * Gibt alle Nachbarn zurück, die noch keinen Index besitzen.
     *
     * @param node Knoten
     * @return Menge unindizierter Nachbarn
     */
    public Set<Node> getUnindexedNeighbors(Node node) {
        if (!_nodes.contains(node)) {
            throw new IllegalArgumentException("node is not contained in the graph");
        }
        Set<Node> neighbors = new HashSet<>();

        for (Edge edge : _edges) {
            // wenn der andere Knoten von a aus erreichbar ist und noch nicht indiziert ist, wird er dem Ergebnis hinzugefügt
            if (edge.isOtherNodeReachableFromA(node)) {
                Node otherNode = edge.getOtherNode(node);
                if (!_indexedNodes.containsKey(otherNode)) {
                    neighbors.add(otherNode);
                }
            }
        }
        return neighbors;
    }

    /**
     * Gibt alle Knoten zurück, die den angegebenen Knoten erreichen können
     * (also entlang der Richtung zur angegebenen Node hin).
     *
     * @param node Knoten
     * @return Menge von Vorgängerknoten
     */
    public Set<Node> getReverseNeighbors(Node node) {
        if (!_nodes.contains(node)) {
            throw new IllegalArgumentException("node is not contained in the graph");
        }
        Set<Node> neighbors = new HashSet<>();
        for (Edge edge : _edges) {
            // wenn der übergebene Knoten von dem anderen Knoten der aktuellen Kante aus erreichbar ist, wird er dem Ergebnis hinzugefügt
            if (edge.isAReachableFromOtherNode(node)) {
                neighbors.add(edge.getOtherNode(node));
            }
        }
        return neighbors;
    }

    /**
     * Gibt denjenigen Rückwärts-Nachbarn zurück, der einen bestimmten Index besitzt.
     *
     * @param node  Knoten
     * @param index Gesuchter Index
     * @return passender Nachbarknoten oder null, falls keiner existiert
     */
    public Node getReverseNeighborWithIndex(Node node, int index) {
        if (!_nodes.contains(node)) {
            throw new IllegalArgumentException("node is not contained in the graph");
        }
        Set<Node> neighbors = getReverseNeighbors(node);
        for (Node neighbor : neighbors) {
            // wenn erster Nachbar mit richtigem Index gefunden wird dieser zurückgegeben
            if (index == getIndexOfNode(neighbor)) {
                return neighbor;
            }
        }
        // null, wenn kein passender Knoten gefunden
        return null;
    }

    /**
     * Ermittelt alle Knoten, die in keiner einzigen Kante vorkommen
     * (isolierte oder "Single"-Knoten).
     *
     * @return Menge isolierter Knoten
     */
    public Set<Node> getSingleNodes() {
        Set<Node> nodes = new HashSet<>();

        //alle Knoten mit Kanten sammeln
        for (Edge edge : _edges) {
            nodes.add(edge.getStart());
            nodes.add(edge.getEnd());
        }
        //dann aus Kopie aller Knoten entfernen = SingleKnoten
        Set<Node> allNodes = new HashSet<>(_nodes);
        allNodes.removeAll(nodes);

        return allNodes;
    }

    /**
     * @return Menge aller Knoten
     */
    public HashSet<Node> getNodes() {
        return _nodes;
    }

    /**
     * @return Menge aller Kanten
     */
    public HashSet<Edge> getEdges() {
        return _edges;
    }

    /**
     * @return Adjazenz-Map
     */
    public Map<Node, Set<Edge>> getAdjacency() {
        return _adjacency;
    }

    /**
     * Prüft, ob der Graph eine Kante zwischen zwei gegebenen Knoten hat,
     * in beide Richtungen (AB oder BA)
     *
     * @param A Knoten A
     * @param B Knoten B
     * @return boolean Kante vorhanden oder nicht
     */
    public boolean hasEdgeBetween(Node A, Node B) {
        for (Edge edge : getEdges()) {
            // Prüft, ob Kante ab oder Kante ba vorhanden
            if ((edge.getStart().equals(A) && edge.getEnd().equals(B)) ||
                    (edge.getStart().equals(B) && edge.getEnd().equals(A))) {
                return true;
            }
        }
        return false;
    }

    public float getTotalWeight() {
        return _edges
                .stream()
                .map(Edge::getWeight)
                .reduce(0.0f, Float::sum);
    }
}

