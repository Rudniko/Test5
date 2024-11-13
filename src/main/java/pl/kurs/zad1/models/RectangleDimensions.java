package pl.kurs.zad1.models;

import java.util.Objects;

public class RectangleDimensions {
    private final double width;
    private final double height;

    public RectangleDimensions(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RectangleDimensions that = (RectangleDimensions) o;
        return Double.compare(that.width, width) == 0 && Double.compare(that.height, height) == 0;
    }


    @Override
    public int hashCode() {
        return Objects.hash(width, height);
    }
}
