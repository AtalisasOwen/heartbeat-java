package io.atalisasowen.heartbeat.network;

import io.atalisasowen.heartbeat.command.HeartBeatCommand;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.List;

public class HeartBeatEncoder extends MessageToMessageEncoder<HeartBeatCommand> {
    private final InetSocketAddress remoteAddr;

    public HeartBeatEncoder(InetSocketAddress remoteAddr){
        this.remoteAddr = remoteAddr;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, HeartBeatCommand heartBeatCommand, List<Object> list) throws Exception {
        byte[] commandName = heartBeatCommand.getCommandName().getBytes(CharsetUtil.UTF_8);
        byte[] uuid = heartBeatCommand.getCommandUuid().getBytes(CharsetUtil.UTF_8);
        byte[] data = heartBeatCommand.getData();
        ByteBuf buf = null;
        if (data != null){
            buf = channelHandlerContext.alloc()
                    .buffer(commandName.length + uuid.length + data.length + 2);
            buf.writeBytes(commandName);
            buf.writeByte(HeartBeatCommand.SEPARATOR);
            buf.writeBytes(uuid);
            buf.writeByte(HeartBeatCommand.SEPARATOR);
            buf.writeBytes(data);
        } else {
            buf = channelHandlerContext.alloc()
                    .buffer(commandName.length + uuid.length + 2);
            buf.writeBytes(commandName);
            buf.writeByte(HeartBeatCommand.SEPARATOR);
            buf.writeBytes(uuid);
            buf.writeByte(HeartBeatCommand.SEPARATOR);
        }
        list.add(new DatagramPacket(buf, remoteAddr));

    }
}
