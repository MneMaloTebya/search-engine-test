package lemmatizer;

import java.io.IOException;

public class LemmaTest {
    public static void main(String[] args) throws IOException {
        String text = "Брат брата не выдаст";
        LemmaFinder lemmaFinder = LemmaFinder.getInstance();
        System.out.println(lemmaFinder.getLemmaSet(text));
    }
}
