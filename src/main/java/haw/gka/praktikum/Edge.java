package haw.gka.praktikum;

public class Edge {
    private final Node _start;
    private final Node _end;
    private final boolean _isDirected;
    private final boolean _isWeighted;
    private final float _weight;

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

    public Edge(Node start, Node end, boolean isDirected, float weight) {
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

    public float getWeight() {
        return _weight;
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

    public boolean isAReachableFromOtherNode(Node a) {
        if(a == null){
            throw new IllegalArgumentException("a == null");
        } else if(!a.equals(_start) && !a.equals(_end)){
            return false;
        }

        if(this._isDirected) {
            return a.equals(_end);
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

    @Override
    public boolean equals(Object other) {
        if (other instanceof Edge) {
            Edge otherEdge = (Edge) other;
            if(!this._isDirected && !otherEdge._isDirected) {
                // wenn die kante ungerichtet ist, dann sind auch kanten gleich, wo start und ziel vertauscht sind
                return ((_start.equals(otherEdge._start) && _end.equals(otherEdge._end)) || (_start.equals(otherEdge._end) && _end.equals(otherEdge._start))) // gleiche Knoten
                        && _isWeighted == otherEdge._isWeighted
                        && _weight == otherEdge._weight;
            }
            return _start.equals(otherEdge._start)
                    && _end.equals(otherEdge._end)
                    && _isDirected == otherEdge._isDirected
                    && _isWeighted == otherEdge._isWeighted
                    && _weight == otherEdge._weight;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result;
        // Multiplikation mit 31 ist effizient und erzeugt eine gleichmäßige Verteilung
        if (_isDirected) {
            result = _start.hashCode() * 31 + _end.hashCode();
        } else {
            // ungerichtet: Egal, was Start und was Ende ist
            result = _start.hashCode() + _end.hashCode();
        }
        result = 31 * result + Boolean.hashCode(_isDirected);
        result = 31 * result + Boolean.hashCode(_isWeighted);
        result = 31 * result + Float.hashCode(_weight);
        return result;
    }

    public String toString() {
        return "(" + _start.toString() + ", " + _end.toString() + "; directed: " + _isDirected+", isWeighted" + _isWeighted +", weight: "+_weight+ ")";
    }
}
