package pl.kurs.zad1.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Circle.class, name = "circle"),
        @JsonSubTypes.Type(value = Rectangle.class, name = "rectangle"),
        @JsonSubTypes.Type(value = Square.class, name = "square")
})
@JsonPropertyOrder("type")
public abstract class Shape {

    @JsonProperty
    private final String type = this.getClass().getSimpleName().toLowerCase();

    public abstract double calculateArea();

    public abstract double calculatePerimeter();
}
