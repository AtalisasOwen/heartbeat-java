package io.atalisasowen.heartbeat;

import io.atalisasowen.heartbeat.command.HeartBeatCommand;
import io.atalisasowen.heartbeat.network.HeartBeatPeer;
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
                    HeartBeatPeer peer = new HeartBeatPeer(address1);
                    peer.send("PING", address2);
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
                    HeartBeatPeer peer = new HeartBeatPeer(address);
                    peer.send2("PING", addresses);
                    Thread.sleep(2000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }
}
