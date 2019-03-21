package topology;

import java.util.HashSet;
import java.util.Set;

public class Topology {
    // TODO: Validate

    public Set<TopologyVertexEntry> vertices = new HashSet<>();
    public Set<TopologyEdgeEntry> edges = new HashSet<>();

    public boolean isDirected = false;


    public void addVertex(TopologyVertexEntry vertex) {
        this.vertices.add(vertex);
    }

    public void addEdge(TopologyEdgeEntry edge) {
        this.edges.add(edge);
    }

    @Override
    public String toString() {
        return "topology.Topology{\n" +
                "vertices=" + vertices +
                "\n, edges=" + edges +
                "\n}";
    }
}
