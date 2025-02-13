package com.activity.product_scout.components;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.activity.product_scout.services.FileProcessorService;

@Component
public class StartupComponent implements CommandLineRunner {
    private static final System.Logger logger = System.getLogger(StartupComponent.class.getName());

    @Autowired
    private FileProcessorService fileProcessorService;

    /* Listen for changes in the input directory and calls the file processor service on new files */
    @Override
    public void run(String... args) throws Exception {
        String inputDir = System.getProperty("user.dir") + "/input";
        Path inputDirPath = Paths.get(inputDir);

        try {
            // create a watcher for the file system
            WatchService watcher = FileSystems.getDefault().newWatchService();

            // register the input directory for all event types
            inputDirPath.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);

            // continuously poll for events
            while (true) {
                WatchKey key = watcher.take();
                    for (WatchEvent<?> event: key.pollEvents()) {
                        // Get the new file name
                        WatchEvent<Path> ev = (WatchEvent<Path>) event;
                        Path fileName = ev.context();

                        logger.log(System.Logger.Level.INFO, "File {0} added to local directory", fileName);
                        
                        fileProcessorService.processFile(fileName.toString());
                    }
                key.reset();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
