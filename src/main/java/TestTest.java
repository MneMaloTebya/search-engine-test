import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestTest {
    public static void main(String[] args) {

        Pattern pattern = Pattern.compile("^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?"); // хороший вариант
        List<String> urls = new ArrayList<>();
        urls.add("http://www.playback.ru/");
        urls.add("https://volochek.life/");
        urls.add("http://radiomv.ru/");
        urls.add("https://ipfran.ru/");
        urls.add("https://dimonvideo.ru/");
        urls.add("https://nikoartgallery.com/");
        urls.add("https://et-cetera.ru/mobile/");
        urls.add("https://www.lutherancathedral.ru/");
        urls.add("https://dombulgakova.ru/");
        urls.add("https://www.svetlovka.ru/");
        urls.add("https://skillbox.ru/");

        for (String url : urls) {
            printDom(url, pattern);
        }

    }

    public static void printDom(String url, Pattern pattern) {
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            String domain = matcher.group(3).replace("//", "").replace("www.", "");
            System.out.println("Domain: " + domain );
        }
    }
}
