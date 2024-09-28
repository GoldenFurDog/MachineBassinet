package org.bassinet;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        // machines List
        final List<Machine> machines = TimetableReader.loadMachines();

        if (machines != null && !(machines.isEmpty())) {    // machines exist and is not empty
            for (Machine machine : machines) {
                // enable all machines and print the information on the screen
                machine.enable();
                System.out.println(machine);
            }
            System.out.println("Bassinet Ready.");
        } else throw new IOException();

        
    }

}
