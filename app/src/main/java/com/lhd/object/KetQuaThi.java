package com.lhd.object;

import java.util.ArrayList;

/**
 * Created by Faker on 9/16/2016.
 */

public class KetQuaThi {
    private String tenLopUuTien;
    private String soTC;
    private ArrayList<ItemKetQuaThiLop> ketQuaThiLops;

    public String getTenLopUuTien() {
        return tenLopUuTien;
    }

    public String getSoTC() {
        return soTC;
    }

    public ArrayList<ItemKetQuaThiLop> getKetQuaThiLops() {
        return ketQuaThiLops;
    }

    public KetQuaThi(String tenLopUuTien, String soTC, ArrayList<ItemKetQuaThiLop> ketQuaThiLops) {

        this.tenLopUuTien = tenLopUuTien;
        this.soTC = soTC;
        this.ketQuaThiLops = ketQuaThiLops;
    }
}
