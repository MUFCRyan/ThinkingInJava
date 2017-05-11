package com.ryan.io.new_io;

import com.ryan.util.Util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;
import nu.xom.Serializer;

/**
 * Created by MUFCRyan on 2017/5/11.
 * Book Page586 Chapter 18.13 XML，相关库推荐：XOM 类库 —— Elliotte Rusty Harold（www.xom.nu）
 */

public class Person {
    private String first, last;
    public Person(String first, String last){
        this.first = first;
        this.last = last;
    }

    public Person(Element person){
        first = person.getFirstChildElement("first").getValue();
        last = person.getFirstChildElement("last").getValue();
    }

    public Element getXML(){
        Element person = new Element("person");
        Element firstName = new Element("first");
        Element lastName = new Element("last");
        firstName.appendChild(first);
        lastName.appendChild(last);
        person.appendChild(firstName);
        person.appendChild(lastName);
        return person;
    }

    @Override
    public String toString() {
        return first + " " + last;
    }

    public static void format(OutputStream out, Document doc) throws IOException {
        Serializer serializer = new Serializer(out, "UTF-8");
        serializer.setIndent(4); // 设置缩进为4个字符
        serializer.setMaxLength(60);
        serializer.write(doc);
        serializer.flush();
    }

    public static void main(String[] args) throws IOException {
        List<Person> persons = Arrays.asList(
                new Person("Dr. Bunsen", "Honeydew"),
                new Person("Gonzo", "The Great"),
                new Person("Phillip J.", "Fry")
        );
        Util.println(persons);

        Element root = new Element("persons");
        for (Person person : persons) {
            root.appendChild(person.getXML());
        }

        Document document = new Document(root);
        format(System.out, document);
        format(new BufferedOutputStream(new FileOutputStream("people.xml")), document);
    }
}

class People extends ArrayList<Person>{
    public People(String fileName) throws ParsingException, IOException {
        Document document = new Builder().build(new File(fileName));
        Elements elements = document.getRootElement().getChildElements();
        for (int i = 0; i < elements.size(); i++) {
            add(new Person(elements.get(i)));
        }
    }

    public static void main(String[] args) throws ParsingException, IOException {
        People people = new People("people.xml");
        Util.println(people);
    }
}
