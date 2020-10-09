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
                for (;;){
                    String uuid = UUID.randomUUID().toString();
                    HeartBeatCommand command = new HeartBeatCommand(address1, address2, "PING", uuid);
                    SimplePingStore.sendAndSave(command);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        new Thread(senderThread).start();

        HeartBeatServer server = new HeartBeatServer(address1);
        server.run();

    }


}
