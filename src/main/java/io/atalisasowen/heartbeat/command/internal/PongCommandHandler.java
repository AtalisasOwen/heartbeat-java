package io.atalisasowen.heartbeat.command.internal;

import io.atalisasowen.heartbeat.command.HeartBeatCommand;
import io.atalisasowen.heartbeat.command.HeartBeatCommandHandler;
import io.atalisasowen.heartbeat.store.SimplePingStore;

public class PongCommandHandler implements HeartBeatCommandHandler {
    @Override
    public void handleCommand(HeartBeatCommand command) {
        HeartBeatCommand ping = SimplePingStore.getHistoryCommand(command.getCommandUuid());
        // System.out.println(System.currentTimeMillis() + ": Getting Pong from " + ping.getDstAddr());
        SimplePingStore.addAliveNode(ping.getDstAddr());
    }
}
