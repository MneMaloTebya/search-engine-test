package site_parser_app;

import site_parser_app.page_parser.PageParser;
import site_parser_app.page_parser.RecursiveParser;
import java.util.concurrent.ForkJoinPool;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserAppStart {
    public static void main(String[] args) throws InterruptedException {
        new ForkJoinPool().invoke(new RecursiveParser(PageParser.parsing(PageParser.DEFAULT_URL)));
        DataTransferToDB.insertEntityToDB();
    }
}
