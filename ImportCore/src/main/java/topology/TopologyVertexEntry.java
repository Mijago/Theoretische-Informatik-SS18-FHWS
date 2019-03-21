package topology;

import java.util.ArrayList;
import java.util.List;

public class TopologyVertexEntry {
    public final String name;
    public List<Integer> arguments;

    public TopologyVertexEntry(String name) {
        this(name, new ArrayList<>());
    }

    public TopologyVertexEntry(String name, List<Integer> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return "topology.TopologyVertexEntry{" +
                "name='" + name + '\'' +
                ", arguments=" + arguments +
                '}';
    }
}
