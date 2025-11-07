package praktikum1;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Methods to write the .gka files
 *
 */

public class GraphOut {
    //write .gka file
    public void writeFile(GraphModel g, String path) throws IOException {
        PrintWriter writer = new PrintWriter(new PrintWriter(path));
        //TODO put g in file
        //but what about multiple graphs?
        writer.close();
        System.out.println("Die .gka Datei wird nach " + path + " gespeichert.");
    }

}
