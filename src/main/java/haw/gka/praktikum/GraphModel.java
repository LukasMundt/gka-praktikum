package haw.gka.praktikum;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * models for directed and undirected graphs, subgraph and overall graph
 */
public class GraphModel {
//Getter, Setter, Felder (Nodes, ID, Kantengewichte...)
    private final HashSet<Node> _nodes;
    private final HashSet<Edge> _edges;
    private final HashMap<Node, Integer> _indexedNodes;


    public GraphModel(Set<Node> nodes, Set<Edge> edges) {
        _nodes = (nodes == null) ? new HashSet<>() : new HashSet<>(nodes);
        _edges = (edges == null) ? new HashSet<>() : new HashSet<>(edges);
        _indexedNodes = new HashMap<>();
    }

//    public GraphModel addNodes(Set<Node> nodes) {
//        if (nodes == null) {
//            throw new NullPointerException("nodes is null");
//        }
//        Set<Node> temp = _nodes;
//        temp.addAll(nodes);
//
//        return new GraphModel(
//            temp,
//            _edges
//        );
//    }

    public GraphModel addNode(String line) {
        if (line == null) {
            throw new NullPointerException("node is null");
        }
        Node tempNode = Node.getNode(line);
        _nodes.add(tempNode);

        return new GraphModel(_nodes, _edges);
    }

    public GraphModel addEdge(Node start, Node end, boolean isDirected, int weight) {
       Edge edge = new Edge(start, end, isDirected, weight);
       _edges.add(edge);

        return new GraphModel(_nodes, _edges);
    }

    public void indexNode(Node node, int index) {
        if (!_nodes.contains(node)) {
            throw new IllegalArgumentException("node is not contained in the graph");
        } else if(_indexedNodes.containsKey(node)) {
            System.err.println("Node already indexed: "+node.getName()+"; Resetting index.");
        }
        _indexedNodes.put(node, index);
    }

    public int getIndexOfNode(Node node) {
        if(node == null) {
            throw new IllegalArgumentException("node is null");
        } else if(_indexedNodes.containsKey(node)) {
            return _indexedNodes.get(node);
        }
        return -1;
    }

    public Set<Node> getNeighbors(Node node) {
        if (!_nodes.contains(node)) {
            throw new IllegalArgumentException("node is not contained in the graph");
        }
        Set<Node> neighbors = new HashSet<>();
        for (Edge edge : _edges) {
            if(edge.isOtherNodeReachableFromA(node)) {
                neighbors.add(edge.getOtherNode(node));
            }
        }
        return neighbors;
    }

    public Set<Node> getUnindexedNeighbors(Node node) {
        if (!_nodes.contains(node)) {
            throw new IllegalArgumentException("node is not contained in the graph");
        }
        Set<Node> neighbors = new HashSet<>();
        for (Edge edge : _edges) {
            if(edge.isOtherNodeReachableFromA(node)) {
                Node otherNode = edge.getOtherNode(node);
                if(!_indexedNodes.containsKey(otherNode)) {
                    neighbors.add(otherNode);
                }
            }
        }
        return neighbors;
    }

    public HashSet<Node> getNodes() {
        return _nodes;
    }

    public HashSet<Edge> getEdges() {
        return _edges;
    }
}
