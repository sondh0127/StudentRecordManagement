import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.project.javafx.model.AnnualStudent;
import com.project.javafx.model.CreditMajor;
import com.project.javafx.model.CreditStudent;
import com.project.javafx.model.Student;
import com.project.javafx.ulti.gsonUtil.RuntimeTypeAdapterFactory;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class TestStudent {

    public class TestExclusionStrategies implements ExclusionStrategy {

        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            if (f.getDeclaringClass() == Student.class) if (f.getName().equals("firstName")) return true;
            if (f.getDeclaringClass() == CreditMajor.class) {
                if (!f.getName().equals("titleMajor")) return true;
            }
            return false;
        }

        @Override
        public boolean shouldSkipClass(Class<?> aClass) {
            return false;
        }
    }

    private RuntimeTypeAdapterFactory<Student> setAdapter() {
        RuntimeTypeAdapterFactory<Student> adapter =
                RuntimeTypeAdapterFactory
                        .of(Student.class, "type")
                        .registerSubtype(CreditStudent.class, CreditStudent.class.getSimpleName())
                        .registerSubtype(AnnualStudent.class, AnnualStudent.class.getSimpleName());
        return adapter;
    }

    private Type setToken() {
        return new TypeToken<ArrayList<Student>>() {}.getType();
    }
    @Test
    public void loadFile() {
        // Get Gson object
        Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapterFactory(setAdapter()).create();

        // read JSON file data as String
        String fileData;
        try {
            fileData = new String(Files.readAllBytes(Paths.get("src/main/resources/public/temp.json")));
            // parse json string to object\
            Type pType = setToken();
            ArrayList<Student> students = gson.fromJson(fileData, pType);
            for (Student student : students) {
                System.out.println(student.getClass());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
