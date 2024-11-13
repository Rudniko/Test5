package pl.kurs.zad1.models;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import pl.kurs.zad1.serializers.ShapeDeserializer;
import pl.kurs.zad1.serializers.ShapeSerializer;
import pl.kurs.zad1.services.IShapeFactory;


public class ObjectMapperHolder {

    private static ObjectMapper objectMapper;

    private ObjectMapperHolder() {
    }

    public static ObjectMapper createObjectMapper(IShapeFactory shapeFactory) {

        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        SimpleModule module = new SimpleModule("shapeSerializer");
        module.addSerializer(Shape.class, new ShapeSerializer(Shape.class));

        SimpleModule module2 = new SimpleModule("shapeDeserializer");
        module2.addDeserializer(Shape.class, new ShapeDeserializer(Shape.class, shapeFactory));

        objectMapper.registerModules(module, module2);

        return objectMapper;
    }
}
