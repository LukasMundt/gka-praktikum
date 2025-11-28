package praktikum1;

import haw.gka.praktikum.Edge;
import haw.gka.praktikum.GraphGenerator;
import haw.gka.praktikum.GraphModel;
import haw.gka.praktikum.Kruskal;

import java.io.IOException;
import java.util.List;

/**
 * for development uses only
 */
public class Main {

    public static void main(String[] args) throws IOException {
//        GraphModel graph = null;
//        String pathIn = "src/test/java/resources/Test_Kruskal.gka";
//        GraphIn graphReader = new GraphIn();
//
        Kruskal kruskal = new Kruskal();
//
//        graph = graphReader.readGraph(pathIn);
//        HashSet<Edge> edges = graph.getEdges();
//        List<Edge> sortedEdges = kruskal.sortEdges(edges);
//        System.out.println(sortedEdges);
//
//        List<Edge> minSpanningTree = kruskal.searchSpanningTree(graph);
//        float totalWeight = kruskal.getTotalWeight(minSpanningTree);
//
//        System.out.println("Minimaler Spannbaum: " + minSpanningTree);
//        System.out.println("Gesamtgewicht:" + totalWeight);


        GraphGenerator generator = new GraphGenerator(3, 3);

        GraphModel genGraph = generator.generateGraph();
        List<Edge> minSpanningTree = kruskal.searchSpanningTree(genGraph);
        float totalWeight = kruskal.getTotalWeight(minSpanningTree);

        System.out.println("Minimaler Spannbaum: " + minSpanningTree);
        System.out.println("Gesamtgewicht:" + totalWeight);

    }
}
