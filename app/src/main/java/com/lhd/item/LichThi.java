package com.lhd.item;

/**
 * Created by Faker on 8/29/2016.
 */

public class LichThi {
    private String mon,thu,ngay,gio,sbd,lanthi,phong,diaDiem,ghiChu;

    public LichThi(String mon, String thu, String ngay, String gio, String sbd, String lanthi, String phong, String diaDiem, String ghiChu) {
        this.mon = mon;
        this.thu = thu;
        this.ngay = ngay;
        this.gio = gio;
        this.sbd = sbd;
        this.lanthi = lanthi;
        this.phong = phong;
        this.diaDiem = diaDiem;
        this.ghiChu = ghiChu;
    }

    public String getMon() {
        return mon;
    }

    public String getThu() {
        return thu;
    }

    public String getNgay() {
        return ngay;
    }

    public String getGio() {
        return gio;
    }

    public String getSbd() {
        return sbd;
    }

    public String getLanthi() {
        return lanthi;
    }

    public String getPhong() {
        return phong;
    }

    public String getDiaDiem() {
        return diaDiem;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    @Override
    public String toString() {
        return    "Lịch thi "+  getMon()+" "+
                  thu +" "+ngay +" "+ gio +
                " Số báo danh: " + sbd +
                " Thi lần: " + lanthi +
                " Phòng: " + phong +
                " Tại " + diaDiem +" "+
                  ghiChu
                 ;
    }
}
