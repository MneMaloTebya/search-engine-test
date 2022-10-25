package site_parser_app.page_parser;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import site_parser_app.DataTransferToDB;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageParser {
    public static final String DEFAULT_URL = "http://www.playback.ru/";
    private static final List<String> STOP_WORDS = Arrays
            .asList("vk", "pdf", "twitter", "facebook", "instagram", "utm", "JPG",
                    "jpg", "jpeg", "JPEG", "png", "hh", "youtube", "apple", "yandex", "google");

    //парсим сайт и добавляем урлы в статический сет
    public static Set<String> parsing(String currentUrl) throws InterruptedException {
        Thread.sleep(500);
        Set<String> urlSet = new LinkedHashSet<>();
        try {
            try {
                Document document = getResponse(currentUrl).parse();
                Elements elements = document.select("a");
                for (Element element : elements) {
                    String url = element.attr("href");

                    boolean condition1 = url.startsWith("/");
                    boolean condition2 =
                            (url.startsWith("http") || (url.startsWith("https")))
                            && url.contains(getDesiredGroupOfURL(url, 4));
                    boolean condition3 = STOP_WORDS.stream().noneMatch(url::contains); //проверяем нет ли в "недопустимых" ссылок в нашем урле

                    if (condition1 && condition3) {
                        url = DEFAULT_URL + url.substring(1);
                        synchronized (DataTransferToDB.getUrlSet()) { //синхронизируемся
                            if (!DataTransferToDB.getUrlSet().contains(url)) {
                                urlSet.add(url);
                                DataTransferToDB.getUrlSet().add(url);
                            }
                        }
                    }
                    if (condition2 && condition3) {
                        synchronized (DataTransferToDB.getUrlSet()) { //синхронизируемся
                            if (!DataTransferToDB.getUrlSet().contains(url)) {
                                urlSet.add(url);
                                DataTransferToDB.getUrlSet().add(url);
                            }
                        }
                    }
                }
            } catch (HttpStatusException e) {
                return Collections.EMPTY_SET;
            }
        } catch (IOException e) {
            e.printStackTrace();
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

    //проверяем урл страницы и возвращаем доменное имя
    public static String getDesiredGroupOfURL(String url, int group) {
        Pattern pattern = Pattern.compile("^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?");
        String desiredGroup = null;
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            switch (group) {
                case 1: desiredGroup = matcher.group(1);
                    break;
                case 2 : desiredGroup = matcher.group(2);
                    break;
                case 3 : desiredGroup = matcher.group(3);
                    break;
                case 4 : desiredGroup = matcher.group(4);
                    break;
                case 5 : desiredGroup = matcher.group(5);
                    break;
            }
        }
        return desiredGroup;
    }
}
