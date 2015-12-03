package ru.bpal.test.rest.resource.jersey;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.bpal.generator.builder.*;
import ru.bpal.reminder.rest.data.dao.PastgresqlDao;
import ru.bpal.reminder.rest.data.entity.db.*;
import ru.bpal.reminder.rest.data.entity.json.MessageEntityJson;
import ru.bpal.reminder.rest.data.service.SmsQueueService;
import ru.bpal.test.AQSuiteTest;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by serg on 28.10.15.
 */
public class MessageControllerJerseyTest extends AQSuiteTest {

    private static final Logger logger = LoggerFactory.getLogger(MessageControllerJerseyTest.class);

    SimpleDateFormat dateFormat;

    PastgresqlDao dao;

    @Before
    public void setUp() throws Exception {
        CleanUpDataBaseBuilder.cleanUp();

        dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
        dao = new PastgresqlDao();
    }


    @Test
    public void postMessage() throws ParseException {
        //setup

        // act
//        Response response = rest.path("/").request(MediaType.APPLICATION_JSON).get();
        MessageEntityJson entity = new MessageEntityJson();
        entity.phone = "79500650500";
        entity.email = "serg2@narod.ru";

        entity.firstName = "Сергей";
        entity.middleName = "Николаевич";
        entity.lastName = "Кукушкни";

        entity.textMessageTemplateUid = UUID.randomUUID().toString();

        entity.fullText = "Дорогой " + entity.firstName + " " + entity.middleName + " " + entity.lastName + " Хорошего тебя дня, млй удачливый!";


        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
        entity.eventDate = dateFormat.format(new Date());
        //Mon, 02 Nov 2015 17:30:11 IRKT

//        Calendar cal = Calendar.getInstance(); // creates calendar
//        cal.setTime(new Date()); // sets calendar time/date
//        cal.add(Calendar.HOUR_OF_DAY, -3); // adds one hour
//        //cal.getTime(); // returns new date object, one hour in the future
//        entity.eventDate = dateFormat.format(cal.getTime());

        System.out.println("eventDate = " + entity.eventDate);

        Response response = rest.path("/message")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(entity, MediaType.APPLICATION_JSON_TYPE));


        String entityString = response.readEntity(String.class);


        // verify
        assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));
        System.out.println("result = " + entityString);
    }

}
