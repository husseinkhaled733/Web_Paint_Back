package com.example.paintbe.Service.Model;

import org.json.JSONObject;

public class Ellipse extends EllipticalShape {
    private double radiusX;
    private double radiusY;

    public double getRadiusX() {
        return radiusX;
    }

    public void setRadiusX(double radiusX) {
        this.radiusX = radiusX;
    }

    public double getRadiusY() {
        return radiusY;
    }

    public void setRadiusY(double radiusY) {
        this.radiusY = radiusY;
    }

    @Override
    public void fromJson(JSONObject object) {
        super.fromJson(object);
        if(object.has("radiusX")) this.setRadiusX(object.getDouble("radiusX"));
        if(object.has("radiusY")) this.setRadiusY(object.getDouble("radiusY"));
    }
    @Override
    public Ellipse clone() {
        return (Ellipse) super.clone();
    }
}
