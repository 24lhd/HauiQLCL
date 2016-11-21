package com.lhd.item;

import java.io.Serializable;

/**
 * Created by Faker on 8/29/2016.
 */

public class DiemThiTheoMon implements Serializable{
    private String linkDiemThiTheoLop,
            tenMon,
            dLan1,
            dTKLan1,
            dLan2,
            dTKLan2,
            dCuoiCung,
            ngay1,
            ngay2,
            ghiChu;

    @Override
    public String toString() {
        return
                tenMon +"=" + dCuoiCung  ;
    }

    public String getLinkDiemThiTheoLop() {
        return linkDiemThiTheoLop;
    }

    public String getTenMon() {
        return tenMon;
    }

    public String getdLan1() {
        return dLan1;
    }

    public String getdTKLan1() {
        return dTKLan1;
    }

    public String getdLan2() {
        return dLan2;
    }

    public String getdTKLan2() {
        return dTKLan2;
    }

    public String getdCuoiCung() {
        return dCuoiCung;
    }

    public String getNgay1() {
        return ngay1;
    }

    public String getNgay2() {
        return ngay2;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public DiemThiTheoMon(String linkDiemThiTheoLop, String tenMon, String dLan1, String  dLan2, String dTKLan1, String dTKLan2, String dCuoiCung, String ngay1, String ngay2, String ghiChu) {

        this.linkDiemThiTheoLop = linkDiemThiTheoLop;
        this.tenMon = tenMon;
        this.dLan1 = dLan1;
        this.dTKLan1 = dTKLan1;
        this.dLan2 = dLan2;
        this.dTKLan2 = dTKLan2;
        this.dCuoiCung = dCuoiCung;
        this.ngay1 = ngay1;
        this.ngay2 = ngay2;
        this.ghiChu = ghiChu;
    }
}
