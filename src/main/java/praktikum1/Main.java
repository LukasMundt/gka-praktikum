package praktikum1;

import java.io.IOException;
import java.util.List;

/**
 * starting point to use the other classes / methods implemented
 */
public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("hello world");
        String path = "src/test/java/resources/directed.gka";
        GraphIn graphReader = new GraphIn();

        //Testfile einlesen
        try {
            List<GraphModel> graphs = graphReader.readGraph(path);
            System.out.println("Datei erfolgreich eingelesen. Anzahl: " + graphs.size());
        } catch (IOException e) {
            System.err.println("Fehler beim Einlesen der Datei: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
