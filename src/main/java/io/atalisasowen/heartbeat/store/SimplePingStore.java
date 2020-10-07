package io.atalisasowen.heartbeat.store;

import io.atalisasowen.heartbeat.command.HeartBeatCommand;

import javax.lang.model.element.VariableElement;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class SimplePingStore {
    public final static ConcurrentHashMap<String, HeartBeatCommand> PING_COMMANDS = new ConcurrentHashMap<>();
    public final static BlockingDeque<HeartBeatCommand> SENDING_QUEUE = new LinkedBlockingDeque<HeartBeatCommand>();

    private final static ConcurrentHashMap<InetSocketAddress, Long> ALIVE_NODES = new ConcurrentHashMap<>();
    private final static ConcurrentHashMap<InetSocketAddress, Object> DEAD_NODES = new ConcurrentHashMap<>();
    private final static Object VALUE = new Object();

    public static void addAliveNode(InetSocketAddress node){
        ALIVE_NODES.put(node, System.currentTimeMillis());
    }

    public static void removeDeadNode(){
        long currentTimeMillis = System.currentTimeMillis();
        for (Map.Entry<InetSocketAddress, Long> entry : ALIVE_NODES.entrySet()){
            if (currentTimeMillis - entry.getValue() > 5000){
                System.out.println(entry.getKey() + " is Dead");
                ALIVE_NODES.remove(entry.getKey());
                DEAD_NODES.put(entry.getKey(), VALUE);
            }
        }
    }

}
