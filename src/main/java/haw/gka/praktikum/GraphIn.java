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
    //Methode nimmt Datei entgegen, liest zeilenweise ein und parst Inhalt, um Graphen zu extrahieren
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
        LogResources.stopTask("Reading graph from " + path);
        return graph;
    }

    /**
     * Hilfs-Methode liest Datei zeilenweise ein, verwirft leere Zeilen und gibt Liste zurück
     * @param path
     * @return
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
     * @param line
     * @param graph
     * @return
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
            graphPart = trimmed.substring(0, colonIndex).trim();
        }

        //TODO für zweites Praktikum
        //(?<target>[a-z]): man kann RegEx-Teile auch direkt in Variablen speichern! Anschauen für Gewicht!


        //RegEx erstellt mit regex101:
        /**
         * [a-z]{1,}\s*(?:->|<-)\s*[a-z]{1,} matcht gerichtete Graphen in beide Richtungen, mit beliebig vielen Leerzeichen
         * [a-z]{1,}\s\-\-\s[a-z]{1,} matcht ungerichtete Graphen mit zwei Knoten
         * [a-z] matcht einzelne Knoten
         **/
        //Muster erstellen
        Pattern directed = Pattern.compile("[a-zA-Z0-9]{1,}\\s*(?:->|<-)\\s*[a-zA-Z0-9]{1,}(?:\\s*\\([^)]+\\))?\\s*");
        Pattern undirected = Pattern.compile("[a-zA-Z0-9]{1,}\\s--\\s[a-zA-Z0-9]{1,}(?:\\s*\\([^)]+\\))?\\s*");
        //eventuell nicht robust?
        Pattern singleNode = Pattern.compile("[a-zA-Z0-9]{1,}\\s*");

        //Matcherobjekt enthält Ergebnis der Prüfung
        Matcher mDirected = directed.matcher(graphPart);
        Matcher mUndirected = undirected.matcher(graphPart);
        Matcher mNode = singleNode.matcher(graphPart);

        boolean isDirected = false;

        if (mDirected.matches()){
            isDirected = true;
            //Aufspaltung der Knoten
            String[] parts = graphPart.split("\\s*(?:->|<-)\\s*");

            if (parts.length == 2) {
                String from = parts[0].trim();
                String to = parts[1].trim();
                Node startNode = Node.getNode(from);
                Node endNode = Node.getNode(to);
                graph.addNodes(startNode, endNode);
                graph.addEdge(startNode, endNode, true, hasWeight, edgeWeight);
                return graph;
            } else {
                failures.add(trimmed);
            }
        } else if (mUndirected.matches()) {
            //extract nodes and double the undirected edge into zwo directed ones
            String[] parts = graphPart.split("\\s*\\-\\-\\s*");
            if (parts.length == 2) {
                String a = parts[0].trim();
                String b = parts[1].trim();
                Node aNode = Node.getNode(a);
                Node bNode = Node.getNode(b);
                graph.addNodes(aNode, bNode);
                graph.addEdge(aNode, bNode, false, hasWeight, edgeWeight);
                return graph;
            }
        } else if (mNode.matches()) {
            // try single node
            graph.addNode(trimmed);
            return graph;
        } else {
            //put line in List<String> failures
            failures.add(line);
        }
        return graph;
    }

    /**
     *
     * @return
     */

    public List<String> getFailures() {
        if (failures.isEmpty())
            failures.add("keine Fehler beim Einlesen");
        return failures;
    }
}
