package io.atalisasowen.heartbeat.command;

import java.net.InetSocketAddress;

public class HeartBeatCommand {
    public static final byte SEPARATOR = (byte)':';
    private InetSocketAddress source;
    private String commandName;
    private String commandUuid;
    private byte[] data;

    public HeartBeatCommand(InetSocketAddress source) {
        this.source = source;
    }

    public HeartBeatCommand(InetSocketAddress source, String commandName, String commandUuid) {
        this.source = source;
        this.commandName = commandName;
        this.commandUuid = commandUuid;
    }

    public HeartBeatCommand(InetSocketAddress source, String commandName, String commandUuid, byte[] data) {
        this.source = source;
        this.commandName = commandName;
        this.commandUuid = commandUuid;
        this.data = data;
    }

    public InetSocketAddress getSource() {
        return source;
    }

    public void setSource(InetSocketAddress source) {
        this.source = source;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandUuid() {
        return commandUuid;
    }

    public void setCommandUuid(String commandUuid) {
        this.commandUuid = commandUuid;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HeartBeatCommand{" +
                "commandName='" + commandName + '\'' +
                ", commandUuid='" + commandUuid + '\'' +
                '}';
    }
}
