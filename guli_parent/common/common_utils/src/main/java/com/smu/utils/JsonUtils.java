package com.smu.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class JsonUtils {

    private JsonUtils(){};

    public static String printJsonObj(Object object){
        ObjectMapper om = new ObjectMapper();
        try {
            String str = om.writeValueAsString(object);
            return str;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String,String> getMapFromJson(String string){
        ObjectMapper om = new ObjectMapper();
        try {
            Map<String,String> map = om.readValue(string, Map.class);
            return map;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
