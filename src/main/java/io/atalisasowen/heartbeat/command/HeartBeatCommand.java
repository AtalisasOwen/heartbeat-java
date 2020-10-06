package io.atalisasowen.heartbeat.command;

import io.atalisasowen.heartbeat.network.HeartBeatProtocol;

public interface HeartBeatCommand {
    HeartBeatProtocol getProtocol();
    void sendCommand();
    void onCommandReceived();
    void onCommandSentTimeout();
}
