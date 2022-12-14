package com.example.paintbe.Service.Model;

import org.json.JSONObject;
import org.apache.commons.lang.SerializationUtils;

public class Rectangle extends Polygon {

    private double height;
    private double width;

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    @Override
    public void fromJson(JSONObject object) {
        super.fromJson(object);
        if (object.has("height")) this.setHeight(object.getDouble("height"));
        if (object.has("width")) this.setWidth(object.getDouble("width"));
    }

    @Override
    public Rectangle clone() {
        return (Rectangle) super.clone();
    }
}
