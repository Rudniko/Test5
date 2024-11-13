package pl.kurs.zad1.services;

import pl.kurs.zad1.models.*;


import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ShapeFactory implements IShapeFactory {

    private final Map<Double, Circle> EXISTING_CIRCLES = new ConcurrentHashMap<>();
    private final Map<Double, Square> EXISTING_SQUARES = new ConcurrentHashMap<>();
    private final Map<RectangleDimensions, Rectangle> EXISTING_RECTANGLES = new ConcurrentHashMap<>();

    public Map<Double, Circle> getCreatedCircles() {
        return Collections.unmodifiableMap(EXISTING_CIRCLES);
    }

    public Map<Double, Square> getCreatedSquares() {
        return Collections.unmodifiableMap(EXISTING_SQUARES);
    }

    public Map<RectangleDimensions, Rectangle> getCreatedRectangles() {
        return Collections.unmodifiableMap(EXISTING_RECTANGLES);
    }

    @Override
    public Square createSquare(double side) {
        if (EXISTING_SQUARES.containsKey(side)) {
            return EXISTING_SQUARES.get(side);
        } else {
            Square square = Square.create(side);
            EXISTING_SQUARES.put(side, square);
            return square;
        }
    }

    @Override
    public Circle createCircle(double radius) {
        if (EXISTING_CIRCLES.containsKey(radius)) {
            return EXISTING_CIRCLES.get(radius);
        } else {
            Circle circle = Circle.create(radius);
            EXISTING_CIRCLES.put(radius, circle);
            return circle;
        }
    }

    @Override
    public Rectangle createRectangle(double width, double height) {
        RectangleDimensions key = new RectangleDimensions(width, height);
        if (EXISTING_RECTANGLES.containsKey(key)) {
            return EXISTING_RECTANGLES.get(key);
        } else {
            Rectangle rectangle = Rectangle.create(width, height);
            EXISTING_RECTANGLES.put(key, rectangle);
            return rectangle;
        }
    }
}
