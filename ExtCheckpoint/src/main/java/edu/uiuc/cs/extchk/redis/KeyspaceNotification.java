package edu.uiuc.cs.extchk.redis;

import com.google.protobuf.InvalidProtocolBufferException;
import edu.uiuc.cs.extchk.graph.CausalStateGraph;
import edu.uiuc.cs.extchk.graph.CheckpointContextImpl;
import edu.uiuc.cs.extchk.graph.OperatorCausalStateNode;
import edu.uiuc.cs.extchk.operators.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import com.google.protobuf.GeneratedMessageV3;

import javax.xml.transform.Source;


/**
 * Uses Jedis
 */

public class KeyspaceNotification {

    private final BlockingQueue<String> workQueue;
    private final ExecutorService service;
    private final Jedis jedis;
    private static Map<String, Class> deserializeMap;
    private static CausalStateGraph graph;

    static{
        graph = new CausalStateGraph();
    }

    public KeyspaceNotification(String host, int numWorkers) {
        this.jedis = new Jedis("localhost");
        this.workQueue = new LinkedBlockingQueue<String>();
        this.service = Executors.newFixedThreadPool(numWorkers);
        KeyspaceNotification.deserializeMap = new HashMap<>();
        KeyspaceNotification.deserializeMap.put("1", SourceOperatorState.class);
        KeyspaceNotification.deserializeMap.put("2", MapOperatorState.class);
        KeyspaceNotification.deserializeMap.put("3", ReduceOperatorState.class);
        KeyspaceNotification.deserializeMap.put("4", SinkOperatorState.class);

        for (int i=0; i < numWorkers; i++) {
            service.submit(new Worker<String>(workQueue, jedis));
        }
    }

    public static void main(String[] args) {


        KeyspaceNotification notif = new KeyspaceNotification("localhost", 1);

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
                    String item = String.valueOf(workQueue.take());
                    byte[] value = jedis.get(item).getBytes();
                    VectorTimeStamp vt;
                    Map<Integer, Integer> inputCounts = null;
                    Map<Integer, Integer> outputCounts = null;
                    switch(item){
                        case "1":
                            SourceOperatorState operator_source = SourceOperatorState.parseFrom(value);
                            vt = new VectorTimeStamp(1, (ArrayList) operator_source.getVTimestampList());
                            inputCounts = operator_source.getInputCountsMap();
                            outputCounts = operator_source.getOutputCountsMap();
                        case "2":
                            MapOperatorState operator_map = MapOperatorState.parseFrom(value);
                            vt = new VectorTimeStamp(1, (ArrayList) operator_map.getVTimestampList());
                            inputCounts = operator_map.getInputCountsMap();
                            outputCounts = operator_map.getOutputCountsMap();
                        case "3":
                            ReduceOperatorState operator_reduce = ReduceOperatorState.parseFrom(value);
                            vt = new VectorTimeStamp(1, (ArrayList) operator_reduce.getVTimestampList());
                            inputCounts = operator_reduce.getInputCountsMap();
                            outputCounts = operator_reduce.getOutputCountsMap();
                        case "4":
                            SinkOperatorState operator_sink = SinkOperatorState.parseFrom(value);
                            vt = new VectorTimeStamp(1, (ArrayList) operator_sink.getVTimestampList());
                            inputCounts = operator_sink.getInputCountsMap();
                            outputCounts = operator_sink.getOutputCountsMap();
                    }
                    OperatorCausalStateNode nodeToAdd = new OperatorCausalStateNode(value, inputCounts, outputCounts);
                    KeyspaceNotification.graph.addNode(nodeToAdd);

                    //Checkpointing
                    KeyspaceNotification.graph.checkpoint();


                } catch (InterruptedException | InvalidProtocolBufferException ex) {
                    Thread.currentThread().interrupt();
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}