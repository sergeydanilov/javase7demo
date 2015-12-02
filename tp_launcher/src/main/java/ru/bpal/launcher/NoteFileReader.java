package ru.bpal.launcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by serg on 02.12.15.
 */
public class NoteFileReader {

    private static Logger logger = Logger.getLogger(NoteFileReader.class.getName());

    public void readFile(String pathString) throws IOException {
//        Path path = Paths.get("../notes/sample_text.txt");
        Path path = Paths.get(pathString);
        if(Files.exists(path)) {
//            Charset charset = Charset.forName("UTF-8");
            Charset charset =  Charset.defaultCharset();
            try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
                String line = null;
                while ((line = reader.readLine()) != null) {
                    logger.info(String.format("reading line = %s", line));
                }
            }
//            catch (IOException x) {
//                System.err.format("IOException: %s%n", x);
//            }

        } else {
            logger.log(Level.SEVERE, "can not find file");
        }
    }
}
