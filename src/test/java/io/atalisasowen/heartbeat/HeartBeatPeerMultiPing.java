package io.atalisasowen.heartbeat;

import io.atalisasowen.heartbeat.network.HeartBeatServer;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class HeartBeatPeerMultiPing {



    public static void main(String[] args) throws Exception {
//        int port1 = Integer.parseInt(args[0]);
//        int port2 = Integer.parseInt(args[1]);

        InetSocketAddress address1 = new InetSocketAddress("127.0.0.1", 6666);
        InetSocketAddress address2 = new InetSocketAddress("127.0.0.1", 8888);
        InetSocketAddress address3 = new InetSocketAddress("127.0.0.1", 8889);
        InetSocketAddress address4 = new InetSocketAddress("127.0.0.1", 8890);
        List<InetSocketAddress> addresses = new ArrayList<>();
        addresses.add(address2);
        addresses.add(address3);
        addresses.add(address4);
        new Thread(InternalCommands.getMultiPingCommand(address1, addresses)).start();

        HeartBeatServer server = new HeartBeatServer(address1);
        server.run();

    }


}
