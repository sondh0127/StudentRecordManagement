package com.project.javafx.ulti.gsonUtil;

import com.google.gson.*;
import com.project.javafx.model.CreditStudent;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class CreditStudentDeserializer implements JsonDeserializer {

    private static final ThreadLocal<Map<Long, CreditStudent>> cache = ThreadLocal.withInitial(HashMap::new);

    @Override
    public CreditStudent deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {
        if (json.isJsonPrimitive()) {
            final JsonPrimitive primitive = json.getAsJsonPrimitive();
            CreditStudent student = cache.get().get(primitive.getAsLong());
            return student;
        }
        // The whole object is available
        if (json.isJsonObject()) {
            JsonObject jsonObject = json.getAsJsonObject();
            CreditStudent student = new Gson().fromJson(jsonObject , CreditStudent.class);
            cache.get().put(student.getStudentID(), student);
            return student;
        }
        throw new JsonParseException("Unexpected JSON type: " + json.getClass().getSimpleName());
    }
}