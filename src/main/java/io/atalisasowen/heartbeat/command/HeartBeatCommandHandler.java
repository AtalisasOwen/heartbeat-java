package io.atalisasowen.heartbeat.command;

import io.atalisasowen.heartbeat.network.HeartBeatProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class HeartBeatCommandHandler extends SimpleChannelInboundHandler<HeartBeatCommand> {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HeartBeatCommand heartBeatCommand) throws Exception {
        System.out.println(heartBeatCommand);
    }
}
