package com.lhd.object;

import java.io.Serializable;

/**
 * Created by root on 9/12/16.
 */

public class SinhVien implements Serializable {
    public String tenSV,maSV,lopDL;

    public SinhVien() {
    }

    public SinhVien(String tenSV, String maSV, String lopDL) {
        this.tenSV = tenSV;
        this.maSV = maSV;
        this.lopDL = lopDL;
    }

    public void setTenSV(String tenSV) {
        this.tenSV = tenSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public void setLopDL(String lopDL) {
        this.lopDL = lopDL;
    }

    public String getTenSV() {
        return tenSV;
    }

    public String getMaSV() {
        return maSV;
    }

    public String getLopDL() {
        return lopDL;
    }

    @Override
    public String toString() {
        return "SinhVien{" +
                "tenSV='" + tenSV + '\'' +
                ", maSV='" + maSV + '\'' +
                ", lopDL='" + lopDL + '\'' +
                '}';
    }
}
