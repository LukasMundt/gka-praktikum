package haw.gka.praktikum;

import java.util.HashSet;
import java.util.Set;

/**
 * models for directed and undirected graphs, subgraph and overall graph
 */
public class GraphModel {
//Getter, Setter, Felder (Nodes, ID, Kantengewichte...)
    private final HashSet<Node> _nodes;
    private final HashSet<Edge> _edges;

    public GraphModel(Set<Node> nodes, Set<Edge> edges) {
        _nodes = (HashSet<Node>) nodes;
        _edges = (HashSet<Edge>) edges;
    }

    public GraphModel addNodes(Set<Node> nodes) {
        if (nodes == null) {
            throw new NullPointerException("nodes is null");
        }
        Set<Node> temp = _nodes;
        temp.addAll(nodes);

        return new GraphModel(
            temp,
            _edges
        );
    }

    public GraphModel addNode(String line) {
        if (line == null) {
            throw new NullPointerException("node is null");
        }
        Node tempNode = new Node(line);
        _nodes.add(tempNode);

        return new GraphModel(_nodes, _edges);
    }

    public HashSet<Node> getNodes() {
        return _nodes;
    }

    public HashSet<Edge> getEdges() {
        return _edges;
    }

    public void addUndirectedEdge(String a, String b) {
    }

    public void addDirectedEdge(String from, String to) {
    }


}
