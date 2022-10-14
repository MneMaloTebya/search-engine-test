package site_parser_app;

import org.hibernate.Session;
import org.hibernate.Transaction;
import site_parser_app.entity.ResponseEntity;
import site_parser_app.my_session.MySession;
import site_parser_app.page_parser.PageParser;
import site_parser_app.page_parser.RecursiveParser;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

public class ParserAppStart {

    private static Set<String> siteTreeSet = new HashSet<>();

    public static Set<String> getSiteTreeSet() {
        return siteTreeSet;
    }

    public static void main(String[] args) {

        try {
            FileWriter fileWriter = new FileWriter("data/siteMap.txt");
            RecursiveParser recursiveParser = new RecursiveParser(PageParser.parsing(PageParser.DEFAULT_URL));
            new ForkJoinPool().invoke(recursiveParser);
            fileWriter.write(PageParser.getStringBuilder().toString());
            fileWriter.flush();
            fileWriter.close();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}

/*
        Session session = site_parser_app.my_session.MySession.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        site_parser_app.entity.ResponseEntity response = new site_parser_app.entity.ResponseEntity("/skillbox.ru", 200, "testtest");

        session.save(response);
        transaction.commit();
        site_parser_app.my_session.MySession.closeSession();
 */