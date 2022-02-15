package uminho.dss.sistema_gestao.business.gestaoArmazem.Dijkstra;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * @author Grupo20
 */

public class Grafo_Armazem {
    private Set<Cruzamento> nodes;
    private boolean directed;

    public Grafo_Armazem(boolean directed) {
        this.directed = directed;
        nodes = new HashSet<>();
    }

    // Doesn't need to be called for any node that has an edge to another node
    // since addEdge makes sure that both nodes are in the nodes Set
    public void addNode(Cruzamento n) {
        // We're using a var arg method so we don't have to call
        // addNode repeatedly
        nodes.addAll(Arrays.asList(n));
    }

    public void addEdge(Cruzamento source, Cruzamento destination, double weight) {
        // Since we're using a Set, it will only add the nodes
        // if they don't already exist in our graph
        nodes.add(source);
        nodes.add(destination);

        // We're using addEdgeHelper to make sure we don't have duplicate edges
        addEdgeHelper(source, destination, weight);

        if (!directed && source != destination) {
            addEdgeHelper(destination, source, weight);
        }
    }

    private void addEdgeHelper(Cruzamento a, Cruzamento b, double weight) {
        // Go through all the edges and see whether that edge has
        // already been added
        for (Corredor edge : a.edges) {
            if (edge.source == a && edge.destination == b) {
                // Update the value in case it's a different one now
                edge.weight = weight;
                return;
            }
        }
        // If it hasn't been added already (we haven't returned
        // from the for loop), add the edge
        a.edges.add(new Corredor(a, b, weight));
    }

    public void printEdges() {
        for (Cruzamento node : nodes) {
            LinkedList<Corredor> edges = node.edges;

            if (edges.isEmpty()) {
                System.out.println("Node " + node.name + " has no edges.");
                continue;
            }
            System.out.print("Node " + node.name + " has edges to: ");

            for (Corredor edge : edges) {
                System.out.print(edge.destination.name + "(" + edge.weight + ") ");
            }
            System.out.println();
        }
    }

    public boolean hasEdge(Cruzamento source, Cruzamento destination) {
        LinkedList<Corredor> edges = source.edges;
        for (Corredor edge : edges) {
            // Again relying on the fact that all classes share the
            // exact same Cruzamento object
            if (edge.destination == destination) {
                return true;
            }
        }
        return false;
    }

    // Necessary call if we want to run the algorithm multiple times
    public void resetNodesVisited() {
        for (Cruzamento node : nodes) {
            node.unvisit();
        }
    }

    public String DijkstraShortestPath(Cruzamento start, Cruzamento end) {
        // We keep track of which path gives us the shortest path for each node
        // by keeping track how we arrived at a particular node, we effectively
        // keep a "pointer" to the parent node of each node, and we follow that
        // path to the start
        HashMap<Cruzamento, Cruzamento> changedAt = new HashMap<>();
        changedAt.put(start, null);

        // Keeps track of the shortest path we've found so far for every node
        HashMap<Cruzamento, Double> shortestPathMap = new HashMap<>();

        // Setting every node's shortest path weight to positive infinity to start
        // except the starting node, whose shortest path weight is 0
        for (Cruzamento node : nodes) {
            if (node == start)
                shortestPathMap.put(start, 0.0);
            else
                shortestPathMap.put(node, Double.POSITIVE_INFINITY);
        }

        // Now we go through all the nodes we can go to from the starting node
        // (this keeps the loop a bit simpler)
        for (Corredor edge : start.edges) {
            shortestPathMap.put(edge.destination, edge.weight);
            changedAt.put(edge.destination, start);
        }

        start.visit();

        // This loop runs as long as there is an unvisited node that we can
        // reach from any of the nodes we could till then
        while (true) {
            Cruzamento currentNode = closestReachableUnvisited(shortestPathMap);
            // If we haven't reached the end node yet, and there isn't another
            // reachable node the path between start and end doesn't exist
            // (they aren't connected)
            if (currentNode == null) {
                System.out.println("\nNão existe um caminho entre " + start.name + " e " + end.name + "!");
                return "";
            }

            // If the closest non-visited node is our destination, we want to print the path
            if (currentNode == end) {
                System.out.println("\nO caminho mais curto entre " + start.name + " e " + end.name + " é:");

                Cruzamento child = end;

                // It makes no sense to use StringBuilder, since
                // repeatedly adding to the beginning of the string
                // defeats the purpose of using StringBuilder
                String path = end.name;
                while (true) {
                    Cruzamento parent = changedAt.get(child);
                    if (parent == null) {
                        break;
                    }

                    // Since our changedAt map keeps track of child -> parent relations
                    // in order to print the path we need to add the parent before the child and
                    // it's descendants
                    path = parent.name + " " + path;
                    child = parent;
                }
                System.out.println(path);
                System.out.println("\nO trajeto custa: " + shortestPathMap.get(end));
                return path;
            }
            currentNode.visit();

            // Now we go through all the unvisited nodes our current node has an edge to
            // and check whether its shortest path value is better when going through our
            // current node than whatever we had before
            for (Corredor edge : currentNode.edges) {
                if (edge.destination.isVisited())
                    continue;

                if (shortestPathMap.get(currentNode) + edge.weight < shortestPathMap.get(edge.destination)) {
                    shortestPathMap.put(edge.destination, shortestPathMap.get(currentNode) + edge.weight);
                    changedAt.put(edge.destination, currentNode);
                }
            }
        }
    }

    private Cruzamento closestReachableUnvisited(HashMap<Cruzamento, Double> shortestPathMap) {

        double shortestDistance = Double.POSITIVE_INFINITY;
        Cruzamento closestReachableNode = null;
        for (Cruzamento node : nodes) {
            if (node.isVisited())
                continue;

            double currentDistance = shortestPathMap.get(node);
            if (currentDistance == Double.POSITIVE_INFINITY)
                continue;

            if (currentDistance < shortestDistance) {
                shortestDistance = currentDistance;
                closestReachableNode = node;
            }
        }
        return closestReachableNode;
    }

    public Cruzamento getNode(String node) {
        Cruzamento res = null;
        for (Cruzamento cruzamento : nodes) {
            if (cruzamento.getName().equals(node)) {
                return cruzamento;
            }
        }
        return res;
    }
}
