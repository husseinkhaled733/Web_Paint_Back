package com.example.paintbe.Service.Model;

public class Triangle extends RegularPolygon {}

/*private Point2D.Double point1;
    private Point2D.Double point2;
    private Point2D.Double point3;

    public Point2D.Double getPoint1() {
        return point1;
    }

    public void setPoint1(Point2D.Double point1) {
        this.point1 = point1;
    }

    public Point2D.Double getPoint2() {
        return point2;
    }

    public void setPoint2(Point2D.Double point2) {
        this.point2 = point2;
    }

    public Point2D.Double getPoint3() {
        return point3;
    }

    public void setPoint3(Point2D.Double point3) {
        this.point3 = point3;
    }

    @Override
    public void fromJson(JSONObject object) {

            super.fromJson(object);

            var array = (ArrayList<Double>) object.
                    getJSONArray("points")
                    .toList()
                    .stream()
                    .map(ob -> (Double) ob)
                    .toList();

            this.setPoint1(new Point2D.Double(array.get(0), array.get(1)));
            this.setPoint2(new Point2D.Double(array.get(2), array.get(3)));
            this.setPoint3(new Point2D.Double(array.get(4), array.get(5)));

    }*/
