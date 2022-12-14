package com.example.paintbe.Service.Model;

import org.json.JSONObject;
import org.springframework.util.SerializationUtils;

import java.math.BigDecimal;
import java.util.ArrayList;

public class LineSegment extends Shape {

    private ArrayList<BigDecimal> points;
    private boolean closed;
    private String lineCap;
    private String lineJoin;

    public ArrayList<BigDecimal> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<BigDecimal> points) {
        this.points = points;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public String getLineCap() {
        return lineCap;
    }

    public void setLineCap(String lineCap) {
        this.lineCap = lineCap;
    }

    public String getLineJoin() {
        return lineJoin;
    }

    public void setLineJoin(String lineJoin) {
        this.lineJoin = lineJoin;
    }

    @Override
    public void fromJson(JSONObject object) {
        super.fromJson(object);
        if (object.has("closed")) this.setClosed(object.getBoolean("closed"));
        if (object.has("lineCap")) this.setLineCap(object.getString("lineCap"));
        if (object.has("lineJoin")) this.setLineJoin(object.getString("lineJoin"));

        if (object.has("points")){
            this.setPoints(new ArrayList<>(object.
                    getJSONArray("points")
                    .toList()
                    .stream()
                    .map(ob -> (BigDecimal) ob)
                    .toList()));
        }
    }

    @Override
    public LineSegment clone() {
        LineSegment line = (LineSegment) super.clone();
        line.setPoints(new ArrayList<>(this.getPoints()));
        return line;
    }


}
