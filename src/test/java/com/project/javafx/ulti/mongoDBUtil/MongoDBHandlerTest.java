package com.project.javafx.ulti.mongoDBUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import com.project.javafx.model.*;
import com.project.javafx.repository.AnnualClassRepository;
import com.project.javafx.repository.CourseRepository;
import com.project.javafx.repository.StudentRepository;
import com.project.javafx.repository.UserRepository;
import com.project.javafx.ulti.AlertMaker;
import com.project.javafx.ulti.DateUtil;
import org.bson.Document;
import org.junit.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.mongodb.client.model.Filters.eq;

public class MongoDBHandlerTest {

    @Test
    public void testJPane() {
//        JOptionPane.showConfirmDialog(null, "hhelo", JComponent.TOOL_TIP_TEXT_KEY,
//                JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
        AlertMaker.getConfirmation("Hello", "JavaFXAlert");
    }

    public final static String HOST = "localhost";
    public final static int PORT = 27017;
    public final static String USERNAME = "username";
    public final static String PASSWORD = "password";
    public final static String DB_NAME = "AppDev";
    public final static String STUDENT_COLL = "Students";

    public final static String DB_NAME_TEST = "test";
    public final static String STUDENT_COLL_TEST = "javastuff";


    public static AnnualClass newClass = new AnnualClass("PHY", "Physic");
    public static CreditMajor comMajor = new CreditMajor("CM02", "Communications", 120, 20);
    public static CreditMajor csMajor = new CreditMajor("CS01", "Computer Science", 120, 20);
    public static AnnualStudent student = new AnnualStudent(20145454, "Son", "Do Hong", "Male", DateUtil.parse("27/10/1998"), "34534543534", "243@gmail.com", "VietNam", newClass);
    public static AnnualStudent student2 = new AnnualStudent(354634, "Son2", "Do 2Hong", "Male", DateUtil.parse("27/12/1998"), "567567", "245345343@gmail.com", "VietNam2", newClass);
    public static CreditStudent student3 = new CreditStudent(2131232, "Son", "Do Hong", "Male", DateUtil.parse("27/10/1996"), "34534543534", "243@gmail.com", "VietNam", csMajor);
    public static CreditStudent student4 = new CreditStudent(35463434, "Son3", "Do 23Hong", "Male", DateUtil.parse("27/12/1997"), "567567", "245345343@gmail.com", "VietNam2", comMajor);
    public static List<CreditCourse> csCatalogMajor = new ArrayList<>();
    public static List<CreditCourse> csCatalogMinor = new ArrayList<>();

    static {
        List<Course> coursesCatalog = new ArrayList<>();
        coursesCatalog.add(new Course("PHY130", "Physics I", 0.3));
        coursesCatalog.add(new Course("PHY132", "Physics I Lab", 0.3));
        coursesCatalog.add(new Course("PHY230", "Physics II", 0.3));
        coursesCatalog.add(new Course("PHY232", "Physics II Lab", 0.3));
        coursesCatalog.add(new Course("PHY245", "Physics III", 0.3));
        coursesCatalog.add(new Course("PHY246", "Physics III Lab", 0.3));
        coursesCatalog.add(new Course("CHE133", "College Chemistry I", 0.3));
        coursesCatalog.add(new Course("CHE134", "College Chemistry II", 0.3));
        coursesCatalog.add(new Course("CHE250", "Organic Chemistry I", 0.3));
        coursesCatalog.add(new Course("CHE251", "Organic Chemistry II", 0.3));
        coursesCatalog.add(new Course("BIO150", "Modern Biology I", 0.3));
        coursesCatalog.add(new Course("BIO152", "Modern Biology II", 0.3));
        coursesCatalog.add(new Course("ENG101", "Standard Freshman Composition", 0.3));
        coursesCatalog.add(new Course("ENG102", "Introduction to Literature", 0.3));
        coursesCatalog.add(new Course("ENG121", "Technical Writing", 0.3));

        newClass.setCoursesCatalog(coursesCatalog, AnnualStudent.YearOfStudy.FIRST_YEAR);
    }

