import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.Locale;


/**
 * Uses Jedis
  */

public class KeyspaceNotification {
    public static void main(String[] args) {

        Jedis jedis = new Jedis("localhost");
        jedis.psubscribe(new JedisPubSub() {
            @Override
            public void onPSubscribe(String pattern, int subscribedChannels) {
                System.out.println("onPSubscribe " + pattern + " " + subscribedChannels);
            }

            @Override
            public void onPMessage(String pattern, String channel, String message) {
                System.out.print("[Pattern:" + pattern + "]");
                System.out.print("[Channel: " + channel + "]");
                System.out.println("[Message: " + message + "]");

                if (!message.toLowerCase().equals("set")) {
                    System.out.println("[Value: " + jedis.get(message) + "]");
                }
            }
        }, "__key*__:*");

    }
}