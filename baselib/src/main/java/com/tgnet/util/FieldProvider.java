package com.tgnet.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;

/**
 * Created by fan-gk on 2017/4/24.
 */
public class FieldProvider extends HashMap<String, String> {

    private Gson mGson;

    public FieldProvider() {
        mGson = new GsonBuilder()
                .serializeNulls().setPrettyPrinting() // 对json结果格式化.
                .create();
    }

    public void putField(Object value) {
        JsonElement element = mGson.toJsonTree(value);
        if (element.isJsonObject()) {
            for (Entry<String, JsonElement> entry : ((JsonObject) element).entrySet()) {
                putField(entry.getKey(), entry.getValue());
            }
        }
    }


    public void putField(String key, Object value) {
        JsonElement element = mGson.toJsonTree(value);
        if (element.isJsonObject()) {
            putField(key, (JsonObject) element);
        } else if (element.isJsonArray()) {
            putField(key, (JsonArray) element);
        } else {
            putField(key, element);
        }
    }


    private void putField(String key, JsonObject object) {
        for (Entry<String, JsonElement> entry : object.entrySet()) {
            JsonElement element = entry.getValue();
            String k = key + "[" + entry.getKey() + "]";
            if (element.isJsonObject()) {
                putField(k, (JsonObject) element);
            } else if (element.isJsonArray()) {
                putField(k, (JsonArray) element);
            } else {
                putField(k, element);
            }
        }
    }

    private void putField(String key, JsonArray array) {
        for (int i = 0; i < array.size(); i++) {
            JsonElement element = array.get(i);
            String k = key + "[" + i + "]";
            if (element.isJsonObject()) {
                putField(k, (JsonObject) element);
            } else if (element.isJsonArray()) {
                putField(k, (JsonArray) element);
            } else {
                putField(k, element);
            }
        }
    }

    private void putField(String key, JsonElement element) {
        if (element.isJsonNull()) {
            this.put(key, "");
        } else {
            this.put(key, element.getAsString());
        }
    }
}
