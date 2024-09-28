package com.bassinet;

import java.io.*;

/**
 *      This program support Windows, Linux and macOS;
 *      shutdown after 1 min;
 */

public interface ShutDownController {

    default void shutDown(String osName) {

        // getOSNameInt();
        final int osNameInt = getOSNameInt(osName);

        // getShutdownCommand();
        final String shutdownCommand = getShutdownCommand(osNameInt);

        ProcessBuilder processBuilder = new ProcessBuilder(
                shutdownCommand.split(" ")
        );

        // stderr
        processBuilder.redirectErrorStream(true);

        // execute
        try {
            Process process = processBuilder.start();       // return Process

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())     // stdin reader
            );
            // .readLine()
            String line;
            if ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getShutdownCommand(int osNameInt) {
        // shutdown after 1 minute;
        int shutdownTime = 60;
        String shutdownTimeString;

        // shutdown -s -t 60; Windows
        // shutdown -h +1 minute; Linux/macOS
        return switch (osNameInt) {
            case 1 -> {
                shutdownTimeString = String.valueOf(shutdownTime);
                yield "shutdown -s -t " + shutdownTimeString;    // Windows
            }
            case 2 -> {
                shutdownTimeString = String.valueOf(shutdownTime / 60);   // toMinute
                yield "shutdown -h " + shutdownTimeString + "minute";    // Linux
            }
            case 3 -> {
                shutdownTimeString = String.valueOf(shutdownTime / 60);   // toMinute
                yield "shutdown -h " + shutdownTimeString + "minute";    // macOS
            }
            default -> {
                shutdownTimeString = String.valueOf(shutdownTime / 60);     // toMinute
                yield "shutdown -h " + shutdownTimeString + "minute";   // default
            }
        };
    }

    private static int getOSNameInt(String osName) {
        return switch (osName) {
            case "Windows" -> (1);
            case "macOS" -> (3);
            default -> (2);     // Linux
        };
    }

}
