package com.activity.product_scout.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:3000")  // allow React app to communicate with backend
@RestController
public class BaseController {
    private static final System.Logger logger = System.getLogger(BaseController.class.getName());

    @GetMapping("/test")
    public String test() {
        return "TEST: Welcome to this server";
    }

    /* Save file to input folder when user uploads file */
    @PostMapping(value = "/uploadFile", consumes = "multipart/form-data")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        logger.log(System.Logger.Level.INFO, "Received file {0} from user", fileName); // log file name

        String uploadDir = System.getProperty("user.dir") + "/input";

        // save file to input folder
        try {
            file.transferTo(new File(uploadDir + "/" + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(fileName, HttpStatus.CREATED);
    }
}
