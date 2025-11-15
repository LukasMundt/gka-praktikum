package haw.gka.praktikum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for all In methods
 * Arrange, Act, Assert
 *
 */

public class GraphInTest {

    String testFileDir;
    String testFileUnDir;
    String testMixedSolo;

    GraphIn graphReader;
    GraphModel expected;

    Node nodeA = new Node("A");
    Node nodeB = new Node("B");
    Node nodeC = new Node("C");


    @BeforeEach
    void setup() throws IOException {
        // Stellt sicher, dass testFile korrekt auf eine existierende Datei verweist
        testFileDir = "src/test/java/resources/TestFileDir.gka";
        testFileUnDir = "src/test/java/resources/TestFileUnDir.gka";
        testMixedSolo = "src/test/java/resources/TestFileSolo.gka";

        //stellt neue Instanzen der jeweiligen Klassen bereit
        graphReader = new GraphIn();
        expected = new GraphModel();
    }

    @Test
    void testReadFile_ModelNotNull() throws IOException {

        expected.addNodes(nodeA, nodeB, nodeC);

        GraphModel actualDir = null;
        GraphModel actualUnDir = null;

        actualDir = graphReader.readGraph(testFileDir);
        actualUnDir = graphReader.readGraph(testFileUnDir);

        assertNotNull(actualDir, "GraphModel darf nicht Null sein");
        assertNotNull(actualUnDir, "GraphModel darf nicht Null sein");

    }

    @Test
    void testReadFile_Directed() throws IOException {
        expected.addNodes(nodeA, nodeB, nodeC);
        expected.addEdge(nodeA, nodeB, true, false, 0);
        expected.addEdge(nodeB, nodeA, true, false, 0);
        expected.addEdge(nodeA, nodeC, true, false, 0);

        GraphModel actualDir = graphReader.readGraph(testFileDir);

        assertEquals(expected.getNodes(), actualDir.getNodes(), "Knotenlisten müssen übereinstimmen");
        assertEquals(expected.getEdges(), actualDir.getEdges());
    }

    @Test
    void testReadFile_UnDirected() throws IOException {
        expected.addNodes(nodeA, nodeB, nodeC);
        expected.addEdge(nodeA, nodeB, false, false, 0);
        expected.addEdge(nodeB, nodeC, false, false, 0);
        expected.addEdge(nodeA, nodeC, false, false, 0);

        GraphModel actualUnDir = graphReader.readGraph(testFileUnDir);

        assertEquals(expected.getNodes(), actualUnDir.getNodes(), "Knotenlisten müssen übereinstimmen");
        assertEquals(expected.getEdges(), actualUnDir.getEdges());
    }

    @Test
    void testReadNonexistentFile() {
        assertThrows(IOException.class, () -> graphReader.readFile("noFile.gka"));
    }

    @Test
    void testReadGraph_UndirectedAndSingleNode() throws IOException {

        GraphModel expected = new GraphModel();
        Node nodeX = Node.getNode("X");
        Node nodeY = Node.getNode("Y");
        Node nodeZ = Node.getNode("Z");

        expected.addNodes(nodeX, nodeY, nodeZ);
        // Annahme: X -- Y führt zu einer ungerichteten Kante (false) ohne Gewichtung
        expected.addEdge(nodeX, nodeY, false, false, 0);

        GraphModel actual = graphReader.readGraph(testMixedSolo);

        // Es muss Node Z in der erwarteten Menge sein
        assertTrue(actual.getNodes().contains(nodeZ), "Der Solitärknoten Z muss vorhanden sein.");
        assertEquals(expected.getEdges(), actual.getEdges(), "Die ungerichtete Kante X--Y muss vorhanden sein.");
    }
}
