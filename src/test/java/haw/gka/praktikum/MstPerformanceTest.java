package haw.gka.praktikum;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MstPerformanceTest {
    GraphGenerator generator = new GraphGenerator();

    Kruskal kruskal = new Kruskal();

    @Test
    public void test10SecKruskal() {
        GraphModel g = generator.generateGraph(580_000, 720_000);
        this.runKruskal(g, "10SecKruskal");
        this.runPrim(g, "10SecKruskal_aberPrim");
    }

    @Test
    public void test1MinKruskal() {
        GraphModel g;
//        try {
//            g = (new GraphIn()).readGraph("src/test/java/resources/Test_kruskal_1min.gka");
//        } catch (IOException e) {
            g = generator.generateGraph(1_200_000, 7_000_000);
//            try {
//                (new GraphOut()).writeFile(g,"src/test/java/resources/Test_kruskal_1min.gka");
//            } catch (IOException e2) {
//                throw new RuntimeException(e2);
//            }
//        }

        this.runKruskal(g, "1MinKruskal");
        this.runPrim(g, "1MinKruskal_aberPrim");
    }

    @Test
    public void test2SecPrim() {
        GraphModel g = generator.generateGraph(580_000, 720_000);
        this.runPrim(g, "2SecPrim");
    }

    @Test
    public void test10SecPrim() {
        GraphModel g = generator.generateGraph(580_000, 6_500_000);
        this.runPrim(g, "10SecPrim");
    }

    @Test
    public void testTask() {
        // i. 32 Knoten, 100 Kanten
        GraphModel g1 = generator.generateGraph(32, 100);
        assertEquals(this.runPrim(g1, "Prim1"), this.runKruskal(g1, "Kruskal1"));

        // ii. 64 Knoten, 300 Kanten
        GraphModel g2 = generator.generateGraph(64, 300);
        this.runPrim(g2, "Prim2");
        this.runKruskal(g2, "Kruskal2");

        // iii. 128 Knoten, 1000 Kanten
        GraphModel g3 = generator.generateGraph(128, 1000);
        this.runPrim(g3, "Prim3");
        this.runKruskal(g3, "Kruskal3");

        // iv. 256 Knoten, 3000 Kanten
        GraphModel g4 = generator.generateGraph(256, 3000);
        this.runPrim(g4, "Prim4");
        this.runKruskal(g4, "Kruskal4");

        // v. 512 Knoten, 104 Kanten
        GraphModel g5 = generator.generateGraph(512, 104);
        this.runPrim(g5, "Prim5");
        this.runKruskal(g5, "Kruskal5");

        // vi. 1024 Knoten, 3 * 10^4 = 30000 Kanten
        GraphModel g6 = generator.generateGraph(1024, 30000);
        this.runPrim(g6, "Prim6");
        this.runKruskal(g6, "Kruskal6");

        // vii. 2^11 = 2048 Knoten, 10^5 = 100000 Kanten
        GraphModel g7 = generator.generateGraph(2048, 100000);
        this.runPrim(g7, "Prim7");
        this.runKruskal(g7, "Kruskal7");

        // viii. 2^12 = 4096 Knoten, 3 * 10^5 = 300000 Kanten
        GraphModel g8 = generator.generateGraph(4096, 300000);
        this.runPrim(g8, "Prim8");
        this.runKruskal(g8, "Kruskal8");

        // ix. 2^13 = 8192 Knoten, 10^6 = 1000000 Kanten
        GraphModel g9 = generator.generateGraph(8192, 1000000);
        this.runPrim(g9, "Prim9");
        this.runKruskal(g9, "Kruskal9");

        // x. 2^14 = 16384 Knoten, 3 * 10^6 = 3000000 Kanten
        GraphModel g10 = generator.generateGraph(16384, 3000000);
        this.runPrim(g10, "Prim10");
        this.runKruskal(g10, "Kruskal10");

        // xi. 2^15 = 32768 Knoten, 10^7 = 10000000 Kanten
        GraphModel g11 = generator.generateGraph(32768, 10000000);
        this.runPrim(g11, "Prim11");
        this.runKruskal(g11, "Kruskal11");
    }

    private float runPrim(GraphModel g, String name) {
        System.out.println(name);
        GraphModel mst = Prim.getMinimalSpanningTree(g);
        return mst.getTotalWeight();
    }

    private float runKruskal(GraphModel g, String name) {
        System.out.println(name);
        List<Edge> mst = kruskal.searchSpanningTree(g);
        return kruskal.getTotalWeight(mst);
    }
}
