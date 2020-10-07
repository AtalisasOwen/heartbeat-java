package io.atalisasowen.heartbeat.network;

import io.atalisasowen.heartbeat.command.HeartBeatCommandRegister;
import io.atalisasowen.heartbeat.command.internal.PingCommandHandler;
import io.atalisasowen.heartbeat.command.internal.PongCommandHandler;
import io.atalisasowen.heartbeat.network.udp.HeartBeatUdpReceiver;
import io.atalisasowen.heartbeat.network.udp.HeartBeatUdpSender;
import io.atalisasowen.heartbeat.store.SimplePingStore;
import io.netty.channel.Channel;

import java.net.InetSocketAddress;

public class HeartBeatServer {

    private InetSocketAddress srcAddr;

    public HeartBeatServer(InetSocketAddress srcAddr) {
        this.srcAddr = srcAddr;
    }

    public Runnable receiverThread = () -> {
        HeartBeatUdpReceiver receiver = new HeartBeatUdpReceiver(srcAddr);
        try {
            System.out.println("Starting Receiving Thread...");
            Channel channel = receiver.bind();
            channel.closeFuture().sync();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            receiver.stop();
        }
    };

    public Runnable clearDeadThread = () -> {
        for (;;){
            try {
                SimplePingStore.removeDeadNode();
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };


    public void run() throws Exception {
        HeartBeatCommandRegister.addHandler("PING", new PingCommandHandler());
        HeartBeatCommandRegister.addHandler("PONG", new PongCommandHandler());

        new Thread(receiverThread).start();

        new Thread(clearDeadThread).start();

        HeartBeatUdpSender beatBroadcaster = new HeartBeatUdpSender(SimplePingStore.SENDING_QUEUE);
        try {
            beatBroadcaster.run();
        } finally {
            beatBroadcaster.stop();
        }

    }
}
