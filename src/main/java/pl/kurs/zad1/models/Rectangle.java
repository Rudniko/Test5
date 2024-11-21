package pl.kurs.zad1.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Rectangle extends Shape {

    @JsonProperty
    private double width;
    @JsonProperty
    private double height;

    private Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @JsonCreator
    public static Rectangle create(@JsonProperty("width") double width, @JsonProperty("height") double height) {
        return new Rectangle(width, height);
    }

    @Override
    public double calculateArea() {
        return width * height;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * width + 2 * height;
    }


    public double getWidth() {
        return width;
    }


    public double getHeight() {
        return height;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rectangle rectangle = (Rectangle) o;
        return Double.compare(rectangle.width, width) == 0 && Double.compare(rectangle.height, height) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height);
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }
}
