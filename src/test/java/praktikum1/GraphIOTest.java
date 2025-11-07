package praktikum1;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for all IO methods
 * Arrange, Act, Assert
 *  TODO
 */

public class GraphIOTest {

    ClassLoader classLoader = getClass().getClassLoader();
    File testFile = new File(Objects.requireNonNull(classLoader.getResource("java/resources/undirected.gka")).getFile());

    /**
     * Tests for readFile(); use sample data
     */
    @Test
    void testReadFile() throws IOException {
        GraphIO graphIO = new GraphIO();
        GraphModel expected = new GraphModel();
        GraphModel actual = graphIO.readGraph(testFile.toString());

        assertNotNull(actual, "GraphModel darf nicht Null sein");
        // assertEquals(expected.getNodes(), actual.getNodes(), "Knotenlisten müssen übereinstimmen");
    }

    @Test
    void testReadNonexistentFile() {
        // TODO fixen, Aufruf nicht korrekt
        //  assertThrows(IOException.class, () -> readFile("noFile.gka"));
    }




    /**
     * Tests for writeFile();
     */
    @Test
    void testWriteFile() throws IOException {

    }

}
