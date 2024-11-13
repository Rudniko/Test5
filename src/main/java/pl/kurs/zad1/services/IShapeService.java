package pl.kurs.zad1.services;

import pl.kurs.zad1.models.Shape;

import java.io.IOException;
import java.util.List;

public interface IShapeService {

    Shape findShapeWithTheBiggestArea(List<Shape> shapes);

    <T extends Shape> Shape findShapeTypeWithTheBiggestPerimeter(List<Shape> shapes, Class<T> shapeType);

    void writeShapeListToJson(List<Shape> shapes, String filePath) throws IOException;

    List<Shape> readShapeListFromJson(String filePath) throws IOException;

}
