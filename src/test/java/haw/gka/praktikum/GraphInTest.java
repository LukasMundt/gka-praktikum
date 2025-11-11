package haw.gka.praktikum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for all In methods
 * Arrange, Act, Assert
 *
 */

public class GraphInTest {

    ClassLoader classLoader = getClass().getClassLoader();
    File testFileDir;
    File testFileUnDir;
    File testMixedSolo;
    GraphIn graphIn;

    GraphModel expected = new GraphModel();

    Node nodeA = new Node("A");
    Node nodeB = new Node("B");
    Node nodeC = new Node("C");

    @BeforeEach
    void setup() throws IOException {
        // Stellt sicher, dass testFile korrekt auf eine existierende Datei verweist
       this.testFileDir = Paths.get("src/test/java/resources/TestFileDir.gka").toFile();
        this.testFileUnDir = Paths.get("src/test/java/resources/TestFileUnDir.gka").toFile();
        this.testMixedSolo = Paths.get("src/test/java/resources/TestFileSolo.gka").toFile();
       this.graphIn = new GraphIn();
    }

    @Test
    void testReadFile_ModelNotNull() throws IOException {

        expected.addNodes(nodeA, nodeB, nodeC);

        GraphModel actualDir = graphIn.readGraph(testFileDir.toString());
        GraphModel actualUnDir = graphIn.readGraph(testFileUnDir.toString());

        assertNotNull(actualDir, "GraphModel darf nicht Null sein");
        assertNotNull(actualUnDir, "GraphModel darf nicht Null sein");

    }

    @Test
    void testReadFile_Directed() throws IOException {

        expected.addNodes(nodeA, nodeB, nodeC);

        GraphModel actualDir = graphIn.readGraph(testFileDir.toString());

        assertEquals(expected.getNodes(), actualDir.getNodes(), "Knotenlisten müssen übereinstimmen");
        assertEquals(expected.getEdges(), actualDir.getEdges());
    }

    @Test
    void testReadFile_UnDirected() throws IOException {

        expected.addNodes(nodeA, nodeB, nodeC);

        GraphModel actualUnDir = graphIn.readGraph(testFileUnDir.toString());

        assertEquals(expected.getNodes(), actualUnDir.getNodes(), "Knotenlisten müssen übereinstimmen");
        assertEquals(expected.getEdges(), actualUnDir.getEdges());
    }

    @Test
    void testReadNonexistentFile() {
        assertThrows(IOException.class, () -> graphIn.readFile("noFile.gka"));
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

        GraphModel actual = graphIn.readGraph(testMixedSolo.toString());

        // Es muss nur Node Z in der erwarteten Menge sein
        assertTrue(actual.getNodes().contains(nodeZ), "Der Solitärknoten Z muss vorhanden sein.");
        assertEquals(expected.getEdges(), actual.getEdges(), "Die ungerichtete Kante X--Y muss vorhanden sein.");
    }








}
