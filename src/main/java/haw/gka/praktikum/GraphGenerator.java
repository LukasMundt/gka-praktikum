package haw.gka.praktikum;

import haw.gka.praktikum.LogResources.LogResources;

import java.util.*;
import java.util.stream.Collectors;

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
     * ungerichteten Graphen mit zufällig gewählten Gewichten. Es werden nur
     * zusammenhängende Graphen zurückgegeben.
     *
     * @param nodesNr int Anzahl der Knoten
     * @param edgesNr int Anzahl der Kanten
     * @param allNodeDegreesEven boolean Alle Knoten sollen einen geraden Knotengrad haben
     * @return GraphModel der erstellte Graph
     */
    public GraphModel generateGraph(int nodesNr, int edgesNr, boolean allNodeDegreesEven, boolean connected) {
        LogResources.startTask("Generate Graph");

        long maxEdges = ((long) nodesNr * ((long) nodesNr - 1)) / 2;
        System.out.println("maxEdges: " + maxEdges);

        if (nodesNr < 1 || edgesNr < 0 || edgesNr > maxEdges) {
            throw new IllegalArgumentException("die Knoten- und/oder " +
                    "Kantenanzahl ist nicht sinnvoll gewählt");
        }

        if (connected && edgesNr < nodesNr-1) {
            throw new IllegalArgumentException("Zu wenige Kanten, um einen zusammenhängenden Graphen zu erzeugen.");
        }

        if (allNodeDegreesEven) {
            // Für einen Graphen mit nur geraden Knotengraden gilt:
            // - Die Anzahl der Kanten muss ausreichen
            // - Minimum: n Kanten für n Knoten (jeder Grad mindestens 2)
            if (edgesNr < nodesNr) {
                throw new IllegalArgumentException("Für einen zusammenhängenden Graphen mit nur " +
                        "geraden Knotengraden werden mindestens " + nodesNr + " Kanten benötigt.");
            }
        }

        //Graph generieren, in den die Nodes und Edges gespeichert werden
        GraphModel generatedGraph = new GraphModel();

        // Adjazenzliste
        HashMap<Integer, HashSet<Integer>> adjacencyList = new HashMap<>();

        // complete graph
        if (edgesNr > maxEdges * 0.9) {
            this.addNodes(generatedGraph, nodesNr);
            this.addEdgesForCompleteGraph(generatedGraph, adjacencyList);
        } else {
            this.addEdgesForSpannigTree(generatedGraph, nodesNr, adjacencyList);
        }


        if (generatedGraph.getEdges().size() > edgesNr) {
            this.removeExcessEdges(generatedGraph, edgesNr, connected, adjacencyList);
        } else if (generatedGraph.getEdges().size() < edgesNr) {
            this.addRemainingEdges(generatedGraph, edgesNr, adjacencyList);
        }

        if(allNodeDegreesEven){
            this.makeAllNodeDegreesEven(generatedGraph, adjacencyList, connected);
        }

        // todo: Alle Knoten des Graphen mit geradem grad ausstatten

        LogResources.stopTask("Generate Graph");
        System.out.println("Edges count: " + generatedGraph.getEdges().size());
        return generatedGraph;
    }

    /**
     * Die Methode generiert gemäß der übergebenen Parameter einen
     * ungerichteten Graphen mit zufällig gewählten Gewichten. Per default
     * wird nicht dafür gesorgt, dass alle Knotengrade gerade sind.
     *
     * @param nodesNr int Anzahl der Knoten
     * @param edgesNr int Anzahl der Kanten
     * @return GraphModel der erstellte Graph
     */
    public GraphModel generateGraph(int nodesNr, int edgesNr) {
        return this.generateGraph(nodesNr, edgesNr, false, false);
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

    private void addNodes(GraphModel generatedGraph, int nodesNr) {
        // nodesNr Nodes anlegen
        for (int i = 1; i <= nodesNr; i++) {
            // brauchen Bezeichnung
            String newNode = "n_" + i;

            //dem Graphen hinzufügen
            generatedGraph.addNode(newNode);
        }
    }

    private void addRemainingEdges(GraphModel generatedGraph, int edgesNr, Map<Integer, HashSet<Integer>> adjacencyList) {
        int nodesNr = generatedGraph.getNodes().size();

        //Zähler für bereits erstellte Kanten
        int j = generatedGraph.getEdges().size();

        // Adjazenzliste
        HashMap<Integer, HashSet<Integer>> usedIndexes = new HashMap<>(adjacencyList);

        while (j < edgesNr) {
            // zufällige Auswahl aus Knoten-Liste
            int rIndexA = _random.nextInt(nodesNr)+1;
            Set<Integer> usedIndexesA = usedIndexes.getOrDefault(rIndexA, new HashSet<>());

            int rIndexB = _random.nextInt(nodesNr)+1;

            // Generierung von Zufallszahlen solange Kante schon existiert oder Start gleich Ziel
            while (usedIndexesA.contains(rIndexB) || rIndexA == rIndexB) {
                rIndexB = _random.nextInt(nodesNr)+1;
            }

            // Knoten generieren
            Node nodeA = Node.getNode("n_"+rIndexA);
            Node nodeB = Node.getNode("n_"+rIndexB);

            // Kante hinzufügen
            this.addEdge(nodeA, rIndexA, nodeB, rIndexB, generatedGraph, usedIndexes);

            // Index generierter Kanten hochzählen
            j++;
        }

    }

    private void addEdgesForCompleteGraph(GraphModel generatedGraph, Map<Integer, HashSet<Integer>> adjacencyList) {
        if (!generatedGraph.getEdges().isEmpty()) {
            generatedGraph.clearEdges();
        }

        for (int i = 1; i <= generatedGraph.getNodes().size(); i++) {
            for (int j = 1; j <= generatedGraph.getNodes().size(); j++) {
                if(i != j && !adjacencyList.getOrDefault(i, new HashSet<>()).contains(j)) {
                    Node nodeA = Node.getNode("n_" + i);
                    Node nodeB = Node.getNode("n_" + j);

                    this.addEdge(nodeA, i, nodeB, j, generatedGraph, adjacencyList);
                }
            }
        }
    }

    private void addEdgesForSpannigTree(GraphModel generatedGraph, int nodesNr, Map<Integer, HashSet<Integer>> adjacencyList) {
        List<Integer> connectedNodes = new ArrayList<>();

        // Ersten Knoten hinzufügen
        generatedGraph.addNode("n_1");
        connectedNodes.add(1);

        for (int newNodeIndex = 2; newNodeIndex <= nodesNr; newNodeIndex++) {
            // Neuen Knoten erstellen
            Node newNode = Node.getNode("n_"+newNodeIndex);

            // Bestehenden Knonten erstellen
            int existingNodeIndex = _random.nextInt(Math.max(connectedNodes.size()-1, 1))+1; // todo: das ist schwachsinn, ich kann die zahl direkt verwenden
            Node existingNode = Node.getNode("n_"+existingNodeIndex);

            //dem Graphen hinzufügen
            generatedGraph.addNodes(newNode);

            // Kante hinzufügen
            this.addEdge(existingNode, existingNodeIndex, newNode, newNodeIndex, generatedGraph, adjacencyList);

            connectedNodes.add(newNodeIndex);
        }
    }

    private void removeExcessEdges(GraphModel generatedGraph, int edgesNr, boolean connected, Map<Integer, HashSet<Integer>> adjacencyList) {
        List<Edge> allEdges = new ArrayList<>(generatedGraph.getEdges());

        int excessEdges = allEdges.size() - edgesNr;

        for (int i = 0; i < excessEdges; i++) {
            int rIndex = _random.nextInt(allEdges.size());

            Edge edge = allEdges.get(rIndex);

            if (connected && generatedGraph.isEdgeABridge(edge)) {
                continue;
            }

            Node nodeA = edge.getStart();
            int indexA = Integer.parseInt(nodeA.getName().substring(2));

            Node nodeB = edge.getEnd();
            int indexB = Integer.parseInt(nodeB.getName().substring(2));

            generatedGraph.removeEdge(edge);
            allEdges.remove(rIndex);
            adjacencyList.get(indexA).remove(indexB);
            adjacencyList.get(indexB).remove(indexA);
        }

        if(allEdges.size() > edgesNr){
            throw new IllegalArgumentException("Failure: Konnte den Graphen nicht herstellen, ohne ihn zu trennen.");
        }
    }

    private void makeAllNodeDegreesEven(GraphModel graph,
                                        Map<Integer, HashSet<Integer>> adjacencyList,
                                        boolean connected) {

        List<Integer> oddNodes = adjacencyList.keySet()
                .stream()
                .filter(i -> adjacencyList.get(i).size() % 2 != 0)
                .collect(Collectors.toList());

        if (oddNodes.size() % 2 != 0) {
            throw new IllegalStateException("Ungerade Anzahl ungerader Knoten – unmöglich.");
        }

        while (!oddNodes.isEmpty()) {
            int a = oddNodes.removeFirst();
            int b = oddNodes.removeFirst();

            Node nodeA = Node.getNode("n_" + a);
            Node nodeB = Node.getNode("n_" + b);

            if (adjacencyList.getOrDefault(a, new HashSet<>()).contains(b)) {
                Optional<Edge> edge = graph
                        .getAdjacency()
                        .getOrDefault(nodeA, new HashSet<>())
                        .stream()
                        .filter(e -> e.getOtherNode(nodeA).equals(nodeB))
                        .findFirst();

                if (edge.isEmpty() || (connected && graph.isEdgeABridge(edge.get()))) {
                    oddNodes.add(a);
                    oddNodes.addFirst(b);
                    continue;
                }

                edge.ifPresent(graph::removeEdge);
                adjacencyList.get(a).remove(b);
                adjacencyList.get(b).remove(a);
            } else {
                this.addEdge(nodeA, a, nodeB, b, graph, adjacencyList);
            }
        }

        // todo: Kanten hinzufügen oder entfernen
    }

    private void addEdge(Node nodeA, int indexA, Node nodeB, int indexB, GraphModel generatedGraph, Map<Integer, HashSet<Integer>> adjacencyList) {
        //Zufallsgewicht generieren lassen
        float genWeight = generateFloat();

        // Kante AB zusammenstellen mit folgenden Parametern:
        Edge ab = new Edge(nodeA, nodeB, false, true, genWeight, null);

        // Kante hinzufügen
        generatedGraph.addEdges(ab);

        // Kante in lokale Adjazenzliste eintragen
        adjacencyList.computeIfAbsent(indexA, k -> new HashSet<>()).add(indexB);
        adjacencyList.computeIfAbsent(indexB, k -> new HashSet<>()).add(indexA);
    }
}
