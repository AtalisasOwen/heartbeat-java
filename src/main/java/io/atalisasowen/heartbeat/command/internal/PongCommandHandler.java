package io.atalisasowen.heartbeat.command.internal;

import io.atalisasowen.heartbeat.command.HeartBeatCommand;
import io.atalisasowen.heartbeat.command.HeartBeatCommandHandler;
import io.atalisasowen.heartbeat.store.SimplePingStore;

public class PongCommandHandler implements HeartBeatCommandHandler {
    @Override
    public void handleCommand(HeartBeatCommand command) {
        System.out.println("Getting Pong: " + System.currentTimeMillis());
        HeartBeatCommand ping = SimplePingStore.PING_COMMANDS.get(command.getCommandUuid());

    }
}
