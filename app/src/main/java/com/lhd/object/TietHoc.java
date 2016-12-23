package com.lhd.object;

/**
 * Created by d on 23/12/2016.
 */

public class TietHoc {
    private int tiet;
    private int phutBatDau;
    private int phutKetThuc;

    public TietHoc(int tiet, int phutBatDau, int phutKetThuc) {
        this.tiet = tiet;
        this.phutBatDau = phutBatDau;
        this.phutKetThuc = phutKetThuc;
    }

    public int getTiet() {
        return tiet;
    }

    public void setTiet(int tiet) {
        this.tiet = tiet;
    }

    public int getPhutBatDau() {
        return phutBatDau;
    }

    public void setPhutBatDau(int phutBatDau) {
        this.phutBatDau = phutBatDau;
    }

    public int getPhutKetThuc() {
        return phutKetThuc;
    }

    public void setPhutKetThuc(int phutKetThuc) {
        this.phutKetThuc = phutKetThuc;
    }
}