package com.htkfood.util;

import java.util.UUID;

public class StringUtil {
    public static String uuid(){
        return UUID.randomUUID().toString();
    }

    public static String simpleUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static boolean isBlank(String value){
        return null==value || "".equals(value.trim());
    }

    public static boolean equals(String str1, String str2, boolean nullAble){
        if(null==str1&& null==str2)
            return nullAble;
        if(null!=str1&&null!=str2)
            return str1.equals(str2);
        return false;
    }

    public static boolean equalsIgnoreCase(String str1, String str2, boolean nullAble){
        if(null==str1&& null==str2)
            return nullAble;
        if(null!=str1&&null!=str2)
            return str1.equalsIgnoreCase(str2);
        return false;
    }

   

    public static String getNotNullString(String value){
        if(null == value)
            return "";
        return value;
    }

    public static String hexTimeStr(){
        return Long.toHexString(System.currentTimeMillis()).toUpperCase();
    }

    public static String checkUTF8ContentLength(String content, int maxLength) {
        String result = content;
        try {
            if(result == null){
                return null;
            }

            StringBuffer buffer = new StringBuffer();
            int length = 0;
            char[] charArray = result.toCharArray();
            for (char c : charArray) {
                if(c<=127){
                    length++;
                }else if(c<=2047){
                    length=length+2;
                }else if(c<=65535){
                    length=length+3;
                }else if(c<=2097151){
                    length=length+4;
                }else if(c<=67108863){
                    length=length+5;
                }else if(c<=2147483647){
                    length=length+6;
                }else{
                    length=length+6;
                }
                if(length<=(maxLength-3)){
                    buffer.append(c);
                }else{
                    buffer.append("...");
                    break;
                }
            }
            return buffer.toString();
        } catch (Exception e) {
            return result;
        }
    }

   

  
}
