package com.example.paintbe.Repository;

import com.example.paintbe.Repository.cache.DbCacheRepository;
import com.example.paintbe.Repository.cache.ShapeCacheRepository;
import com.example.paintbe.Repository.db.CRUD;
import com.example.paintbe.Repository.db.CRUDRepository;
import com.example.paintbe.Service.Model.*;
import com.example.paintbe.Util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.UUID;

@Component
@Scope("singleton")
public class ShapeRepository extends CRUDRepository<Shape> {

    private ShapeCacheRepository shapeCacheRepo;
    private DbCacheRepository dbCacheRepo;

    private final static Stack<Pair<String, CRUD>> undoActions = new Stack<>();
    private final static Stack<Pair<String, CRUD>> redoActions = new Stack<>();

    @Autowired
    public ShapeRepository(ShapeCacheRepository cacheRepository, DbCacheRepository dbCacheRepository) {
        this.shapeCacheRepo = cacheRepository;
        this.dbCacheRepo = dbCacheRepository;
    }

    public boolean createShape(Shape shape) {
        undoActions.push(new Pair<>(shape.getId(), CRUD.CREATE));
        shapeCacheRepo.save(shape.clone());
        return super.insert(shape);
    }


    public boolean updateShape(Shape shape) {
        undoActions.push(new Pair<>(shape.getId(), CRUD.UPDATE));
        shapeCacheRepo.update(shape.clone());
        return super.update(shape);
    }

    public void clearDB() {
        undoActions.push(new Pair<>("clear", CRUD.CLEAR));
        dbCacheRepo.save(getDataBase());
        deleteAll();
    }

    public boolean deleteShape(String id) {
        undoActions.push(new Pair<>(id, CRUD.DELETE));
        shapeCacheRepo.update(getByID(id).clone());
        return super.delete(id);
    }

    public Shape copyShape(String id) {
        Shape shape = getByID(id).clone();
        shape.setId(UUID.randomUUID().toString());
        return shape;
    }


    public Pair<List<Shape>, CRUD> undo() {
        if (undoActions.isEmpty()) return null;
        Pair<String, CRUD> lastAction = undoActions.pop();
        redoActions.push(lastAction);
        String id = lastAction.getFirst();
        CRUD action = lastAction.getSecond();

        System.out.println(id);

        switch (action) {
            case CREATE -> {
                Shape shape = getByID(id);
                super.delete(id);
                shapeCacheRepo.undoById(id);
                return new Pair<>(List.of(shape), CRUD.DELETE);
            }
            case UPDATE -> {
                Shape shape = shapeCacheRepo.undoById(id);
                super.update(shape.clone());
                return new Pair<>(List.of(shape), CRUD.UPDATE);
            }
            case DELETE -> {
                Shape shape = shapeCacheRepo.undoById(id);
                super.insert(shape.clone());
                return new Pair<>(List.of(shape), CRUD.CREATE);
            }
            case CLEAR -> {
                ArrayList<Shape> cacheDb = dbCacheRepo.undoClear();
                super.loadDataBase(cacheDb);
                return new Pair<>(cacheDb, CRUD.CREATE);
            }
        }
        return null;
    }

    public Pair<List<Shape>, CRUD> redo() {
        if (redoActions.isEmpty()) return null;
        Pair<String, CRUD> lastAction = redoActions.pop();
        undoActions.push(lastAction);
        String id = lastAction.getFirst();
        CRUD action = lastAction.getSecond();
        switch (action) {
            case CREATE -> {
                Shape shape = shapeCacheRepo.redoById(id);
                super.insert(shape.clone());
                return new Pair<>(List.of(shape), CRUD.CREATE);
            }
            case UPDATE -> {
                Shape shape = shapeCacheRepo.redoById(id);
                super.update(shape.clone());
                return new Pair<>(List.of(shape), CRUD.UPDATE);
            }
            case DELETE -> {
                Shape shape = shapeCacheRepo.redoById(id);
                super.delete(shape.getId());
                return new Pair<>(List.of(shape), CRUD.DELETE);
            }
            case CLEAR -> {
                var db = dbCacheRepo.redoClear();
                super.deleteAll();
                return new Pair<>(null,CRUD.CLEAR);
            }
        }
        return null;
    }

    public void clearRedoStack(){
        redoActions.clear();
        dbCacheRepo.clearRedoDB();
        shapeCacheRepo.clearRedo();
    }

    public void clearUndoActions(){
       undoActions.clear();
    }

    public void clearAll(){
        deleteAll();
        shapeCacheRepo.clear();
        undoActions.clear();
        redoActions.clear();
    }

    public void printDb(){
        var db = getDataBase();
        System.out.println(db.size());
        for (Shape shape : db){
            System.out.println(shape.getId());
        }
    }
}
