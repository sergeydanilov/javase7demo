package ru.bpal.launcher.regex;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RegexTestHarness {

    private static Logger logger = Logger.getLogger(RegexTestHarness.class.getName());

    public static void main(String[] args) {
        Console console = System.console();

//        if (console == null) {
//            System.err.println("No console.");
//            System.exit(1);
//        }

        while (true) {

            String regexFromConsole = getRegexFromConsole(console, "%nEnter your regex:");
            Pattern pattern =
                    Pattern.compile(regexFromConsole);

            String testStringFromConsole = getTestStringFromConsole(console, "Enter input string to search: ");
            Matcher matcher =
                    pattern.matcher(testStringFromConsole);

            boolean found = false;
            while (matcher.find()) {
                System.out.println(
                        String.format("I found the text" +
                                " \"%s\" starting at " +
                                "index %d and ending at index %d.%n",
                        matcher.group(),
                        matcher.start(),
                        matcher.end())
                );
                found = true;
            }
            if (!found) {
                System.out.println(String.format("No match found.%n"));
            }
        }
    }

    private static String getTestStringFromConsole(Console console, String fmt) {
        if (console != null) {
            return console.readLine(fmt);
        } else {
            System.out.println(fmt);
//            Charset charset = Charset.forName("UTF-8");
            Charset charset = Charset.defaultCharset();
            logger.info("set input encoding to " + charset);
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(System.in, charset));
            String input;

            try {
                input = br.readLine();
                return input;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static String getRegexFromConsole(Console console, String fmt) {
        if (console != null) {
            return console.readLine(fmt);
        } else {
            System.out.println(fmt);
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(System.in));
            String input;

            try {
                input = br.readLine();
                return input;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
