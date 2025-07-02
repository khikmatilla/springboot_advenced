package com.myproject.springboot_advenced;


import com.mongodb.client.*;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class SpringbootAdvencedApplication {

    public static void main(String[] args) {

        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017/?directConnection=true");
        MongoDatabase db = mongoClient.getDatabase("pdpjava");
        MongoCollection<Document> collection = db.getCollection("posts");

        //insertOne(collection);
        //insertMany(collection);

        FindIterable<Document> documents = collection.find();
        for (Document document : documents) {
           // System.out.println("document.getString(\"title\") = " + document.getString("title"));
            //System.out.println("document.get(\"title\", String.class) = " + document.get("title", String.class));
            System.out.println(document);
        }
    }



    private static void insertMany(MongoCollection<Document> collection) {
        String post2 = """
                {
                "title": "Post 2",
                "body": "Post 2",
                }
                """;
        Map<String, Object> post3 = Map.of(
                "title", "Post 3",
                "body" , "Post 3",
                "createdAt", new Date()
        );

        List<Document> documents = List.of(
                Document.parse(post2),
                new Document(post3));
        InsertManyResult insertManyResult = collection.insertMany(documents);
        // System.out.println(insertManyResult);
        if (insertManyResult.wasAcknowledged()) {
            insertManyResult.getInsertedIds().forEach((k, v)->{
                ObjectId objectId = v.asObjectId().getValue();
                System.out.println(k + " : " + objectId.toString());
            });
        }
    }

    private static void insertOne(MongoCollection<Document> collection) {
        Document post = Document.parse(
                """
                        {
                            "id": 1,
                            "name": "Leanne Graham",
                            "username": "Bret",
                            "email": "Sincere@april.biz",
                            "address": {
                              "street": "Kulas Light",
                              "suite": "Apt. 556",
                              "city": "Gwenborough",
                              "zipcode": "92998-3874",
                              "geo": {
                                "lat": "-37.3159",
                                "lng": "81.1496"
                              }
                            },
                            "phone": "1-770-736-8031 x56442",
                            "website": "hildegard.org",
                            "company": {
                              "name": "Romaguera-Crona",
                              "catchPhrase": "Multi-layered client-server neural-net",
                              "bs": "harness real-time e-markets"
                            }
                          }
                        """
        ) ;

//                new Document("title", "Java Programming")
//                .append("body", "Java Programming is a programming language")
//                .append("createdAt", new Date());
        InsertOneResult insertOneResult = collection.insertOne(post);
        System.out.println("insertOneResult = " + insertOneResult);
        if (insertOneResult.wasAcknowledged()) {
            BsonValue insertedId = insertOneResult.getInsertedId();
            ObjectId objectId = insertedId.asObjectId().getValue();
            System.out.println("objectId = " + objectId.toString());
        }
    }

}
