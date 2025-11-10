package haw.gka.praktikum;

public class Edge {
    private final Node _start;
    private final Node _end;
    private final boolean _isDirected;
    private final boolean _isWeighted;
    private final int _weight;

    public Edge(Node start, Node end, boolean isDirected) {
        if (start == null || end == null) {
            throw new IllegalArgumentException();
        }
        this._start = start;
        this._end = end;
        this._isDirected = isDirected;
        this._isWeighted = false;
        this._weight = 0;
    }

    public Edge(Node start, Node end, boolean isDirected, int weight) {
        if (start == null || end == null) {
            throw new IllegalArgumentException();
        }
        this._start = start;
        this._end = end;
        this._isDirected = isDirected;
        this._isWeighted = true;
        this._weight = weight;
    }

    public Node getStart() {
        return _start;
    }

    public Node getEnd() {
        return _end;
    }

    public boolean isDirected() {
        return _isDirected;
    }

    public boolean isWeighted() {
        return _isWeighted;
    }

    public int getWeight() {
        return _weight;
    }

    public Edge getUndirected() {
        return new Edge(_start, _end, false, _weight);
    }
}
