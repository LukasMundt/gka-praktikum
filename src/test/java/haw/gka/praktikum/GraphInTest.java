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
    String emptyTestFile;

    GraphIn graphReader;
    GraphModel expected;
    GraphModel unexpected;
    GraphModel empty;

    Node nodeA = new Node("A");
    Node nodeB = new Node("B");
    Node nodeC = new Node("C");


    @BeforeEach
    void setup() throws IOException {
        // Stellt sicher, dass testFile korrekt auf eine existierende Datei verweist
        testFileDir = "src/test/java/resources/TestFileDir.gka";
        testFileUnDir = "src/test/java/resources/TestFileUnDir.gka";
        testMixedSolo = "src/test/java/resources/TestFileSolo.gka";
        emptyTestFile = "src/test/java/resources/empty.gka";

        //stellt neue Instanzen der jeweiligen Klassen bereit
        graphReader = new GraphIn();
        expected = new GraphModel();
        unexpected = new GraphModel();
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
    void testReadFile_Empty() throws IOException {
        empty = graphReader.readGraph(emptyTestFile);

        expected.addNodes(nodeA, nodeB, nodeC);
        expected.addEdge(nodeA, nodeB, true, false, 0, null);
        expected.addEdge(nodeB, nodeA, true, false, 0, null);

        //prüft, ob eine leere Datei ohne Fehler / Programmabbruch verarbeitet wird
        assertFalse(expected.getEdges().isEmpty(), "das Graphmodell darf nicht leer sein");
        assertTrue(empty.getEdges().isEmpty(), "das GraphModell muss leer sein");
    }

    @Test
    void testReadNonexistentFile() {
        assertThrows(IOException.class, () -> graphReader.readFile("noFile.gka"));
    }

    @Test
    void testReadFile_Directed() throws IOException {
        expected.addNodes(nodeA, nodeB, nodeC);
        expected.addEdge(nodeA, nodeB, true, false, 0, "e");
        expected.addEdge(nodeB, nodeA, true, false, 0, "f");
        expected.addEdge(nodeA, nodeC, true, false, 0, "g");

        unexpected.addNodes(nodeA, nodeC);

        GraphModel actualDir = graphReader.readGraph(testFileDir);

        //prüfen, ob der erwartete Inhalt geparst wird
        assertEquals(expected.getNodes(), actualDir.getNodes(), "Knotenlisten müssen übereinstimmen");
        assertEquals(expected.getEdges(), actualDir.getEdges(), "Kantenlisten müssen übereinstimmen");
        //prüft auf Ungleichheit der Knotenlisten
        assertNotEquals(unexpected.getNodes(), actualDir.getNodes(), "Knotenlisten dürfen nicht übereinstimmen");
    }

    @Test
    void testReadFile_UnDirected() throws IOException {
        expected.addNodes(nodeA, nodeB, nodeC);
        expected.addEdge(nodeA, nodeB, false, false, 0, null);
        expected.addEdge(nodeB, nodeC, false, false, 0, null);
        expected.addEdge(nodeA, nodeC, false, false, 0, null);

        GraphModel actualUnDir = graphReader.readGraph(testFileUnDir);

        assertEquals(expected.getNodes(), actualUnDir.getNodes(), "Knotenlisten müssen übereinstimmen");
        assertEquals(expected.getEdges(), actualUnDir.getEdges());
    }

    @Test
    void testReadGraph_UndirectedAndSingleNode() throws IOException {

        GraphModel expected = new GraphModel();
        Node nodeX = Node.getNode("X");
        Node nodeY = Node.getNode("Y");
        Node nodeZ = Node.getNode("Z");

        expected.addNodes(nodeX, nodeY, nodeZ);
        // Annahme: X -- Y führt zu einer ungerichteten Kante (false) ohne Gewichtung
        expected.addEdge(nodeX, nodeY, false, false, 0, "e");

        GraphModel actual = graphReader.readGraph(testMixedSolo);

        // Es muss Node Z in der erwarteten Menge sein
        assertTrue(actual.getNodes().contains(nodeZ), "Der Solitärknoten Z muss vorhanden sein.");
        assertEquals(expected.getEdges(), actual.getEdges(), "Die ungerichtete Kante X--Y muss vorhanden sein.");
    }
}
