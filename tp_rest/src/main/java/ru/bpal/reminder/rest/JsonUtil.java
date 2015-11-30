package ru.bpal.reminder.rest;

import com.google.gson.Gson;
import spark.ResponseTransformer;

/**
 * Created by serg on 28.10.15.
 */
public class JsonUtil {

    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }

    public static ResponseTransformer json() {
        return JsonUtil::toJson;
    }

    public static Object fromJson(String jsonString, Class clazz) {
        return new Gson().fromJson(jsonString, clazz);
    }
}
