package io.atalisasowen.heartbeat.network.udp;

import io.atalisasowen.heartbeat.command.HeartBeatCommand;
import io.atalisasowen.heartbeat.codec.HeartBeatUdpEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetSocketAddress;
import java.util.concurrent.BlockingDeque;

public class HeartBeatUdpSender {
    private final EventLoopGroup group;
    private final Bootstrap bootstrap;
    private final BlockingDeque<HeartBeatCommand> sendingQueue;


    public HeartBeatUdpSender(BlockingDeque<HeartBeatCommand> sendingQueue) {
        this.sendingQueue = sendingQueue;
        this.group = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new HeartBeatUdpEncoder());
    }

    public void run() throws Exception{
        Channel ch = bootstrap.bind(0).sync().channel();
        for (;;){
            HeartBeatCommand command = sendingQueue.take();
            // System.out.println("Sending " + command);
            ch.writeAndFlush(command);
            // ch.read();
        }
    }

    public void stop(){
        group.shutdownGracefully();
    }
}
