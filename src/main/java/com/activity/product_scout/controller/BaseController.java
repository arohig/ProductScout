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

    /* Create new file with item when user enters an item */
    @PostMapping(value = "/enterItem", consumes = "text/plain")
    public ResponseEntity<String> enterItem(@RequestBody String item) {
        logger.log(System.Logger.Level.INFO, "Received a request of item {0} from user", item); // log item

        // Write item to file
        String uploadDir = System.getProperty("user.dir") + "/input";
        try {
            File file = new File(uploadDir + "/user_items.txt");
            file.createNewFile();

            java.io.FileWriter writer = new java.io.FileWriter(file);
            writer.write(item);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }
}
