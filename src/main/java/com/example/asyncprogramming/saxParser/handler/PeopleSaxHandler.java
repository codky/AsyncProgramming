package com.example.asyncprogramming.saxParser.handler;

import com.example.asyncprogramming.saxParser.model.Person;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class PeopleSaxHandler extends DefaultHandler {

    // 파싱한 Person 객체를 넣을 리스트
    private List<Person> personList;

    // 파싱한 Person 객체
    private Person person;

    // character 메소드에서 저장할 문자열 변수
    private String str;

    public PeopleSaxHandler() {
        personList = new ArrayList<>();
    }

    public void startElement(String uri, String localName, String name, Attributes att) {
        // 시작 태그를 만났을 때 발생하는 이벤트
        if (name.equals("person")) {
            person = new Person();
            personList.add(person);
        }
    }

    public void endElement(String uri, String localName, String name) {
        // 끝 태그를 만났을 때
        if (name.equals("age")) {
            person.setAge(Integer.parseInt(str));
        }else if (name.equals("name")) {
            person.setName(str);
        }else if (name.equals("gender")) {
            person.setGender(str);
        } else if (name.equals("role")) {
            person.setRole(str);
        }
    }

    public void characters(char[] ch, int start, int length) {
        // 태그와 태그 사이의 내용을 처리
        str = new String(ch, start, length);
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }
}
