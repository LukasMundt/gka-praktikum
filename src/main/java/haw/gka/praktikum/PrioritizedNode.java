package haw.gka.praktikum;

public class PrioritizedNode implements Comparable<PrioritizedNode> {
    private final Node _node;
    private final float _priority;
    private final Edge _connectingEdge;

    public PrioritizedNode(Node node, Edge connectingEdge, float priority) {
        _node = node;
        _priority = priority;
        _connectingEdge = connectingEdge;
    }

    public Node getNode() {
        return _node;
    }

    public float getPriority() {
        return _priority;
    }

    public Edge getConnectingEdge() {
        return _connectingEdge;
    }

    /**
     * @param o the object to be compared.
     * @return
     */
    @Override
    public int compareTo(PrioritizedNode o) {
        return Float.compare(_priority, o._priority);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PrioritizedNode) {
            return _node.equals(((PrioritizedNode) obj)._node);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return _node.hashCode();
    }
}
