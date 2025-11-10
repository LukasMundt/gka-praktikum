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
        GraphModel graph = new GraphModel(null, null);

        for (String l : lines) {
            GraphModel tempGraph = parseLine(l);
            graph.addGraph(tempGraph);
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
    private GraphModel parseLine(String line) {
        GraphModel graph = new GraphModel(null, null);

        if (line == null) throw new IllegalArgumentException("line is null");
        String trimmed = line.trim();
        if (trimmed.isEmpty()) {
            return graph;
        }

        //RegEx erstellt mit regex101:
        /**
         * [a-z]{1,}\s*(?:->|<-)\s*[a-z]{1,} matcht gerichtete Graphen in beide Richtungen, mit beliebig vielen Leerzeichen
         * [a-z]{1,}\s\-\-\s[a-z]{1,} matcht ungerichtete Graphen mit zwei Knoten
         * [a-z] matcht einzelne Knoten
         **/
        //Pattern aus Java.utils verwenden
        //Muster erstellen
        Pattern directed = Pattern.compile("[a-z]{1,}\\s*(?:->|<-)\\s*[a-z]{1,} ");
        Pattern undirected = Pattern.compile("[a-z]{1,}\\s\\-\\-\\s[a-z]{1,}");
        //eventuell nicht robust?
        Pattern singleNode = Pattern.compile("[a-z]");

        //Matcherobjekt enthält Ergebnis der Prüfung
        Matcher mDirected = directed.matcher(trimmed);
        Matcher mUndirected = undirected.matcher(trimmed);
        Matcher mNode = singleNode.matcher(trimmed);

        if (mDirected.matches()){
            String[] parts = trimmed.split("\\s*(?:->|<-|-)\\s*");

            if (parts.length == 2) {
                String from = parts[0].trim();
                String to = parts[1].trim();
                graph.addDirectedEdge(from, to);
            } else {
                failures.add(trimmed);
            }
        } else if (mUndirected.matches()) {
            //extract nodes and double the undirected edge into zwo directed ones
            String[] parts = trimmed.split("\\s*\\-\\-\\s*");
            if (parts.length == 2) {
                String a = parts[0].trim();
                String b = parts[1].trim();
                graph.addUndirectedEdge(a, b);
            }
        } else if (mNode.matches()) {
            // try single node
            graph.addNode(trimmed);
        } else {
            //put line in List<String> failures
            failures.add(line);
        }

        return graph;
    }

    public List<String> getFailures() {
        return failures;
    }
}
