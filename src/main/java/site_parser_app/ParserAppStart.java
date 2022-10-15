package site_parser_app;

import org.hibernate.Session;
import org.hibernate.Transaction;
import site_parser_app.entity.ResponseEntity;
import site_parser_app.my_session.MySession;
import site_parser_app.page_parser.PageParser;
import site_parser_app.page_parser.RecursiveParser;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

public class ParserAppStart {

    private static Set<ResponseEntity> responseEntitySet = new HashSet<>();

    public static Set<ResponseEntity> getResponseEntitySet() {
        return responseEntitySet;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        RecursiveParser recursiveParser = new RecursiveParser(PageParser.parsing(PageParser.DEFAULT_URL));
        new ForkJoinPool().invoke(recursiveParser);
        Session session = MySession.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Iterator<ResponseEntity> entityIterator = responseEntitySet.iterator();
        while (entityIterator.hasNext()){

            ResponseEntity entity = entityIterator.next();
            session.save(entity);
        }

        transaction.commit();
        MySession.closeSession();

    }
}
