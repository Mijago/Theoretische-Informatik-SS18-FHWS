package topology;

import java.util.ArrayList;
import java.util.List;

public class TopologyEdgeEntry {
    public final String from;
    public final String to;
    public List<Integer> arguments;

    public TopologyEdgeEntry(String from, String to) {
        this(from, to, new ArrayList<>());
    }

    public TopologyEdgeEntry(String from, String to, List<Integer> arguments) {
        this.from = from;
        this.to = to;
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return "topology.TopologyEdgeEntry{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", arguments=" + arguments +
                '}';
    }
}
