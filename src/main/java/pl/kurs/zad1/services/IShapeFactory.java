package pl.kurs.zad1.services;

import pl.kurs.zad1.models.Circle;
import pl.kurs.zad1.models.Rectangle;
import pl.kurs.zad1.models.Square;

public interface IShapeFactory {
    Square createSquare(double side);
    Circle createCircle(double radius);
    Rectangle createRectangle(double width, double height);
}
