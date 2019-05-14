package com.testing.two.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerNotificationResponse {
    private static ObjectMapper objectMapper = new ObjectMapper();

    int status;
    String message;
    String error;
    String path;
    Long timestamp;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        try{
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "PartnerNotificationResponse{" +
                    "status=" + status +
                    ", message='" + message + '\'' +
                    ", error='" + error + '\'' +
                    ", path='" + path + '\'' +
                    ", timestamp=" + timestamp +
                    '}';
        }
    }
}
