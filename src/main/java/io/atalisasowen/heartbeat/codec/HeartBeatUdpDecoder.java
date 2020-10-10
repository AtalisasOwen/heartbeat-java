package io.atalisasowen.heartbeat.codec;

import io.atalisasowen.heartbeat.command.HeartBeatCommand;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.List;

public class HeartBeatUdpDecoder extends MessageToMessageDecoder<DatagramPacket> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket, List<Object> list) throws Exception {
        ByteBuf buf = datagramPacket.content();
        int idx1 = buf.indexOf(0, buf.readableBytes(), HeartBeatCommand.SEPARATOR);
        int idx2 = buf.indexOf(idx1+1, buf.readableBytes(), HeartBeatCommand.SEPARATOR);
        int idx3 = buf.indexOf(idx2+1, buf.readableBytes(), HeartBeatCommand.SEPARATOR);
        int idx4 = buf.indexOf(idx3+1, buf.readableBytes(), HeartBeatCommand.SEPARATOR);
        String[] commandNameAndUuid = buf.slice(0, idx4).toString(CharsetUtil.UTF_8).split(":");
        String commandName = commandNameAndUuid[0];
        String commandUuid = commandNameAndUuid[1];
        String hostName = commandNameAndUuid[2];
        String port = commandNameAndUuid[3];
//        System.out.println("readableBytes: " + buf.readableBytes());
//        System.out.println("commandName: " + commandName);
//        System.out.println("commandUuid: " + commandUuid);
//        System.out.println("hostname: " + hostName);
//        System.out.println("port: " + port);
        HeartBeatCommand command = new HeartBeatCommand(new InetSocketAddress(hostName, Integer.parseInt(port)), null, commandName, commandUuid);
        if (idx2 + 1 != buf.readableBytes()){
            if(buf.hasArray()){ // 堆存储
                command.setData(buf.slice(idx2+1, buf.readableBytes()).array());
            }else{              // 直接内存，需要复制出来！
                byte[] array = new byte[buf.readableBytes()-idx2-1];
                buf.getBytes(idx2+1, array);
                command.setData(array);
            }
        }
        // System.out.println("Receiving " + command);

        list.add(command);
    }
}
