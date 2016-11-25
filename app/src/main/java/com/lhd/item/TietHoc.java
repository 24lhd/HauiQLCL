package com.lhd.item;

/**
 * Created by Duong on 11/24/2016.
 */

public class TietHoc {
    int tiet;
    int phutBatDau;
    int phutKetThuc;

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
