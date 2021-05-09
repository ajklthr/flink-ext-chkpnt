package edu.uiuc.cs.extchk;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.Locale;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * Uses Jedis
 */

public class KeyspaceNotification {

    private final BlockingQueue<String> workQueue;
    private final ExecutorService service;
    private final Jedis jedis;

    public KeyspaceNotification(String host, int numWorkers) {
        this.jedis = new Jedis("localhost");
        this.workQueue = new LinkedBlockingQueue<String>();
        this.service = Executors.newFixedThreadPool(numWorkers);

        for (int i=0; i < numWorkers; i++) {
            service.submit(new Worker<String>(workQueue, jedis));
        }
    }

    public static void main(String[] args) {


        KeyspaceNotification notif = new KeyspaceNotification("localhost", 5);

        notif.jedis.psubscribe(new JedisPubSub() {
            @Override
            public void onPSubscribe(String pattern, int subscribedChannels) {
                System.out.println("onPSubscribe " + pattern + " " + subscribedChannels);
            }

            @Override
            public void onPMessage(String pattern, String channel, String message) {
                System.out.print("[Pattern:" + pattern + "]");
                System.out.print("[Channel: " + channel + "]");
                System.out.println("[Message: " + message + "]");

                try{
                    if (!message.toLowerCase().equals("set")) {
                        notif.workQueue.put(message);
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }, "__key*__:*");

    }

    private static class Worker<T> implements Runnable {
        private final BlockingQueue<T> workQueue;
        private final Jedis jedis;

        public Worker(BlockingQueue<T> workQueue, Jedis jedis) {
            this.workQueue = workQueue;
            this.jedis = jedis;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    T item = workQueue.take();
                    System.out.println(jedis.get(String.valueOf(item)));
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

}