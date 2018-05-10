package com.project.javafx.ulti.gsonUtil;

import com.google.gson.*;
import com.project.javafx.model.AnnualClass;
import com.project.javafx.model.AnnualStudent;
import com.project.javafx.model.Course;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class AnnualClassDeserializer implements JsonDeserializer {

    private static final ThreadLocal<Map<String, AnnualClass>> cache = ThreadLocal.withInitial(HashMap::new);

    @Override
    public AnnualClass deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {
        if (json.isJsonPrimitive()) {
            final JsonPrimitive primitive = json.getAsJsonPrimitive();
            AnnualClass annualClass = cache.get().get(primitive.getAsString());
            return annualClass;

        }
        // The whole object is available
        if (json.isJsonObject()) {
            JsonObject jsonObject = json.getAsJsonObject();
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(AnnualStudent.class, new AnnualStudentDeserializer())
                    .registerTypeAdapter(Course.class, new CourseDeserializer())
                    .create();
            AnnualClass annualClass = gson.fromJson(jsonObject , AnnualClass.class);

            for (AnnualStudent student : annualClass.getStudents()) {
                student.setAnnualClass(annualClass);
            }
            cache.get().put(annualClass.getClassCode(), annualClass);
            return annualClass;
        }
        throw new JsonParseException("Unexpected JSON type: " + json.getClass().getSimpleName());
    }
}