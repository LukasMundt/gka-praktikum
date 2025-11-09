package haw.gka.praktikum;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class MainApplication extends Application {
    public void start(Stage primaryStage) {
    }

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

        edges.add(new Edge(Node.getNode("c"), Node.getNode("d"), false));
        edges.add(new Edge(Node.getNode("d"), Node.getNode("f"), false));
        edges.add(new Edge(Node.getNode("d"), Node.getNode("e"), false));
        edges.add(new Edge(Node.getNode("d"), Node.getNode("g"), false));
        edges.add(new Edge(Node.getNode("e"), Node.getNode("j"), false));
        edges.add(new Edge(Node.getNode("e"), Node.getNode("c"), false));
        edges.add(new Edge(Node.getNode("e"), Node.getNode("e"), false));
        edges.add(new Edge(Node.getNode("e"), Node.getNode("f"), false));
        edges.add(new Edge(Node.getNode("f"), Node.getNode("a"), false));
        edges.add(new Edge(Node.getNode("f"), Node.getNode("g"), false));
        edges.add(new Edge(Node.getNode("f"), Node.getNode("h"), false));
        edges.add(new Edge(Node.getNode("f"), Node.getNode("i"), false));
        edges.add(new Edge(Node.getNode("g"), Node.getNode("g"), false));
        edges.add(new Edge(Node.getNode("g"), Node.getNode("e"), false));
        edges.add(new Edge(Node.getNode("g"), Node.getNode("b"), false));
        edges.add(new Edge(Node.getNode("g"), Node.getNode("d"), false));
        edges.add(new Edge(Node.getNode("h"), Node.getNode("b"), false));
        edges.add(new Edge(Node.getNode("h"), Node.getNode("c"), false));
        edges.add(new Edge(Node.getNode("h"), Node.getNode("f"), false));
        edges.add(new Edge(Node.getNode("i"), Node.getNode("a"), false));
        edges.add(new Edge(Node.getNode("i"), Node.getNode("c"), false));
        edges.add(new Edge(Node.getNode("i"), Node.getNode("i"), false));
        edges.add(new Edge(Node.getNode("i"), Node.getNode("g"), false));
        edges.add(new Edge(Node.getNode("j"), Node.getNode("k"), false));
        edges.add(new Edge(Node.getNode("j"), Node.getNode("c"), false));
        edges.add(new Edge(Node.getNode("j"), Node.getNode("a"), false));
        edges.add(new Edge(Node.getNode("j"), Node.getNode("b"), false));
        edges.add(new Edge(Node.getNode("k"), Node.getNode("c"), false));
        edges.add(new Edge(Node.getNode("k"), Node.getNode("g"), false));
        edges.add(new Edge(Node.getNode("k"), Node.getNode("d"), false));

        GraphModel graph = new GraphModel(nodes, edges);

        System.setProperty("org.graphstream.ui", "javafx");

        Graph graphViz = new SingleGraph("GKA Praktikum");

        graph.getNodes().stream().forEach(node -> {
            graphViz.addNode(node.getName());
        });

        graph.getEdges().stream().forEach(edge -> {
            String idFrom = edge.getStart().getName();
            String idTo = edge.getEnd().getName();
            try {
                graphViz.addEdge(idFrom+idTo, idFrom, idTo);
            } catch (EdgeRejectedException exception) {
                System.out.println(exception.getMessage());
            }
        });

        graphViz.nodes().forEach(n -> n.setAttribute("ui.label", n.getId()));

        graphViz.setAttribute("ui.stylesheet",
                "node { fill-color: #1E88E5; size: 20px; text-alignment: above; text-size: 14px; } " +
                        "edge { fill-color: #999; size: 2px; }");

        graphViz.display();
    }
}
