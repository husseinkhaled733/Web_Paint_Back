package com.example.paintbe.Service.FileMangementService;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
public class FilesStorageService {

    private final Path root = Paths.get("uploads");

    private final SaveService saveService;
    private final LoadService loadService;

    @Autowired
    public FilesStorageService(SaveService saveService, LoadService loadService) {
        this.saveService = saveService;
        this.loadService = loadService;
    }


    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    public File createFile(String jsonStageAndType){
        return saveService.createFile(jsonStageAndType);
    }

    private File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }


    public JSONObject save(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()),REPLACE_EXISTING);
            return loadService.load(convert(file));
        } catch (Exception e) {
            throw (e instanceof FileAlreadyExistsException) ?
                    new RuntimeException("A file of that name already exists.") :
                    new RuntimeException(e.getMessage());
        }
    }

    public Resource load(File file) {
        try {
            Resource resource = new UrlResource(file.toURI());
            if (resource.exists() || resource.isReadable()) return resource;
            else throw new RuntimeException("Could not read the file!");

        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }
}