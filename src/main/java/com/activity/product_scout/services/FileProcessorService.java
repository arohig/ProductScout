package com.activity.product_scout.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.activity.product_scout.components.MessageProducer;

/* Split file into products */
@Service
public class FileProcessorService {
    private static final System.Logger logger = System.getLogger(FileProcessorService.class.getName());

    @Autowired
    private MessageProducer messageProducer;

    public void processFile(String file) {
        try {
            Scanner sc = new Scanner(new File("input/"+file));

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                messageProducer.sendMessage("products", line);
            }
        } catch (FileNotFoundException e) {
            logger.log(System.Logger.Level.ERROR, "File {0} not found", file);
            logger.log(System.Logger.Level.ERROR, e.getMessage(), e);
        }
    }
}
