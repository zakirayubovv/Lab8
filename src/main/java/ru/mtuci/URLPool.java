package ru.mtuci;

import java.util.LinkedList;

public class URLPool {
    private static LinkedList<URLDepthPair> list = new LinkedList<>();

    static {
        list.add(new URLDepthPair("https://www.nytimes.com/", 0));
        list.add(new URLDepthPair("https://slashdot.org/", 0));
        list.add(new URLDepthPair("https://javarush.ru", 0));
    }

    private static URLPool urlPool;

    private URLPool() {}

    public static URLPool getUrlPool() {
        if (urlPool == null) {
            urlPool = new URLPool();
        }

        return urlPool;
    }

    public synchronized URLDepthPair getAndRemove() {
        if (list.size() <= 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return list.remove();
    }

    public synchronized void add(URLDepthPair urlDepthPair) {
        list.add(urlDepthPair);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }
}
