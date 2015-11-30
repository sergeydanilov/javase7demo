package ru.bpal.reminder.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ru.bpal.reminder.rest.JsonUtil.toJson;
import static spark.Spark.*;


/**
 * Created by serg on 21.10.15.
 */
public class MainPoint {

    public static final String APPLICATION_JSON = "application/json";

    static final Logger logger = LoggerFactory.getLogger(MainPoint.class);

    public static void main(String[] args) {
        //config
        port(9090);


        staticFileLocation("/public");


        get("/hello", (req, res) -> {
            return "Hello World !!! ";
        });

        get("/firstData", (req, res) -> {
            //Gson gson = new GsonBuilder();
            Gson gson = new Gson();
            FistDataEntity fistDataEntity = new FistDataEntity("Nmae of Entity", "123123-123123123-123123-asdadasdasd");

//            Gson gjson = new GsonBuilder()
//                    .setDateFormat("yyyy-MM-dd hh:mm:ss.S")
//                    .setDateFormat("EEE, dd MMM yyyy HH:mm:ss z")
//                    .create();
//            MessageEntity entity = gjson.fromJson(req.body(), MessageEntity.class);


            res.type(APPLICATION_JSON);
            return gson.toJson(fistDataEntity);
        });

        get("/index2page", (req, res) -> {
            res.redirect("/index2.html");
            return "";
        });

        exception(IllegalArgumentException.class, (e, req, res) -> {
            res.status(400);
            res.body(toJson(new ResponseError(e)));
        });
    }
}
