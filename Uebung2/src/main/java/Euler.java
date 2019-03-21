import graph.GraphDrawer;
import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.cycle.HierholzerEulerianCycle;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.traverse.DepthFirstIterator;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Euler {
    static HierholzerEulerianCycle eulerianCycle = new HierholzerEulerianCycle<String, DefaultWeightedEdge>();

    public static void main(String[] args) throws Exception {

        checkFile("./graphen/Euler1.txt");
        checkFile("./graphen/Euler2.txt");
        checkFile("./graphen/Euler3.txt");
        checkFile("./graphen/Euler4.txt");

    }

    public static void checkFile(String p) throws Exception {
        Path fp1 = new File(p).toPath();
        Graph<String, DefaultWeightedEdge> graph = new UltraSimpleGraphBuilder(fp1).build();

        System.out.println("Graph " + p + " :");
        System.out.println(" Fertiger Algorithmus sagt: Er ist " + (eulerianCycle.isEulerian(graph) ? " " : "k") + "ein Eulerkreis");
        System.out.println(" Unser Algorithmus sagt:    Er ist " + (isEulerCircle(graph) ? " " : "k") + "ein Eulerkreis");
        System.out.println(" Unser Algorithmus sagt:    Er ist " + (isEulerPath(graph) ? " " : "k") + "ein Eulerpfad");
        System.out.println(" Suche Pfad A-C: " + doDepthFirstSearch(graph, "A", "C"));

        System.out.println();

        GraphDrawer.drawGraph(graph, new FileWriter(p + ".dot"));
    }

    // Aufgabe 2b)
    public static List<String> doDepthFirstSearch(Graph<String, DefaultWeightedEdge> graph, String start, String end) {
        DepthFirstIterator<String, DefaultWeightedEdge> dfi = new DepthFirstIterator<>(graph, start);

        List<String> path = new ArrayList<>();

        // Hole den nächsten Wert und vergleiche ihn mit dem Endwert.
        // Gib null zurück, wenn kein Pfad gefunden wurde.
        while (dfi.hasNext()) {
            String next = dfi.next();
            path.add(next);
            if (next.equals(end)) {
                return path;
            }
        }

        return null;
    }


    // Aufgabe 2a)
    public static boolean isEulerPath(Graph<String, DefaultWeightedEdge> graph) {
        // Zusammenhangstest - also keine "losen" Graphstücke
        ConnectivityInspector<String, DefaultWeightedEdge> ci = new ConnectivityInspector<>(graph);
        if (!ci.isConnected())
            return false;


        int amountOfVerticesWithOddEdgeCount = 0;
        // Check A: Gerade Anzahl an Kanten je Knoten
        for (String knotenName : graph.vertexSet()) {
            //System.out.println(graph.edgesOf(knotenName));
            if (graph.edgesOf(knotenName).size() % 2 != 0) {
                if (++amountOfVerticesWithOddEdgeCount > 2)
                    return false;
            }
        }
        if (amountOfVerticesWithOddEdgeCount != 2) {
            return false;
        }


        return true;
    }

    // Aufgabe 2a)
    public static boolean isEulerCircle(Graph<String, DefaultWeightedEdge> graph) {
        // Zusammenhangstest - also keine "losen" Graphstücke
        ConnectivityInspector<String, DefaultWeightedEdge> ci = new ConnectivityInspector<>(graph);
        if (!ci.isConnected())
            return false;

        // Check A: Gerade Anzahl an Kanten je Knoten
        for (String knotenName : graph.vertexSet()) {
            //System.out.println(graph.edgesOf(knotenName));
            if (graph.edgesOf(knotenName).size() % 2 != 0)
                return false;
        }


        return true;
    }
}
