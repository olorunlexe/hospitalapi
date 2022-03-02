package com.assessment.hospitalapi.helpers;

import java.util.Map;

public class MapUtil {

    public static String getStringValue(Map<String, Object> body, String key) {
        if(key == null || body == null || body.isEmpty() || body.get(key) == null) return "";
        return body.get(key).toString();
    }

    public static long getLongValue(Map<String, Object> body, String key) {
        if(key == null || body == null || body.isEmpty() || body.get(key) == null || body.get(key).toString().isEmpty()) return 0;
        return (long) Double.parseDouble(body.get(key).toString());
    }

    public static Map<String, Object> getMap(Map<String, Object> body, String key) {
        if(key == null || body == null || body.isEmpty() || !body.containsKey(key)) return null;
        return (Map<String, Object>) body.get(key);
    }

    public static boolean getBooleanValue(Map<String, Object> body, String key) {
        if(key == null || body == null || body.isEmpty() || body.get(key) == null || body.get(key).toString().isEmpty()) return false;
        return Boolean.parseBoolean(body.get(key).toString());
    }

    public static int getIntegerValue(Map<String, Object> body, String key) {
        if(key == null || body == null || body.isEmpty() || body.get(key) == null || body.get(key).toString().isEmpty()) return 0;
        return (int) Double.parseDouble(body.get(key).toString());
    }
}
