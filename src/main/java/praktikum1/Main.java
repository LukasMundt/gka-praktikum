package praktikum1;

import haw.gka.praktikum.GraphIn;
import haw.gka.praktikum.GraphModel;

import java.io.IOException;
import java.util.List;

/**
 * starting point to use the other classes / methods implemented
 */
public class Main {

    public static void main(String[] args) throws IOException {
        String path = "src/test/java/resources/graph03.gka";
        GraphIn graphReader = new GraphIn();

        //Testfile einlesen
        try {
            GraphModel graph = graphReader.readGraph(path);
            List<String> failures = graphReader.getFailures();
            System.out.println("Datei erfolgreich eingelesen.");
            System.out.println();
            System.out.println("fehlerhaft eingelesene Teilgraphen: "+ failures);

        } catch (IOException e) {
            System.err.println("Fehler beim Einlesen der Datei: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
