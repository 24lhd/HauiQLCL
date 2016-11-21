package com.lhd.item;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by root on 9/12/16.
 */

public class KetQuaHocTap implements Serializable {
    private SinhVien sinhVien;
    private ArrayList<ItemBangKetQuaHocTap> itemBangKetQuaHocTaps;

    public KetQuaHocTap(SinhVien sinhVien, ArrayList<ItemBangKetQuaHocTap> itemBangKetQuaHocTaps) {
        this.sinhVien = sinhVien;
        this.itemBangKetQuaHocTaps = itemBangKetQuaHocTaps;
    }

    public void setSinhVien(SinhVien sinhVien) {
        this.sinhVien = sinhVien;
    }

    public void setItemBangKetQuaHocTaps(ArrayList<ItemBangKetQuaHocTap> itemBangKetQuaHocTaps) {
        this.itemBangKetQuaHocTaps = itemBangKetQuaHocTaps;
    }

    public SinhVien getSinhVien() {
        return sinhVien;
    }

    public ArrayList<ItemBangKetQuaHocTap> getBangKetQuaHocTaps() {
        return itemBangKetQuaHocTaps;
    }

    @Override
    public String toString() {
        return "KetQuaHocTap{" +
                "sinhVien=" + sinhVien +
                ", ItemBangKetQuaHocTaps=" + itemBangKetQuaHocTaps +
                '}';
    }
}
