package io.atalisasowen.heartbeat.store;

import io.atalisasowen.heartbeat.command.HeartBeatCommand;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingDeque;

public class SimplePingStore {
    public final static ConcurrentHashMap<String, HeartBeatCommand> PING_COMMANDS = new ConcurrentHashMap<>();
    public final static BlockingDeque<HeartBeatCommand> SENDING_QUEUE = new LinkedBlockingDeque<HeartBeatCommand>();
}
