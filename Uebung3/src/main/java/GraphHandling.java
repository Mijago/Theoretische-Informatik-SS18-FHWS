import de.normalisiert.utils.graphs.ElementaryCyclesSearch;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * https://stackoverflow.com/questions/14146165/find-all-the-paths-forming-simple-cycles-on-an-undirected-graph/14305054#14305054
 */
public class GraphHandling<V> {

    private Graph<V, DefaultWeightedEdge> graph;
    private List<V> vertexList;
    private boolean adjMatrix[][];

    public GraphHandling(Graph<V, DefaultWeightedEdge> graph) {
        this.graph = graph;
        this.vertexList = new ArrayList<>(graph.vertexSet());
    }

    public Graph<V, DefaultWeightedEdge> getGraph() {
        return graph;
    }

    public List<List<V>> getAllCycles() {
        this.buildAdjancyMatrix();

        @SuppressWarnings("unchecked")
        V[] vertexArray = (V[]) this.vertexList.toArray();
        ElementaryCyclesSearch ecs = new ElementaryCyclesSearch(this.adjMatrix, vertexArray);

        @SuppressWarnings("unchecked")
        List<List<V>> cycles0 = ecs.getElementaryCycles();

        // remove cycles of size 2
        Iterator<List<V>> listIt = cycles0.iterator();
        while (listIt.hasNext()) {
            List<V> cycle = listIt.next();

            if (cycle.size() == 2) {
                listIt.remove();
            }
        }

        // remove repeated cycles (two cycles are repeated if they have the same vertex (no matter the order)
        List<List<V>> cycles1 = removeRepeatedLists(cycles0);

        for (List<V> cycle : cycles1) {
            System.out.println(cycle);
        }


        return cycles1;
    }

    private void buildAdjancyMatrix() {
        Set<DefaultWeightedEdge> edges = this.graph.edgeSet();
        Integer nVertex = this.vertexList.size();
        this.adjMatrix = new boolean[nVertex][nVertex];

        for (DefaultWeightedEdge edge : edges) {
            V v1 = this.graph.getEdgeSource(edge);
            V v2 = this.graph.getEdgeTarget(edge);

            int i = this.vertexList.indexOf(v1);
            int j = this.vertexList.indexOf(v2);

            this.adjMatrix[i][j] = true;
            this.adjMatrix[j][i] = true;
        }
    }

    /* Here repeated lists are those with the same elements, no matter the order,
     * and it is assumed that there are no repeated elements on any of the lists*/
    private List<List<V>> removeRepeatedLists(List<List<V>> listOfLists) {
        List<List<V>> inputListOfLists = new ArrayList<List<V>>(listOfLists);
        List<List<V>> outputListOfLists = new ArrayList<List<V>>();

        while (!inputListOfLists.isEmpty()) {
            // get the first element
            List<V> thisList = inputListOfLists.get(0);
            // remove it
            inputListOfLists.remove(0);
            outputListOfLists.add(thisList);
            // look for duplicates
            Integer nEl = thisList.size();
            Iterator<List<V>> listIt = inputListOfLists.iterator();
            while (listIt.hasNext()) {
                List<V> remainingList = listIt.next();

                if (remainingList.size() == nEl) {
                    if (remainingList.containsAll(thisList)) {
                        listIt.remove();
                    }
                }
            }

        }

        return outputListOfLists;
    }

}
