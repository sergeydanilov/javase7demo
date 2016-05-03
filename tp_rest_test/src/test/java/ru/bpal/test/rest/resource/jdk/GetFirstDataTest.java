package ru.bpal.test.rest.resource.jdk;

import com.google.gson.Gson;
import org.junit.Test;
import spark.utils.IOUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by serg on 03.05.16.
 */
public class GetFirstDataTest {


    @Test
    public void testGetData() throws Exception {
        //setup
        String path = "/firstDatas";

        //act
        TestResponse res = request("GET", path, null);

        assertEquals(200, res.status);
        System.out.println(res.body);
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
