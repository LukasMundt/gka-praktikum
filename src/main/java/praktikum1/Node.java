package praktikum1;

public class Node {
    private final String _name;

    public Node(String name) {
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
