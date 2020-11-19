package com.jt.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * linkHashMap转  自定义对象
     * @param lst List<LinkHashMap>
     * @param responseType 要转的目标类型
     * @param <T> 类型
     * @return 转换为目标类型后的列表
     */
    public static <T> List<T> linkedMapTypeListToObjectList(List<T> lst,Class<T> responseType){
        List<T> list = new ArrayList<>();
        for (T t : lst){
            list.add((toObject(toJSON(t),responseType)));
        }
        return list;
    }


}
