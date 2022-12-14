package com.example.paintbe.Service;

import com.example.paintbe.Service.Model.*;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class ShapeFactory {
    public Shape getShape(JSONObject object) {
        String shapeType = object.getJSONObject("attrs").getString("type");
        Shape shape = switch (shapeType) {
            case "Square" -> new Square();
            case "Rectangle" -> new Rectangle();
            case "RegularPolygon" -> new RegularPolygon();
            case "Triangle" -> new Triangle();
            case "Ellipse" -> new Ellipse();
            case "Circle" -> new Circle();
            case "LineSegment" -> new LineSegment();
            default -> null;
        };
        if (shape != null) shape.fromJson(object.getJSONObject("attrs"));

        return shape;
    }
}
