package com.bassinet;

import java.io.*;
import java.net.*;

/**
 *      "Wake-on-LAN" should be enabled in BIOS and your system;
 *      This program tries to send a WOL Pack to the port 9 of the machine you want;
 *      WOL Pack includes 6 "FF" bytes and 16 target MAC bytes;
 */


public interface WakeUpController {

    // SendWOLPack(FF:FF:FF:FF:FF:FF);
    default void wakeUp (String macAddress) {
        // WOL uses udp protocol
        try (DatagramSocket sockClient = new DatagramSocket()) {
            // Broadcast:9
            InetAddress broadcast = InetAddress.getByName("255.255.255.255");
            // byte macAddress used in WOL packs
            byte[] macBytes = macAddress.replaceAll(":", "").toUpperCase().getBytes();

            // build wolPack
            byte[] wolPack = new byte[6 + macBytes.length * 16];
            for (int i = 0; i < 6; i++) {
                wolPack[i] = (byte) 0xff;       // six times "FF"
            }
            for (int i = 6; i < wolPack.length; i += macBytes.length) {
                System.arraycopy(macBytes, 0, wolPack, i, macBytes.length);     // 16 times MAC Address
            }

            // send the WOL Pack to 255.255.255.255:9
            DatagramPacket WOLPack = new DatagramPacket(wolPack, wolPack.length, broadcast, 9);
            //sockClient.setSoTimeout(2000);
            sockClient.send(WOLPack);

        } catch (IOException e) {
            System.out.println("SendWOLPack() went wrong.");
            e.printStackTrace();
        }

    }

}
