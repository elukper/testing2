package com.testing.two.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerNotificationRequest {
    private static ObjectMapper objectMapper = new ObjectMapper();

    String guid;
    String id;
    String type;
    String message;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "PartnerNotificationRequest{" +
                    "guid='" + guid + '\'' +
                    ", id='" + id + '\'' +
                    ", type='" + type + '\'' +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
}
