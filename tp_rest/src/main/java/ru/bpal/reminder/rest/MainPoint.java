package ru.bpal.reminder.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.javafx.scene.control.skin.LabeledImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.bpal.reminder.rest.data.CitiesDataProvider;
import ru.bpal.reminder.rest.data.dao.PastgresqlDao;
import ru.bpal.reminder.rest.data.entity.db.MessageEntity;
import ru.bpal.reminder.rest.data.service.SmsQueueService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

        PastgresqlDao dao = new PastgresqlDao();

        get("/hello", (req, res) -> {
            return "Hello World !!! ";
        });

        get("/firstData", (req, res) -> {
            Gson gson = new Gson();
            FistDataEntity fistDataEntity = new FistDataEntity("Nmae of Entity", "123123-123123123-123123-asdadasdasd");

            res.type(APPLICATION_JSON);
            return gson.toJson(fistDataEntity);
        });

        get("/index2page", (req, res) -> {
            res.redirect("/index2.html");
            return "";
        });

        get("/firstDatas", (req, res) -> {
            Gson gson = new Gson();
            List<FistDataEntity> list = new ArrayList<FistDataEntity>();
            for (int i = 0; i < 20; i++) {
                UUID uuid = UUID.randomUUID();
                FistDataEntity fistDataEntity = new FistDataEntity("Имя_"+i, uuid.toString());
                list.add(fistDataEntity);
            }

            res.type(APPLICATION_JSON);
            return gson.toJson(list);
        });

        get("/cities", (req, res) -> {
            Gson gson = new Gson();
            res.type(APPLICATION_JSON);
            return gson.toJson(CitiesDataProvider.cities);
        });

        get("/index2page", (req, res) -> {
            res.redirect("/index2.html");
            return "";
        });


        post("/message", APPLICATION_JSON, (req, res) -> {

//            MessageEntity entity = new Gson().fromJson(req.body(), MessageEntity.class);
            Gson gjson = new GsonBuilder()
//                    .setDateFormat("yyyy-MM-dd hh:mm:ss.S")
                    .setDateFormat("EEE, dd MMM yyyy HH:mm:ss z")
                    .create();
            MessageEntity entity = gjson.fromJson(req.body(), MessageEntity.class);

            logger.debug("got entity = " + entity);


            entity.entepriseUid = UUID.randomUUID().toString();
            entity.consumerUid = UUID.randomUUID().toString();

            SmsQueueService queueService = new SmsQueueService(dao);

            MessageEntity messageEntity = null;
            messageEntity = queueService.add(entity);

            if (messageEntity == null) {
                halt(500);
            }

            Gson gson = new GsonBuilder()
//                    .setDateFormat(DateFormat.FULL, DateFormat.FULL).create();
                    .setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();

            res.type(APPLICATION_JSON);
            return gson.toJson(messageEntity);
        });


        exception(IllegalArgumentException.class, (e, req, res) -> {
            res.status(400);
            res.body(toJson(new ResponseError(e)));
        });
    }
}
