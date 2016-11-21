package com.lhd.item;

/**
 * Created by Faker on 8/29/2016.
 */

public class ItemKetQuaThiLop {
    private String msv,linkKetQuaThiTheoMon,ten,dL1,dL2,ghiChu;

    @Override
    public String toString() {
        return "DiemThiTheoLop{" +
                "msv='" + msv + '\'' +
                ", linkKetQuaThiTheoMon='" + linkKetQuaThiTheoMon + '\'' +
                ", ten='" + ten + '\'' +
                ", dL1='" + dL1 + '\'' +
                ", dL2='" + dL2 + '\'' +
                ", ghiChu='" + ghiChu + '\'' +
                '}';
    }

    public String getMsv() {
        return msv;
    }

    public String getLinkKetQuaThiTheoMon() {
        return linkKetQuaThiTheoMon;
    }

    public String getTen() {
        return ten;
    }

    public String getdL1() {
        return dL1;
    }

    public String getdL2() {
        return dL2;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public ItemKetQuaThiLop(String msv, String linkKetQuaThiTheoMon, String ten, String dL1, String dL2, String ghiChu) {
        if (ghiChu.split(" ").length>1){
            ghiChu=ghiChu.split(" ")[0]+" "+ghiChu.split(" ")[1]+" "+ghiChu.split(" ")[2];
        }
        this.msv = msv;
        this.linkKetQuaThiTheoMon = linkKetQuaThiTheoMon;
        this.ten = ten;
        this.dL1 = dL1;
        this.dL2 = dL2;
        this.ghiChu = ghiChu;
    }
}
