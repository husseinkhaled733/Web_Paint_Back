package com.example.paintbe.Service.FileMangementService;

import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class InitiateFileSystemService implements CommandLineRunner {

    @Resource
    FilesStorageService storageService;

    @Override
    public void run(String... arg) {
        storageService.deleteAll();
        storageService.init();
    }
}
