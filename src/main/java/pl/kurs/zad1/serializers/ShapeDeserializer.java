package pl.kurs.zad1.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import pl.kurs.zad1.models.Shape;
import pl.kurs.zad1.services.IShapeFactory;


import java.io.IOException;

public class ShapeDeserializer extends StdDeserializer<Shape> {

    private final IShapeFactory shapeFactory;

    public ShapeDeserializer(Class<?> vc, IShapeFactory shapeFactory) {
        super(vc);
        this.shapeFactory = shapeFactory;
    }

    @Override
    public Shape deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
        String type = jsonNode.get("type").asText();

        return switch (type) {
            case "square" -> shapeFactory.createSquare(jsonNode.get("side").asDouble());
            case "circle" -> shapeFactory.createCircle(jsonNode.get("radius").asDouble());
            case "rectangle" -> shapeFactory.createRectangle(jsonNode.get("width").asDouble(), jsonNode.get("height").asDouble());
            default -> throw new IllegalArgumentException("Unknown shape type: " + type);
        };

    }
}
