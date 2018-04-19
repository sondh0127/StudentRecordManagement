import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.project.javafx.model.*;
import com.project.javafx.repository.AnnualClassRepository;
import com.project.javafx.repository.CourseRepository;
import com.project.javafx.repository.CreditMajorRepository;
import com.project.javafx.repository.StudentRepository;
import com.project.javafx.ulti.DateUtil;
import com.project.javafx.ulti.RuntimeTypeAdapterFactory;
import de.jensd.fx.glyphs.GlyphsStyle;
import de.jensd.fx.glyphs.control.GlyphCheckBox;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.octicons.OctIcon;
import de.jensd.fx.glyphs.octicons.OctIconView;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class TestStudent {

    @Test
    public void testRegisterCourse() {
        CreditMajorRepository.getInstance().initSomeMajor();
        AnnualClassRepository.getInstance().initSomeClass();
        CourseRepository.getInstance().initCourses();
        StudentRepository.getInstance().gSonLoad();

        Student student = StudentRepository.getInstance().findById(String.valueOf(20151228));
        if (student != null) {
            if (student instanceof CreditStudent) {
                ((CreditStudent) student).registerCourse("CSC103");
                ((CreditStudent) student).updateStudentResult("CSC103", 8.5, 9.0);
                StudentResult csc103GR = student.getGradeResult("CSC103");
                Assert.assertTrue(csc103GR.getFinalPoint() == 9.0);
                System.out.println(csc103GR.getScore());
            }
        }

    }

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
