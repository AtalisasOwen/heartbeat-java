package io.atalisasowen.heartbeat.network;

import io.atalisasowen.heartbeat.command.HeartBeatCommand;
import io.atalisasowen.heartbeat.command.SimpleHeartBeatCommand;
import io.atalisasowen.heartbeat.store.SimplePingStore;
import io.atalisasowen.heartbeat.utils.Utils;

import java.net.InetSocketAddress;
import java.util.Arrays;
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
            String uuid = UUID.randomUUID().toString();
            return new HeartBeatCommand(address, Utils.stringToSocketAddress(s),  command.getCommandName(), uuid, command.getData());
        }).collect(Collectors.toList());

        sendAllCommands(commands);
    }

    public void send(String command, List<String> peers){
        List<HeartBeatCommand> commands = peers.stream().map(s -> {
            String uuid = UUID.randomUUID().toString();
            return new HeartBeatCommand(address,Utils.stringToSocketAddress(s),  command, uuid);
        }).collect(Collectors.toList());

        sendAllCommands(commands);
    }

    public void send2(SimpleHeartBeatCommand command, List<InetSocketAddress> peers){
        List<HeartBeatCommand> commands = peers.stream().map(s -> {
            String uuid = UUID.randomUUID().toString();
            return new HeartBeatCommand(address,s,  command.getCommandName(), uuid, command.getData());
        }).collect(Collectors.toList());

        sendAllCommands(commands);
    }

    public void send2(String command, List<InetSocketAddress> peers){
        List<HeartBeatCommand> commands = peers.stream().map(s -> {
            String uuid = UUID.randomUUID().toString();
            return new HeartBeatCommand(address,s,  command, uuid);
        }).collect(Collectors.toList());

        sendAllCommands(commands);
    }

    public void send(String command, InetSocketAddress... peers){
        List<HeartBeatCommand> commands = Arrays.asList(peers)
                .stream()
                .map(s -> {
            String uuid = UUID.randomUUID().toString();
            return new HeartBeatCommand(address,s,  command, uuid);
        }).collect(Collectors.toList());

        sendAllCommands(commands);
    }

    private void sendAllCommands(List<HeartBeatCommand> commands){
        commands.forEach((s) ->{
            SimplePingStore.sendAndSave(s);

        });

    }

}
