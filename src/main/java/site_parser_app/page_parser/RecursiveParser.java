package site_parser_app.page_parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.RecursiveTask;

public class RecursiveParser extends RecursiveTask<Set<String>> {
    private Set<String> urlsSet;

    public RecursiveParser(Set<String> urlsMap) {
        this.urlsSet = urlsMap;
    }

    @Override
    protected Set<String> compute() {
        Set<String> urls = new HashSet<>();
        try {
            for (String url : urlsSet) {
                RecursiveParser task = new RecursiveParser(PageParser.parsing(url));
                task.fork();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return urls;
    }
}