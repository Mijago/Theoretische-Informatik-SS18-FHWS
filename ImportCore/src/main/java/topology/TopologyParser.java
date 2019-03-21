package topology;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TopologyParser {
    public Topology parse(String content) throws Exception {
        return parse(Arrays.asList(content.split("\n")));
    }

    public Topology parse(Path file) throws Exception {
        assert (Files.exists(file));
        assert (Files.isRegularFile(file));
        assert (Files.isReadable(file));

        return parse(Files.readAllLines(file));
    }

    public Topology parse(List<String> code) {

        // Prepare the list by removing stuff and splitting the arguments
        List<List<String>> filteredCode = code.stream()
                .filter(s -> !s.isEmpty())
                .filter(s -> !s.startsWith("#"))
                .map(s -> Arrays.asList(s.split(" ")))
                .collect(Collectors.toList());

        Topology resultTopology = new Topology();

        for (List<String> strings : filteredCode) {
            switch (strings.get(0)) {
                case "knoten":
                    resultTopology.addVertex(
                            new TopologyVertexEntry(
                                    strings.get(1),
                                    strings.subList(2, strings.size())
                                            .stream()
                                            .map(Integer::parseInt)
                                            .collect(Collectors.toList())
                            )
                    );
                    break;
                case "kante":
                    resultTopology.addEdge(
                            new TopologyEdgeEntry(
                                    strings.get(1),
                                    strings.get(2),
                                    strings.subList(3, strings.size())
                                            .stream()
                                            .map(Integer::parseInt)
                                            .collect(Collectors.toList())
                            )
                    );
                    break;
                default:
                    throw new RuntimeException("Invalid keyword: " + strings.get(0));
            }
        }
        return resultTopology;
    }

}

