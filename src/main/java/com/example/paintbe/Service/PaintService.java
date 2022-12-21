package com.example.paintbe.Service;

import com.example.paintbe.Repository.ShapeRepository;
import com.example.paintbe.Repository.db.CRUD;
import com.example.paintbe.Service.Model.Shape;
import com.example.paintbe.Util.JSONUtil;
import com.example.paintbe.Util.Pair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PaintService implements IOperation{

    private ShapeRepository shapeRepository;
    private final ShapeFactory shapeFactory;

    public PaintService(ShapeRepository shapeRepository, ShapeFactory shapeFactory) {
        this.shapeRepository = shapeRepository;
        this.shapeFactory = shapeFactory;
    }

    @Override
    public String addNewShape(String json) {
        Shape shape = shapeFactory.getShape(new JSONObject(json));
        shapeRepository.createShape(shape);
        shapeRepository.clearRedoStack(); // for clear the redo stack after every operation

        shapeRepository.printDb();

        return shape.getId();
    }

    @Override
    public void updateShape(String json) {
        Shape shape = shapeFactory.getShape(new JSONObject(json));
        shape.setId(new JSONObject(json).getJSONObject("attrs").getString("id"));
        shapeRepository.updateShape(shape);
        shapeRepository.clearRedoStack();

        //shapeRepository.printDb();
    }

    @Override
    public String copyAndInsert(String id) {
       Shape shape = shapeRepository.copyShape(id);
       shapeRepository.createShape(shape);
       shapeRepository.clearRedoStack();

       shapeRepository.printDb();

       return shape.getId();
    }

    @Override
    public void deleteShape(String id) {
        shapeRepository.deleteShape(id);
        shapeRepository.clearRedoStack();

        shapeRepository.printDb();
    }

    @Override
    public String undo() {
        Pair<List<Shape>, CRUD> result = shapeRepository.undo();
        if (result == null) return null;

        shapeRepository.printDb();

        JSONArray array = JSONUtil.convertListToJSONArray(new ArrayList<>(result.getFirst()));
        return JSONUtil.wrapListAndOperation(array,result.getSecond().name()).toString();
    }

    @Override
    public String redo() {
        Pair<List<Shape>, CRUD> result = shapeRepository.redo();
        if (result == null) return null;

        shapeRepository.printDb();

        JSONArray array = JSONUtil.convertListToJSONArray(new ArrayList<>(result.getFirst()));
        return JSONUtil.wrapListAndOperation(array,result.getSecond().name()).toString();
    }

    @Override
    public void clearStage() {
        shapeRepository.clearDB();
        shapeRepository.clearRedoStack();

        shapeRepository.printDb();
    }

    public void loadDBAndCache(String json){
        // clears database without caching
        shapeRepository.clearAll();

        JSONObject jsonObject = new JSONObject(json);
        System.out.println(jsonObject);

        JSONArray childrenOne = jsonObject.getJSONArray("children");
        JSONObject childOneObject = (JSONObject) childrenOne.get(0);
        JSONArray childTwo = childOneObject.getJSONArray("children");
        System.out.println(childTwo);

        for (int i = 1; i < childTwo.length(); i++) addNewShape(childTwo.get(i).toString());
        shapeRepository.clearUndoActions();

        shapeRepository.printDb();
    }

    public String loadAfterRefresh(){
        ArrayList<Shape> db = shapeRepository.getDataBase();
        if (db == null || db.size() == 0) return null;

        JSONArray array = JSONUtil.convertListToJSONArray(db);
        return JSONUtil.wrapListAndOperation(array,"Create").toString();
    }
}

