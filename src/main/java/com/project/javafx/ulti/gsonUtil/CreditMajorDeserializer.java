package com.project.javafx.ulti.gsonUtil;

import com.google.gson.*;
import com.project.javafx.model.CreditMajor;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class CreditMajorDeserializer implements JsonDeserializer {

    private static final ThreadLocal<Map<String, CreditMajor>> cache = ThreadLocal.withInitial(HashMap::new);

    @Override
    public CreditMajor deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {
        if (json.isJsonPrimitive()) {
            final JsonPrimitive primitive = json.getAsJsonPrimitive();
            CreditMajor student = cache.get().get(primitive.getAsString());
            return student;

        }
        // The whole object is available
        if (json.isJsonObject()) {
            JsonObject jsonObject = json.getAsJsonObject();
            CreditMajor major = new Gson().fromJson(jsonObject , CreditMajor.class);
            cache.get().put(major.getMajorCode(), major);
            return major;
        }
        throw new JsonParseException("Unexpected JSON type: " + json.getClass().getSimpleName());
    }
}