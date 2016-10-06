package com.ken.hauiclass.item;

import java.io.Serializable;

/**
 * Created by root on 9/13/16.
 */

public class ItemBangKetQuaHocTap implements Serializable{
    private String linkDiemLop,
            linkLichThiLop,
            tenMon,
            maMon,
            d1,
            d2,
            d3,
            dGiua,
            dTB,
            soTietNghi,
            dieuKien;

    public void setLinkDiemLop(String linkDiemLop) {
        this.linkDiemLop = linkDiemLop;
    }

    public void setLinkLichThiLop(String linkLichThiLop) {
        this.linkLichThiLop = linkLichThiLop;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public void setMaMon(String maMon) {
        this.maMon = maMon;
    }

    public void setD1(String d1) {
        this.d1 = d1;
    }

    public void setD2(String d2) {
        this.d2 = d2;
    }

    public void setD3(String d3) {
        this.d3 = d3;
    }

    public void setdGiua(String dGiua) {
        this.dGiua = dGiua;
    }

    public void setdTB(String dTB) {
        this.dTB = dTB;
    }

    public void setSoTietNghi(String soTietNghi) {
        this.soTietNghi = soTietNghi;
    }

    public void setDieuKien(String dieuKien) {
        this.dieuKien = dieuKien;
    }

    public String getLinkDiemLop() {

        return linkDiemLop;
    }

    public String getLinkLichThiLop() {
        return linkLichThiLop;
    }

    public String getTenMon() {
        return tenMon;
    }

    public String getMaMon() {
        return maMon;
    }

    public String getD1() {
        return d1;
    }

    public String getD2() {
        return d2;
    }

    public String getD3() {
        return d3;
    }

    public String getdGiua() {
        return dGiua;
    }

    public String getdTB() {
        return dTB;
    }

    public String getSoTietNghi() {
        return soTietNghi;
    }

    public String getDieuKien() {
        return dieuKien;
    }

    public ItemBangKetQuaHocTap(String linkDiemLop, String linkLichThiLop, String tenMon, String maMon, String d1, String d2, String d3, String dGiua, String dTB, String soTietNghi, String dieuKien) {

        this.linkDiemLop = linkDiemLop;
        this.linkLichThiLop = linkLichThiLop;
        this.tenMon = tenMon;
        this.maMon = maMon;
        this.d1 = d1;
        this.d2 = d2;
        this.d3 = d3;
        this.dGiua = dGiua;
        this.dTB = dTB;
        this.soTietNghi = soTietNghi;
        this.dieuKien = dieuKien;
    }
}