    static {
        csCatalogMajor.add(new CreditCourse("CSC101", "Continuous mathematics", 2, 0.3));
        csCatalogMajor.add(new CreditCourse("CSC102", "Design and analysis of algorithms", 3, 0.3));
        csCatalogMajor.add(new CreditCourse("CSC103", "Digital systems", 2, 0.3));
        csCatalogMajor.add(new CreditCourse("CSC104", "Discrete mathematics", 2, 0.3));
        csCatalogMajor.add(new CreditCourse("CSC105", "Functional programming", 3, 0.3));
        csCatalogMajor.add(new CreditCourse("CSC106", "Imperative programming", 2, 0.3));
        csCatalogMajor.add(new CreditCourse("CSC107", "Introduction to formal proof", 2, 0.3));
        csCatalogMajor.add(new CreditCourse("CSC108", "Linear algebra", 2, 0.3));
        csCatalogMajor.add(new CreditCourse("CSC109", "Probability", 2, 0.3));
        //2nd year
        csCatalogMajor.add(new CreditCourse("CSC201", "Algorithms", 3, 0.3));
        csCatalogMajor.add(new CreditCourse("CSC202", "Compilers", 3, 0.3));
        csCatalogMajor.add(new CreditCourse("CSC203", "Concurrent programming", 2, 0.3));
        csCatalogMajor.add(new CreditCourse("CSC204", "Models of computation", 2, 0.3));

        csCatalogMinor.add(new CreditCourse("CSO205", "Computer architecture", 2, 0.3));
        csCatalogMinor.add(new CreditCourse("CSO206", "Computer graphics", 2, 0.3));
        csCatalogMinor.add(new CreditCourse("CSO207", "Computer networks", 2, 0.3));
        csCatalogMinor.add(new CreditCourse("CSO208", "Databases", 2, 0.3));
        csCatalogMinor.add(new CreditCourse("CSO209", "Intelligent systems", 2, 0.3));
        csCatalogMinor.add(new CreditCourse("CSO210", "Logic and proof", 2, 0.3));
        // 3rd year
        // 4th year

        csMajor.createMajorCatalog(csCatalogMajor);
        csMajor.createMinorCatalog(csCatalogMinor);

        comMajor.createMinorCatalog(csCatalogMajor);
        comMajor.createMajorCatalog(csCatalogMinor);
    }

    //    public static void main(String args[]) {
//        try {
//            MongoClient mongoClient = new MongoClient(HOST, PORT);
//            DB db = mongoClient.getDB(DB_NAME_TEST);
//            System.out.println("Successfully connected to secure database");
//            DBCollection coll = db.getCollection(STUDENT_COLL_TEST);
//
////            doDBs(coll);
////            doDBs2(coll);
////            doDBs3(coll);
////            doDBs0(coll);
////            doDBs4(coll);
//
////            doDBs01(coll);
////            doDBs41(coll);
//
//
////            DBCursor cursor = coll.find();
////            while (cursor.hasNext()) {
////                DBObject next = cursor.next();
////
////            }
//        } catch (Exception e) {
//            System.err.println(e.getClass().getName() + ": " +
//                    e.getMessage());
//        }
//    }
    //new api
    @Test
    public void createAdmin() {
        User user = new User("Admin", "adminpass");
        UserRepository.getInstance().save(user);
        Set<Student> students = StudentRepository.getInstance().getObjectCollection();

        for (Student s : students) {
            long id = s.getStudentID();
            String username = String.valueOf(id);
            UserRepository.getInstance().save(new User(username, username));
        }
    }
    @Test
    public void createCourse() {
        CourseRepository.getInstance().initCourses();
    }

    public static void main(String args[]) {
        try {


            AnnualClassRepository.getInstance().initSomeClass();
            Set<Student> students = new HashSet<>();
            MongoCollection<Document> coll = getDocumentMongoCollection(STUDENT_COLL);
//            doDBsSaveTest(coll);
            Gson gson = getGson();
            FindIterable<Document> cursor = coll.find(eq("educationSystem", "ANNUAL"));
//            Document query = new Document("educationSystem", "ANNUAL");
//            FindIterable<Document> cursor = coll.find(query);
            for (Document obj : cursor) {
                AnnualStudent s = gson.fromJson(obj.toJson(), AnnualStudent.class);
                students.add(s);
            }
            for (Student s : students) {
                if (s instanceof AnnualStudent) {
                    System.out.println("Annual: " + s);
                }
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " +
                    e.getMessage());
        }
    }

    private void doDBsSaveTest(MongoCollection<Document> coll) {
        Set<Student> students = new HashSet<>();
        students.add(student);
        students.add(student4);
        students.add(student2);
        students.add(student3);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        for (Student s : students) {
            String json = gson.toJson(s);
            Document doc = gson.fromJson(json, Document.class);
            coll.insertOne(doc);
        }




    }


    public static Gson getGson() {
        return new GsonBuilder().
                setPrettyPrinting().
                create();
    }

    public static MongoCollection<Document> getDocumentMongoCollection(String collectionName) {
        MongoClient mongoClient = new MongoClient(HOST, PORT);
        MongoDatabase db = mongoClient.getDatabase(DB_NAME);
        System.out.println("Successfully connected to secure database");
        return db.getCollection(collectionName);
    }

