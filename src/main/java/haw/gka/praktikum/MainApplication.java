package haw.gka.praktikum;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Set;

public class MainApplication extends Application {
    public void start(Stage primaryStage) {}

    public static void main(String[] args) {
        Set<Node> nodes = new HashSet<>();
        nodes.add(Node.getNode("a"));
        nodes.add(Node.getNode("b"));
        nodes.add(Node.getNode("c"));
        nodes.add(Node.getNode("d"));
        nodes.add(Node.getNode("e"));
        nodes.add(Node.getNode("f"));
        nodes.add(Node.getNode("g"));
        nodes.add(Node.getNode("h"));
        nodes.add(Node.getNode("i"));
        nodes.add(Node.getNode("j"));
        nodes.add(Node.getNode("k"));

        Set<Edge> edges = new HashSet<>();

        edges.add(new Edge(Node.getNode("c"), Node.getNode("d"), true));
        edges.add(new Edge(Node.getNode("d"), Node.getNode("f"), true));
        edges.add(new Edge(Node.getNode("d"), Node.getNode("e"), true));
        edges.add(new Edge(Node.getNode("d"), Node.getNode("g"), true));
        edges.add(new Edge(Node.getNode("e"), Node.getNode("j"), true));
        edges.add(new Edge(Node.getNode("e"), Node.getNode("c"), true));
        edges.add(new Edge(Node.getNode("e"), Node.getNode("e"), true));
        edges.add(new Edge(Node.getNode("e"), Node.getNode("f"), true));
        edges.add(new Edge(Node.getNode("f"), Node.getNode("a"), true));
        edges.add(new Edge(Node.getNode("f"), Node.getNode("g"), true));
        edges.add(new Edge(Node.getNode("f"), Node.getNode("h"), true));
        edges.add(new Edge(Node.getNode("f"), Node.getNode("i"), true));
        edges.add(new Edge(Node.getNode("g"), Node.getNode("g"), true));
        edges.add(new Edge(Node.getNode("g"), Node.getNode("e"), true));
        edges.add(new Edge(Node.getNode("g"), Node.getNode("b"), true));
        edges.add(new Edge(Node.getNode("g"), Node.getNode("d"), true));
        edges.add(new Edge(Node.getNode("h"), Node.getNode("b"), true));
        edges.add(new Edge(Node.getNode("h"), Node.getNode("c"), true));
        edges.add(new Edge(Node.getNode("h"), Node.getNode("f"), true));
        edges.add(new Edge(Node.getNode("i"), Node.getNode("a"), true));
        edges.add(new Edge(Node.getNode("i"), Node.getNode("c"), true));
        edges.add(new Edge(Node.getNode("i"), Node.getNode("i"), true));
        edges.add(new Edge(Node.getNode("i"), Node.getNode("g"), true));
        edges.add(new Edge(Node.getNode("j"), Node.getNode("k"), true));
        edges.add(new Edge(Node.getNode("j"), Node.getNode("c"), true));
        edges.add(new Edge(Node.getNode("j"), Node.getNode("a"), true));
        edges.add(new Edge(Node.getNode("j"), Node.getNode("b"), true));
        edges.add(new Edge(Node.getNode("k"), Node.getNode("c"), true));
        edges.add(new Edge(Node.getNode("k"), Node.getNode("g"), true));
        edges.add(new Edge(Node.getNode("k"), Node.getNode("d"), true));

        GraphModel graph = new GraphModel(nodes, edges);
        graph = GraphConverter.getUndirectedGraphModel(graph);
        GraphVisualizer.displayGraph(graph);

        BreadthFirstSearch.search(graph, Node.getNode("a"), Node.getNode("b"));
    }
}
