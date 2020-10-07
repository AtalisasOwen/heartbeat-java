package io.atalisasowen.heartbeat.network.udp;

import io.atalisasowen.heartbeat.command.HeartBeatHandler;
import io.atalisasowen.heartbeat.codec.HeartBeatUdpDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetSocketAddress;

public class HeartBeatUdpReceiver {
    private final EventLoopGroup group;
    private final Bootstrap bootstrap;

    public HeartBeatUdpReceiver(InetSocketAddress address){
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast(new HeartBeatUdpDecoder());
                        pipeline.addLast(new HeartBeatHandler());
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
