package haw.gka.praktikum;

public class Edge {
    private final Node _start;
    private final Node _end;
    private final boolean isDirected;

    public Edge(Node start, Node end, boolean isDirected) {
        if (start == null || end == null) {
            throw new IllegalArgumentException();
        }
        this._start = start;
        this._end = end;
        this.isDirected = isDirected;
    }

    public Node getStart() {
        return _start;
    }

    public Node getEnd() {
        return _end;
    }

    public boolean isDirected() {
        return isDirected;
    }
}
