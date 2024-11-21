package pl.kurs.zad1.services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;
import pl.kurs.zad1.models.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class ShapeServiceTest {

    private IShapeService shapeService;
    private List<Shape> shapes;
    private String testFilePath;


    @Before
    public void init() {

        shapeService = new ShapeService(ObjectMapperHolder.createObjectMapper());

        testFilePath = "src/test/java/pl/kurs/zad1/services/testShapes.json";

        ShapeFactory shapeFactory = new ShapeFactory();
        shapes = List.of(
                shapeFactory.createCircle(10),
                shapeFactory.createCircle(20),
                shapeFactory.createSquare(2),
                shapeFactory.createSquare(5),
                shapeFactory.createRectangle(20, 24),
                shapeFactory.createRectangle(14, 7)
        );
    }

    @After
    public void clean() {
        new File(testFilePath).delete();
    }


    @Test
    public void shouldReturnCircleWithRadius20AsBiggestAreaShape() {
        Shape shapeWithTheBiggestArea = shapeService.findShapeWithTheBiggestArea(shapes);
        assertEquals(shapes.get(1), shapeWithTheBiggestArea);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowNoSuchElementExceptionWhenListIsEmptyInBiggestAreaMethod() {
        List<Shape> emptyList = Collections.emptyList();
        shapeService.findShapeWithTheBiggestArea(emptyList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenListIsNullInBiggestAreaMethod() {
        shapeService.findShapeWithTheBiggestArea(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowNoSuchElementExceptionWhenListHasOnlyNullValuesInBiggestAreaMethod() {
        List<Shape> listWithNulls = new ArrayList<>();
        listWithNulls.add(null);
        listWithNulls.add(null);
        shapeService.findShapeWithTheBiggestArea(listWithNulls);
    }


    @Test
    public void shouldReturnSquareWithSide5AsBiggestPerimeterSquare() {
        Shape biggestPerimeterSquare = shapeService.findShapeTypeWithTheBiggestPerimeter(shapes, Square.class);
        assertEquals(shapes.get(3), biggestPerimeterSquare);
    }

    @Test
    public void shouldReturnRectangleWithWidth20AsBiggestPerimeterRectangle() {
        Shape biggestPerimeterRectangle = shapeService.findShapeTypeWithTheBiggestPerimeter(shapes, Rectangle.class);
        assertEquals(shapes.get(4), biggestPerimeterRectangle);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowNoSuchElementExceptionWhenListIsEmptyInBiggestPerimeterMethod() {
        List<Shape> emptyList = Collections.emptyList();
        shapeService.findShapeTypeWithTheBiggestPerimeter(emptyList, Circle.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenListIsNullInBiggestPerimeterMethod() {
        shapeService.findShapeTypeWithTheBiggestPerimeter(null, Square.class);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowNoSuchElementExceptionWhenListHasNullValuesInBiggestPerimeterMethod() {
        List<Shape> listWithNulls = new ArrayList<>();
        listWithNulls.add(null);
        listWithNulls.add(null);
        shapeService.findShapeTypeWithTheBiggestPerimeter(listWithNulls, Circle.class);
    }


    @Test
    public void shouldCreateJsonFileFromList() throws IOException {
        shapeService.writeShapeListToJson(shapes, testFilePath);
        boolean isCreated = new File(testFilePath).exists();

        assertTrue(isCreated);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenListIsNullInExportMethod() throws IOException {
        shapeService.writeShapeListToJson(null, testFilePath);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenPathIsNullInExportMethod() throws IOException {
        shapeService.writeShapeListToJson(shapes, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenPathIsNullInImportMethod() throws IOException {
        shapeService.readShapeListFromJson(null);
    }

    @Test(expected = FileNotFoundException.class)
    public void shouldThrowFileNotFoundExceptionWhenPathIsNotFound() throws IOException {
        shapeService.readShapeListFromJson("randomPath");
    }

    @Test
    public void shouldReturnListOfShapesFromExportedJson() throws IOException {
        shapeService.writeShapeListToJson(shapes, testFilePath);
        List<Shape> importedList = shapeService.readShapeListFromJson(testFilePath);

        assertEquals(shapes.size(),importedList.size());
        assertEquals(shapes, importedList);
    }



}