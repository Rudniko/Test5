package pl.kurs.zad1.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import pl.kurs.zad1.models.Shape;

import java.io.IOException;

public class ShapeSerializer extends StdSerializer<Shape> {

    public ShapeSerializer(Class<Shape> t) {
        super(t);
    }

    @Override
    public void serialize(Shape shape, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("type", shape.getClass().getSimpleName().toLowerCase());

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.valueToTree(shape);
        jsonNode.fields().forEachRemaining(field -> {
            try {
                jsonGenerator.writeFieldName(field.getKey());
                jsonGenerator.writeObject(field.getValue());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        jsonGenerator.writeEndObject();

    }
}
