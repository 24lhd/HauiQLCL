package com.lhd.object;

/**
 * Created by root on 9/14/16.
 */

public class ItemBangDiemThanhPhan {
    private String msv;
    private String linkDiemMon;
    private String tenSv;
    private String d1;
    private String d2;
    private String d3;
    private String d4;
    private String soTietNghi;
    private String dTB;
    private String dieuKien;
    public String getMsv() {
        return msv;
    }
    public String getLinkDiemMon() {
        return linkDiemMon;
    }
    public String getTenSv() {
        return tenSv;
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
    public String getD4() {
        return d4;
    }
    public String getSoTietNghi() {
        return soTietNghi;
    }
    public String getdTB() {
        return dTB;
    }
    public String getDieuKien() {
        return dieuKien;
    }
    public ItemBangDiemThanhPhan(String msv, String linkDiemMon, String tenSv, String d1, String d2, String d3, String d4, String soTietNghi, String dTB, String dieuKien) {
        this.msv = msv;
        this.linkDiemMon = linkDiemMon;
        this.tenSv = tenSv;
        this.d1 = d1;
        this.d2 = d2;
        this.d3 = d3;
        this.d4 = d4;
        this.soTietNghi = soTietNghi;
        this.dTB = dTB;
        this.dieuKien = dieuKien;
    }

    @Override
    public String toString() {
        return
                 tenSv +
                        "\n(" + msv + ")" +
                "\nĐiểm 1: " + d1 +
                "\nĐiểm 2: " + d2 +
                "\nĐiểm 3: " + d3 +
                "\nĐiểm 2: " + d4 +
                "\nĐiểm TB: " + dTB +    "\nNghỉ " + soTietNghi +
                "\n" + dieuKien  ;
    }
}
