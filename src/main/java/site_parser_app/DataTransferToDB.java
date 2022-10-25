package site_parser_app;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import site_parser_app.entity.PageEntity;
import site_parser_app.my_session.MySession;
import site_parser_app.page_parser.PageParser;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DataTransferToDB {
    private static Set<String> urlSet = Collections.synchronizedSet(new LinkedHashSet<>());
    //private static Set<String> urlSet = ConcurrentHashMap.newKeySet(); - записывает, но в не в порядке парсинга
    private static Set<PageEntity> responseEntitySet = new LinkedHashSet<>();

    public static Set<String> getUrlSet() {
        return urlSet;
    }

    //создаем сессию и транзакцию. проходимся по сету из энтити и добавляем их в БД
    public static void insertPageEntityToDB() {
        Session session = MySession.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        for (PageEntity responseEntity : addResponseToEntity(urlSet)) {
            session.save(responseEntity);
        }
        transaction.commit();
        MySession.closeSession();
    }

    //проходимся по сету из урлов и заново переходим по текущей ссылке, где получаем код ответа и контент данной страницы
    // TODO: 21.10.2022 возможно ли из текущего document получить контент и код ответа (строки 46-48)
    protected static Set<PageEntity> addResponseToEntity(Set<String> urlSet) {
        Iterator<String> iterator = urlSet.iterator();
        while (iterator.hasNext()) {
            String url = iterator.next();
            try {
                Document document = PageParser.getResponse(url).parse();
                String content = document.outerHtml();
                int statusCode = PageParser.getResponse(url).statusCode();

                if (url.contains(PageParser.getDesiredGroupOfURL(url, 4))) {
                    url = PageParser.getDesiredGroupOfURL(url, 5);
                }
                PageEntity responseEntity = new PageEntity(url, statusCode, content);
                responseEntitySet.add(responseEntity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseEntitySet;
    }
}
