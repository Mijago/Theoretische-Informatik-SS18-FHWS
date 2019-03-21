package graph;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.io.ComponentNameProvider;
import org.jgrapht.io.DOTExporter;

import java.io.Writer;

public class GraphDrawer {

    static ComponentNameProvider<String> vertexIdProvider = v -> v;
    static ComponentNameProvider<DefaultWeightedEdge> edgeLabelProvider = v -> v.toString();

    public static void drawGraph(Graph graph, Writer w) {
        new DOTExporter(vertexIdProvider, null, edgeLabelProvider, null, null)
                .exportGraph(graph, w);
    }
}
