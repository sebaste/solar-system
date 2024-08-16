package solarsystem;

import com.google.common.graph.ImmutableGraph;
import com.google.common.graph.Traverser;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SolarSystemImpl implements SolarSystem {

    // Note: Guava does not have a way to index the graph nodes. This could be done with for instance CQEngine.
    private ImmutableGraph<AstronomicalBody> graph;
    protected Map<String, AstronomicalBody> map;

    SolarSystemImpl() {
        map = new HashMap<>();
        GraphParserJson graphParser = new GraphParserJson();
        graph = graphParser.buildGraph(map);
    }

    public double dist(String name1, String name2) throws SolarSystemException {
        CoordinatesCartesian coordinatesCartesian1 = distToRoot(name1);
        CoordinatesCartesian coordinatesCartesian2 = distToRoot(name2);
        return coordinatesCartesian1.dist(coordinatesCartesian2);
    }

    private CoordinatesCartesian distToRoot(String name) throws SolarSystemException {
        AstronomicalBody body = map.get(name);
        if (body == null) {
            throw new SolarSystemException(String.format("%s not found in solar system", name));
        }

        Traverser.forGraph(graph).depthFirstPostOrder(body)
            .forEach(x->System.out.println(x));

        Set<AstronomicalBody> result = graph.predecessors(body);

        CoordinatesCartesian coordinatesCartesian = new CoordinatesCartesian(body.getRelativePosition());
        Set<AstronomicalBody> predecessors;
        while (!(predecessors = graph.predecessors(body)).isEmpty()) {
            AstronomicalBody parent = (AstronomicalBody)predecessors.toArray()[0];
            CoordinatesCartesian coordinatesCartesianParent = new CoordinatesCartesian(parent.getRelativePosition());
            coordinatesCartesian.add(coordinatesCartesianParent);
            body = parent;
        }

        return coordinatesCartesian;
    }
}
