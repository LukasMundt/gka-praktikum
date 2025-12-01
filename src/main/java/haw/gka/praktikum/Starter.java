package haw.gka.praktikum;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Startklasse der JavaFX-GUI für das GKA-Praktikum.
 * <p>
 * Diese Anwendung ermöglicht das:
 * <ul>
 *     <li>Auswählen einer Graphdatei</li>
 *     <li>Visualisieren des Graphen</li>
 *     <li>Berechnen des kürzesten Wegs mittels BFS</li>
 *     <li>Konvertieren zwischen gerichteten und ungerichteten Graphen</li>
 * </ul>
 * </p>
 */
public class Starter extends Application {

    /** Button zum Anzeigen des Graphen */
    final Button _buttonDisplay = new Button("Graph anzeigen");
    /** Button zum Anzeigen des Minimalen Spannbaums */
    final Button _buttonDisplaySpanningTree = new Button("Minimalen Spannbaum anzeigen");

    /** Button zum Öffnen des BFS-Fensters */
    final Button _buttonBFS = new Button("Kürzesten Weg finden");

    /** Button zum Konvertieren zu einem gerichteten Graphen */
    final Button _buttonConvertToDirected = new Button("zu gerichtetem Graph konvertieren");

    /** Button zum Konvertieren zu einem ungerichteten Graphen */
    final Button _buttonConvertToUndirected = new Button("zu ungerichtetem Graph konvertieren");

    /** Label zur Anzeige von Fehlermeldungen oder Hinweisen */
    final Label _notification = new Label ();

    /** Auswahlbox für die Liste der verfügbaren Graphdateien */
    final ComboBox<String> _filesComboBox = new ComboBox<>();

    /** Basisverzeichnis für alle Graphdateien (Hier werden auch konvertierte Graphen hin gespeichert) */
    private static final String BASE_PATH = "src/test/java/resources/";

    /**
     * Startpunkt der Anwendung.
     */
    public static void main(String[] args) {
        launch(args);
    }


