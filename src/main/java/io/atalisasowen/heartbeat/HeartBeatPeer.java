package io.atalisasowen.heartbeat;

import io.atalisasowen.heartbeat.command.HeartBeatCommand;
import io.atalisasowen.heartbeat.network.p2p.HeartBeatBroadcaster;
import io.atalisasowen.heartbeat.network.p2p.HeartBeatReceiver;
import io.netty.channel.Channel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class HeartBeatPeer {
    public static BlockingDeque<HeartBeatCommand> sendingQueue = new LinkedBlockingDeque<HeartBeatCommand>();
    public static BlockingDeque<HeartBeatCommand> receivingQueue = new LinkedBlockingDeque<HeartBeatCommand>();


    public static Runnable receiverThread = () -> {
        HeartBeatReceiver receiver = new HeartBeatReceiver(new InetSocketAddress(6666));
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

    public static Runnable senderThread = () -> {
        try {
            System.out.println("Starting Sending Thread...");
            Thread.sleep(1000);
            HeartBeatCommand command1 = new HeartBeatCommand(null, "PINGPONG",UUID.randomUUID().toString() );
            sendingQueue.offer(command1);
            Thread.sleep(1000);
            HeartBeatCommand command2 = new HeartBeatCommand(null, "PING", UUID.randomUUID().toString(), "FUUU".getBytes());
            sendingQueue.offer(command2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    };


    public static void main(String[] args) throws Exception {
        Thread thread = new Thread(receiverThread);
        thread.start();
        Thread.sleep(1000L);
        HeartBeatBroadcaster beatBroadcaster = new HeartBeatBroadcaster(
                new InetSocketAddress("255.255.255.255", 6666),
                sendingQueue);
        try {
            Thread thread2 = new Thread(senderThread);
            thread2.start();
            beatBroadcaster.run();
        }finally {
            beatBroadcaster.stop();
        }



    }


}
