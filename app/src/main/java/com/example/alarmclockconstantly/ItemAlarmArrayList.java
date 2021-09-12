package com.example.alarmclockconstantly;

public class ItemAlarmArrayList {
    private String endTime = null;
    private String wasGone = null;

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getWasGone() {
        return wasGone;
    }

    public void setWasGone(String wasGone) {
        this.wasGone = wasGone;
    }

    @Override
    public String toString() {
        return "ItemAlarmArrayList{" +
                "endTime='" + endTime + '\'' +
                ", wasGone='" + wasGone + '\'' +
                '}';
    }
}
