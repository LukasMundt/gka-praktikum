package haw.gka.praktikum;


import org.junit.jupiter.api.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GraphOutTest {


    private GraphOut graphOut;
    private Path testFileDir;

    @BeforeEach
    void setup() {
        this.graphOut = new GraphOut();
        this.testFileDir = Paths.get("src/test/java/resources/TestOutput.gka");
    }
    /**
     * Tests for writeFile();
     */

    @Test
    void testWriteFile_MixedEdges() throws IOException {
        GraphModel graph = new GraphModel();

        Node a = new Node("A");
        Node b = new Node("B");
        Node c = new Node("C");

        graph.addNodes(a, b, c);
        graph.addEdge(a, b, true, false, 0);
        graph.addEdge(b, c, false, true, 4.2f);

        graphOut.writeFile(graph, testFileDir.toString());
        List<String> actualLines = Files.readAllLines(testFileDir);
        List<String> expectedLines = List.of(
                "A -> B;",
                "B -- C : 4.2;"
        );

        assertTrue(actualLines.containsAll(expectedLines) && expectedLines.containsAll(actualLines),
                "Die geschriebenen Zeilen müssen die korrekten gerichteten und ungerichteten Formate aufweisen.");
        assertEquals(2, actualLines.size(), "Es sollten genau zwei Kantenzeilen geschrieben werden.");
    }

    @Test
    void testWriteFile_WithSingleNode() throws IOException {
        GraphModel graph = new GraphModel();

        Node x = new Node("X");
        Node y = new Node("Y");
        Node z = new Node("Z");

        graph.addNodes(x, y, z);
        graph.addEdge(x, y, true, false, 0);

        graphOut.writeFile(graph, testFileDir.toString());
        List<String> actualLines = Files.readAllLines(testFileDir);

        assertTrue(actualLines.contains("X -> Y;"), "Die Kante muss korrekt geschrieben werden.");
        assertTrue(actualLines.contains("Z;"), "Der Solitärknoten muss korrekt geschrieben werden.");
        assertEquals(2, actualLines.size(), "Es sollten genau zwei Zeilen geschrieben werde.");
    }
}

