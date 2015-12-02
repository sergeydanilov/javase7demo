package ru.bpal.launcher;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LauncherMain {

    private static Logger logger = Logger.getLogger(LauncherMain.class.getName());

    public static void main(String [] args) {
        logger.info("Reading file .....");
        String curPath = TPFileUtils.getCurrentPath();
        curPath = curPath + "/notes/sample_text.txt";

        NoteFileReader reader = new NoteFileReader();

        try {
            reader.readFile(curPath);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "can not read file!");
        }
    }
}
