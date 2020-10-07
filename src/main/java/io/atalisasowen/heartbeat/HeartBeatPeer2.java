package io.atalisasowen.heartbeat;

import io.atalisasowen.heartbeat.command.HeartBeatCommand;
import io.atalisasowen.heartbeat.network.HeartBeatServer;
import io.atalisasowen.heartbeat.network.udp.HeartBeatUdpSender;
import io.atalisasowen.heartbeat.store.SimplePingStore;

import java.net.InetSocketAddress;
import java.util.UUID;

public class HeartBeatPeer2 {



    public static void main(String[] args) throws Exception {
//        int port1 = Integer.parseInt(args[0]);
//        int port2 = Integer.parseInt(args[1]);

        InetSocketAddress address1 = new InetSocketAddress("127.0.0.1",8888);
        InetSocketAddress address2 = new InetSocketAddress("127.0.0.1",6666);
        HeartBeatServer server = new HeartBeatServer(address1, address2);
        server.run();

    }


}
