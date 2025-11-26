package haw.gka.praktikum;

import java.util.HashMap;
import java.util.Map;

/**
 * Repräsentiert einen Knoten (Node) eines Graphen.
 * <p>
 * Diese Klasse verwendet das Singleton-ähnliche Muster:
 * Für jeden Knotennamen existiert nur genau ein Node-Objekt.
 * Dadurch bleiben Knoten eindeutig vergleichbar (Referenzen und equals).
 * </p>
 */
public class Node {
    /**
     * Der Name des Knotens
     */
    private final String _name;

    /**
     * Statische Map, die sicherstellt, dass jeder Knotenname nur einmal existiert.
     */
    private static Map<String, Node> _nodes = new HashMap<>();

    /**
     * Gibt den Knoten zu einem Namen zurück. Wenn kein Knoten mit diesem Namen existiert,
     * wird ein neuer erzeugt und gespeichert.
     *
     * @param name Name des Knotens (darf nicht null sein)
     * @return das eindeutige Node-Objekt zum angegebenen Namen
     * @throws IllegalArgumentException wenn {@code name == null}
     */
    public static Node getNode(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name == null");
        }
        if (!_nodes.containsKey(name)) {
            _nodes.put(name, new Node(name));
        }
        return _nodes.get(name);
    }

    /**
     * Privater Konstruktor, da Knoten nur über {@link #getNode(String)} erzeugt werden sollen.
     *
     * @param name Name des Knotens
     */
    Node(String name) {
        _name = name;
    }


    /**
     * Gibt den Namen des Knotens zurück.
     *
     * @return Name des Knotens
     */
    public String getName() {
        return _name;
    }

    /**
     * Zwei Knoten sind gleich, wenn sie denselben Namen haben.
     *
     * @param other das andere Objekt
     * @return true, wenn beide Nodes denselben Namen besitzen
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Node)) {
            return false;
        }
        Node otherNode = (Node) other;
        return _name.equals(otherNode._name);
    }

    /**
     * HashCode basiert ausschließlich auf dem Knotennamen.
     *
     * @return HashCode des Knotennamens
     */
    @Override
    public int hashCode() {
        return _name.hashCode();
    }

    /**
     * Löscht die Liste der Nodes.
     */
    public static void resetNodeCache() {
        _nodes.clear();
    }

    @Override
    public String toString() {
        return this._name;
    }
}
