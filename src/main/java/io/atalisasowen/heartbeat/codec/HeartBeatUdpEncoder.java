package io.atalisasowen.heartbeat.codec;

import io.atalisasowen.heartbeat.command.HeartBeatCommand;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;
import java.util.List;

public class HeartBeatUdpEncoder extends MessageToMessageEncoder<HeartBeatCommand> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, HeartBeatCommand heartBeatCommand, List<Object> list) throws Exception {
        byte[] commandName = heartBeatCommand.getCommandName().getBytes(CharsetUtil.UTF_8);
        byte[] uuid = heartBeatCommand.getCommandUuid().getBytes(CharsetUtil.UTF_8);
        byte[] hostname = heartBeatCommand.getSrcAddr().getHostString().getBytes(CharsetUtil.UTF_8);
        int port = heartBeatCommand.getSrcAddr().getPort();
        byte[] data = heartBeatCommand.getData();
        ByteBuf buf = null;
        if (data != null){
            buf = channelHandlerContext.alloc()
                    .buffer(commandName.length + uuid.length + data.length + hostname.length + 4 + 4);
            buf.writeBytes(commandName);
            buf.writeByte(HeartBeatCommand.SEPARATOR);
            buf.writeBytes(uuid);
            buf.writeByte(HeartBeatCommand.SEPARATOR);
            buf.writeBytes(hostname);
            buf.writeByte(HeartBeatCommand.SEPARATOR);
            buf.writeInt(port);
            buf.writeByte(HeartBeatCommand.SEPARATOR);
            buf.writeBytes(data);
        } else {
            buf = channelHandlerContext.alloc()
                    .buffer(commandName.length + uuid.length + hostname.length + 4 + 4);
            buf.writeBytes(commandName);
            buf.writeByte(HeartBeatCommand.SEPARATOR);
            buf.writeBytes(uuid);
            buf.writeByte(HeartBeatCommand.SEPARATOR);
            buf.writeBytes(hostname);
            buf.writeByte(HeartBeatCommand.SEPARATOR);
            buf.writeInt(port);
            buf.writeByte(HeartBeatCommand.SEPARATOR);
        }
        list.add(new DatagramPacket(buf, heartBeatCommand.getDstAddr()));

    }
}
