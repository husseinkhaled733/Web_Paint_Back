package com.example.paintbe.Service.Model;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.UUID;

public abstract class Shape implements Cloneable {
    private double x = 0;
    private double y = 0;
    private String fill = "";
    private String stroke = "black";
    private double scaleX = 1;
    private double scaleY = 1;
    private double skewX = 0;
    private double skewY = 0;
    private double rotation=0;
    private double strokeWidth = 2;
    private boolean draggable = false;
    private double opacity=1;
    private String type;
    private String id = UUID.randomUUID().toString();

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getFill() {
        return fill;
    }

    public void setFill(String fill) {
        this.fill = fill;
    }

    public String getStroke() {
        return stroke;
    }

    public void setStroke(String stroke) {
        this.stroke = stroke;
    }

    public double getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(double strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public boolean isDraggable() {
        return draggable;
    }

    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
    }

    public double getScaleX() {
        return scaleX;
    }

    public void setScaleX(double scaleX) {
        this.scaleX = scaleX;
    }

    public double getScaleY() {
        return scaleY;
    }

    public void setScaleY(double scaleY) {
        this.scaleY = scaleY;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getSkewX() {
        return skewX;
    }

    public void setSkewX(double skewX) {
        this.skewX = skewX;
    }

    public double getSkewY() {
        return skewY;
    }

    public void setSkewY(double skewY) {
        this.skewY = skewY;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public double getOpacity() {
        return opacity;
    }

    public void setOpacity(double opacity) {
        this.opacity = opacity;
    }

    public void fromJson(JSONObject object) {
        if (object.has("x")) this.setX(object.getDouble("x"));
        if (object.has("y")) this.setY(object.getDouble("y"));
        if (object.has("stroke")) this.setStroke(object.getString("stroke"));
        if (object.has("fill")) this.setFill(object.getString("fill"));
        if (object.has("strokeWidth")) this.setStrokeWidth(object.getInt("strokeWidth"));
        if (object.has("draggable")) this.setDraggable(object.getBoolean("draggable"));
        if (object.has("scaleX")) this.setScaleX(object.getDouble("scaleX"));
        if (object.has("scaleY")) this.setScaleY(object.getDouble("scaleY"));
        if (object.has("type")) this.setType(object.getString("type"));
        if (object.has("id")) this.setId(object.getString("id"));
        if (object.has("skewX")) this.setSkewX(object.getDouble("skewX"));
        if (object.has("skewY")) this.setSkewY(object.getDouble("skewY"));
        if (object.has("rotation")) this.setRotation(object.getDouble("rotation"));
        if (object.has("opacity")) this.setOpacity(object.getDouble("opacity"));
    }

    @Override
    public Shape clone() {
        try {
            return (Shape) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
