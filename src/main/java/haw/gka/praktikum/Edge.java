package haw.gka.praktikum;

/**
 * Repräsentiert eine Kante zwischen zwei Knoten eines Graphen.
 * <p>
 * Eine Kante kann gerichtet oder ungerichtet sowie gewichtet oder ungewichtet sein.
 * Die Klasse stellt Funktionen bereit, um zu prüfen, ob bestimmte Knoten
 * entlang dieser Kante erreicht werden können.
 * </p>
 */
public class Edge {
    /** Startknoten der Kante (bei ungerichteten Kanten ohne besondere Bedeutung) */
    private final Node _start;

    /** Endknoten der Kante (bei ungerichteten Kanten ohne besondere Bedeutung) */
    private final Node _end;

    /** Gibt an, ob die Kante gerichtet ist */
    private final boolean _isDirected;

    /** Gibt an, ob die Kante ein Gewicht besitzt */
    private final boolean _isWeighted;

    /** Gewicht der Kante (0, falls ungewichtet) */
    private final float _weight;

    /**
     * Erstellt eine ungewichtete Kante.
     *
     * @param start      Startknoten
     * @param end        Endknoten
     * @param isDirected true → gerichtete Kante, false → ungerichtete Kante
     * @throws IllegalArgumentException wenn einer der Knoten null ist
     */
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

    /**
     * Erstellt eine gewichtete Kante.
     *
     * @param start      Startknoten
     * @param end        Endknoten
     * @param isDirected true -> gerichtet
     * @param weight     Gewicht der Kante
     * @throws IllegalArgumentException wenn einer der Knoten null ist
     */
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

    /**
     * Prüft, ob Knoten b von Knoten a über diese Kante erreicht werden kann.
     *
     * @param a Ausgangsknoten
     * @param b Zielknoten
     * @return true, wenn b entlang dieser Kante von a aus erreichbar ist
     * @throws IllegalArgumentException wenn einer der Knoten nicht Teil der Kante ist
     */
    public boolean isBReachableFromA(Node a, Node b) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("a == null || b == null");
        } else if(!a.equals(_start) && !a.equals(_end)){
            throw new IllegalArgumentException("The node a is not part of this edge.");
        } else if(!b.equals(_start) && !b.equals(_end)){
            throw new IllegalArgumentException("The node b is not part of this edge.");
        }

        if(this._isDirected) {
            // wenn die kante gerichtet ist, dann muss a der start und b das ende sein, damit b von a aus erreichbar ist
            return this._start.equals(a) && this._end.equals(b);
        }
        // bei einer ungerichteten Kante ist es egal, was start und was ziel ist
        return this._start.equals(a) && this._end.equals(b) ||
                this._end.equals(a) && this._start.equals(b);
    }

    /**
     * Prüft, ob von einem Knoten a aus der andere Knoten dieser Kante erreichbar ist.
     * Berücksichtigt dabei die Richtung (falls gerichtet).
     *
     * @param a Ausgangsknoten
     * @return true, wenn der andere Knoten erreichbar ist
     */
    public boolean isOtherNodeReachableFromA(Node a) {
        if(a == null){
            throw new IllegalArgumentException("a == null");
        } else if(!a.equals(_start) && !a.equals(_end)){
            return false;
        }

        if(this._isDirected) {
            // wenn die kante gerichtet ist, dann muss a der startknoten sein, damit der andere Knoten von a aus erreichbar ist
            return a.equals(_start);
        }
        // bei einer ungerichteten Kante ist es egal, ob a Start- oder Endknoten der Kante ist
        return this._start.equals(a) || this._end.equals(a);
    }

    /**
     * Prüft, ob der gegebene Knoten a von dem jeweils anderen Knoten aus erreichbar ist.
     * Dies ist die "umgekehrte" Richtung zu {@link #isOtherNodeReachableFromA(Node)}.
     *
     * @param a Zielknoten
     * @return true, wenn a vom anderen Knoten der Kante erreicht werden kann
     */
    public boolean isAReachableFromOtherNode(Node a) {
        if(a == null){
            throw new IllegalArgumentException("a == null");
        } else if(!a.equals(_start) && !a.equals(_end)){
            return false;
        }

        if(this._isDirected) {
            // wenn die Kante gerichtet ist, dann muss a der Endknoten sein
            return a.equals(_end);
        }
        // bei einer ungerichteten Kante ist es egal, a kann Start- und End-Knoten sein
        return this._start.equals(a) || this._end.equals(a);
    }

    /**
     * Gibt den jeweils anderen Knoten der Kante zurück.
     *
     * @param node einer der beiden Knoten der Kante
     * @return der jeweils andere Knoten
     * @throws IllegalArgumentException wenn der Knoten nicht Teil der Kante ist
     */
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

    /**
     * Vergleicht diese Kante mit einer anderen.
     * <p>
     * Für ungerichtete Kanten gilt: Kanten sind auch dann gleich, wenn Start und Ende vertauscht sind.
     * </p>
     */
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

    /**
     * Berechnet den Hashcode der Kante.
     * <p>
     * Für ungerichtete Kanten wird die Reihenfolge von Start/Ende ignoriert.
     * Multiplikation mit 31 ist ein etablierter Standard,
     * da 31 eine Primzahl ist und eine breite Hash-Verteilung erzeugt.
     * </p>
     */
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
