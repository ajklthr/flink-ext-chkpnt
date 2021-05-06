import io.lettuce.core.RedisClient;
import io.lettuce.core.pubsub.RedisPubSubAdapter;
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.async.RedisPubSubAsyncCommands;
import io.lettuce.core.pubsub.api.reactive.RedisPubSubReactiveCommands;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;

/**
 * Uses Lettuceio (Not functional atm)
 */

public class KeyspaceNotification2 {

    public static void listen(String host) {
        RedisClient client = RedisClient.create("redis://" + host + "/0");
        StatefulRedisPubSubConnection<String, String> con = client.connectPubSub();

        RedisPubSubListener<String, String> listener = new RedisPubSubAdapter<String, String>() {

            @Override
            public void message(String pattern, String channel, String message) {
                System.out.println(String.format("Channel: %s, Message: %s", message, message));
            }
        };

        con.addListener(listener);
        RedisPubSubReactiveCommands<String, String> sync = con.reactive();
        sync.psubscribe("__key*__:*");
    }
    public static void main(String... args) {
        System.out.println("Listening for keyspace notifications");
        listen("localhost");
    }


}