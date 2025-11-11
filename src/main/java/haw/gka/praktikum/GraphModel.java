package haw.gka.praktikum;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * methods to build graphs from nodes end edges &
 * methods needed for BFS
 */
public class GraphModel {
    private final HashSet<Node> _nodes;
    private final HashSet<Edge> _edges;
    private final HashMap<Node, Integer> _indexedNodes;

    public GraphModel() {
        _nodes = new HashSet<>();
        _edges = new HashSet<>();
        _indexedNodes = new HashMap<>();

    }

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

    public void addNodes(Node ...nodes) {
        for (Node node : nodes){
            if(node == null) {
                throw new NullPointerException("node is null");
            }
            _nodes.add(node);
        }
    }

    public void addEdges(Edge ...edges) {
        for (Edge edge : edges) {
            if(edge == null) {
                throw new NullPointerException("edge is null");
            }
            _edges.add(edge);
        }
    }

    public void addNode(String line) {
        if (line == null) {
            throw new NullPointerException("node is null");
        }
        Node tempNode = Node.getNode(line);
        _nodes.add(tempNode);
    }

    public void addEdge(Node start, Node end, boolean isDirected, boolean isWeighted, float weight) {
       Edge edge;
       if (isWeighted){
           edge = new Edge(start, end, isDirected, weight);
       } else {
           edge = new Edge(start, end, isDirected);
       }
       _edges.add(edge);
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

    public Set<Node> getReverseNeighbors(Node node) {
        if (!_nodes.contains(node)) {
            throw new IllegalArgumentException("node is not contained in the graph");
        }
        Set<Node> neighbors = new HashSet<>();
        for (Edge edge : _edges) {
            if(edge.isAReachableFromOtherNode(node)) {
                neighbors.add(edge.getOtherNode(node));
            }
        }
        return neighbors;
    }

    public Node getReverseNeighborWithIndex(Node node, int index) {
        if (!_nodes.contains(node)) {
            throw new IllegalArgumentException("node is not contained in the graph");
        }
        Set<Node> neighbors = getReverseNeighbors(node);
        for (Node neighbor : neighbors) {
            if(index == getIndexOfNode(neighbor)) {
                return neighbor;
            }
        }
        return null;
    }

    public Set<Node> getSingleNodes() {
        Set<Node> nodes = new HashSet<>();

        //alle Knoten mit Kanten sammeln
        for (Edge edge : _edges) {
            nodes.add(edge.getStart());
            nodes.add(edge.getEnd());
        }
        //dann aus Kopie aller Knoten entfernen = SingleKnoten
        Set<Node> allNodes = new HashSet<>(_nodes);
        allNodes.removeAll(nodes);

        return allNodes;
    }



    public HashSet<Node> getNodes() {
        return _nodes;
    }

    public HashSet<Edge> getEdges() {
        return _edges;
    }
}
