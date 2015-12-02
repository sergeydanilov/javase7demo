package ru.bpal.launcher.xml.simple;

import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXQueryDemo {

    public static final String XML_FILE_PATH = "/home/serg/projects/teaching/tpjavase7/javase7demo/notes/sample.xml";

    public static void main(String[] args) {

        try {
            File inputFile = new File(XML_FILE_PATH);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
//            DefaultHandler userhandler = new UserHandler();
            DefaultHandler userhandler = new UserHandler2();
            saxParser.parse(inputFile, userhandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class UserHandler extends DefaultHandler {

    boolean bFirstName = false;
    boolean bLastName = false;
    boolean bNickName = false;
    boolean bMarks = false;
    String rollNo = null;

    @Override
    public void startElement(String uri,
                             String localName, String qName, Attributes attributes)
            throws SAXException {

        if (qName.equalsIgnoreCase("student")) {
            rollNo = attributes.getValue("rollno");
        }
        if (("393").equals(rollNo) &&
                qName.equalsIgnoreCase("student")) {
            System.out.println("Start Element :" + qName);
        }
        if (qName.equalsIgnoreCase("firstname")) {
            bFirstName = true;
        } else if (qName.equalsIgnoreCase("lastname")) {
            bLastName = true;
        } else if (qName.equalsIgnoreCase("nickname")) {
            bNickName = true;
        } else if (qName.equalsIgnoreCase("marks")) {
            bMarks = true;
        }
    }

    @Override
    public void endElement(String uri,
                           String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("student")) {
            if (("393").equals(rollNo)
                    && qName.equalsIgnoreCase("student"))
                System.out.println("End Element :" + qName);
        }
    }


    @Override
    public void characters(char ch[],
                           int start, int length) throws SAXException {

        if (bFirstName && ("393").equals(rollNo)) {
            //age element, set Employee age
            System.out.println("First Name: " +
                    new String(ch, start, length));
            bFirstName = false;
        } else if (bLastName && ("393").equals(rollNo)) {
            System.out.println("Last Name: " +
                    new String(ch, start, length));
            bLastName = false;
        } else if (bNickName && ("393").equals(rollNo)) {
            System.out.println("Nick Name: " +
                    new String(ch, start, length));
            bNickName = false;
        } else if (bMarks && ("393").equals(rollNo)) {
            System.out.println("Marks: " +
                    new String(ch, start, length));
            bMarks = false;
        }
    }
}