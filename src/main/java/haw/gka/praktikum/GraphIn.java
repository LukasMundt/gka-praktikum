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

    List<String> failures = new ArrayList<>();

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
     *
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
            if (line.trim().isEmpty()) {
                continue;
            }
            lines.add(line);
        }
        scanner.close();

        return lines;
    }

    /**
     * Hilfsmethode übernimmt das pattern matching via RegEx
     *
     * @param line  eine Zeile aus der Datei
     * @param graph der Gesamtgraph
     * @return der Gesamtgraph, der um die neue Zeile (Teilgraph) ergänzt wird
     */
    private GraphModel parseLine(String line, GraphModel graph) {

        //auf leere Zeile prüfen
        if (line == null) {
            throw new IllegalArgumentException("line is null");
        }
        if (graph == null) {
            throw new IllegalArgumentException("graph is null");
        }
        String trimmed = line.trim();
        if (trimmed.isEmpty()) {
            return graph;
        }
        //Zeichen für Zeilenende entfernen
        if (trimmed.endsWith(";") || trimmed.endsWith(",")) {
            trimmed = trimmed.substring(0, trimmed.length() - 1).trim();
        }

        //RegEx erstellt mit regex101
        // refactor: Named Capturing Groups einsetzen ((?<name>xxx),
        // optionales Gewicht mit reinnehmen

        //Muster erstellen
        Pattern graphPattern = Pattern.compile("(?<nodeA>[\\p{L}0-9ß]{1,})" +
                "\\s*((?<direction>->|<-|--)?\\s*(?<nodeB>[\\p{L}0-9ß]{1,}?)" +
                "\\s*" +
                "(?<edgeName>\\([^)]+\\))?\\s*(?<edgeWeight>:\\s*\\d*)?)?");

        //um Named Capturing Group aus RegEx zu nutzen
        String edgeName = "";
        float edgeWeight = 1; //ist ja immer 1, 1 wird aber nicht angezeigt


        //Matcherobjekt enthält Ergebnis der Prüfung
        Matcher match = graphPattern.matcher(trimmed);

        boolean isDirected = false;
        boolean hasWeight = false;
        String nodeBStr = "";

        // TODO check if that works correct!
        if (match.matches()) {
            // KnotenA auslesen (deckt Single Nodes ab) und Graph hinzufügen
            String nodeAStr = match.group("nodeA");
            graph.addNode(nodeAStr);

            Node startNode = null;
            Node endNode = null;
            Node nodeA = null;
            Node nodeB = null;


            if (match.group("nodeB") != null) {
                //wenn zweiten Knote, dann auch Kante, evtl Gewicht, Name
                nodeBStr = match.group("nodeB");
                graph.addNode(nodeBStr); //ist das sinnvoll? HELP

                String direction = match.group("direction");
                //gerichtet (wenn ja, wie herum?) oder ungerichtet?
                if (direction.equals("->")) {
                    isDirected = true;
                    startNode = Node.getNode(nodeAStr);
                    endNode = Node.getNode(nodeBStr);

                } else if (direction.equals("<-")) {
                    isDirected = true;
                    startNode = Node.getNode(nodeBStr);
                    endNode = Node.getNode(nodeAStr);
                } else { //ungerichtet
                    nodeB = Node.getNode(nodeBStr);
                    nodeA = Node.getNode(nodeAStr);
                }

                // optionalen Kantenname auslesen
                edgeName = match.group("edgeName");
                if (edgeName != null) {
                    edgeName = edgeName.replaceAll("^\\(|\\)$", "");
                }

                // optionales Gewicht auslesen, Doppelpunkt löschen, in Float
                // konvertieren
                if (match.group("edgeWeight") != null) {
                    String temp = match.group("edgeWeight").replace(":", "").trim();
                    edgeWeight = Float.parseFloat(temp);
                    if (edgeWeight > 1.0) {
                        hasWeight = true;
                    }
                }

                //alles zusammenstellen für Kante
                if (isDirected) {
                    graph.addEdge(startNode, endNode, true, hasWeight,
                            edgeWeight, edgeName);
                } else {
                    graph.addEdge(nodeA, nodeB, false, hasWeight,
                            edgeWeight, edgeName);
                }
            }
            return graph;

        } else { //keine der Optionen traf zu
            failures.add(line); //put line in List<String> failures
        }

        return graph;
    }

    /**
     * Liste sammelt Zeilen, die nicht geparst werden konnten
     *
     * @return Liste der fehlerhaften Zeilen
     */

    public List<String> getFailures() {
        if (failures.isEmpty()) {
            failures.add("keine Fehler beim Einlesen");
        }
        return failures;
    }
}
