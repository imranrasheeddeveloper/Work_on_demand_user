package com.rizorsiumani.user.utils;

import java.util.List;

public class TimeSlot {
    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;
    private boolean isTaken;

    public TimeSlot(int startHour, int startMinute, int endHour, int endMinute) {
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public int getEndHour() {
        return endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }


    public boolean isTaken() {
        return isTaken;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }

    public static boolean isTimeSlotTaken(List<TimeSlot> timeSlots, int startHour, int startMinute, int endHour, int endMinute) {
        for (TimeSlot timeSlot : timeSlots) {
            if ((timeSlot.getEndHour() == endHour && timeSlot.getEndMinute() == endMinute) ||
                    (timeSlot.getStartHour() < endHour && timeSlot.getEndHour() > startHour) ||
                    (timeSlot.getStartHour() == startHour && timeSlot.getEndMinute() > startMinute) ||
                    (timeSlot.getEndHour() == endHour && timeSlot.getStartMinute() < endMinute)) {
                // The time slot is already taken
                return true;
            }
        }
        // The time slot is available
        return false;
    }

}