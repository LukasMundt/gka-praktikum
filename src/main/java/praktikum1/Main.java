package praktikum1;

import haw.gka.praktikum.*;

import java.io.IOException;
import java.util.List;

/**
 * for development uses only
 */
public class Main {

    public static void main(String[] args) throws IOException {
        GraphModel graph = null;
        String pathIn = "src/test/java/resources/graph03.gka";
        GraphIn graphReader = new GraphIn();
//
        Kruskal kruskal = new Kruskal();
//
        graph = graphReader.readGraph(pathIn);
//        HashSet<Edge> edges = graph.getEdges();
//        List<Edge> sortedEdges = kruskal.sortEdges(edges);
//        System.out.println(sortedEdges);
//
//        List<Edge> minSpanningTree = kruskal.searchSpanningTree(graph);
//        float totalWeight = kruskal.getTotalWeight(minSpanningTree);
//
//        System.out.println("Minimaler Spannbaum: " + minSpanningTree);
//        System.out.println("Gesamtgewicht:" + totalWeight);


        GraphGenerator generator = new GraphGenerator();

        //GraphModel genGraph = generator.generateGraph(-3, 3);
        List<Edge> minSpanningTree = kruskal.searchSpanningTree(graph);
        float totalWeight = kruskal.getTotalWeight(minSpanningTree);

        System.out.println("Minimaler Spannbaum: " + minSpanningTree);
        System.out.println("Gesamtgewicht:" + totalWeight);

    }
}