    /**
     * Initialisiert die JavaFX-Oberfläche.
     *
     * @param primaryStage Das Hauptfenster der GUI
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Suche dir eine Datei aus.");
        primaryStage.setResizable(true);

        Scene scene = new Scene(new Group(), 550, 250);

        // alle verfügbaren Dateien einlesen und der Combobox hinzufügen
        List<String> fileList = Stream.of(Objects.requireNonNull(new File(BASE_PATH).listFiles()))
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .toList();
        _filesComboBox.getItems().addAll(fileList);

        // Buttons und Click-Aktionen konfigurieren
        this.configureShowGraphButton(_filesComboBox);
        this.configureBFSButton(_filesComboBox);
        this.configureConvertToDirected(_filesComboBox);
        this.configureConvertToUndirected(_filesComboBox);
        this.configureDisplayMinimalSpanningTree(_filesComboBox);

        // Grundlayout
        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setHgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));

        grid.add(new Label("Datei: "), 0, 0);
        grid.add(_filesComboBox, 1, 0);
        grid.add(_buttonDisplay, 2, 0);
        grid.add(_buttonBFS, 2, 1);
        grid.add(_buttonConvertToDirected, 2, 2);
        grid.add(_buttonConvertToUndirected, 2, 3);
        grid.add(_buttonDisplaySpanningTree, 2, 4);
        grid.add (_notification, 1, 3, 3, 1);

        ((Group)scene.getRoot()).getChildren().add(grid);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Lädt die aktuell ausgewählte Datei ein und gibt den erzeugten Graphen zurück.
     *
     * @param filesComboBox ComboBox mit Dateiauswahl
     * @return das eingelesene {@link GraphModel} oder {@code null}, falls keine Datei gewählt wurde oder ein Fehler aufgetreten ist
     */
    private GraphModel getSelectedGraph(ComboBox<String> filesComboBox) {
        // Ermittlung der ausgewählten Datei -> wenn keine Datei ausgewählt wird eine Fehlermeldung angezeigt und null zurückgegeben
        String file = filesComboBox.getSelectionModel().getSelectedItem();
        if(file == null) {
            System.err.println("No file selected");
            _notification.setText("Keine Datei ausgewählt.");
            return null;
        } else {
            _notification.setText("");
        }

        // Pfad zu der Datei wird gesetzt und der Reader für die Datei initialisiert
        String path = BASE_PATH + file;
        GraphIn graphReader = new GraphIn();

        try {
            // Es wird versucht den Graphen aus der Datei zu lesen und zu parsen
            GraphModel graph = graphReader.readGraph(path);
            System.out.println("Datei erfolgreich eingelesen.");
            System.out.println("Fehlerhaft eingelesene Teilgraphen: "+ graphReader.getFailures());
            return graph;
        } catch (IOException e) {
            // Es konnte nicht auf die Datei zugegriffen werden oder ein ähnlicher Fehler ist aufgetreten
            System.err.println("Fehler beim Einlesen der Datei: " + e.getMessage());
            _notification.setText("Fehler beim Einlesen der Datei: "+ path);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Konfiguriert den Button „Graph anzeigen“.
     *
     * @param filesComboBox ComboBox für Dateiauswahl
     */
    private void configureShowGraphButton(ComboBox<String> filesComboBox) {
        _buttonDisplay.setOnAction(event -> {
            GraphModel graph = this.getSelectedGraph(filesComboBox);
            if(graph != null){
                GraphVisualizer.displayGraph(graph);
            }
        });
    }

    /**
     *
     */
    private void configureDisplayMinimalSpanningTree(ComboBox<String> filesComboBox) {
        _buttonDisplaySpanningTree.setOnAction(event -> {
            GraphModel graph = this.getSelectedGraph(filesComboBox);

            if(graph != null){
                // Minimalen Spannbaum mit Prim erzeugen
                GraphModel mstPrim = Prim.getMinimalSpanningTree(graph);

                // Minimalen Spannbaum mit Kruskal erzeugen
                List<Edge> mstEdgesKruskal = (new Kruskal()).searchSpanningTree(graph);
                System.out.println("Kante Kruskal: "+mstEdgesKruskal.toString());
                GraphModel mstKruskal = new GraphModel();
                mstKruskal.addEdges(mstEdgesKruskal.toArray(new Edge[0]));

                // Graphen anzeigen
                GraphVisualizer.displayGraph(graph, "Normal");
                GraphVisualizer.displayGraph(mstPrim, "Prim");
                GraphVisualizer.displayGraph(mstKruskal, "Kruskal");
            }
        });
    }

    /**
     * Konfiguriert den Button „Kürzesten Weg finden“, der ein neues Fenster öffnet.
     *
     * @param filesComboBox ComboBox für Dateiauswahl
     */
    private void configureBFSButton(ComboBox<String> filesComboBox) {
        _buttonBFS.setOnAction(event -> {
            // wenn kein Graph aus der Datei erzeugt werden konnte wird abgebrochen
            GraphModel graph = this.getSelectedGraph(filesComboBox);
            if(graph == null){
                return;
            }

            // Neues Fenster für BFS-Eingaben
            Stage bfsStage = new Stage();
            bfsStage.setTitle("Wo möchtest du hin?");

            // Verschiedene Bestandteile des BFS-Fensters werden initialisiert
            Scene bfsScene = new Scene(new Group(), 450, 250);
            Label bfsNotifications = new Label();
            ComboBox<String> startComboBox = new ComboBox<>();
            ComboBox<String> endComboBox = new ComboBox<>();
            Button calculateButton = new Button("Berechnen");

            // Knotenliste der Start- und Ziel-Combobox hinzufügen
            List<String> nodes = graph.getNodes().stream().map(Node::getName).toList();
            startComboBox.getItems().addAll(nodes);
            endComboBox.getItems().addAll(nodes);

            // BFS berechnen
            calculateButton.setOnAction(event1 -> {
                // Start und Ziel ermitteln -> bei Fehler abbrechen
                String start = startComboBox.getSelectionModel().getSelectedItem();
                String end = endComboBox.getSelectionModel().getSelectedItem();
                if(start == null || end == null) {
                    bfsNotifications.setText("Bitte gib ein Start und ein Ziel an.");
                    return;
                }

                // BFS berechnen
                List<Node> result = BreadthFirstSearch.search(graph, Node.getNode(start), Node.getNode(end));

                // Ergebnis auf GUI ausgeben (kein Pfad oder den kürzesten Pfad)
                if(result.isEmpty()) {
                    bfsNotifications.setText("Keine Verbindung gefunden.");
                } else {
                    String notificationString = result
                            .stream()
                            .map(Node::getName)
                            .collect(Collectors.joining(" -> "));
                    bfsNotifications.setText("Kürzester Weg: "+notificationString);
                }
            });

            // Layout für BFS-Fenster
            GridPane grid = new GridPane();
            grid.setVgap(4);
            grid.setHgap(10);
            grid.setPadding(new Insets(5, 5, 5, 5));
            grid.add(new Label("Start: "), 0, 0);
            grid.add(startComboBox, 1, 0);
            grid.add(new Label("Ziel: "), 0, 1);
            grid.add(endComboBox, 1, 1);
            grid.add(calculateButton, 3, 0);
            grid.add(bfsNotifications, 0, 3, 3, 1);

            ((Group)bfsScene.getRoot()).getChildren().add(grid);
            bfsStage.setScene(bfsScene);
            bfsStage.show();
        });
    }

    /**
     * Konfiguriert den Button zum Konvertieren in einen gerichteten Graphen.
     *
     * @param filesComboBox ComboBox für die Dateiauswahl
     */
    private void configureConvertToDirected(ComboBox<String> filesComboBox) {
        _buttonConvertToDirected.setOnAction(event -> {
            // Ermittlung des ausgewählten Graphen und der ausgewählten Datei
            String file = filesComboBox.getSelectionModel().getSelectedItem();
            GraphModel graph = this.getSelectedGraph(filesComboBox);
            if(graph == null) {
                return;
            }

            // Konvertierung
            GraphModel directedGraph = GraphConverter.getDirectedGraphModel(graph);

            // Ausgabe des konvertierten Graphen und Fehlerbehandlung
            GraphOut graphWriter = new GraphOut();
            String prefixDir = "Directed_";
            String pathOut = BASE_PATH + prefixDir + file;
            try {
                graphWriter.writeFile(directedGraph, pathOut);
            } catch (IOException e) {
                _notification.setText("Fehler beim schreiben der Datei: " + pathOut);
                System.err.println("Fehler beim Schreiben der Datei: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    /**
     * Konfiguriert den Button zum Konvertieren in einen ungerichteten Graphen.
     *
     * @param filesComboBox ComboBox für die Dateiauswahl
     */
    private void configureConvertToUndirected(ComboBox<String> filesComboBox) {
        _buttonConvertToUndirected.setOnAction(event -> {
            // Ermittlung des ausgewählten Graphen und der ausgewählten Datei
            GraphModel graph = this.getSelectedGraph(filesComboBox);
            String file = filesComboBox.getSelectionModel().getSelectedItem();
            if(graph == null) {
                return;
            }

            // Konvertierung
            GraphModel undirectedGraph = GraphConverter.getUndirectedGraphModel(graph);

            // Ausgabe des konvertierten Graphen und Fehlerbehandlung
            GraphOut graphWriter = new GraphOut();
            String prefixUndir = "Undirected_";
            String pathOut = BASE_PATH + prefixUndir + file;
            try {
                graphWriter.writeFile(undirectedGraph, pathOut);
            } catch (IOException e) {
                _notification.setText("Fehler beim schreiben der Datei: " + pathOut);
                System.err.println("Fehler beim Schreiben der Datei: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
