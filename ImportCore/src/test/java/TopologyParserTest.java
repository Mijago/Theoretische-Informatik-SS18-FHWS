import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxGraph;
import org.jgrapht.Graph;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultListenableGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.io.ComponentNameProvider;
import org.jgrapht.io.DOTExporter;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.util.Collection;

// TODO: More tests
class TopologyParserTest {
    ComponentNameProvider<String> vertexIdProvider = v -> v;
    ComponentNameProvider<DefaultWeightedEdge> edgeLabelProvider = v -> v.toString();

    @Test
    void parse() throws Exception {

        String dijkstraStr = "" +
                "# 9 Knotendefinitionen, A bis I\n" +
                "knoten A 1\n" +
                "knoten B 2 \n" +
                "knoten C 3\n" +
                "knoten D 4\n" +
                "knoten E 5\n" +
                "knoten F 6\n" +
                "knoten G 7\n" +
                "knoten H 8\n" +
                "knoten I 9\n" +
                "\n" +
                "# Kantendefinitionen \n" +
                "kante A B 2\n" +
                "kante A F 9\n" +
                "kante A G 15\n" +
                "kante B C 4\n" +
                "kante B G 6\n" +
                "kante C D 2\n" +
                "kante C I 15\n" +
                "kante D E 1\n" +
                "kante D I 1\n" +
                "kante E F 6\n" +
                "kante E H 3\n" +
                "kante F H 11\n" +
                "kante G H 15\n" +
                "kante G I 2\n" +
                "kante H I 4\n";


        Graph<String, DefaultWeightedEdge> graph = new UltraSimpleGraphBuilder(dijkstraStr).build();
        System.out.println(graph);


        //GraphMLExporter<String, DefaultWeightedEdge> exporter = new GraphMLExporter<>();
        //exporter.setExportEdgeWeights(true);
        //exporter.exportGraph(graph, new FileWriter("test.gv"));


        //new DOTExporter(vertexIdProvider, null, edgeLabelProvider, null, null)
//                .exportGraph(graph, new FileWriter("test.dot"));

    }
}
