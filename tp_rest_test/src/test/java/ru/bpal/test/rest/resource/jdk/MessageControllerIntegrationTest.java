package ru.bpal.test.rest.resource.jdk;

import com.google.gson.Gson;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.bpal.reminder.rest.data.entity.db.MessageEntity;
import spark.utils.IOUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * Created by serg on 28.10.15.
 */
public class MessageControllerIntegrationTest {

    static final Logger logger = LoggerFactory.getLogger(MessageControllerIntegrationTest.class);

    @BeforeClass
    public static void beforeClass() {
//        MainPoint.main(null);
    }

    @AfterClass
    public static void afterClass() {
//        Spark.stop();
    }

    @Test
    public void postMessage() {

        MessageEntity entity = new MessageEntity();
        entity.phone  = "79500650327";
        entity.email="serg2@narod.ru";

        entity.firstName="Сергей";
        entity.middleName="Николаевич";
        entity.lastName="Данилов";

        entity.textMessageTemplateUid =  UUID.randomUUID().toString();;

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
        entity.datetime = dateFormat.format(new Date());

        String jsonString = new Gson().toJson(entity);

        TestResponse res = request("POST", "/message", jsonString);
        Map<String, String> json = res.json();

        assertEquals(200, res.status);

//        assertEquals("john", json.get("name"));
//        assertEquals("john@foobar.com", json.get("email"));
//        assertNotNull(json.get("id"));
    }



    private TestResponse request(String method, String path, String reqbody) {
        try {
            URL url = new URL("http://localhost:9090" + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setRequestProperty( "charset", "utf-8");
//            connection.setRequestProperty("Content-type", "text/xml; charset=" + "UTF-8");
            connection.setRequestProperty("Content-type", "application/json; charset=" + "UTF-8");
            connection.setDoOutput(true);

            if( reqbody != null){
                connection.setDoInput(true);
                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                out.writeBytes(reqbody);
                out.flush();
                out.close();
            }

            connection.connect();

            String body = IOUtils.toString(connection.getInputStream());

            return new TestResponse(connection.getResponseCode(), body);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Sending request failed: " + e.getMessage());
            return null;
        }
    }

    private static class TestResponse {

        public final String body;
        public final int status;

        public TestResponse(int status, String body) {
            this.status = status;
            this.body = body;
        }

        public Map<String,String> json() {
            return new Gson().fromJson(body, HashMap.class);
        }
    }
}
