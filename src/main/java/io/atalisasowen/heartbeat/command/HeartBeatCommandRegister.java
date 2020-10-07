package io.atalisasowen.heartbeat.command;

import io.atalisasowen.heartbeat.exception.HandlerNotExistException;

import java.util.concurrent.ConcurrentHashMap;

public class HeartBeatCommandRegister {

    private HeartBeatCommandRegister(){}

    private static final ConcurrentHashMap<String, HeartBeatCommandHandler> COMMAND_HANDLERS = new ConcurrentHashMap<>();

    public static void addHandler(String commandName, HeartBeatCommandHandler handler){
        COMMAND_HANDLERS.putIfAbsent(commandName, handler);
    }

    public static HeartBeatCommandHandler getHandler(String commandName) {
        HeartBeatCommandHandler handler = COMMAND_HANDLERS.get(commandName);
        if (handler == null){
            throw new HandlerNotExistException("Don't find CommandHandler for Command: " + commandName);
        }
        return handler;
    }


}
