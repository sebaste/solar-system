package solarsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.ImmutableGraph;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SolarSystemImplTest {

    private GraphParserJson graphParserMock;
    private ImmutableGraph.Builder graphBuilder;
    private ImmutableGraph<AstronomicalBody> graph;
    private Map<String, AstronomicalBody> map;

    @BeforeEach
    public void setup() {
        graphParserMock = mock(GraphParserJson.class);
        graphBuilder = GraphBuilder.directed().<AstronomicalBody>immutable();
        map = new HashMap<>();

        AstronomicalBody sun = new AstronomicalBody(
            "Sun",
            new CoordinatesSpherical(0.0, 0.0, 0.0)
        );
        AstronomicalBody earth = new AstronomicalBody(
            "Earth",
            new CoordinatesSpherical(42.0, 3.14, 2.78)
        );
        AstronomicalBody moon = new AstronomicalBody(
            "Moon",
            new CoordinatesSpherical(-4.0, 3.14, 2.78)
        );
        AstronomicalBody mars = new AstronomicalBody(
            "Mars",
            new CoordinatesSpherical(128.0, 3.14, 2.78)
        );
        AstronomicalBody phobos = new AstronomicalBody(
            "Phobos",
            new CoordinatesSpherical(8.0, -3.14, 3.50318306054)
        );
        map.put("Sun", sun);
        map.put("Earth", earth);
        map.put("Moon", moon);
        map.put("Mars", mars);
        map.put("Phobos", phobos);
        graphBuilder.addNode(sun);
        graphBuilder.addNode(earth);
        graphBuilder.addNode(moon);
        graphBuilder.addNode(mars);
        graphBuilder.addNode(phobos);
        graphBuilder.putEdge(sun, earth);
        graphBuilder.putEdge(earth, moon);
        graphBuilder.putEdge(sun, mars);
        graphBuilder.putEdge(mars, phobos);
        graph = graphBuilder.build();
    }

    @Test
    void exceptionWhenNameNotInMap() {
        SolarSystemImpl solarSystem = new SolarSystemImpl();

        assertThrows(SolarSystemException.class, () -> {
            solarSystem.dist("Krakkawounschia", "Moon");
        });
    }

    @Test
    void returnsCorrectDistanceForRootAndChild() throws SolarSystemException {
        when(graphParserMock.buildGraph(any())).thenReturn(graph);

        SolarSystemImpl solarSystem = new SolarSystemImpl();
        solarSystem.map = map;
        double result = solarSystem.dist("Sun", "Earth");

        assertEquals(42.0, result);
    }

    @Test
    void returnsCorrectDistanceForRootAndGrandchild() throws SolarSystemException {
        when(graphParserMock.buildGraph(any())).thenReturn(graph);

        SolarSystemImpl solarSystem = new SolarSystemImpl();
        solarSystem.map = map;
        double result = solarSystem.dist("Sun", "Moon");

        assertEquals(38.0, result);
    }

    @Test
    void returnsCorrectDistanceForChildAndChild() throws SolarSystemException {
        when(graphParserMock.buildGraph(any())).thenReturn(graph);

        SolarSystemImpl solarSystem = new SolarSystemImpl();
        solarSystem.map = map;
        double result = solarSystem.dist("Earth", "Mars");

        assertEquals(86.0, result);
    }

    @Test
    void returnsCorrectDistanceForGrandchildAndGrandchild() throws SolarSystemException {
        when(graphParserMock.buildGraph(any())).thenReturn(graph);

        SolarSystemImpl solarSystem = new SolarSystemImpl();
        solarSystem.map = map;
        double result = solarSystem.dist("Moon", "Phobos");

        assertEquals(96.14350380868838, result);
    }

    @Test
    void returnsZeroForXToX() throws SolarSystemException {
        when(graphParserMock.buildGraph(any())).thenReturn(graph);

        SolarSystemImpl solarSystem = new SolarSystemImpl();
        solarSystem.map = map;
        double result = solarSystem.dist("Earth", "Earth");

        assertEquals(0.0, result);
    }
}
