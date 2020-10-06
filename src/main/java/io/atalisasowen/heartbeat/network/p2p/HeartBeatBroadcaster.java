package io.atalisasowen.heartbeat.network.p2p;

import io.atalisasowen.heartbeat.command.HeartBeatCommand;
import io.atalisasowen.heartbeat.network.HeartBeatEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetSocketAddress;
import java.util.concurrent.BlockingDeque;

public class HeartBeatBroadcaster {
    private final EventLoopGroup group;
    private final Bootstrap bootstrap;
    private final BlockingDeque<HeartBeatCommand> sendingQueue;


    public HeartBeatBroadcaster(InetSocketAddress address, BlockingDeque<HeartBeatCommand> sendingQueue) {
        this.sendingQueue = sendingQueue;
        this.group = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new HeartBeatEncoder(address));
    }

    public void run() throws Exception{
        Channel ch = bootstrap.bind(0).sync().channel();
        for (;;){
            HeartBeatCommand command = sendingQueue.take();
            ch.writeAndFlush(command);
        }
    }

    public void stop(){
        group.shutdownGracefully();
    }
}
