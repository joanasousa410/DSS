package uminho.dss.sistema_gestao.business.gestaoArmazem.Dijkstra;

import java.util.LinkedList;

public class Cruzamento {
    // The int n and String name are just arbitrary attributes
    // we've chosen for our nodes these attributes can of course
    // be whatever you need
    String n;
    String name;
    private boolean visited;
    LinkedList<Corredor> edges;

    public Cruzamento(String n, String name) {
        this.n = n;
        this.name = name;
        visited = false;
        edges = new LinkedList<>();
    }

    boolean isVisited() {
        return visited;
    }

    void visit() {
        visited = true;
    }

    void unvisit() {
        visited = false;
    }

    public String getName() {
        return this.name;
    }
}