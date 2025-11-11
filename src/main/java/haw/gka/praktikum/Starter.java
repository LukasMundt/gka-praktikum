package haw.gka.praktikum;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Starter extends Application {

    final Button button = new Button ("Graph anzeigen");
    final Button buttonBFS = new Button ("Kürzesten Weg finden");
    final Button buttonConvertToDirected = new Button("zu gerichtetem Graph konvertieren");
    final Button buttonConvertToUndirected = new Button("zu ungerichtetem Graph konvertieren");
    final Label notification = new Label ();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Suche dir eine Datei aus.");
        primaryStage.setResizable(true);

        Scene scene = new Scene(new Group(), 550, 250);

        List<String> fileList = Stream.of(new File("src/test/java/resources/").listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toList());

        final ComboBox filesComboBox = new ComboBox();
        filesComboBox.getItems().addAll(
                fileList
        );

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String file = (String) filesComboBox.getSelectionModel().getSelectedItem();
                if(file == null) {
                    System.err.println("No file selected");
                    notification.setText("No file selected");
                    return;
                }

                String path = "src/test/java/resources/" + file;

                GraphIn graphReader = new GraphIn();
                try {
                    GraphModel graph = graphReader.readGraph(path);
                    List<String> failures = graphReader.getFailures();
                    System.out.println("Datei erfolgreich eingelesen.");
                    System.out.println("fehlerhaft eingelesene Teilgraphen: "+ failures);
                    System.out.println(graph.getNodes().size());
                    GraphVisualizer.displayGraph(graph);



                    // BreadthFirstSearch.search(graph, Node.getNode("D"), Node.getNode("B"));


                } catch (IOException e) {
                    System.err.println("Fehler beim Einlesen der Datei: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });

        buttonBFS.setOnAction(event -> {
            String file = (String) filesComboBox.getSelectionModel().getSelectedItem();
            if(file == null) {
                System.err.println("No file selected");
                notification.setText("No file selected");
                return;
            }

            String path = "src/test/java/resources/" + file;

            GraphIn graphReader = new GraphIn();
            try {
                GraphModel graph = graphReader.readGraph(path);

                Stage bfsStage = new Stage();
                Scene bfsScene = new Scene(new Group(), 450, 250);
                bfsStage.setTitle("Wo möchtest du hin?");

                List<String> nodes = graph.getNodes().stream().map(node -> node.getName()).collect(Collectors.toList());

                ComboBox startComboBox = new ComboBox();
                startComboBox.getItems().addAll(
                        nodes
                );

                ComboBox endComboBox = new ComboBox();
                endComboBox.getItems().addAll(
                        nodes
                );
                Label bfsNotifications = new Label();

                Button calculateButton = new Button("Berechnen");

                calculateButton.setOnAction(event1 -> {
                    String start = (String) startComboBox.getSelectionModel().getSelectedItem();
                    if(start == null) {
                        bfsNotifications.setText("No start selected");
                        return;
                    }
                    String end = (String) endComboBox.getSelectionModel().getSelectedItem();
                    if(end == null) {
                        bfsNotifications.setText("No end selected");
                        return;
                    }
                    List<Node> result = BreadthFirstSearch.search(graph, Node.getNode(start), Node.getNode(end));
                    if(result.isEmpty()) {
                        bfsNotifications.setText("Keine Verbindung gefunden.");
                    } else {
                        Node endNode = Node.getNode(end);
                        String notificationString = "";
                        for (Node node : result) {
                            if(!node.equals(endNode)){
                                notificationString = notificationString+node.getName()+" -> ";
                            } else {
                                notificationString = notificationString+node.getName();
                            }
                        }
                        bfsNotifications.setText(notificationString);
                    }
                });

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

                Group root = (Group)bfsScene.getRoot();
                root.getChildren().add(grid);

                bfsStage.setScene(bfsScene);

                bfsStage.show();
                List<String> failures = graphReader.getFailures();
                System.out.println("Datei erfolgreich eingelesen.");
                System.out.println("fehlerhaft eingelesene Teilgraphen: "+ failures);
                System.out.println(graph.getNodes().size());



                // BreadthFirstSearch.search(graph, Node.getNode("D"), Node.getNode("B"));


            } catch (IOException e) {
                System.err.println("Fehler beim Einlesen der Datei: " + e.getMessage());
                e.printStackTrace();
            }
        });


        buttonConvertToDirected.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String file = (String) filesComboBox.getSelectionModel().getSelectedItem();
                if(file == null) {
                    System.err.println("No file selected");
                    notification.setText("No file selected");
                    return;
                }

                try {
                    GraphIn graphReader = new GraphIn();
                    String pathIn = "src/test/java/resources/" + file;
                    GraphModel graph = graphReader.readGraph(pathIn);
                    System.out.println("Graph erfolgreich eingelesen.");
                    List<String> failures = graphReader.getFailures();
                    System.out.println("Datei erfolgreich eingelesen.");
                    System.out.println("fehlerhaft eingelesene Teilgraphen: "+ failures);

                    GraphModel directedGraph = GraphConverter.getDirectedGraphModel(graph);

                    GraphOut graphWriter = new GraphOut();
                    String pathOut = "src/test/java/resources/" + file + "Undirected.gka";
                    graphWriter.writeFile(directedGraph, pathOut);


                } catch (IOException e) {
                    System.err.println("Fehler beim Einlesen der Datei: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });

        buttonConvertToUndirected.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String file = (String) filesComboBox.getSelectionModel().getSelectedItem();
                if(file == null) {
                    System.err.println("No file selected");
                    notification.setText("No file selected");
                    return;
                }

                try {
                    GraphIn graphReader = new GraphIn();
                    String pathIn = "src/test/java/resources/" + file;
                    GraphModel graph = graphReader.readGraph(pathIn);
                    System.out.println("Graph erfolgreich eingelesen.");
                    List<String> failures = graphReader.getFailures();
                    System.out.println("Datei erfolgreich eingelesen.");
                    System.out.println("fehlerhaft eingelesene Teilgraphen: "+ failures);

                    GraphModel undirectedGraph = GraphConverter.getUndirectedGraphModel(graph);

                    GraphOut graphWriter = new GraphOut();
                    String pathOut = "src/test/java/resources/" + file + "Undirected.gka";
                    graphWriter.writeFile(undirectedGraph, pathOut);


                } catch (IOException e) {
                    System.err.println("Fehler beim Einlesen der Datei: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });

        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setHgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(new Label("Datei: "), 0, 0);
        grid.add(filesComboBox, 1, 0);
        grid.add(button, 2, 0);
        grid.add(buttonBFS, 2, 1);
        grid.add(buttonConvertToDirected, 2, 2);
        grid.add(buttonConvertToUndirected, 2, 3);
        grid.add (notification, 1, 3, 3, 1);

        Group root = (Group)scene.getRoot();
        root.getChildren().add(grid);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
