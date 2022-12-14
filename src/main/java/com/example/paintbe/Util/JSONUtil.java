package com.example.paintbe.Util;

import com.example.paintbe.Service.Model.Shape;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONUtil {
    private static JSONObject convertShapeToJSON(Shape shape) {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = null;
        try {
            json = objectWriter.writeValueAsString(shape);
        } catch (Exception e) {e.printStackTrace();}
        return new JSONObject(json);
    }

    public static JSONArray convertListToJSONArray(ArrayList<Shape> shapes) {
        JSONArray jsonArray = new JSONArray();
        for (Shape shape : shapes) {
            jsonArray.put(convertShapeToJSON(shape));
        }
        return jsonArray;
    }

    public static JSONObject wrapListAndOperation(JSONArray array, String operation) {
        JSONObject json = new JSONObject();
        json.put("shapes", array);
        json.put("operation", operation);
        return json;
    }
}
