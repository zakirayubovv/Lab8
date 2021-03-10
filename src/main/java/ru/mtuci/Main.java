package ru.mtuci;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class Main {
    private static URLPool urlPool = URLPool.getUrlPool();
    private static int threadsCount;

    public static void main(String[] args) throws IOException, InterruptedException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        CrawlerTask.setDepth(Integer.parseInt(reader.readLine()));
        threadsCount = Integer.parseInt(reader.readLine());

        ExecutorService executor = Executors.newFixedThreadPool(threadsCount);
        ThreadPoolExecutor ex = (ThreadPoolExecutor) executor;

        System.out.println("Количество работающих нитей:" + ex.getActiveCount());
        System.out.println("Количество нитей:" + ex.getPoolSize());

        for (int i = 0; i < threadsCount; i++) {
            executor.execute(new CrawlerTask());
        }

        while (true) {
            urlPool.add(new URLDepthPair(reader.readLine(), 0));
            executor.execute(new CrawlerTask());
            Thread.sleep(10);
            System.out.println("Количество работающих нитей:" + ex.getActiveCount());
        }
    }
}
