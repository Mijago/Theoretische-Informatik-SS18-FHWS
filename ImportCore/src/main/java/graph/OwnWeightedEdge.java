package graph;

import org.jgrapht.graph.DefaultWeightedEdge;

public class OwnWeightedEdge extends DefaultWeightedEdge {
    @Override
    public String toString() {
        return Double.toString(getWeight());
    }
}
