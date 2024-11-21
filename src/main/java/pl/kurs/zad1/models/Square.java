package pl.kurs.zad1.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Square extends Shape {

    @JsonProperty
    private double side;

    private Square(double side) {
        this.side = side;
    }

    @JsonCreator
    public static Square create(@JsonProperty("side") double side) {
        return new Square(side);
    }

    @Override
    public double calculateArea() {
        return side * side;
    }

    @Override
    public double calculatePerimeter() {
        return side * 4;
    }


    public double getSide() {
        return side;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return Double.compare(square.side, side) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(side);
    }

    @Override
    public String toString() {
        return "Square{" +
                "side=" + side +
                '}';
    }
}
