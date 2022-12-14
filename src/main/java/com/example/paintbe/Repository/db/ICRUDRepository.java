package com.example.paintbe.Repository.db;

import com.example.paintbe.Service.Model.Shape;

import java.util.ArrayList;

public interface ICRUDRepository<T extends Shape> {

    boolean update(T obj);

    ArrayList<T> getDataBase();

    T getByID(String id);

    T getByIndex(int index);

    int count();

    boolean delete(String id);

    boolean insert(T shape);

    void deleteAll();

}
