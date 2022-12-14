package com.example.paintbe.Service;

import com.example.paintbe.Service.Model.Shape;
import org.json.JSONObject;

import java.util.List;

public interface IOperation {


    /**
     * clears the dataBase then , then calls the ----- function.
     */
    void clearStage();


    /**
     * @param json ToDo: jsonString posted by the frontend to be converted to jsonObject then passes it to the ShapeFactory
     *                         which creates the object with the values in jsonObject then adds it to the dataBase , then calls
     *                         the ----- function with
     */
    String addNewShape(String json);


    /**
     * @param json This is the new object which we get from the front end
     *                           ToDO : Use the newJson object to get the id then get the object by id
     *                                  from the database then update that object according to the newJSOnObject
     *                                  then calls the ----- function.
     */
    void updateShape(String json);

    /**
     * @param id This is the id which we get from the front end which belongs to the object we want to copy
     *           ToDO : Clone the object which we get from the database and generates a new id for the cloned object
     *                  then calls the ----- function.
     */
    String copyAndInsert(String id);


    /**
     * @param id This is the id which we get from the front end which belongs to the object we want to delete
     *           ToDo : get the object we want to delete by ID then delete that object then calls the ----- function.
     */
    void deleteShape(String id);


    /**
     * fronts waits for undo
     * <p>
     * In case of Undo of shapeModification pop the front of the undo stack and put it in the redo stack
     * then get the front of the undo stack and get the id of that object and search with it in the database
     * to get the object then convert the other object(front of undo stack) to jsonString then update DataBase Object.
     * return newObject(front of undo stack) and Modify operation
     * <p>
     * in case of Undo of Clone And draw Operations we pop the front of the undo stack and puts it
     * the redo stack then removes that object from the dataBase with id (NO Modification)
     * <p>
     * in case of Undo of Delete operation we pop the front of the undo stack and puts the object in the redo
     * stack and the dataBase.
     * <p>
     * in case of undo of Clear operation we pop the front of the undo stack and puts the object in the redo
     * stack and puts the dataBaseObject in your dataBase.
     */
    String undo();

    String redo();


    /**
     * @param shape this is the shape to be cloned
     *              ToDO : clones the shapeObject then pushes it with the corresponding operation in the undoStack
     */
//    void cloneThenPush(Shape shape, String operation);
    /*{
    public cloneThenPush(Shape shape,String operation){
        Shape shapeClone = shapeObject.clone().cloneWithSameID(shapeObject.getId());
        stackDriver.push(stackDriver.getUndo(),new Pair<>(shapeClone,operation));
        }
     */

}
