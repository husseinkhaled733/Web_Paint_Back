package com.example.paintbe.Repository.cache;

import com.example.paintbe.Service.Model.Shape;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Stack;

@Component
@Scope("singleton")
public class DbCacheRepository {

    private final static Stack<ArrayList<Shape>> undoDB = new Stack<>();
    private final static Stack<ArrayList<Shape>> redoDB = new Stack<>();

    public void save(ArrayList<Shape> old){
        ArrayList<Shape> newDB = new ArrayList<>();
        old.forEach(shape -> newDB.add(shape.clone()));
        undoDB.push(newDB);
    }

    public ArrayList<Shape> undoClear(){
        redoDB.push(undoDB.pop());
        if (redoDB.isEmpty()) return null;
        return redoDB.peek();
    }

    public ArrayList<Shape> redoClear(){
        undoDB.push(redoDB.pop());
        if (undoDB.isEmpty()) return null;
        return undoDB.peek();
    }

    public void clearRedoDB(){
        redoDB.clear();
    }

}
