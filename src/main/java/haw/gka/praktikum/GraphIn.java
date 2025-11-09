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
    public List<GraphModel> readGraph(String path) throws IOException {
        //Datei einlesen
        List<String> lines = readFile(path);

        //Liste der Graphen parsen
        GraphModel graph = new GraphModel(null, null);

        List<GraphModel> graphs = new ArrayList<>();
        for (String l : lines) {
            GraphModel tempGraph = parseLine(l);
            graphs.add(tempGraph);

        }
        return graphs;
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

    //Hilfsmethode übernimmt das pattern matching via RegEx
    private GraphModel parseLine(String line) {
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
        Matcher mDirected = directed.matcher(line);
        Matcher mUndirected = undirected.matcher(line);
        Matcher mNode = singleNode.matcher(line);

        GraphModel graph = new GraphModel(null, null);
        List<String> failures = new ArrayList<>();

        //TODO das hier noch sauber einarbeiten, macht gerade keinen SInn mehr in meinem Kopf
        if (mDirected.matches()){
            //
            String[] parts = line.split("\\s*(?:->|<-|-)\\s*");

            if (parts.length == 2) {
                String from = parts[0].trim();
                String to = parts[1].trim();
                graph.addDirectedEdge(from, to);
            } else {
                failures.add(line);
            }
        } else if (mUndirected.matches()) {
            //extract nodes and double the undirected edge into zwo directed ones
            String[] parts = line.split("\\s*\\-\\-\\s*");
            if (parts.length == 2) {
                String a = parts[0].trim();
                String b = parts[1].trim();
                graph.addUndirectedEdge(a, b);
            }
        } else if (mNode.matches()) {
            // try single node
            String node = line.trim();
            graph.addNode(node);
        } else {
            //put line in List<String> failures TODO Liste noch von außerhalb erreichbar machen
            failures.add(line);
        }

        return graph;
    }
}
