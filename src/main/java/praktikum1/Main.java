package praktikum1;

import haw.gka.praktikum.GraphIn;
import haw.gka.praktikum.GraphModel;

import java.io.IOException;

import static haw.gka.praktikum.euler.Hierholzer.searchEulerCircle;

/**
 * for development uses only
 */
public class Main {

    public static void main(String[] args) throws IOException {
        GraphModel graph = null;
        String pathIn = "src/test/java/resources/graph03.gka";
        String euler = "src/test/java/resources/euler/Eulerkreis.gka";
        GraphIn graphReader = new GraphIn();
//
        //        Kruskal kruskal = new Kruskal();
        //Hierholzer hierholzer = new Hierholzer();
//
        graph = graphReader.readGraph(euler);
//        HashSet<Edge> edges = graph.getEdges();
//        List<Edge> sortedEdges = kruskal.sortEdges(edges);
//        System.out.println(sortedEdges);
//
//        List<Edge> minSpanningTree = kruskal.searchSpanningTree(graph);
//        float totalWeight = kruskal.getTotalWeight(minSpanningTree);
//
//        System.out.println("Minimaler Spannbaum: " + minSpanningTree);
//        System.out.println("Gesamtgewicht:" + totalWeight);


//        GraphGenerator generator = new GraphGenerator();
//
//        //GraphModel genGraph = generator.generateGraph(-3, 3);
//        List<Edge> minSpanningTree = kruskal.searchSpanningTree(graph);
//        float totalWeight = kruskal.getTotalWeight(minSpanningTree);
//
//        System.out.println("Minimaler Spannbaum: " + minSpanningTree);
//        System.out.println("Gesamtgewicht:" + totalWeight);

        GraphModel eulerCircle = searchEulerCircle(graph);
        System.out.println("Eulerkreis: " + eulerCircle.toString());
    }
}
