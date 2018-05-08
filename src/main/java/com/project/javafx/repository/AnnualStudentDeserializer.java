package com.project.javafx.repository;

import com.google.gson.*;
import com.project.javafx.model.AnnualStudent;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class AnnualStudentDeserializer implements JsonDeserializer {

    private static final ThreadLocal<Map<Long, AnnualStudent>> cache = new ThreadLocal<Map<Long, AnnualStudent>>() {
        @Override
        protected Map<Long, AnnualStudent> initialValue() {
            return new HashMap<>();
        }
    };

    @Override
    public AnnualStudent deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {
        if (json.isJsonPrimitive()) {
            final JsonPrimitive primitive = json.getAsJsonPrimitive();
            AnnualStudent student = cache.get().get(primitive.getAsLong());
            int size = cache.get().size();
            return student;

        }
        // The whole object is available
        if (json.isJsonObject()) {
            JsonObject jsonObject = json.getAsJsonObject();
            AnnualStudent student = new Gson().fromJson(jsonObject , AnnualStudent.class);
            cache.get().put(student.getStudentID(), student);
            return student;
        }
        throw new JsonParseException("Unexpected JSON type: " + json.getClass().getSimpleName());
    }
}