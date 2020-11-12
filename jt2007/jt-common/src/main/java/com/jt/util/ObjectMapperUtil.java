package com.jt.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    /**
     * 将对象转化为JSON
     */
    public static String toJSON(Object target){
        try {
            return MAPPER.writeValueAsString(target);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    /**
     * 将JSON转化为对象
     */
    public static <T> T toObject(String json,Class<T> targetClass){
        try {
            return MAPPER.readValue(json, targetClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
