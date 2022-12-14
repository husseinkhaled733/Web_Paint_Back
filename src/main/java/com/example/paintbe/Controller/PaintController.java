package com.example.paintbe.Controller;

import com.example.paintbe.Service.*;

import com.example.paintbe.Service.FileMangementService.FilesStorageService;
import com.example.paintbe.Service.FileMangementService.InitiateFileSystemService;
import com.example.paintbe.Service.Model.FileInfo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.File;

@CrossOrigin()
@RequestMapping("/api")
@RestController
public class PaintController {

    private final PaintService paintService;
    private final InitiateFileSystemService initiateFileSystemService;
    private final FilesStorageService storageService;

    @Autowired
    public PaintController(PaintService paintService, InitiateFileSystemService initiateFileSystemService, FilesStorageService storageService) {
        this.paintService = paintService;
        this.initiateFileSystemService = initiateFileSystemService;
        this.storageService= storageService;
    }

    // add , update , copy , delete , undo , redo, clear

    @PostMapping("/Create")
    public ResponseEntity<String> createShape(@RequestBody String json) {
        return ResponseEntity.ok(new JSONObject().put("id", paintService.addNewShape(json)).toString());
    }

    @PostMapping("/Update")
    public void updateShape(@RequestBody String json) {
        paintService.updateShape(json);
    }

    @PostMapping("/Copy")
    public ResponseEntity<String> copyShape(@RequestBody String id) {
        return ResponseEntity.ok(new JSONObject().put("id", paintService.copyAndInsert(id)).toString());
    }

    @PostMapping("/Delete")
    public void deleteShape(@RequestBody String id) {
        paintService.deleteShape(id);
    }

    @GetMapping("/Undo")
    public ResponseEntity<String> undoOperation() {
        return ResponseEntity.ok(paintService.undo());
    }

    @GetMapping("/Redo")
    public ResponseEntity<String> redoOperation() {
        return ResponseEntity.ok(paintService.redo());
    }

    @GetMapping("/Clear")
    public void clearDataBase() {
        paintService.clearStage();
    }


    @PostMapping("/Save")
    public FileInfo save(@RequestBody String json) {
        File file = storageService.createFile(json);
        String url = MvcUriComponentsBuilder
                .fromMethodName(PaintController.class, "getFile", file)
                .build().toString();
        return new FileInfo(file.getName(),url);
    }

    @PostMapping("/Load")
    public ResponseEntity<String> load(@RequestParam("file") MultipartFile file) {
        String json = storageService.save(file).toString();
        paintService.loadDBAndCache(json);
        return ResponseEntity.ok(json);
    }


    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable File filename) {
        Resource file = storageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\""
                                + file.getFilename() + "\"").body(file);
    }



    @GetMapping("/Refresh")
    public ResponseEntity<String> getDbAfterRefresh() {
        var temp = paintService.loadAfterRefresh();
        System.out.println(temp);
        return ResponseEntity.ok(temp);
    }
}
