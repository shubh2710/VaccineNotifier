package com.shubh.VaccineNotifire;

import java.util.Objects;

public class AreaInfo {
    int pin;
    String date;

    public AreaInfo(int pin, String date) {
        this.pin = pin;
        this.date = date;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AreaInfo areaInfo = (AreaInfo) o;
        return pin == areaInfo.pin &&
                Objects.equals(date, areaInfo.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pin, date);
    }
}
