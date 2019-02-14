package com.htkfood.util;

import java.util.LinkedHashMap;
import java.util.Map;

import com.htkfood.exception.CommonException;
import com.htkfood.exception.CommonRuntimeException;

public class ResultHelper {

    public static Map<String, Object> success() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("code", "success");
        return map;
    }

    public static Map<String, Object> fail(String code, String errorMsg) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("code",code);
        map.put("errorMsg", errorMsg);
        return map;
    }
    
    public static String formatException(CommonException exception) {
        return String.format("code=%s,errorMsg=%s",exception.getCode(), exception.getErrorMessage());
    }

    public static String formatException(CommonRuntimeException exception) {
        return String.format("code=%s,errorMsg=%s",exception.getCode(), exception.getErrorMessage());
    }

  
}
