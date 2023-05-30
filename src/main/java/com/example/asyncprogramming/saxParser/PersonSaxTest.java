package com.example.asyncprogramming.saxParser;

import com.example.asyncprogramming.saxParser.handler.PeopleSaxHandler;
import com.example.asyncprogramming.saxParser.model.Person;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.List;

public class PersonSaxTest {
    public static void main(String[] args) {
        File file = new File("src/main/java/com/example/asyncprogramming/saxParser/people.xml");
        SAXParserFactory factory = SAXParserFactory.newInstance();

        try {
            SAXParser saxParser = factory.newSAXParser();
            PeopleSaxHandler handler = new PeopleSaxHandler();
            saxParser.parse(file, handler);

            List<Person> list = handler.getPersonList();

            for (Person p:list) {
                System.out.println(p);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
