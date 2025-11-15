package haw.gka.praktikum;

import haw.gka.praktikum.LogResources.LogResources;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Methods to read the .gka files and extract graphs
 *
 */
public class GraphIn {

    /**
     * nimmt Datei entgegen, liest zeilenweise ein und parst Inhalt, um Graphen zu extrahieren
     *
     * @param path
     * @return der Gesamtgraph, ergänzt um alle Teilgraphen aus der Datei
     * @throws IOException
     */
    public GraphModel readGraph(String path) throws IOException {
        LogResources.startTask("Reading graph from " + path);
        //Datei einlesen
        List<String> lines = readFile(path);

        //neuen Graphen EINMAL erzeugen, an parseLine übergeben
        GraphModel graph = new GraphModel();
        //Liste der Graphen parsen
        for (String l : lines) {
            parseLine(l, graph);
        }

        //Fehlermeldung, wenn keine Zeilen geparst werden konnten
        if (lines.isEmpty()) {
            System.err.println("es konnten keine Graphen geparst werden, evtl. ist die Datei leer oder defekt");
        }

        LogResources.stopTask("Reading graph from " + path);


        return graph;
    }

    /**
     * Hilfs-Methode liest Datei zeilenweise ein, verwirft leere Zeilen und gibt Liste zurück
     * @param path der Dateipfad
     * @return der Dateiinhalt als Liste
     * @throws FileNotFoundException
     */
    List<String> readFile(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.trim().isEmpty()) continue;
            lines.add(line);
        }
        scanner.close();

        return lines;
    }

    List<String> failures = new ArrayList<>();

    /**
     * Hilfsmethode übernimmt das pattern matching via RegEx
     * @param line eine Zeile aus der Datei
     * @param graph der Gesamtgraph
     * @return der Gesamtgraph, der um die neue Zeile (Teilgraph) ergänzt wird
     */
    private GraphModel parseLine(String line, GraphModel graph) {

        //auf leere Zeile prüfen
        if (line == null) throw new IllegalArgumentException("line is null");
        if (graph == null) throw new IllegalArgumentException("graph is null");
        String trimmed = line.trim();
        if (trimmed.isEmpty()) {
            return graph;
        }
        //Zeichen für Zeilenende entfernen
        if (trimmed.endsWith(";")) {
            trimmed = trimmed.substring(0, trimmed.length() - 1).trim();
        }

        //ggf. Gewichtung abtrennen
        String graphPart = trimmed;
        float edgeWeight = 0;
        boolean hasWeight = false;
        int colonIndex = trimmed.lastIndexOf(':');
        if (colonIndex != -1) {
            try {
                //Gewichtung speichern
                edgeWeight = Float.parseFloat(trimmed.substring(colonIndex + 1).trim());
                hasWeight = true;

            } catch (NumberFormatException e) {
                failures.add("ungültige Gewichtung bei: " + trimmed);
            }
            //graphPart OHNE ": Gewichtung"
            graphPart = trimmed.substring(0, colonIndex).trim();
        }

        //RegEx erstellt mit regex101:
        /**
         *
         **/
        //Muster erstellen
        Pattern directed = Pattern.compile("(?<startNode>[\\p{L}0-9ß]{1,})\\s*(?:->|<-)\\s*(?<endNode>[\\p{L}0-9ß]{1,})\\s*(?<edgeName>\\([^)]+\\))?\\s*");
        Pattern undirected = Pattern.compile("(?<nodeA>[\\p{L}0-9ß]{1,})\\s*--\\s*(?<nodeB>[\\p{L}0-9ß]{1,})\\s*(?<edgeName>\\([^)]+\\))?\\s*");
        Pattern singleNode = Pattern.compile("(?<node>[\\p{L}0-9ß]{1,})\\s*");

        //um Named Capturing Group aus RegEx zu nutzen
        String edgeName = null;

        //Matcherobjekt enthält Ergebnis der Prüfung
        Matcher mDirected = directed.matcher(graphPart);
        Matcher mUndirected = undirected.matcher(graphPart);
        Matcher mNode = singleNode.matcher(graphPart);

        boolean isDirected = false;

        if (mDirected.matches()) {
            isDirected = true;

            // Die Knotennamen aus den benannten Gruppen auslesen
            String startNodeStr = mDirected.group("startNode");
            String endNodeStr = mDirected.group("endNode");

            //Kantenname auslesen
            edgeName = mDirected.group("edgeName");
            if (edgeName != null) {
                edgeName = edgeName.replaceAll("^\\(|\\)$", "");
            }

            //Knoten und Kanten zusammenstellen
            Node startNode = Node.getNode(startNodeStr);
            Node endNode = Node.getNode(endNodeStr);
            graph.addNodes(startNode, endNode);
            graph.addEdge(startNode, endNode, true, hasWeight, edgeWeight, edgeName);

            return graph;

        } else if (mUndirected.matches()) {

            // Die Knotennamen aus den benannten Gruppen auslesen
            String nodeAStr = mUndirected.group("nodeA");
            String nodeBStr = mUndirected.group("nodeB");

            //Kantenname auslesen
            edgeName = mUndirected.group("edgeName");
            if (edgeName != null) {
                edgeName = edgeName.replaceAll("^\\(|\\)$", "");
            }

            //Knoten und Kanten zusammenstellen
            Node aNode = Node.getNode(nodeAStr);
            Node bNode = Node.getNode(nodeBStr);
            graph.addNodes(aNode, bNode);
            graph.addEdge(aNode, bNode, false, hasWeight, edgeWeight, edgeName);

            return graph;

        } else if (mNode.matches()) {
            // add single node
            String nodeStr = mNode.group("node");
            graph.addNode(nodeStr);

            return graph;
        } else {
            //put line in List<String> failures
            failures.add(line);
        }

        return graph;
    }
    /**
     * Liste sammelt Zeilen, die nicht geparst werden konnten
     *
     * @return Liste der fehlerhaften Zeilen
     */

    public List<String> getFailures() {
        if (failures.isEmpty())
            failures.add("keine Fehler beim Einlesen");
        return failures;
    }
}
