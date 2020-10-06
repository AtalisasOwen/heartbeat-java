package io.atalisasowen.heartbeat.network;

import io.atalisasowen.heartbeat.command.HeartBeatCommand;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.util.List;

public class HeartBeatDecoder extends MessageToMessageDecoder<DatagramPacket> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket, List<Object> list) throws Exception {
        ByteBuf buf = datagramPacket.content();
        int idx1 = buf.indexOf(0, buf.readableBytes(), HeartBeatCommand.SEPARATOR);
        int idx2 = buf.indexOf(idx1+1, buf.readableBytes(), HeartBeatCommand.SEPARATOR);
        String commandName = buf.slice(0, idx1).toString(CharsetUtil.UTF_8);
        String commandUuid = buf.slice(idx1+1, idx2).toString(CharsetUtil.UTF_8);
        System.out.println("idx1: " + idx1);
        System.out.println("idx2: " + idx2);
        System.out.println("readableBytes: " + buf.readableBytes());
        HeartBeatCommand command = new HeartBeatCommand(datagramPacket.sender(), commandName, commandUuid);
        if (idx2 != buf.readableBytes()){
            command.setData(buf.slice(idx2+1, buf.readableBytes()).array());
        }
        list.add(command);
    }
}
