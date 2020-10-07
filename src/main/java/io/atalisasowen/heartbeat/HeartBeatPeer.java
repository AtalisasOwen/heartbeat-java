package io.atalisasowen.heartbeat;

import io.atalisasowen.heartbeat.command.HeartBeatCommand;
import io.atalisasowen.heartbeat.network.HeartBeatServer;
import io.atalisasowen.heartbeat.store.SimplePingStore;

import java.net.InetSocketAddress;
import java.util.UUID;

public class HeartBeatPeer {


    public static void main(String[] args) throws Exception {
//        int port1 = Integer.parseInt(args[0]);
//        int port2 = Integer.parseInt(args[1]);

        InetSocketAddress address1 = new InetSocketAddress("127.0.0.1", 6666);
        InetSocketAddress address2 = new InetSocketAddress("127.0.0.1", 8888);

        Runnable senderThread = () -> {
            try {
                System.out.println("Starting Sending Thread...");
                Thread.sleep(1000);
                String key1 = UUID.randomUUID().toString();
                HeartBeatCommand command1 = new HeartBeatCommand(address1, address2, "PING", key1);
                SimplePingStore.PING_COMMANDS.put(key1, command1);
                SimplePingStore.SENDING_QUEUE.offer(command1);
                Thread.sleep(1000);
                String key2 = UUID.randomUUID().toString();
                HeartBeatCommand command2 = new HeartBeatCommand(address1, address2, "PING", key2, "FUUU".getBytes());
                SimplePingStore.PING_COMMANDS.put(key2, command2);
                SimplePingStore.SENDING_QUEUE.offer(command2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        new Thread(senderThread).start();

        HeartBeatServer server = new HeartBeatServer(address1, address2);
        server.run();

    }


}
