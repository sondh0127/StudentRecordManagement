package com.project.javafx.ulti.mongoDBUtil;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.Document;

import java.net.UnknownHostException;
import java.util.Arrays;

public class MongoDBHandler {

    public final static String HOST = "localhost";
    public final static String USERNAME = "username";
    public final static String PASSWORD = "password";
    public final static String DB_NAME = "AppDev";
    public final static int PORT = 27017;

    public final static String STUDENT_COLL = "Students";
    public final static String COURSE_COLL = "Courses";
    public final static String MAJOR_COLL = "Majors";
    public final static String CLASS_COLL = "Classes";
    public final static String CREDIT_CLASS_COLL = "CreditClasses";
    public final static String USER_COLL = "Users";

    public static MongoCollection<Document> mongoLoadCollectionTo(String dbName, String collName) {
        try {
            MongoClient mongoClient = getMongoClient();
            MongoDatabase db = mongoClient.getDatabase(dbName);
            System.out.println("Successfully connected to secure database");
            return db.getCollection(collName);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

   // connect to MongoDB is not mandatory security.
   private static MongoClient getMongoClient_1() throws UnknownHostException {
       MongoClient mongoClient = new MongoClient(HOST, PORT);
       return mongoClient;
   }

   // connect to the DB MongoDB with security.
   private static MongoClient getMongoClient_2() throws UnknownHostException {
       MongoCredential credential = MongoCredential.createCredential(
               USERNAME, DB_NAME, PASSWORD.toCharArray());

       return new MongoClient(
               new ServerAddress(HOST, PORT), Arrays.asList(credential));
   }

   public static MongoClient getMongoClient() throws UnknownHostException {
       // Connect to MongoDB is not mandatory security.
       return getMongoClient_1();

       // You can replace by getMongoClient_2 ()
       // in case of connection to MongoDB with security.
   }
 
   private static void ping() throws UnknownHostException {
       MongoClient mongoClient = getMongoClient();
        
       System.out.println("List all DB:");

       // Get database names
       MongoIterable<String> dbNames = mongoClient.listDatabaseNames();
       for (String dbName : dbNames) {
           System.out.println("+ DB Name: " + dbName);
       }

       System.out.println("Done!");
   }
 
   // Test
   public static void main(String[] args) throws UnknownHostException {
       ping();
   }

    //    public void gSonLoad() {
//        System.out.println("Loaded file: " + filepath);
//        Gson gson = gsonCreator();
//        String fileData;
//        try {
//            fileData = new String(Files.readAllBytes(Paths.get(filepath)));
//            addAll(gson.fromJson(fileData, setToken()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void gSonSave() {
//        System.out.println("Saved file: " + filepath);
//        Gson gson = gsonCreator();
//        String json = gson.toJson(getObjectCollection(), setToken());
//        try {
//            OutputStream stream = new FileOutputStream(new File(filepath));
//            stream.write(json.getBytes());
//            stream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    public void mongoDBSave() {
//        MongoCollection coll = MongoDBHandler.mongoLoadCollectionTo(MongoDBHandler.DB_NAME, "Majors");
//        Gson gson = gsonCreator();
//        for (T obj : getObjectCollection()) {
//            String json = gson.toJson(obj);
//            Document doc = gson.fromJson(json, Document.class);
//            coll.insertOne(doc);
//        }
//    }

//    protected void mongoDBLoad(){
//        MongoCollection coll = MongoDBHandler.mongoLoadCollectionTo(MongoDBHandler.DB_NAME, "Majors");
//        MongoCursor<Document> cursor = coll.find().iterator();
//        Gson gson = gsonCreator();
//        try {
//            while (cursor.hasNext()) {
//                Document doc = cursor.next();
//                T obj = gson.fromJson(doc.toJson(), clazz);
//                objects.add(obj);
//            }
//        } finally {
//            cursor.close();
//        }
//    }
}