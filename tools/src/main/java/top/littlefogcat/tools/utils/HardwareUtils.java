package top.littlefogcat.tools.utils;

import java.net.NetworkInterface;
import java.net.SocketException;

/**
 * Author: LittleFogCat
 * Email: littlefogcat@foxmail.com
 */
public class HardwareUtils {
    public static String getMAC() {
        String macAddress;
        StringBuilder buf = new StringBuilder();
        NetworkInterface networkInterface;
        try {
            networkInterface = NetworkInterface.getByName("eth0");
            if (networkInterface == null) {
                networkInterface = NetworkInterface.getByName("wlan0");
            }
            if (networkInterface == null) {
                return null;
            }
            byte[] addr = networkInterface.getHardwareAddress();
            for (byte b : addr) {
                buf.append(String.format("%02X", b)).append(":");
            }
            if (buf.length() > 0) {
                buf.deleteCharAt(buf.length() - 1);
            }
            macAddress = buf.toString();
        } catch (SocketException e) {
            e.printStackTrace();
            return null;
        }
        return macAddress;
    }
}
