package site_parser_app.page_parser;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import site_parser_app.ParserAppStart;
import site_parser_app.entity.ResponseEntity;


import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageParser {

    public static final String DEFAULT_URL = "https://skillbox.ru/";
    private static final List<String> STOP_WORDS = Arrays.asList("vkontakte", "pdf", "twitter", "facebook", "instagram", "utm", "jpg", "jpeg");

    //парсим сайт и добавляем энтити в БД
    public static Set<String> parsing(String currentUrl) throws InterruptedException, IOException {
        Thread.sleep(500);
        Set<String> urlSet = new HashSet<>();
        Document document = getResponse(currentUrl).parse();
        String content = document.outerHtml(); //получаем контент страницы
        int statusCode = getResponse(currentUrl).statusCode(); //получаем код ответа страницы
        Elements elements = document.select("a");
        for (Element element : elements) {
            String url = element.attr("href");
            boolean condition1 = url.startsWith("/");
            boolean condition2 = (url.startsWith("http") || (url.startsWith("https"))) && url.contains(getHostName(url));
            boolean condition3 = STOP_WORDS.stream().noneMatch(url::contains); //проверяем нет ли в "недопустимых" ссылок в нашем урле
            synchronized (ParserAppStart.getResponseEntitySet()) {
                if (condition1 && condition3) {
                    urlSet.add(url = DEFAULT_URL + url.substring(1)); // добавляем текущий упл в сет, который мы будем в следующий раз парсить
                    url = url.substring(1);
                    ResponseEntity responseEntity = new ResponseEntity(url, statusCode, content);
                    ParserAppStart.getResponseEntitySet().add(responseEntity); // добавляем энтити в статический сет
                }
                if (condition2 && condition3) {
                    urlSet.add(url); // добавляем текущий упл в сет, который мы будем в следующий раз парсить
                    url = url.replace("//", "").replace("www.", "");
                    ResponseEntity responseEntity = new ResponseEntity(url, statusCode, content);
                    ParserAppStart.getResponseEntitySet().add(responseEntity); // добавляем энтити в статический сет
                }
            }
        }
        return urlSet;
    }

    //получаем ответ
    public static Connection.Response getResponse(String linkPage) throws IOException {
        Connection.Response response = Jsoup.connect(linkPage)
                .userAgent("Mozilla/5.0 (Windows; U; WindowsNT5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                .referrer("http://google.com")
                .timeout(5000)
                .execute();
        return response;
    }

    //валидируем урл страницы и возвращаем доменное имя
    public static String getHostName(String url) {
        Pattern pattern = Pattern.compile("^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?");
        String domain = null;
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            domain = matcher.group(3).replace("//", "").replace("www.", "");
        }
        return domain;
    }
}
