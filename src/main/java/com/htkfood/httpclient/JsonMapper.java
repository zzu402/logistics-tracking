package com.htkfood.httpclient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Map;

/**
 * Created by hongshuiqiao on 2017/6/14.
 */
public class JsonMapper {
    private static Logger logger = LoggerFactory.getLogger(JsonMapper.class);
    private static ThreadLocal<JsonMapper> nonEmpty = new ThreadLocal<JsonMapper>() {
        @Override
        protected JsonMapper initialValue() {
            return new JsonMapper(JsonInclude.Include.NON_EMPTY);
        }
    };
    private static ThreadLocal<JsonMapper> nonDefault = new ThreadLocal<JsonMapper>() {
        @Override
        protected JsonMapper initialValue() {
            return new JsonMapper(JsonInclude.Include.NON_DEFAULT);
        }
    };
    private static ThreadLocal<JsonMapper> defaultMapper = new ThreadLocal<JsonMapper>() {
        @Override
        protected JsonMapper initialValue() {
            return new JsonMapper();
        }
    };

    private ObjectMapper mapper;

    public JsonMapper() {
        this(null);
    }

    public JsonMapper(JsonInclude.Include include) {
        mapper = new ObjectMapper();
        // 设置输出时包含属性的风格

        if (include != null) {
            mapper.setSerializationInclusion(include);
        }

        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S"));
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public static JsonMapper defaultMapper() {
        return defaultMapper.get();
    }

    /**
     * 创建只输出非Null且非Empty(如List.isEmpty)的属性到Json字符串的Mapper,建议在外部接口中使用.
     */
    public static JsonMapper nonEmptyMapper() {
        return nonEmpty.get();
    }

    /**
     * 创建只输出初始值被改变的属性到Json字符串的Mapper, 最节约的存储方式，建议在内部接口中使用。
     */
    public static JsonMapper nonDefaultMapper() {
        return nonDefault.get();
    }

    /**
     * Object可以是POJO，也可以是Collection或数组。
     * <p>
     * 如果对象为Null, 返回"null".
     * <p>
     * 如果集合为空集合, 返回"[]".
     */
    public String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            logger.warn("write to json string error:" + object, e);
            return null;
        }
    }

    public String toFormatJson(Object object) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (IOException e) {
            logger.warn("write to json string error:" + object, e);
            return null;
        }
    }

    /**
     * 默认转为LinkedHashMap,ArrayList等
     * @param jsonString
     * @return
     */
    public Object fromJson(String jsonString) {
        JavaType javaType = constructType(Object.class);
        return fromJson(jsonString, javaType);
    }

    /**
     * 反序列化POJO或简单Collection如List<String>.
     * <p>
     * <p>
     * <p>
     * 如果JSON字符串为Null或"null"字符串, 返回Null.
     * <p>
     * 如果JSON字符串为"[]", 返回空集合.
     * <p>
     * <p>
     * <p>
     * 如需反序列化复杂Collection如List<MyBean>, 请使用fromJson(String, JavaType)
     *
     * @see #fromJson(String, JavaType)
     */
    public <T> T fromJson(String jsonString, Class<T> clazz) {
        if (jsonString==null||jsonString.equals("")) {
            return null;
        }

        try {
            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            logger.warn("parse json string error:" + jsonString, e);
            return null;
        }
    }

    /**
     * 反序列化复杂Collection如List<Bean>, constructCollectionType()或contructMapType()构造类型, 然后调用本函数.
     *
     * @see #constructCollectionType(Class, Class)
     */
    @SuppressWarnings("unchecked")
    public <T> T fromJson(String jsonString, JavaType javaType) {
        if (jsonString==null||jsonString.equals("")) {
            return null;
        }

        try {
            return (T) mapper.readValue(jsonString, javaType);
        } catch (Exception e) {
            logger.error("parse json string error:" + jsonString, e);
            return null;
        }
    }

    /**
     * 构造数组类型.
     */
    public JavaType constructArrayType(Class<?> elementType) {
        return getTypeFactory().constructArrayType(elementType);
    }

    public TypeFactory getTypeFactory() {
        return mapper.getTypeFactory();
    }

    /**
     * 构造数组类型.
     */
    public JavaType constructArrayType(JavaType elementType) {
        return getTypeFactory().constructArrayType(elementType);
    }

    /**
     * 构造Collection类型.
     */
    public JavaType constructCollectionType(Class<? extends Collection> collectionClass, Class<?> elementClass) {
        return getTypeFactory().constructCollectionType(collectionClass, elementClass);
    }

    /**
     * 构造Collection类型.
     */
    public JavaType constructCollectionType(Class<? extends Collection> collectionClass, JavaType elementType) {
        return getTypeFactory().constructCollectionType(collectionClass, elementType);
    }

    /**
     * 构造类型
     * @param type
     * @return
     */
    public JavaType constructType(Type type) {
        return getTypeFactory().constructType(type);
    }

    /**
     * 构造Map类型.
     */
    public JavaType constructMapType(Class<? extends Map> mapClass, Class<?> keyClass, Class<?> valueClass) {
        return getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
    }

    public JavaType constructMapType(Class<? extends Map> mapClass, JavaType keyType, JavaType valueType) {
        return getTypeFactory().constructMapType(mapClass, keyType, valueType);
    }

    /**
     * 當JSON裡只含有Bean的部分屬性時，更新一個已存在Bean，只覆蓋該部分的屬性.
     */
    public <T> T update(String jsonString, T object) {
        try {
            return mapper.readerForUpdating(object).readValue(jsonString);
        } catch (JsonProcessingException e) {
            logger.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
        } catch (IOException e) {
            logger.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
        }
        return null;
    }

    /**
     * 輸出JSONP格式數據.
     */
    public String toJsonP(String functionName, Object object) {
        return toJson(new JSONPObject(functionName, object));
    }

    /**
     * 設定是否使用Enum的toString函數來讀寫Enum,
     * <p>
     * 為False時時使用Enum的name()函數來讀寫Enum, 默認為False.
     * <p>
     * 注意本函數一定要在Mapper創建後, 所有的讀寫動作之前調用.
     */
    public void enableEnumUseToString() {
        mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
    }

    /**
     * 取出Mapper做进一步的设置或使用其他序列化API.
     */
    public ObjectMapper getMapper() {
        return mapper;
    }
}
