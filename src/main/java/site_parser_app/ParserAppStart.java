package site_parser_app;

import site_parser_app.page_parser.PageParser;
import site_parser_app.page_parser.RecursiveParser;
import java.util.concurrent.ForkJoinPool;

public class ParserAppStart {
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        new ForkJoinPool().invoke(new RecursiveParser(PageParser.parsing(PageParser.DEFAULT_URL)));
        System.out.println("Спарсили сайт. Время: " + ((System.currentTimeMillis() - start) / 1000) + " сек.");
        DataTransferToDB.insertPageEntityToDB();
        System.out.println("Залили в БД. Время: " + ((System.currentTimeMillis() - start) / 1000) + " сек.");
    }
}
