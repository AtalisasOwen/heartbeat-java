package io.atalisasowen.heartbeat;

import io.atalisasowen.heartbeat.command.HeartBeatCommand;
import io.atalisasowen.heartbeat.store.SimplePingStore;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.UUID;

public class InternalCommands {


    public static Runnable getPingCommand(InetSocketAddress address1, InetSocketAddress address2){
        return  () -> {
            try {
                System.out.println("Starting Sending Thread...");
                for (;;){
                    String uuid = UUID.randomUUID().toString();
                    HeartBeatCommand command = new HeartBeatCommand(address1, address2, "PING", uuid);
                    SimplePingStore.PING_COMMANDS.put(uuid, command);
                    SimplePingStore.SENDING_QUEUE.offer(command);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }

    public static Runnable getMultiPingCommand(InetSocketAddress address, List<InetSocketAddress> addresses){
        return  () -> {
            try {
                System.out.println("Starting Sending Thread...");
                for (;;){
                    for (InetSocketAddress addr : addresses){
                        String uuid = UUID.randomUUID().toString();
                        HeartBeatCommand command = new HeartBeatCommand(address, addr, "PING", uuid);
                        SimplePingStore.PING_COMMANDS.put(uuid, command);
                        SimplePingStore.SENDING_QUEUE.offer(command);
                    }

                    Thread.sleep(5000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }
}
