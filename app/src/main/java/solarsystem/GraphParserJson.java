package solarsystem;

import static java.lang.Double.parseDouble;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.ImmutableGraph;
import java.io.InputStream;
import java.util.Map;

public class GraphParserJson implements GraphParser {

    private final String RESOURCE_SOLAR_SYSTEM = "solar_system.json";
    private final String FIELD_NAME = "name";
    private final String FIELD_POS = "pos";
    private final String FIELD_CHILDREN = "children";

    private ObjectMapper objectMapper;
    private ImmutableGraph.Builder graphBuilder;

    GraphParserJson() {
        objectMapper = new ObjectMapper();
    }

    public ImmutableGraph buildGraph(Map<String, AstronomicalBody> map) {
        graphBuilder = GraphBuilder.directed().<AstronomicalBody>immutable();
        try (InputStream in=Thread.currentThread().getContextClassLoader().getResourceAsStream(RESOURCE_SOLAR_SYSTEM)) {
            JsonNode jsonNode = objectMapper.readValue(in, JsonNode.class);
            parseGraphFromJson(graphBuilder, map, jsonNode, null);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
        return graphBuilder.build();
    }

    private AstronomicalBody parseGraphFromJson(
        ImmutableGraph.Builder graphBuilder,
        Map<String, AstronomicalBody> map,
        JsonNode root,
        AstronomicalBody parent) throws GraphParserException
    {
        if (root.isObject()) {
            AstronomicalBody node = new AstronomicalBody(
                getField(root, FIELD_NAME).asText(),
                parseSphericalCoordinates(getField(root, FIELD_POS).asText())
            );
            graphBuilder.addNode(node);
            map.put(getField(root, FIELD_NAME).asText(), node);
            if (parent != null) {
                graphBuilder.putEdge(parent, node);
            }
            parent = node;
            if (!getField(root, FIELD_CHILDREN).isEmpty()) {
                parseGraphFromJson(graphBuilder, map, getField(root, FIELD_CHILDREN), parent);
            }
            return node;
        } else if (root.isArray()) {
            ArrayNode arrayNode = (ArrayNode)root;
            for(int i = 0; i < arrayNode.size(); i++) {
                parseGraphFromJson(graphBuilder, map, arrayNode.get(i), parent);
            }
        } else {
            throw new GraphParserException("JsonNode is a single value field");
        }
        return null;
    }

    private JsonNode getField(JsonNode jsonNode, String field) throws GraphParserException {
        JsonNode result = jsonNode.get(field);
        if (result == null) {
            throw new GraphParserException(String.format("Field '%s' does not exist", field));
        }
        return result;
    }

    private CoordinatesSpherical parseSphericalCoordinates(String s) throws GraphParserException {
        String[] pos = s.split(",");
        if (pos.length != 3) {
            throw new GraphParserException(String.format("Position length must be 3: '%s'", s));
        }
        return new CoordinatesSpherical(
            parseDouble(pos[0]),
            parseDouble(pos[1]),
            parseDouble(pos[2])
        );
    }
}
