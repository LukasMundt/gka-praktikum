package haw.gka.praktikum;

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
        //Datei einlesen
        List<String> lines = readFile(path);

        //Liste der Graphen parsen
        GraphModel graph = new GraphModel();

        for (String l : lines) {
            parseLine(l, graph);
        }
        return graph;
    }

    //Hilfs-Methode liest Datei zeilenweise ein, verwirft leere Zeilen und gibt Liste zurück
    private List<String> readFile(String path) throws FileNotFoundException {
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
    //Hilfsmethode übernimmt das pattern matching via RegEx
    private void parseLine(String line, GraphModel graph) {
        if (line == null) throw new IllegalArgumentException("line is null");
        if (graph == null) throw new IllegalArgumentException("graph is null");
        String trimmed = line.trim();
        if (trimmed.isEmpty()) {
            return;
        }

        //RegEx erstellt mit regex101:
        /**
         * [a-z]{1,}\s*(?:->|<-)\s*[a-z]{1,} matcht gerichtete Graphen in beide Richtungen, mit beliebig vielen Leerzeichen
         * [a-z]{1,}\s\-\-\s[a-z]{1,} matcht ungerichtete Graphen mit zwei Knoten
         * [a-z] matcht einzelne Knoten
         **/
        //Pattern aus Java.utils verwenden
        //Muster erstellen
        Pattern directed = Pattern.compile("[a-zA-Z]{1,}\\s*(?:->|<-)\\s*[a-zA-Z]{1,}\\s*:\\s*[0-9]{0,}");
        Pattern undirected = Pattern.compile("[a-zA-Z]{1,}\\s\\-\\-\\s[a-zA-Z]{1,}\\s*:\\s*[0-9]{0,}");
        //eventuell nicht robust?
        Pattern singleNode = Pattern.compile("[a-zA-Z]{1,}\\s*");

        //Matcherobjekt enthält Ergebnis der Prüfung
        Matcher mDirected = directed.matcher(trimmed);
        Matcher mUndirected = undirected.matcher(trimmed);
        Matcher mNode = singleNode.matcher(trimmed);

        if (mDirected.matches()){
            String[] parts = trimmed.split("\\s*(?:->|<-|-)\\s*");

            if (parts.length == 2) {
                String from = parts[0].trim();
                String to = parts[1].trim();
                Node startNode = Node.getNode(from);
                Node endNode = Node.getNode(to);
                Edge edge = new Edge(startNode, endNode, true);
                graph.addNodes(startNode, endNode);
                graph.addEdges(edge);
            } else {
                failures.add(trimmed);
            }
        } else if (mUndirected.matches()) {
            //extract nodes and double the undirected edge into zwo directed ones
            String[] parts = trimmed.split("\\s*\\-\\-\\s*");
            if (parts.length == 2) {
                String a = parts[0].trim();
                String b = parts[1].trim();
                Node aNode = Node.getNode(a);
                Node bNode = Node.getNode(b);

                Edge edge = new Edge(aNode, bNode, false);
                graph.addNodes(aNode, bNode);
                graph.addEdges(edge);
            }
        } else if (mNode.matches()) {
            // try single node
            graph.addNode(trimmed);
        } else {
            //put line in List<String> failures
            failures.add(line);
        }
    }

    public List<String> getFailures() {
        if (failures.isEmpty())
            failures.add("keine Fehler beim Einlesen");
        return failures;
    }
}
