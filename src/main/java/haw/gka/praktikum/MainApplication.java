package haw.gka.praktikum;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MainApplication extends Application {
    public void start(Stage primaryStage) {}

    public static void main(String[] args) {
        String path = "src/test/java/resources/directed.gka";
        GraphIn graphReader = new GraphIn();

        //Testfile einlesen
        try {
            GraphModel graph = graphReader.readGraph(path);
            List<String> failures = graphReader.getFailures();
            System.out.println("Datei erfolgreich eingelesen.");
            System.out.println("fehlerhaft eingelesene Teilgraphen: "+ failures);
            System.out.println(graph.getNodes().size());
            GraphVisualizer.displayGraph(graph);
            BreadthFirstSearch.search(graph, Node.getNode("D"), Node.getNode("B"));


        } catch (IOException e) {
            System.err.println("Fehler beim Einlesen der Datei: " + e.getMessage());
            e.printStackTrace();
        }

        // print ellapsed time for bfs
        long start = System.currentTimeMillis();
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("\n------------------------\n");
        System.out.println("Time elapsed: "+timeElapsed+"ms");

        // print ressource consumption
        Runtime runtime = Runtime.getRuntime();
        // Run garbarge collector
        runtime.gc();

        long memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Used memory is bytes: " + memory);
        System.out.println("Used memory is megabytes: "
                + (double)memory/1_000_000);
    }
}
