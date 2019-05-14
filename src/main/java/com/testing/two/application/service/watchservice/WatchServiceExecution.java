package com.testing.two.application.service.watchservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;

@Component
public class WatchServiceExecution {
    private final String fileName = "partner.json";
    private final String fileDirectory = System.getProperty("catalina.base")+"/app/config/testingtwo/partners";
    private WatchService watchService;
    private Path jsonFilePath;

    @Autowired
    PartnerPropertyResolver partnerPropertyResolver;

    public WatchServiceExecution() throws IOException {
        watchService = FileSystems.getDefault().newWatchService();
        jsonFilePath = Paths.get(PartnerPropertyResolver.FILE_DIR);
        jsonFilePath.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY,StandardWatchEventKinds.ENTRY_CREATE,StandardWatchEventKinds.ENTRY_DELETE);
    }

    /**
     * Method starts a Java WatchService, which waits for any changes in a directory</br>
     * If changes are for a specific file, it will call the {@link PartnerPropertyResolver} which will parse the file and store any changes in it's {@link PartnerPropertyResolver#partnerMap}</br></br>
     * Since the WatchService will stop execution until a change occurs in the directory, this code must run in a separate Thread</br>
     * Spring's @Async is used here - this means that a new Thread will be created and this method will be run in it. It's a Spring-simple way of running a new thread, without extensive configurations
     * See {@link com.testing.two.config.rootconfig.RootConfig} for configuration necessary to support @Async
     */
    @Async
    public void startWatchService() {
        WatchKey key;
        System.out.println("triggered");
        try {
            while ((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    System.out.println(
                            "Event kind:" + event.kind()
                                    + ". File affected: " + event.context() + ".");

                    if(fileName.equals(event.context().toString())) {
                        try {
                            partnerPropertyResolver.updatePartnerMap();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                key.reset();
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
