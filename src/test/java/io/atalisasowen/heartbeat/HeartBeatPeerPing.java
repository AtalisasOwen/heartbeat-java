package io.atalisasowen.heartbeat;

import io.atalisasowen.heartbeat.command.HeartBeatCommand;
import io.atalisasowen.heartbeat.network.HeartBeatServer;
import io.atalisasowen.heartbeat.store.SimplePingStore;

import java.net.InetSocketAddress;
import java.util.UUID;

public class HeartBeatPeerPing {


    public static void main(String[] args) throws Exception {
//        int port1 = Integer.parseInt(args[0]);
//        int port2 = Integer.parseInt(args[1]);

        InetSocketAddress address1 = new InetSocketAddress("127.0.0.1", 6666);
        InetSocketAddress address2 = new InetSocketAddress("127.0.0.1", 8888);

        new Thread(InternalCommands.getPingCommand(address1, address2)).start();

        HeartBeatServer server = new HeartBeatServer(address1);
        server.run();

    }


}
