import graph.OwnWeightedEdge;
import gui.EulerGui;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.DepthFirstIterator;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;

public class Spannbaum {
    public static void main(String[] args) throws Exception {
        doSpannbaum("./graphen/Dijkstra.txt");
    }

    public static void doSpannbaum(String p) throws Exception {
        Path fp1 = new File(p).toPath();
        Graph<String, DefaultWeightedEdge> graph = new UltraSimpleGraphBuilder(fp1).build();

        ArrayList<DefaultWeightedEdge> selectedEdges = new ArrayList<>();
        ArrayList<DefaultWeightedEdge> edges = new ArrayList<>(graph.edgeSet());
        edges.sort(Comparator.comparingDouble(graph::getEdgeWeight));

        DefaultWeightedEdge first = edges.get(0);
        selectedEdges.add(first);
        edges.remove(first);

        while (!edges.isEmpty()) {
            DefaultWeightedEdge next = edges.get(0);
            edges.remove(next);


            // Build Graph using all edges in selectedEdges
            Graph<String, DefaultWeightedEdge> tempGraph = new SimpleWeightedGraph<>(OwnWeightedEdge.class);
            for (DefaultWeightedEdge selectedEdge : selectedEdges) {
                addEdgeToGraph(graph, tempGraph, selectedEdge);
            }
            // .. and add current selected edge
            addEdgeToGraph(graph, tempGraph, next);

            //CycleDetector<String, DefaultWeightedEdge> cycleDetector = new CycleDetector<>(tempGraph);
            if (new GraphHandling(tempGraph).getAllCycles().size() == 0) {
                selectedEdges.add(next);
            }
            DepthFirstIterator<String, DefaultWeightedEdge> dfi = new DepthFirstIterator<>(tempGraph);
            System.out.println(dfi.getStack());


            System.out.println(" -- ");
        }

        Graph<String, DefaultWeightedEdge> tempGraph = new SimpleWeightedGraph<>(OwnWeightedEdge.class);
        for (DefaultWeightedEdge selectedEdge : selectedEdges) {
            addEdgeToGraph(graph, tempGraph, selectedEdge);
        }

        EulerGui.showGraph(graph);
        EulerGui.showGraph(tempGraph);
    }

    private static void addEdgeToGraph(
            Graph<String, DefaultWeightedEdge> graph,
            Graph<String, DefaultWeightedEdge> tempGraph,
            DefaultWeightedEdge selectedEdge) {
        String edgeSource = graph.getEdgeSource(selectedEdge);
        String edgeTarget = graph.getEdgeTarget(selectedEdge);

        if (!tempGraph.containsVertex(edgeSource))
            tempGraph.addVertex(edgeSource);
        if (!tempGraph.containsVertex(edgeTarget))
            tempGraph.addVertex(edgeTarget);
        tempGraph.addEdge(edgeSource, edgeTarget);

        tempGraph.setEdgeWeight(edgeSource, edgeTarget, graph.getEdgeWeight(selectedEdge));

        System.out.println("Edge [ " + edgeSource + " -> " + edgeTarget + " ]");

    }


}
