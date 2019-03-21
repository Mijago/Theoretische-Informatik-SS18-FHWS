import graph.GraphGenerator;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import topology.Topology;
import topology.TopologyParser;

import java.nio.file.Path;

public class UltraSimpleGraphBuilder {
    private Topology topology;

    public UltraSimpleGraphBuilder(Topology topology) {
        this.topology = topology;
    }

    public UltraSimpleGraphBuilder(Path file) throws Exception {
        this(new TopologyParser().parse(file));
    }

    public UltraSimpleGraphBuilder(String content) throws Exception {
        this(new TopologyParser().parse(content));
    }

    public UltraSimpleGraphBuilder declareTopologyDirected() {
        return declareTopologyDirected(true);
    }

    public UltraSimpleGraphBuilder declareTopologyDirected(boolean tf) {
        this.topology.isDirected = tf;
        return this;
    }

    public Graph<String, DefaultWeightedEdge> build() {
        return new GraphGenerator().generate(this.topology);
    }
}