    private static void doDBs4(DBCollection coll) {
        DBObject query = new BasicDBObject("_id", "AnnualStudents");
        DBCursor cursor = coll.find(query);
        BasicDBList objs = (BasicDBList) cursor.next().get("list");

        DBObject query2 = new BasicDBObject("_id", "CreditStudents");
        DBCursor cursor2 = coll.find(query2);
        BasicDBList objs2 = (BasicDBList) cursor2.next().get("list");


        //create gson
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
//                .registerTypeAdapterFactory(setAdapter())
//                .serializeNulls()
//                .setExclusionStrategies(new StudentExclusionStrategy())
                .create();
        Set<Student> students = new HashSet<>();

        for (Object obj : objs) {
            AnnualStudent s = gson.fromJson(obj.toString(), AnnualStudent.class);
            students.add(s);
        }
        for (Object obj : objs2) {
            CreditStudent s = gson.fromJson(obj.toString(), CreditStudent.class);
            students.add(s);
        }

        for (Student student1 : students) {
            if (student1 instanceof AnnualStudent)
                System.out.println(student1);
            else if (student1 instanceof CreditStudent) {
                CreditMajor major = ((CreditStudent) student1).getCreditMajor();
                List<CreditCourse> catalog = major.getMajorCatalog();

                for (CreditCourse creditCourse : catalog) {
                    System.out.println(creditCourse.getCourseName());
                }
            }
        }
    }

    private static void doDBs41(DBCollection coll) {
        DBObject query = new BasicDBObject("educationSystem", "ANNUAL");
        DBCursor cursor = coll.find(query);

        DBObject query2 = new BasicDBObject("educationSystem", "CREDIT");
        DBCursor cursor2 = coll.find(query2);

        //create gson
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
//                .registerTypeAdapterFactory(setAdapter())
//                .serializeNulls()
//                .setExclusionStrategies(new StudentExclusionStrategy())
                .create();
        Set<Student> students = new HashSet<>();

        for (DBObject obj : cursor) {
            AnnualStudent s = gson.fromJson(obj.toString(), AnnualStudent.class);
            students.add(s);
        }
        for (DBObject obj : cursor2) {
            CreditStudent s = gson.fromJson(obj.toString(), CreditStudent.class);
            students.add(s);
        }

        for (Student student1 : students) {
            if (student1 instanceof AnnualStudent)
                System.out.println("Annual: " + student1);
            else if (student1 instanceof CreditStudent) {
                System.out.println("Credit: " + student1);
//                CreditMajor major = ((CreditStudent) student1).getCreditMajor();
//                List<CreditCourse> catalog = major.getMajorCatalog();
//
//                for (CreditCourse creditCourse : catalog) {
//                    System.out.println(creditCourse.getCourseName());
//                }
            }
        }
    }

    private static void doDBs0(DBCollection coll) {
        Set<Student> students = new HashSet<>();
        students.add(student);
        students.add(student4);
        students.add(student2);
        students.add(student3);
        Set<DBObject> dbStudents = new HashSet<>();

        //create gson
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
//                .registerTypeAdapterFactory(setAdapter())
//                .serializeNulls()
//                .setExclusionStrategies(new StudentExclusionStrategy())
                .create();


        DBObject annualList = new BasicDBList();
        DBObject creditList = new BasicDBList();
        for (Student s : students) {
            String json = gson.toJson(s);
            DBObject dbObject = (DBObject) JSON.parse(json);
            if (s instanceof AnnualStudent) {
                ((BasicDBList) annualList).add(dbObject);
            } else if (s instanceof CreditStudent) {
                ((BasicDBList) creditList).add(dbObject);
            }


        }

        DBObject doc = new BasicDBObject("_id", "AnnualStudents")
                .append("list", annualList);
        DBObject doc1 = new BasicDBObject("_id", "CreditStudents")
                .append("list", creditList);
        coll.insert(doc);
        coll.insert(doc1);
    }

    private static void doDBs01(DBCollection coll) {
        Set<Student> students = new HashSet<>();
        students.add(student);
        students.add(student4);
        students.add(student2);
        students.add(student3);
        Set<DBObject> dbStudents = new HashSet<>();

        //create gson
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
//                .serializeNulls()
//                .setExclusionStrategies(new StudentExclusionStrategy())
                .create();


        BasicDBList studentDB = new BasicDBList();
        for (Student s : students) {
            String json = gson.toJson(s);
            DBObject dbObject = (DBObject) JSON.parse(json);
            coll.insert(dbObject);
        }
    }

    private static void doDBs3(DBCollection coll) {
        DBObject doc = new BasicDBObject("_id", "12345678")
                .append("name", "jim")
                .append("age", 47)
                .append("info", new BasicDBObject("email", "owen@mail.com").
                        append("phone", "111-222-333"));
        coll.insert(doc);
    }

    private static void doDBs2(DBCollection coll) {
        List<DBObject> kids = new ArrayList<>();
        kids.add(new BasicDBObject("name", "mike"));
        kids.add(new BasicDBObject("name", "faye"));
        DBObject doc = new BasicDBObject("name", "john")
                .append("age", 35)
                .append("kids", kids)
                .append("info",
                        new BasicDBObject("email", "john@mail.com")
                                .append("phone", "876-134-667"));
        coll.insert(doc);
    }

    private static void doDBs(DBCollection coll) {
        DBObject doc = new BasicDBObject("name", "owen")
                .append("age", 47)
                .append("email", "owen@mail.com")
                .append("phone", "111-222-333");
        coll.insert(doc);
    }

}