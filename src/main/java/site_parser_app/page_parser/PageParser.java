package site_parser_app.page_parser;

import org.apache.commons.lang3.StringUtils;
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
    private static final List<String> STOP_WORDS = Arrays.asList("vkontakte", "pdf", "twitter", "facebook", "instagram", "utm");
    private static StringBuilder stringBuilder = new StringBuilder();

    public static StringBuilder getStringBuilder() {
        return stringBuilder;
    }

    // парсим сайт, проходимся по элементам и вызываем метод addDataToDB()
    public static Set<String> parsing(String linkPage) throws InterruptedException, IOException {
        Thread.sleep(500);
        Set<String> urlSet = new HashSet<>();
        Document document = getResponse(linkPage).parse();
        Elements elements = document.select("a");
        for (Element element : elements) {
            String url = element.attr("href");
            if (url.startsWith("/")) {
                url = DEFAULT_URL + url.substring(1);
                addDataToDB(url, urlSet);
            } else if (url.startsWith("http") && url.contains(getHostName(DEFAULT_URL))) {
                addDataToDB(url, urlSet);
            }
        }
        return urlSet;
    }

    //добавляем данные в БД
    public static void addDataToDB(String url, Set<String> urlSet) {
        if (STOP_WORDS.stream().noneMatch(url::contains)) {
            synchronized (ParserAppStart.getSiteTreeSet()) { // TODO: 13.10.2022 позже поменять объект синхронизации
                if (!ParserAppStart.getSiteTreeSet().contains(url)) {
                    // TODO: 13.10.2022 реализовать добавление в БД энтити
                    urlSet.add(url);
                    ParserAppStart.getSiteTreeSet().add(url);
                    tabulation(url);
                }
            }
        }
    }

    //получаем ответ после подключения
    public static Connection.Response getResponse(String linkPage) throws IOException {
        Connection.Response response = Jsoup.connect(linkPage)
                .userAgent("Mozilla/5.0 (Windows; U; WindowsNT5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                .referrer("http://google.com")
                .timeout(5000)
                .execute();
        return response;
    }

    //получаем имя домена из урла
    public static String getHostName(String url) {
        Pattern pattern = Pattern.compile("^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?");
        String domain = null;
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            domain = matcher.group(3).replace("//", "").replace("www.", "");
            System.out.println(domain); // TODO: 13.10.2022 позже удалить
        }
        return domain;
    }

    // TODO: 13.10.2022 позже удалить
    public static void tabulation(String url) {
        if (StringUtils.countMatches(url, "/") == 3) {
            stringBuilder.append(url + "\n");
        } else if (StringUtils.countMatches(url, "/") == 4) {
            stringBuilder.append("\t" + url + "\n");
        } else if (StringUtils.countMatches(url, "/") == 5) {
            stringBuilder.append("\t" + "\t" + url + "\n");
        } else if (StringUtils.countMatches(url, "/") == 6) {
            stringBuilder.append("\t" + "\t" + "\t" + url + "\n");
        } else if (StringUtils.countMatches(url, "/") == 7) {
            stringBuilder.append("\t" + "\t" + "\t" + "\t" + url + "\n");
        } else if (StringUtils.countMatches(url, "/") == 8) {
            stringBuilder.append("\t" + "\t" + "\t" + "\t" + "\t" + url + "\n");
        }
    }
}
