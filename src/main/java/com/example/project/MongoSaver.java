package com.example.project;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MongoSaver {
    static Logger logger = LoggerFactory.getLogger(MongoSaver.class);
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
            logger.info("XXXXXXXXXXXXXXXXXX ERROR WHILE SAVING TO MONGO XXXXXXXXXXXXXXXXXXXXXXXXXX");
            mongoException.printStackTrace();
            success = false;
        }
        return success;

    }

    public static void main(String... args) {
        logger.info("Hello World");
    }

}
