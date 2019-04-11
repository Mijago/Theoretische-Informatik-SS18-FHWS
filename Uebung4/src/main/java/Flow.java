import org.jgrapht.Graph;
import org.jgrapht.alg.flow.EdmondsKarpMFImpl;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

public class Flow {
    public static void main(String[] args) throws Exception {
        Path fp1 = new File("./graphen/Fluss2.txt").toPath();
        Graph<String, DefaultWeightedEdge> graph = new UltraSimpleGraphBuilder(fp1)
                .declareTopologyDirected()
                .build();


        EdmondsKarpMFImpl edmondsKarpMF = new EdmondsKarpMFImpl(graph);
        double v = edmondsKarpMF.calculateMaximumFlow("S", "T");
        System.out.println("Fertiger Algorithmus sagt: " + v);

        Map<DefaultWeightedEdge, Info> cMap = graph.edgeSet()
                .stream()
                .collect(Collectors.toMap(
                        o -> o,
                        o -> new Info(graph.getEdgeWeight((DefaultWeightedEdge) o)))
                );


        float flow = 0;

        Map<String, DefaultWeightedEdge> tgt;
        do {
            tgt = new HashMap<>();

            // Find the path
            Queue<String> todo = new ArrayDeque<>();
            todo.add("S");
            while (todo.size() > 0) {
                String poll = todo.poll();


                for (DefaultWeightedEdge edge : graph.outgoingEdgesOf(poll)) {
                    String edgeTarget = graph.getEdgeTarget(edge);
                    if (tgt.containsKey(edgeTarget))
                        continue;
                    if (edgeTarget.equals("S"))
                        continue;
                    Info info = cMap.get(edge);
                    if (info.MaxFlow <= info.CurrentFlow)
                        continue;
                    tgt.put(edgeTarget, edge);
                    todo.add(edgeTarget);
                }
            }

            if (!tgt.containsKey("T"))
                continue;

            double df = Double.POSITIVE_INFINITY;
            DefaultWeightedEdge edge = tgt.get("T");
            while (edge != null) {
                Info info = cMap.get(edge);
                df = Math.min(df, info.MaxFlow - info.CurrentFlow);
                edge = tgt.get(graph.getEdgeSource(edge));
            }

            edge = tgt.get("T");
            while (edge != null) {
                Info info = cMap.get(edge);
                info.CurrentFlow += df;

                edge = tgt.get(graph.getEdgeSource(edge));
            }
            flow += df;

        } while (tgt.containsKey("T"));

        System.out.println(flow);
    }


    public static class Info {
        public double MaxFlow;
        public double CurrentFlow = 0;

        public Info(double maxFlow) {
            MaxFlow = maxFlow;
        }
    }

}
