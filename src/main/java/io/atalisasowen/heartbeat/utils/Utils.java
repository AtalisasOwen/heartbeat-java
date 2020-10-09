package io.atalisasowen.heartbeat.utils;

import java.net.InetSocketAddress;

public class Utils {
    public static String socketAddress2String(InetSocketAddress address){
        return address.getHostString() + ":" + address.getPort();
    }

    public static InetSocketAddress stringToSocketAddress(String s){
        String[] ss = s.split(":");
        String ip = ss[0];
        int port = Integer.parseInt(ss[1]);
        return new InetSocketAddress(ip, port);
    }
}
