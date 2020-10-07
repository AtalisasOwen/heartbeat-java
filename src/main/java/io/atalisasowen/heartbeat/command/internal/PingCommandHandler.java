package io.atalisasowen.heartbeat.command.internal;

import io.atalisasowen.heartbeat.command.HeartBeatCommand;
import io.atalisasowen.heartbeat.command.HeartBeatCommandHandler;
import io.atalisasowen.heartbeat.store.SimplePingStore;

public class PingCommandHandler implements HeartBeatCommandHandler {
    @Override
    public void handleCommand(HeartBeatCommand command) {
        System.out.println(System.currentTimeMillis() + ": Getting Ping from " + command.getSrcAddr());
        HeartBeatCommand pong = new HeartBeatCommand(command.getSrcAddr(), command.getSrcAddr(), "PONG", command.getCommandUuid());
        SimplePingStore.SENDING_QUEUE.offer(pong);
    }
}
