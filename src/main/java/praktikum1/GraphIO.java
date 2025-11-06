package praktikum1;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Methods to read and write the .gka files
 *
 */
public class GraphIO {

    //parse .gka file
    public GraphModel readFile(String path) throws IOException {

        File file = new File(path);
        Scanner scanner = new Scanner(file);
        GraphModel graph = new GraphModel();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            //TODO: the actual parsing
        }
        scanner.close();
        return graph;
        //TODO es sollen ja alle Graphen aus der Datei gelesen werden, nicht nur einer, fixen
    }

    //write .gka file
    public void writeFile(GraphModel g, String path) throws IOException{
        PrintWriter writer = new PrintWriter(new PrintWriter(path));
            //TODO put g in file
            //but what about multiple graphs?
        writer.close();
        System.out.println("Die .gka Datei wird nach " + path + " gespeichert.");
    }

}
