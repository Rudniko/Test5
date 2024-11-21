package pl.kurs.zad1.services;

import org.junit.Before;
import org.junit.Test;
import pl.kurs.zad1.models.*;

import java.util.Map;

import static org.junit.Assert.*;

public class ShapeFactoryTest {

    private ShapeFactory shapeFactory;

    @Before
    public void init() {
        shapeFactory = new ShapeFactory();
    }

    @Test
    public void shouldReturnAnInstanceOfASquare() {
        Square square = shapeFactory.createSquare(10);

        assertEquals(Square.class, square.getClass());
    }

    @Test
    public void cacheShouldContainAddedSquare() {
        Square square = shapeFactory.createSquare(12345);

        Map<Double, Square> createdSquares = shapeFactory.getCreatedSquares();

        Square square2 = createdSquares.get(12345.0);

        assertSame(square, square2);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldReturnUnmodifiableSquaresMap() {
        Map<Double, Square> createdSquares = shapeFactory.getCreatedSquares();
        createdSquares.put(222.0, shapeFactory.createSquare(222));
    }
    @Test(expected = UnsupportedOperationException.class)
    public void shouldReturnUnmodifiableCirclesMap() {
        Map<Double, Circle> createdCircles = shapeFactory.getCreatedCircles();
        createdCircles.put(222.0, shapeFactory.createCircle(22));
    }
    @Test(expected = UnsupportedOperationException.class)
    public void shouldReturnUnmodifiableRectanglesMap() {
        Map<RectangleDimensions, Rectangle> createdRectangles = shapeFactory.getCreatedRectangles();
        createdRectangles.put(new RectangleDimensions(21,50), shapeFactory.createRectangle(21,50));
    }

    @Test
    public void shouldReturnTheSameSquareReference() {
        Square square1 = shapeFactory.createSquare(10);
        Square square2 = shapeFactory.createSquare(10);

        assertSame(square1, square2);
    }

    @Test
    public void shouldReturnTwoDifferentSquares() {
        Square square1 = shapeFactory.createSquare(20);
        Square square2 = shapeFactory.createSquare(10);

        assertNotSame(square1, square2);
    }

    @Test
    public void shouldReturnAnInstanceOfARectangle() {
        Shape rectangle = shapeFactory.createRectangle(10, 20);

        assertEquals(Rectangle.class, rectangle.getClass());
    }

    @Test
    public void shouldReturnTheSameRectangleReference() {
        Rectangle rectangle1 = shapeFactory.createRectangle(10, 20);
        Rectangle rectangle2 = shapeFactory.createRectangle(10, 20);

        assertSame(rectangle1, rectangle2);
    }

    @Test
    public void shouldReturnTwoDifferentRectangles() {
        Rectangle rectangle1 = shapeFactory.createRectangle(10, 20);
        Rectangle rectangle2 = shapeFactory.createRectangle(20, 10);

        assertNotSame(rectangle1, rectangle2);
    }

    @Test
    public void shouldReturnAnInstanceOfACircle() {
        Shape circle = shapeFactory.createCircle(5);

        assertEquals(Circle.class, circle.getClass());
    }

    @Test
    public void shouldReturnTheSameCircleReference() {
        Circle circle1 = shapeFactory.createCircle(5);
        Circle circle2 = shapeFactory.createCircle(5);

        assertSame(circle1, circle2);
    }

    @Test
    public void shouldReturnTwoDifferentCircles() {
        Circle circle1 = shapeFactory.createCircle(8);
        Circle circle2 = shapeFactory.createCircle(5);

        assertNotSame(circle1, circle2);
    }


}