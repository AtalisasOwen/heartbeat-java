package io.atalisasowen.heartbeat.network.p2p;

import io.atalisasowen.heartbeat.command.HeartBeatCommandHandler;
import io.atalisasowen.heartbeat.network.HeartBeatDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetSocketAddress;

public class HeartBeatReceiver {
    private final EventLoopGroup group;
    private final Bootstrap bootstrap;

    public HeartBeatReceiver(InetSocketAddress address){
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast(new HeartBeatDecoder());
                        pipeline.addLast(new HeartBeatCommandHandler());
                    }
                }).localAddress(address);
    }

    public Channel bind(){
        return bootstrap.bind().syncUninterruptibly().channel();
    }

    public void stop(){
        group.shutdownGracefully();
    }


}
