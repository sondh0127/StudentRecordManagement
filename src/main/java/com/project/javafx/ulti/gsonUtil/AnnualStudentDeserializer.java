package com.project.javafx.ulti.gsonUtil;

import com.google.gson.*;
import com.project.javafx.model.AnnualStudent;
import com.project.javafx.model.Course;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class AnnualStudentDeserializer implements JsonDeserializer {

    private static final ThreadLocal<Map<Long, AnnualStudent>> cache = ThreadLocal.withInitial(HashMap::new);

    @Override
    public AnnualStudent deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {
        if (json.isJsonPrimitive()) {
            final JsonPrimitive primitive = json.getAsJsonPrimitive();
            AnnualStudent student = cache.get().get(primitive.getAsLong());
            return student;

        }
        // The whole object is available
        if (json.isJsonObject()) {
            JsonObject jsonObject = json.getAsJsonObject();
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Course.class, new CourseDeserializer())
                    .create();
            AnnualStudent student = gson.fromJson(jsonObject , AnnualStudent.class);
            cache.get().put(student.getStudentID(), student);
            return student;
        }
        throw new JsonParseException("Unexpected JSON type: " + json.getClass().getSimpleName());
    }
}