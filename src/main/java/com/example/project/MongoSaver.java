package com.example.project;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;

public class MongoSaver {

    public static boolean saveEmail(String to, String from, String subject, String text, Boolean html) {
        String userName = "YOUR NAME";
        String password = "YOUR PASS";
        String database = "YOUR DB";

        MongoCredential credential = MongoCredential.createCredential(userName, database, password.toCharArray());

        boolean success = true;

        try (MongoClient mongoClient = new MongoClient(new ServerAddress("YOUR HOST", 27939), credential, MongoClientOptions.builder().build())) {

            MongoDatabase db = mongoClient.getDatabase(database);

            MongoCollection<Document> c = db.getCollection("email");

            Document doc = new Document("to", to)
                    .append("from", from)
                    .append("subject", subject)
                    .append("text", text)
                    .append("asHtml", html);
            c.insertOne(doc);
        } catch (MongoException mongoException) {
            System.out.println("XXXXXXXXXXXXXXXXXX ERROR WHILE SAVING TO MONGO XXXXXXXXXXXXXXXXXXXXXXXXXX");
            mongoException.printStackTrace();
            success = false;
        }

        return success;

    }

    public static void main(String... args) throws UnknownHostException {
        // To connect wercker and sonar!
        Logger logger = LoggerFactory.getLogger(MongoSaver.class);
        logger.info("Hello World");
        //System.out.println("test");
    }

}
