package com.project.javafx.ulti.gsonUtil;

import com.google.gson.*;
import com.project.javafx.model.CreditCourse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreditCourseDeserializer implements JsonDeserializer {

    private static final ThreadLocal<Map<String, CreditCourse>> cache = ThreadLocal.withInitial(HashMap::new);

    @Override
    public CreditCourse deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {
        if (json.isJsonPrimitive()) {
            final JsonPrimitive primitive = json.getAsJsonPrimitive();
            return getOrCreate(primitive.getAsString());
        }
        // The whole object is available
        if (json.isJsonObject()) {
            JsonObject jsonObject = json.getAsJsonObject();

            final CreditCourse creditCourse = getOrCreate(jsonObject.get("courseCode").getAsString());
            creditCourse.setCourseName(jsonObject.get("courseName").getAsString());
            creditCourse.setScale(jsonObject.get("scale").getAsDouble());
            creditCourse.setCreditHours(jsonObject.get("creditHours").getAsInt());

            List<CreditCourse> courses = new ArrayList<>();
            JsonArray prerequisiteCourse = jsonObject.get("prerequisiteCourse").getAsJsonArray();
            for (JsonElement jsonElement : prerequisiteCourse) {
                CreditCourse course = context.deserialize(jsonElement, CreditCourse.class);
                courses.add(course);
            }
            creditCourse.setPrerequisiteCourse(courses);

            return creditCourse;
        }
        throw new JsonParseException("Unexpected JSON type: " + json.getClass().getSimpleName());
    }

    private CreditCourse getOrCreate(final String id) {
        CreditCourse course = cache.get().get(id);
        if (course == null) {
            course = new CreditCourse();
            course.setCourseCode(id);
            cache.get().put(id, course);
        }
        return course;
    }
}