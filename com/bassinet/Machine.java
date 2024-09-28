package com.bassinet;

import java.util.*;

/**
 *      A machine contains wakeUp() method and shutDown() method;
 *      An abstraction of the physical machine you want to control;
 */

public class Machine implements WakeUpController, ShutDownController {

    // machineName, osType, start, shut can be read from timetable.json.bak;
    public String machineName;
    public String osType;
    public String MAC;

    // {HOUR,MINUTE,SECOND}
    // for example: {17,0,0} stands for 5 PM local time;
    public int[] startTime;
    public int[] shutTime;

    // isLocal decides whether to enable timerTaskShut() object;
    public boolean isLocal;

    // json init
    public Machine() {
        // empty constructor for
    }

    // init
    public Machine(String machineName, String osType, String MAC, int[] startTime, int[] shutTime, boolean isLocal) throws RuntimeException{

        if (TimetableInit(startTime) && TimetableInit(shutTime)) {
            this.machineName = machineName;
            this.osType = osType;
            this.MAC = MAC;
            this.startTime = startTime;
            this.shutTime = shutTime;
            this.isLocal = isLocal;
        } else throw new RuntimeException("Illegal timetable value.");

    }

    public Machine(Machine machine) {
        this.machineName = machine.machineName;
        this.osType = machine.osType;
        this.MAC = machine.MAC;
        this.startTime = machine.startTime;
        this.shutTime = machine.shutTime;
        this.isLocal = machine.isLocal;
        this.timer = machine.timer;
        this.timerTaskStart = machine.timerTaskStart;
        this.timerTaskShut = machine.timerTaskShut;
    }

    private static boolean TimetableInit(int[] time) {
        // 0<=time[0]<24; 0<=time[1]<60; 0<=time[2]<60;
        if (!(0 <= time[0] && time[0] < 24)) {  // Hour
            return false;
        } else if (!(0 <= time[1] && time[1] < 60)) {   // Min
            return false;
        } else return 0 <= time[2] && time[2] < 60; // Sec
    }

    // Timer used for shutDown and wakeUp;
    Timer timer = new Timer(true);
    TimerTask timerTaskStart = new TimerTask() {
        @Override
        public void run() {
            shutDown(osType);
        }
    };
    TimerTask timerTaskShut = new TimerTask() {
        @Override
        public void run() {
            wakeUp(MAC);
        }
    };

    // .enable()
    // default Date(99,Calendar.JANUARY,1,startTime[0],startTime[1],startTime[2]));
    // 2089/01/01/HH/MM/SS;
    public void enable() throws RuntimeException {
        try {
            timer.schedule(timerTaskStart, new Date(99, Calendar.JANUARY, 1, startTime[0], startTime[1], startTime[2]));
            if (isLocal && !(Arrays.equals(startTime,shutTime))) {
                timer.schedule(timerTaskShut, new Date(99, Calendar.JANUARY, 1, shutTime[0], shutTime[1], shutTime[2]));
            }
            System.out.println(this);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return(
                this.machineName
                        + "@" + this.osType
                        + ":" + MAC
                        + " | " + Arrays.toString(startTime) + "~" + Arrays.toString(shutTime)
                );
    }

}
