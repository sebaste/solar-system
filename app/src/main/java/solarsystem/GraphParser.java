package solarsystem;

import com.google.common.graph.ImmutableGraph;
import java.util.Map;

public interface GraphParser {

    ImmutableGraph buildGraph(Map<String, AstronomicalBody> map);
}
