package pl.kurs.zad1.models;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public enum ObjectMapperHolder {

    INSTANCE;


    private final ObjectMapper objectMapper;

    ObjectMapperHolder() {
        this.objectMapper = createObjectMapper();
    }

    public static ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }
}
