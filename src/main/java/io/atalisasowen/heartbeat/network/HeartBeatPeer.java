package io.atalisasowen.heartbeat.network;

import io.atalisasowen.heartbeat.command.HeartBeatCommand;
import io.atalisasowen.heartbeat.command.SimpleHeartBeatCommand;
import io.atalisasowen.heartbeat.store.SimplePingStore;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class HeartBeatPeer {

    private InetSocketAddress address;

    public HeartBeatPeer(InetSocketAddress address){
        this.address = address;
    }

    public void send(SimpleHeartBeatCommand command, List<String> peers){
        List<HeartBeatCommand> commands = peers.stream().map(s -> {
            String[] ss = s.split(":");
            String ip = ss[0];
            int port = Integer.parseInt(ss[1]);
            String uuid = UUID.randomUUID().toString();

            return new HeartBeatCommand(address,new InetSocketAddress(ip, port),  command.getCommandName(), uuid, command.getData());
        }).collect(Collectors.toList());

        SimplePingStore.SENDING_QUEUE.addAll(commands);
    }

    public void send(String command, List<String> peers){
        List<HeartBeatCommand> commands = peers.stream().map(s -> {
            String[] ss = s.split(":");
            String ip = ss[0];
            int port = Integer.parseInt(ss[1]);
            String uuid = UUID.randomUUID().toString();

            return new HeartBeatCommand(address,new InetSocketAddress(ip, port),  command, uuid);
        }).collect(Collectors.toList());

        SimplePingStore.SENDING_QUEUE.addAll(commands);
    }

}
