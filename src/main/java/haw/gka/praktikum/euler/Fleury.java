package haw.gka.praktikum.euler;

import haw.gka.praktikum.Edge;
import haw.gka.praktikum.GraphModel;

import java.util.List;

public class Fleury {

    private GraphModel originalGraph;
    private List<Edge> eulerCircle;
    private GraphModel eulerCircleGraph;

    /**
     *
     *
     * @param originalGraph Graph, in dem der Eulerkreis gesucht werden soll
     * @param eulerCircle Liste der Kanten des Eulerkreises
     * @param eulerCircleGraph Graph, der den Eulerkreis enthält
     */
    private Fleury(GraphModel originalGraph, List<Edge> eulerCircle, GraphModel eulerCircleGraph) {
        this.originalGraph = originalGraph;
        this.eulerCircle = eulerCircle;
        this.eulerCircleGraph = eulerCircleGraph;
    }

    /**
     * Führt den Fleury-Algorithmus zur Suche nach einem Eulerkreis aus. An der zurückgegebenen
     * Fleury-Instanz kann das Resultat abgerufen werden.
     *
     * @param graphModel Graph, in dem Eulerkreis gesucht werden soll
     * @return Fleury-Objekt
     */
    public static Fleury search(GraphModel graphModel) {
        // todo: prechecks: zusammenhängend + knotengrade

        // todo: execute fleury

        return new Fleury(graphModel, null, null);
    }

    /**
     * Gibt den Eulerkreis als Liste von Kanten zurück.
     *
     * @return Liste von Kanten
     */
    public List<Edge> getEulerCircleTour() {
        return eulerCircle;
    }

    /**
     * Gibt den Eulerkreis als Graph zurück.
     *
     * @return Eulerkreis als Graph
     */
    public GraphModel getEulerCircleGraph() {
        return eulerCircleGraph;
    }
}
