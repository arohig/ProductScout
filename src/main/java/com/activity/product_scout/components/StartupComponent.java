package com.activity.product_scout.components;

import java.io.File;
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

// public class StartupComponent implements CommandLineRunner {
//     private final FileProcessorService fileProcessorService;

//     public StartupComponent(FileProcessorService fileProcessorService) {
//         this.fileProcessorService = fileProcessorService;
//     }

//     @Override
//     public void run(String... args) throws Exception {
//         // process all files in the input directory
//         File inputDir = new File("input");
//         File[] files = inputDir.listFiles();
//         for (File file : files) {
//             fileProcessorService.processFile(file.getAbsolutePath());
//         }
//     }
// }

@Component
public class StartupComponent implements CommandLineRunner {
    private static final System.Logger logger = System.getLogger(StartupComponent.class.getName());

    @Autowired
    private FileProcessorService fileProcessorService;

    /* Listens for changes in the input directory and calls the file processor service on new files */
    @Override
    public void run(String... args) throws Exception {
        String inputDir = System.getProperty("user.dir") + "/input";
        Path inputDirPath = Paths.get(inputDir);

        logger.log(System.Logger.Level.INFO, "directory " + inputDir);

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
                        Path child = inputDirPath.resolve(fileName);

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
