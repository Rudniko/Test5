package pl.kurs.zad1;

import pl.kurs.zad1.models.*;
import pl.kurs.zad1.services.IShapeService;
import pl.kurs.zad1.services.ShapeFactory;
import pl.kurs.zad1.services.IShapeFactory;
import pl.kurs.zad1.services.ShapeService;

import java.io.IOException;
import java.util.List;

public class App {
    public static void main(String[] args) throws IOException {

        IShapeFactory shapeFactory = new ShapeFactory();
        IShapeService shapeService = new ShapeService(shapeFactory);


        Circle circle = shapeFactory.createCircle(10);
        Circle circle2 = shapeFactory.createCircle(210);

        Square square = shapeFactory.createSquare(5);
        Square square2 = shapeFactory.createSquare(2);

        Rectangle rectangle = shapeFactory.createRectangle(20, 24);
        Rectangle rectangle2 = shapeFactory.createRectangle(155, 7);

        List<Shape> shapes = List.of(circle, circle2, rectangle, rectangle2, square, square2);


        Shape shapeWithTheBiggestArea = shapeService.findShapeWithTheBiggestArea(shapes);
        System.out.println(shapeWithTheBiggestArea);

        Shape shapeTypeWithTheBiggestPerimeter = shapeService.findShapeTypeWithTheBiggestPerimeter(shapes, Rectangle.class);
        System.out.println(shapeTypeWithTheBiggestPerimeter);


        shapeService.writeShapeListToJson(shapes, "src/main/java/pl/kurs/zad1/jsons/shapes.json");

        List<Shape> importedShapes = shapeService.readShapeListFromJson("src/main/java/pl/kurs/zad1/jsons/shapes.json");

        importedShapes.forEach(System.out::println);



    }
}
