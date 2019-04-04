import gui.EulerGui;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.io.File;
import java.nio.file.Path;
import java.util.*;

public class Dijkstra {

    public static void main(String[] args) throws Exception {
        System.out.println("A -> D\n");
        doDijkstra("./graphen/Dijkstra.txt", "A", "D");
        System.out.println();
        System.out.println("----------------------------------");
        System.out.println();
        System.out.println("H -> B\n");
        doDijkstra("./graphen/Dijkstra.txt", "H", "B");
    }

    public static void doDijkstra(String p, String source, String target) throws Exception {
        Path fp1 = new File(p).toPath();
        Graph<String, DefaultWeightedEdge> graph = new UltraSimpleGraphBuilder(fp1).build();

        Map<String, NodeInformation> lookup = new HashMap<>();

        EulerGui.showGraph(graph);

        // initialize the map - for each node exists a NodeInformation object
        for (String s : graph.vertexSet()) {
            lookup.put(s, new NodeInformation(s));
        }

        // Init start node
        NodeInformation startNode = lookup.get(source);
        startNode.cost = 0;
        startNode.status = NodeStatus.Visited;

        // While there is at least one visited, but not finished node ...
        while (lookup.values().stream().anyMatch(info -> info.status == NodeStatus.Visited)) {
            // Get the Node which is visited and has the lowest cost
            NodeInformation currentNode = lookup.values()
                    .stream()
                    .filter(info -> info.status == NodeStatus.Visited)
                    .sorted()
                    .findFirst()
                    .get();

            // We are done if the node with the lowest cost is the target node
            if (currentNode.nodeName.equals(target)) {
                break; // YAY!
            }

            // Mark the current node as finished
            currentNode.status = NodeStatus.Finished;

            // For each outgoing edge...
            Set<DefaultWeightedEdge> outgoingEdges = graph.outgoingEdgesOf(currentNode.nodeName);
            for (DefaultWeightedEdge outgoingEdge : outgoingEdges) {
                // get the node on the other side of the edge
                String searchNode = getEdgeTarget(graph, currentNode, outgoingEdge);

                NodeInformation otherNode = lookup.get(searchNode);

                double newCost = currentNode.cost + graph.getEdgeWeight(outgoingEdge);
                if (newCost < otherNode.cost) {
                    otherNode.cost = newCost;
                    otherNode.predecessor = currentNode.nodeName;
                    otherNode.status = NodeStatus.Visited;
                }
            }
        }

        /*
        for (NodeInformation value : lookup.values()) {
            System.out.println(value);
        }
        */

        List<String> lst = new ArrayList<>();

        NodeInformation targetNode = lookup.get(target);
        lst.add(targetNode.nodeName);
        while(targetNode.predecessor != null) {
            lst.add(0, targetNode.predecessor);
            targetNode = lookup.get(targetNode.predecessor);
        }

        System.out.println(String.join(" -> ", lst));
    }

    private static String getEdgeTarget(Graph<String, DefaultWeightedEdge> graph, NodeInformation currentNode, DefaultWeightedEdge outgoingEdge) {
        String edgeTarget = graph.getEdgeTarget(outgoingEdge);
        String edgeSource = graph.getEdgeSource(outgoingEdge);
        return edgeTarget.equals(currentNode.nodeName) ? edgeSource : edgeTarget;
    }


    enum NodeStatus {
        None,
        Visited,
        Finished
    }

    static class NodeInformation implements Comparable<NodeInformation> {
        String nodeName;
        NodeStatus status = NodeStatus.None;
        double cost = Double.MAX_VALUE;
        String predecessor = null;

        NodeInformation(String nodeName) {
            this.nodeName = nodeName;
        }

        @Override
        public int compareTo(NodeInformation o) {
            return Double.compare(cost, o.cost);
        }

        @Override
        public String toString() {
            return "NodeInformation{" +
                    "nodeName='" + nodeName + '\'' +
                    ", status=" + status +
                    ", cost=" + cost +
                    ", predecessor='" + predecessor + '\'' +
                    '}';
        }
    }
}
