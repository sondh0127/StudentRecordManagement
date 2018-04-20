package com.project.javafx.ulti.mongoDBUtil;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

public class MongoUtils {

    public final static String HOST = "localhost";
    public final static String USERNAME = "username";
    public final static String PASSWORD = "password";
    public final static String DB_NAME = "AppDev";
    public final static int PORT = 27017;

    public final static String ANUAL_STUDENT_COLL = "AnnualStudents";
    public final static String CREDIT_STUDENT_COLL = "CreditStudents";
    public final static String COURSE_COLL = "Courses";
    public final static String MAJOR_COLL = "Majors";
    public final static String CLASS_COLL = "Classes";

    public static MongoCollection<Document> mongoLoadCollectionTo(String dbName, String collName) {
        try {
            MongoClient mongoClient = MongoUtils.getMongoClient();
            MongoDatabase db = mongoClient.getDatabase(dbName);
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
       MongoCredential credential = MongoCredential.createMongoCRCredential(
               USERNAME, DB_NAME, PASSWORD.toCharArray());
 
       MongoClient mongoClient = new MongoClient(
               new ServerAddress(HOST, PORT), Arrays.asList(credential));
       return mongoClient;
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
       List<String> dbNames = mongoClient.getDatabaseNames();
       for (String dbName : dbNames) {
           System.out.println("+ DB Name: " + dbName);
       }
 
       System.out.println("Done!");
   }
 
   // Test
   public static void main(String[] args) throws UnknownHostException {
       ping();
   }
}