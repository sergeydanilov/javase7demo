package ru.bpal.test;

/**
 *
 */
public class AppTestProp {
    public static final String BASE_URL = System.getProperty("base.url");

    public static String getBaseUrl() {
        return BASE_URL;
    }
}
