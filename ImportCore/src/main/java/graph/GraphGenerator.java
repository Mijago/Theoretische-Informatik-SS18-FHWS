package graph;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.graph.builder.GraphBuilder;
import topology.Topology;
import topology.TopologyEdgeEntry;
import topology.TopologyVertexEntry;

public class GraphGenerator {
    public Graph<String, DefaultWeightedEdge> generate(Topology topology) {

        Graph<String, DefaultWeightedEdge> result;

        if (topology.isDirected) {
            result = new SimpleDirectedWeightedGraph<>(OwnWeightedEdge.class);
        } else {
            result = new SimpleWeightedGraph<>(OwnWeightedEdge.class);
        }

        for (TopologyVertexEntry vertex : topology.vertices) {
            result.addVertex(vertex.name);
        }

        for (TopologyEdgeEntry edge : topology.edges) {
            DefaultWeightedEdge newEdge = result.addEdge(edge.from, edge.to);
            if (edge.arguments.size() == 1)
                result.setEdgeWeight(newEdge, edge.arguments.get(0));
            else if (edge.arguments.size() > 1) {
                throw new RuntimeException("Unsupported: More than one argument");
            }
        }

        return result;
    }
}
