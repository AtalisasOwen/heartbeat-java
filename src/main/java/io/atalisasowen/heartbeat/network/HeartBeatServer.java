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
    private InetSocketAddress dstAddr;

    public HeartBeatServer(InetSocketAddress srcAddr, InetSocketAddress dstAddr) {
        this.srcAddr = srcAddr;
        this.dstAddr = dstAddr;
    }


//    public Runnable senderThread = () -> {
//        try {
//            System.out.println("Starting Sending Thread...");
//            Thread.sleep(1000);
//            String key1 = UUID.randomUUID().toString();
//            HeartBeatCommand command1 = new HeartBeatCommand(srcAddr, dstAddr, "PING", key1);
//            SimplePingStore.PING_COMMANDS.put(key1, command1);
//            sendingQueue.offer(command1);
//            Thread.sleep(1000);
//            String key2 = UUID.randomUUID().toString();
//            HeartBeatCommand command2 = new HeartBeatCommand(srcAddr, dstAddr, "PING", key2, "FUUU".getBytes());
//            SimplePingStore.PING_COMMANDS.put(key2, command2);
//            sendingQueue.offer(command2);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    };

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


    public void run() throws Exception {
        HeartBeatCommandRegister.addHandler("PING", new PingCommandHandler());
        HeartBeatCommandRegister.addHandler("PONG", new PongCommandHandler());
        Thread thread = new Thread(receiverThread);
        thread.start();
        HeartBeatUdpSender beatBroadcaster = new HeartBeatUdpSender(SimplePingStore.SENDING_QUEUE);
        try {
//            Thread thread2 = new Thread(senderThread);
//            thread2.start();
            beatBroadcaster.run();
        } finally {
            beatBroadcaster.stop();
        }

    }
}
