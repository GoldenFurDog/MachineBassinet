package org.bassinet;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;
import java.io.*;

/**
 *      read the timetable.json file;
 *      create one if timetable.json not exist;
 *      return machines List;
 */

public class TimetableReader {

    private List<Machine> machines;

    public static List<Machine> loadMachines() throws IOException {

        // path to timetable.json
        final String timetablePath = "timetable.json";

        ObjectMapper objectMapper = new ObjectMapper();

        // read
        try {

            TimetableReader timetableReader = objectMapper.readValue(new File(timetablePath), TimetableReader.class);
            return timetableReader.getMachines();

        } catch (IOException e) {
            if (new File(timetablePath).createNewFile()) {
                System.out.println("timetable.json is generated.");
                throw(new IOException());
            } else {
                System.out.println("Permission denied. Can't read timetable file.");
                throw(new IOException());
            }
        }
    }

    public List<Machine> getMachines() {
        return machines;
    }

}
