package com.lhd.item;

/**
 * Created by Faker on 8/29/2016.
 */

public class LichThiLop {
    private String maLop;
    private String tenMon;
    private String ngayThi;
    private String gioThi;
    private String lanThi;
    private String tenLop;
    private String khoa;

    @Override
    public String toString() {
        return  "Kế hoạch thi \n" + tenMon  +
                "(" + maLop +")"+
                "\nNgày: " + ngayThi +
                "\nLúc" + gioThi ;
    }

    public String getMaLop() {
        return maLop;
    }
    public String getTenMon() {
        return tenMon;
    }
    public String getNgayThi() {
        return ngayThi;
    }
    public String getGioThi() {
        return gioThi;
    }
    public String getLanThi() {
        return lanThi;
    }
    public String getTenLop() {
        return tenLop;
    }
    public String getKhoa() {
        return khoa;
    }
    public LichThiLop(String maLop, String tenMon, String ngayThi, String gioThi, String lanThi, String tenLop, String khoa) {
        this.maLop = maLop;
        this.tenMon = tenMon;
        this.ngayThi = ngayThi;
        this.gioThi = gioThi;
        this.lanThi = lanThi;
        this.tenLop = tenLop;
        this.khoa = khoa;
    }


}
