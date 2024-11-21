package pl.kurs.zad1.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.kurs.zad1.models.ObjectMapperHolder;
import pl.kurs.zad1.models.Shape;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ShapeService implements IShapeService {

    private final ObjectMapper objectMapper;

    public ShapeService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Shape findShapeWithTheBiggestArea(List<Shape> shapes) {

        return Optional.ofNullable(shapes).orElseThrow(() -> new IllegalArgumentException("List cannot be null"))
                .stream()
                .filter(Objects::nonNull)
                .max(Comparator.comparing(Shape::calculateArea))
                .orElseThrow(() -> new NoSuchElementException("Could not find Shape"));
    }

    @Override
    public <T extends Shape> Shape findShapeTypeWithTheBiggestPerimeter(List<Shape> shapes, Class<T> shapeType) {

        return Optional.ofNullable(shapes).orElseThrow(() -> new IllegalArgumentException("List cannot be null"))
                .stream()
                .filter(Objects::nonNull)
                .filter(x -> x.getClass().equals(shapeType))
                .max(Comparator.comparing(Shape::calculatePerimeter))
                .orElseThrow(() -> new NoSuchElementException("Could not find Shape"));
    }

    @Override
    public void writeShapeListToJson(List<Shape> shapes, String filePath) throws IOException {
        if (shapes == null || filePath == null) {
            throw new IllegalArgumentException("Null argument");
        }
        objectMapper.writeValue(new File(filePath), shapes);
    }

    @Override
    public List<Shape> readShapeListFromJson(String filePath) throws IOException {
        if (filePath == null) {
            throw new IllegalArgumentException("Null argument");
        }
        return objectMapper.readValue(new File(filePath), new TypeReference<>() {});
    }
}
