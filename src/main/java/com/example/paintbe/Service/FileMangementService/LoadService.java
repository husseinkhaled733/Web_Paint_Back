package com.example.paintbe.Service.FileMangementService;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collections;

@Component
public class LoadService {

    public JSONObject load(File savedData) {
        StringBuilder builder = new StringBuilder();
        String str, data, fileFormat;
        try {
            BufferedReader br = new BufferedReader(new FileReader(savedData));
            while ((str = br.readLine()) != null) builder.append(str);
        } catch (Exception ignored) {
        }

        data = builder.toString();
        String fileName = savedData.getName();

        builder = new StringBuilder();
        for (int i = fileName.length() - 1; i >= 0; i--) {
            if (fileName.charAt(i) == '.') break;
            builder.append(fileName.charAt(i));
        }

        fileFormat = builder.reverse().toString();
        JSONObject jsonObject = convertFileToJSON(data, fileFormat);
        System.out.println(jsonObject.toString());
        return convertFileToJSON(data, fileFormat);
    }

    public JSONObject convertFileToJSON(String data, String format) {
        return switch (format) {
            case "json" -> new JSONObject(data);
            case "xml" -> addJsonArray(XML.toJSONObject(data).getJSONObject("root"));
            default -> null;
        };
    }

    private JSONObject addJsonArray(JSONObject jsonObject){
        JSONObject childrenOne = jsonObject.getJSONObject("children");
        JSONArray array = new JSONArray();
        array.put(childrenOne);
        jsonObject = new JSONObject();
        jsonObject.put("children", array);
        return jsonObject;
    }

}
