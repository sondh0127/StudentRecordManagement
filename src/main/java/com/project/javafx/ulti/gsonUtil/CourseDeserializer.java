package com.project.javafx.ulti.gsonUtil;

import com.google.gson.*;
import com.project.javafx.model.Course;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class CourseDeserializer implements JsonDeserializer {

    private static final ThreadLocal<Map<String, Course>> cache = ThreadLocal.withInitial(HashMap::new);

    @Override
    public Course deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {
        if (json.isJsonPrimitive()) {
            final JsonPrimitive primitive = json.getAsJsonPrimitive();
            Course course = cache.get().get(primitive.getAsString());
            return course;
        }
        // The whole object is available
        if (json.isJsonObject()) {
            JsonObject jsonObject = json.getAsJsonObject();
            Course course = new Gson().fromJson(jsonObject , Course.class);
            cache.get().put(course.getCourseCode(), course);
            return course;
        }
        throw new JsonParseException("Unexpected JSON type: " + json.getClass().getSimpleName());
    }
}