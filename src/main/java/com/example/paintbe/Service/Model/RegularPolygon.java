package com.example.paintbe.Service.Model;

import org.json.JSONObject;
import org.springframework.util.SerializationUtils;

public class RegularPolygon extends Polygon {
    private double radius;
    private int sides;

    public int getSides() {
        return sides;
    }

    public void setSides(int sides) {
        this.sides = sides;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public void fromJson(JSONObject object) {
        super.fromJson(object);
        if (object.has("radius")) this.setRadius(object.getInt("radius"));
        if (object.has("sides")) this.setSides(object.getInt("sides"));
    }
    @Override
    public RegularPolygon clone() {
        return (RegularPolygon) super.clone();
    }

}
