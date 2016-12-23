package com.lhd.object;

/**
 * Created by d on 23/12/2016.
 */

public class DiemChu {
    private String name;
    private double start;
    private double end;
    public DiemChu(String name, double start, double end) {
        this.name = name;
        this.start = start;
        this.end = end;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getStart() {
        return start;
    }
    public void setStart(double start) {
        this.start = start;
    }
    public double getEnd() {
        return end;
    }
    public void setEnd(double end) {
        this.end = end;
    }
}
