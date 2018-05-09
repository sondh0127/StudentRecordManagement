package com.project.javafx.ulti.gsonUtil;

import com.google.gson.*;
import com.project.javafx.model.CreditCourse;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class CreditCourseDeserializer implements JsonDeserializer {

    private static final ThreadLocal<Map<String, CreditCourse>> cache = ThreadLocal.withInitial(HashMap::new);

    @Override
    public CreditCourse deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {
        if (json.isJsonPrimitive()) {
            final JsonPrimitive primitive = json.getAsJsonPrimitive();
            CreditCourse creditCourse = cache.get().get(primitive.getAsString());
            return creditCourse;
        }
        // The whole object is available
        if (json.isJsonObject()) {
            JsonObject jsonObject = json.getAsJsonObject();
            CreditCourse creditCourse = new Gson().fromJson(jsonObject , CreditCourse.class);
            cache.get().put(creditCourse.getCourseCode(), creditCourse);
            return creditCourse;
        }
        throw new JsonParseException("Unexpected JSON type: " + json.getClass().getSimpleName());
    }
}