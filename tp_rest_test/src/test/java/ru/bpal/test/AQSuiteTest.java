package ru.bpal.test;

import org.glassfish.jersey.filter.LoggingFilter;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public abstract class AQSuiteTest {

    private static final Logger logger = LoggerFactory.getLogger(AQSuiteTest.class);
    private static final java.util.logging.Logger ulogger = java.util.logging.Logger.getLogger("AQSuiteTest");

    protected static boolean initialized = false;

    protected static WebTarget rest;

    @Rule
    public TestName name = new TestName();

    @BeforeClass
    public synchronized static void setUpBeforeClass() throws SQLException, IOException {
        if (initialized) return;

        fetchProperties();

        Client client = ClientBuilder.newClient();
        client.register(new LoggingFilter(ulogger, true));
        rest = client.target(AppTestProp.getBaseUrl());

        //ClientConfig cc = new ClientConfig().register(new JacksonFeature()).register(MultiPartFeature.class);
        //rest = ClientBuilder.newClient(cc).target(AppTestProp.getBaseUrl());

//        prepareDbConnection();
        databaseCleanUp();

        initialized = true;
    }

    protected static void databaseCleanUp() throws SQLException {
    }

    private static void fetchProperties() throws IOException {
        logger.info("Reading properties ... ");
        if (System.getProperty("base.url") == null) {
            Properties properties = System.getProperties();

            String currentPath = new File(".").getAbsoluteFile().toString();
            File file;
            file = new File(currentPath + "/properties/test.properties");

            if (file.exists()) {
                properties.load(new FileInputStream(file));
                System.setProperties(properties);
            } else {
                throw new RuntimeException("No properties");
            }
            logger.info("AppTestProp.getBaseUrl()=%s", AppTestProp.getBaseUrl());
        }
    }

    protected static void sleepSilently(int sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
        }
    }

    public static void sleep(long millis) {
        try {
            System.out.println("Acceptance test is going to sleep " + millis + "ms");
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    public Connection getConnection() throws SQLException {
        return null;
    }
}
