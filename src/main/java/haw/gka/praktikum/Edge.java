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

    public boolean isBReachableFromA(Node a, Node b) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("a == null || b == null");
        } else if(!a.equals(_start) && !a.equals(_end)){
            throw new IllegalArgumentException("The node a is not part of this edge.");
        } else if(!b.equals(_start) && !b.equals(_end)){
            throw new IllegalArgumentException("The node b is not part of this edge.");
        }

        if(this._isDirected) {
            return this._start.equals(a) && this._end.equals(b);
        }
        return this._start.equals(a) && this._end.equals(b) ||
                this._end.equals(a) && this._start.equals(b);
    }

    public boolean isOtherNodeReachableFromA(Node a) {
        if(a == null){
            throw new IllegalArgumentException("a == null");
        } else if(!a.equals(_start) && !a.equals(_end)){
            return false;
        }

        if(this._isDirected) {
            return a.equals(_start);
        }
        return this._start.equals(a) || this._end.equals(a);
    }

    public Node getOtherNode(Node node) {
        if(node == null){
            throw new IllegalArgumentException("node == null");
        } else if(!node.equals(_start) && !node.equals(_end)){
            throw new IllegalArgumentException("The node is not part of this edge.");
        }

        if(node.equals(_start)){
            return _end;
        }
        return _start;
    }
}
