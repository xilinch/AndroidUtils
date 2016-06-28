package com.xiaocoder.android_xcfw.json;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.xiaocoder.android_xcfw.io.XCLog;
import com.xiaocoder.android_xcfw.util.UtilString;

import java.io.StringReader;
import java.lang.reflect.Type;

/**
 * @author songxin
 */
public class SXGsonParse {
    private static Gson gson = null;

    private static Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    /**
     * Transfer the java bean to JSON string.
     * Notice the bean mast can be serialize.
     *
     * @param bean
     * @return
     */
    public static String toJson(Object bean) {
        return getGson().toJson(bean);
    }

    /**
     * 将bean转成数组形式
     *
     * @param bean
     * @param typeOfSrc Type typeOfSrc = new TypeToken<ArrayList<Object>>(){}.getType();
     * @return
     */
    public static String toJson(Object bean, Type typeOfSrc) {
        return getGson().toJson(bean, typeOfSrc);
    }


    /**
     * parse JSON to java bean
     *
     * @param json
     * @param objClass
     * @return
     */
    public static <T> Object parseJson(String json, Class<T> objClass) {
        if (!UtilString.isBlank(json)) {
            try {
                return getGson().fromJson(json, objClass);
            } catch (JsonSyntaxException e) {
                XCLog.e("Parse json error: " + e.getMessage());
            }
        }
        return null;
    }

    /**
     * @param json
     * @param typeOfT Type typeOfSrc = new TypeToken<ArrayList<Object>>(){}.getType();
     * @return
     */
    public static <T> Object fromJson(String json, Type typeOfT) {
        if (!UtilString.isBlank(json)) {
            try {
                return getGson().fromJson(json, typeOfT);
            } catch (JsonSyntaxException e) {
                XCLog.e("Parse json error: " + e.getMessage());
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T fromJson(String json, Class<T> type) {
        if (json == null) {
            return null;
        }
        JsonReader reader = new JsonReader(new StringReader(json));
        reader.setLenient(true);
        return (T) getGson().fromJson(reader, TypeToken.get(type).getType());
    }
}
