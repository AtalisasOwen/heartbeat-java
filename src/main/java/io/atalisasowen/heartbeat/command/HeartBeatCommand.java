package io.atalisasowen.heartbeat.command;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.Objects;

public class HeartBeatCommand {
    public static final byte SEPARATOR = (byte)':';
    private InetSocketAddress srcAddr;
    private InetSocketAddress dstAddr;
    private String commandName;
    private String commandUuid;
    private byte[] data;

    public HeartBeatCommand(InetSocketAddress srcAddr, InetSocketAddress dstAddr) {
        this.srcAddr = srcAddr;
        this.dstAddr = dstAddr;
    }

    public HeartBeatCommand(InetSocketAddress srcAddr,InetSocketAddress dstAddr, String commandName, String commandUuid) {
        this.srcAddr = srcAddr;
        this.dstAddr = dstAddr;
        this.commandName = commandName;
        this.commandUuid = commandUuid;
    }

    public HeartBeatCommand(InetSocketAddress srcAddr, InetSocketAddress dstAddr, String commandName, String commandUuid, byte[] data) {
        this.srcAddr = srcAddr;
        this.dstAddr = dstAddr;
        this.commandName = commandName;
        this.commandUuid = commandUuid;
        this.data = data;
    }

    public InetSocketAddress getSrcAddr() {
        return srcAddr;
    }

    public void setSrcAddr(InetSocketAddress srcAddr) {
        this.srcAddr = srcAddr;
    }

    public InetSocketAddress getDstAddr() {
        return dstAddr;
    }

    public void setDstAddr(InetSocketAddress dstAddr) {
        this.dstAddr = dstAddr;
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
                "srcAddr=" + srcAddr +
                ", dstAddr=" + dstAddr +
                ", commandName='" + commandName + '\'' +
                ", commandUuid='" + commandUuid + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeartBeatCommand command = (HeartBeatCommand) o;
        return Objects.equals(srcAddr, command.srcAddr) &&
                Objects.equals(dstAddr, command.dstAddr) &&
                commandName.equals(command.commandName) &&
                commandUuid.equals(command.commandUuid) &&
                Arrays.equals(data, command.data);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(srcAddr, dstAddr, commandName, commandUuid);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }
}
