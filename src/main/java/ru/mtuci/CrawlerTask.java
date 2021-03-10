package ru.mtuci;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class CrawlerTask implements Runnable {
    private static int depth;

    private static Queue<URLDepthPair> urls = new LinkedList<>();

    @Override
    public void run() {
        URLPool urlPool = URLPool.getUrlPool();

        while (!urlPool.isEmpty()) {
            URLDepthPair currentUrl = urlPool.getAndRemove();
            urls.add(currentUrl);
            if (currentUrl.getDepth() < getDepth()) {
                Set<String> links1 = getLinks(currentUrl.getUrl());
                for (String url : links1) {
                    URLDepthPair e = new URLDepthPair(url, currentUrl.getDepth() + 1);
                    urlPool.add(e);
                    System.out.println(e.getUrl() + ": " + e.getDepth() + " ---- " + Thread.currentThread().getName());
                }
            }
        }
    }

    private static Set<String> getLinks(String url) {
        Set<String> urls = new HashSet<>();
        try {
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.select("a");
            for (Element element : elements) {
                urls.add(element.absUrl("href"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return urls;
    }

    public static int getDepth() {
        return depth;
    }

    public static void setDepth(int depth) {
        CrawlerTask.depth = depth;
    }
}
