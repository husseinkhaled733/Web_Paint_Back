package com.example.paintbe.Repository.db;

import com.example.paintbe.Service.Model.Shape;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CRUDRepository<T extends Shape> implements ICRUDRepository<T> {

    private final ArrayList<T> DataBase  = new ArrayList<>();

    public ArrayList<T> getDataBase() {
        return DataBase;
    }

    public void loadDataBase(ArrayList<T> db){
        // dataBase is already empty in this method but clear operation for safety
        DataBase.clear();
        DataBase.addAll(db);
    }

    @Override
    public boolean update(T obj) {
        T shape = this.getByID(obj.getId());
        if (shape == null) return false;
        delete(shape.getId());
        insert(obj);
        return true;
    }

    @Override
    public T getByID(String id) {
        for (T shape : DataBase) {
            if (shape.getId().equals(id)) {
                return shape;
            }
        }
        return null;
    }

    @Override
    public T getByIndex(int index) {
        return DataBase.get(index);
    }

    @Override
    public int count() {
        return DataBase.size();
    }

    @Override
    public boolean delete(String id) {
        T shape = this.getByID(id);
        if (shape == null) return false;
        return DataBase.remove(shape);
    }

    @Override
    public boolean insert(T shape) {
        return DataBase.add(shape);
    }

    @Override
    public void deleteAll() {
        DataBase.clear();
    }

}
