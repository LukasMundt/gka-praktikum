package haw.gka.praktikum;

import java.util.HashMap;
import java.util.Map;

public class Node {
    private final String _name;
    private static Map<String, Node> _nodes = new HashMap<String, Node>();

    public static Node getNode(String name) {
        if(!_nodes.containsKey(name)) {
            _nodes.put(name, new Node(name));
        }
        return _nodes.get(name);
    }

    Node(String name) {
        _name = name;
    }

    public String getName() {
        return _name;
    }

    public boolean equals(Object other) {
        if (!(other instanceof Node)) {
            return false;
        }
        Node otherNode = (Node) other;
        return _name.equals(otherNode._name);
    }

    public int hashCode() {
        return _name.hashCode();
    }
}
