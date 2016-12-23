package com.lhd.object;

import java.util.ArrayList;

/**
 * Created by root on 9/12/16.
 */

public class BangDiemThanhPhan {
    public BangDiemThanhPhan(String maLopDL, String tenLopUuTien, String soTin, ArrayList<ItemBangDiemThanhPhan> bangDiemThanhPhen) {
        this.maLopDL = maLopDL;
        this.tenLopUuTien = tenLopUuTien;
        this.soTin = soTin;
        this.bangDiemThanhPhen = bangDiemThanhPhen;
    }

    private String maLopDL;
    private String tenLopUuTien;
    private String soTin;
    private ArrayList<ItemBangDiemThanhPhan> bangDiemThanhPhen;
    public String getTenLopUuTien() {

        return tenLopUuTien;
    }

    public String getSoTin() {
        return soTin;
    }

    public String getMaLopDL() {
        return maLopDL;
    }

    public ArrayList<ItemBangDiemThanhPhan> getBangDiemThanhPhan() {
        return bangDiemThanhPhen;
    }


}
