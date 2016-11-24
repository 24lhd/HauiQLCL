package com.lhd.item;

/**
 * Created by Duong on 11/24/2016.
 */

public class TietHoc {
    int tiet;
    int gioBatDau;
    int phutBatDau;
    int gioKetThuc;
    int phutKetThuc;

    @Override
    public String toString() {
        return tiet +" "+gioBatDau +
                "h" + phutBatDau +
                "         " + gioKetThuc +
                "h" + phutKetThuc;
    }

    public int getTiet() {
        return tiet;
    }
    public int getGioBatDau() {
        return gioBatDau;
    }
    public int getPhutBatDau() {
        return phutBatDau;
    }
    public int getGioKetThuc() {
        return gioKetThuc;
    }


    public int getPhutKetThuc() {
        return phutKetThuc;
    }


    public TietHoc(int tiet, int gioBatDau, int phutBatDau, int gioKetThuc, int phutKetThuc) {
        this.tiet = tiet;
        this.gioBatDau = gioBatDau;
        this.phutBatDau = phutBatDau;
        this.gioKetThuc = gioKetThuc;
        this.phutKetThuc = phutKetThuc;
    }
}
