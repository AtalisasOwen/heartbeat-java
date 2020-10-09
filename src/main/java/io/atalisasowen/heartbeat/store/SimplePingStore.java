package io.atalisasowen.heartbeat.store;

import io.atalisasowen.heartbeat.command.HeartBeatCommand;
import io.atalisasowen.heartbeat.utils.Utils;

import javax.lang.model.element.VariableElement;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class SimplePingStore {
    private final static DelayedConcurrentHashMap<String, HeartBeatCommand> PING_COMMANDS = new DelayedConcurrentHashMap<>();
    private final static BlockingDeque<HeartBeatCommand> SENDING_QUEUE = new LinkedBlockingDeque<HeartBeatCommand>();
    private final static ConcurrentHashMap<String, Long> ALIVE_NODES = new ConcurrentHashMap<>();
    private final static ConcurrentHashMap<String, Object> DEAD_NODES = new ConcurrentHashMap<>();
    private final static Object VALUE = new Object();

    public static void sendAndSave(HeartBeatCommand command){
        SimplePingStore.PING_COMMANDS.put(command.getCommandUuid(), command);
        SimplePingStore.SENDING_QUEUE.add(command);

    }

    public static void send(HeartBeatCommand command){
        SimplePingStore.SENDING_QUEUE.add(command);
    }

    public static HeartBeatCommand getHistoryCommand(String uuid){
        return PING_COMMANDS.get(uuid);
    }


    public static void addAliveNode(InetSocketAddress node){
        String key = Utils.socketAddress2String(node);
        ALIVE_NODES.put(key, System.currentTimeMillis());
        if (DEAD_NODES.containsKey(key)){
            System.out.println(key + " is Alive Again");
            DEAD_NODES.remove(key);
        }
    }

    public static void removeDeadNode(){
        long currentTimeMillis = System.currentTimeMillis();
        for (Map.Entry<String, Long> entry : ALIVE_NODES.entrySet()){
            if (currentTimeMillis - entry.getValue() > 5000){
                System.out.println(entry.getKey() + " is Dead");
                ALIVE_NODES.remove(entry.getKey());
                DEAD_NODES.put(entry.getKey(), VALUE);
            }
        }
    }

}
