package ru.bpal.launcher;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

/**
 * Created by serg on 02.12.15.
 */
public final class  TPFileUtils {

    private static Logger logger = Logger.getLogger(TPFileUtils.class.getName());

    public static String getCurrentPath() {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();

        logger.info("Current relative path is: " + s);
        return s;
    }

    private TPFileUtils() {
    }

}
