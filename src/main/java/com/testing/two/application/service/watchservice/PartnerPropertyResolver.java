package com.testing.two.application.service.watchservice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;

@Component
public class PartnerPropertyResolver {
    public static final String FILE_NAME = "partner.json";
    public static final String FILE_DIR = System.getProperty("catalina.base")+"/app/config/testingtwo/partners";

    private volatile Map<String,Partner> partnerMap;

    public PartnerPropertyResolver() throws IOException {
        updatePartnerMap();
    }

    public Partner getPartner(String partnerId) {
        return partnerMap.get(partnerId);
    }

    public void updatePartnerMap() throws IOException {
        byte[] jsonData = Files.readAllBytes(Paths.get(FILE_DIR+"/"+FILE_NAME));
        ObjectMapper objectMapper = new ObjectMapper();
        this.partnerMap = objectMapper.readValue(jsonData, new TypeReference<Map<String,Partner>>(){});
    }

    public Map<String, Partner> getPartnerMap() {
        return Collections.unmodifiableMap(partnerMap);
    }
}
