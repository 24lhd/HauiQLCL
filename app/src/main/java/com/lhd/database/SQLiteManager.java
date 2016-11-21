package com.lhd.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lhd.item.DiemThanhPhan;
import com.lhd.item.DiemThiTheoMon;
import com.lhd.item.ItemBangDiemThanhPhan;
import com.lhd.item.ItemBangKetQuaHocTap;
import com.lhd.item.ItemKetQuaThiLop;
import com.lhd.item.KetQuaThi;
import com.lhd.item.LichThi;
import com.lhd.item.LichThiLop;
import com.lhd.item.SinhVien;

import java.util.ArrayList;

/**
 * Created by Faker on 9/5/2016.
 */
public class SQLiteManager {
    //le hong duong
    public static final String CREATE_TABLE_DLOP="CREATE TABLE IF NOT EXISTS `dlop`(" +
            "`stt`INTEGER PRIMARY KEY AUTOINCREMENT," +
            "`maLop`TEXT ," +
            "`masv`TEXT ," +
            "`soTC`TEXT ," +
            "`linkDiemMon`TEXT ," +
            "`tenSv`TEXT," +
            "`d1`TEXT," +
            "`d2`TEXT," +
            "`d3`TEXT," +
            "`d4`TEXT," +
            "`soTietNghi`TEXT," +
            "`dTB`TEXT," +
            "`dieuKien`TEXT," +
            "`tenLop`TEXT);";
    public static final String CREATE_TABLE_DMON="CREATE TABLE IF NOT EXISTS `dmon`(" +
            "`stt`INTEGER PRIMARY KEY AUTOINCREMENT," +
            "`linkDiemLop`TEXT NOT NULL," +
            "`linkLichThiLop`TEXT NOT NULL," +
            "`maSV`TEXT," +
            "`tenMon`TEXT," +
            "`maMon`TEXT," +
            "`d1`TEXT," +
            "`d2`TEXT, " +
            "`d3`TEXT," +
            "`dGiuaKi`TEXT," +
            "`dTB`TEXT," +
            "`soTietNghi`TEXT," +
            "`dieuKien`TEXT);";
    public static final String CREATE_TABLE_SV="CREATE TABLE IF NOT EXISTS `sv`(" +
            "`stt`INTEGER PRIMARY KEY AUTOINCREMENT," +
            "`tenSV`TEXT," +
            "`maSV`TEXT," +
            "`lopDL`TEXT);";
    public static final String CREATE_TABLE_DTHIMON="CREATE TABLE IF NOT EXISTS `dthimon` (`stt`INTEGER PRIMARY KEY AUTOINCREMENT," +
            "`linkDiemThiTheoLop`TEXT NOT NULL," +
            "`maSV`TEXT NOT NULL," +
            "`tenMon`TEXT NOT NULL," +
            "`dLan1`TEXT," +
            "`dTKLan1`TEXT," +
            "`dLan2`TEXT," +
            "`dTKLan2`TEXT," +
            "`dCuoiCung`TEXT," +
            "`ngay1`TEXT," +
            "`ngay2`TEXT," +
            "`ghiChu`TEXT);";
    public static final String CREATE_TABLE_DTHILOP= "CREATE TABLE IF NOT EXISTS `dthilop` (`stt`INTEGER PRIMARY KEY AUTOINCREMENT," +
            "`maLop`TEXT," +
            "`tenLopUuTien`TEXT," +
            "`soTC`TEXT," +
            "`msv`TEXT NOT NULL," +
            " `linkKetQuaThiTheoMon`TEXT NOT NULL," +
            "`ten`TEXT," +
            "`dL1`TEXT," +
            "`dL2`TEXT," +
            "`ghiChu`TEXT);";
    public static final String CREATE_TABLE_LTHI= "CREATE TABLE IF NOT EXISTS `lthi` (" +
            "`stt`INTEGER PRIMARY KEY AUTOINCREMENT," +
            "`maSV`TEXT ," +
            "`mon`TEXT ," +
            "`thu`TEXT ," +
            "`ngay`TEXT," +
            "`gio`TEXT," +
            "`sbd`TEXT," +
            "`lanthi`TEXT," +
            "`phong`TEXT," +
            "`diaDiem`TEXT," +
            "`ghiChu`TEXT);";
    public static final String CREATE_TABLE_LTHILOP= "CREATE TABLE IF NOT EXISTS `lthilop` (`stt`INTEGER PRIMARY KEY AUTOINCREMENT," +
            "`maLop`TEXT ," +
            "`tenMon`TEXT," +
            "`ngayThi`TEXT," +
            "`gioThi`TEXT," +
            "`sbd`TEXT," +
            "`lanThi`TEXT," +
            "`tenLop`TEXT," +
            "`khoa`TEXT);";
    private Context context;
    private SQLiteDatabase database;
    public SQLiteManager(Context context) {
        this.context = context;
        openDatabases();
    }
    /**
     * đóng database khi k sử dụng
     */
    private void closeDatabases() {
        database.close();
    }
    /**
     * openOrCreateDatabase mở hoặc tạo mới một database
     * execSQL truy vấn bằng câu lệnh
     */
    private void openDatabases() {
        database=context.openOrCreateDatabase("data.sqlite",Context.MODE_APPEND,null);
        database.execSQL(CREATE_TABLE_DMON);
        database.execSQL(CREATE_TABLE_DLOP);
        database.execSQL(CREATE_TABLE_DTHIMON);
        database.execSQL(CREATE_TABLE_DTHILOP);
        database.execSQL(CREATE_TABLE_LTHI);
        database.execSQL(CREATE_TABLE_LTHILOP);
        database.execSQL(CREATE_TABLE_SV);
    }
    /**
     * chèn thêm 1 dòng vào bảng điểm học tập theo môn
     * @return id là kết quả khi chèn id<0 chèn thất bại
     */
    public long insertSV(SinhVien sv){
        long id = 0;
        if (getSV(sv.getMaSV())==null){
            ContentValues contentValues=new ContentValues();
            contentValues.put("tenSV",sv.getTenSV());
            contentValues.put("maSV",sv.getMaSV());
            contentValues.put("lopDL",sv.getLopDL());
            openDatabases();
             id=database.insert("sv", null, contentValues);
            closeDatabases();
        }
        return id;
    }
    public long insertDMon(ItemBangKetQuaHocTap bangKetQuaHocTap, String maSV){
        ContentValues contentValues=new ContentValues();
        contentValues.put("maSV",maSV);
        contentValues.put("linkDiemLop",bangKetQuaHocTap.getLinkDiemLop());
        contentValues.put("linkLichThiLop",bangKetQuaHocTap.getLinkLichThiLop());
        contentValues.put("tenMon",bangKetQuaHocTap.getTenMon());
        contentValues.put("maMon",bangKetQuaHocTap.getMaMon());
        contentValues.put("d1",bangKetQuaHocTap.getD1());
        contentValues.put("d2",bangKetQuaHocTap.getD2());
        contentValues.put("d3",bangKetQuaHocTap.getD3());
        contentValues.put("dGiuaKi",bangKetQuaHocTap.getdGiua());
        contentValues.put("dTB",bangKetQuaHocTap.getdTB());
        contentValues.put("soTietNghi",bangKetQuaHocTap.getSoTietNghi());
        contentValues.put("dieuKien",bangKetQuaHocTap.getDieuKien());
        openDatabases();
        long id=database.insert("dmon", null, contentValues);
        closeDatabases();
        return id;
    }
    public long updateDMon(ItemBangKetQuaHocTap bangKetQuaHocTap, String maSV,String maMon){
        ContentValues contentValues=new ContentValues();
        contentValues.put("maSV",maSV);
        String []s={maMon,maSV};
        contentValues.put("linkDiemLop",bangKetQuaHocTap.getLinkDiemLop());
        contentValues.put("linkLichThiLop",bangKetQuaHocTap.getLinkLichThiLop());
        contentValues.put("tenMon",bangKetQuaHocTap.getTenMon());
        contentValues.put("maMon",bangKetQuaHocTap.getMaMon());
        contentValues.put("d1",bangKetQuaHocTap.getD1());
        contentValues.put("d2",bangKetQuaHocTap.getD2());
        contentValues.put("d3",bangKetQuaHocTap.getD3());
        contentValues.put("dGiuaKi",bangKetQuaHocTap.getdGiua());
        contentValues.put("dTB",bangKetQuaHocTap.getdTB());
        contentValues.put("soTietNghi",bangKetQuaHocTap.getSoTietNghi());
        contentValues.put("dieuKien",bangKetQuaHocTap.getDieuKien());
        openDatabases();
        long id=database.update("dmon",contentValues,"maMon=? and maSV=?",s);
        closeDatabases();
        return id;
    }
    public SinhVien getSV(String masv) {
        try {
            SinhVien sinhVien;
            openDatabases();
            String [] s={masv};
            Cursor cursor=database.query("sv",null,"maSV=?",s,null,null,null);
            cursor.getCount();// tra ve so luong ban ghi no ghi dc
            cursor.getColumnNames();// 1 mang cac cot
            cursor.moveToFirst(); // di chuyển con trỏ đến dòng đầu tiền trong bảng
            int tenSV=cursor.getColumnIndex("tenSV");
            int maSV=cursor.getColumnIndex("maSV");
            int lopDL=cursor.getColumnIndex("lopDL");
            sinhVien=new SinhVien(
                    cursor.getString(tenSV),
                    cursor.getString(maSV),
                    cursor.getString(lopDL));
            closeDatabases();
            return sinhVien ;
        }catch (CursorIndexOutOfBoundsException e){
            Log.e("faker","CursorIndexOutOfBoundsException");
            return null;
        }


    }
    public void deleteSV() {
        openDatabases();
        database.delete("sv",null,null);
        closeDatabases();
    }
    public void deleteDLop(String maSV) {
        openDatabases();
        String []s={maSV};
        database.delete("dlop","maLop=?",s);
        closeDatabases();
    }
    public void deleteDThiMon(String maSV) {
        openDatabases();
        String []s={maSV};
        database.delete("dthimon","maSV=?",s);
        closeDatabases();
    }
    public void deleteDLThi(String maSV) {
        openDatabases();
        String []s={maSV};
        database.delete("lthi","maSV=?",s);
        closeDatabases();
    }

    public void deleteDThiLop(String maLop) {
        openDatabases();
        String []s={maLop};
        database.delete("dthilop","maLop=?",s);
        closeDatabases();
    }
    // lấy danh sách điểm học tập theo môn
    public ArrayList<ItemBangKetQuaHocTap> getBangKetQuaHocTap(String id) {
        ArrayList<ItemBangKetQuaHocTap> bangKetQuaHocTaps=new ArrayList<>();
        openDatabases();
        String []s={id};
        Cursor cursor=database.query("dmon",null,"maSV=?",s,null,null,null);
        cursor.moveToFirst(); // di chuyển con trỏ đến dòng đầu tiền trong bảng
        int linkDiemLop=cursor.getColumnIndex("linkDiemLop");
        int linkLichThiLop=cursor.getColumnIndex("linkLichThiLop");
        int ten=cursor.getColumnIndex("tenMon"); // trả về vị trí cột tương ứng
        int ma=cursor.getColumnIndex("maMon");
        int d1=cursor.getColumnIndex("d1");
        int d2=cursor.getColumnIndex("d2");
        int d3=cursor.getColumnIndex("d3");
        int dGiua=cursor.getColumnIndex("dGiuaKi");
        int dTB=cursor.getColumnIndex("dTB");
        int soTietNghi=cursor.getColumnIndex("soTietNghi");
        int dieuKien=cursor.getColumnIndex("dieuKien");
        while (!cursor.isAfterLast()){// neu no o ban ghi cuoi cung thi tra ve true
            bangKetQuaHocTaps.add(new ItemBangKetQuaHocTap(
                    cursor.getString(linkDiemLop), // trả về dữ liệu của dòng cột đang xét
                    cursor.getString(linkLichThiLop),
                    cursor.getString(ten),
                    cursor.getString(ma),
                    cursor.getString(d1),
                    cursor.getString(d2),
                    cursor.getString(d3),
                    cursor.getString(dGiua),
                    cursor.getString(dTB),
                    cursor.getString(soTietNghi),
                    cursor.getString(dieuKien)));
            cursor.moveToNext();
        }
        closeDatabases();
        return bangKetQuaHocTaps ;
    }
    /**
     * chèn thêm 1 dòng vào bảng điểm học tập theo lớp
     */
    public void deleteDMon(String id) {
        openDatabases();
        String []s={id};
        database.delete("dmon","maSV=?",s);
        closeDatabases();
    }
    public long insertDLop(ItemBangDiemThanhPhan dlop, String maLopDL, String tenLop,String sotc){
        ContentValues contentValues=new ContentValues(); // tạo mơi một đối tượng chứ các dữ liệu
        contentValues.put("masv",dlop.getMsv()); // thêm các thuộc tính
        contentValues.put("linkDiemMon",dlop.getLinkDiemMon());
        contentValues.put("tenSv",dlop.getTenSv());
        contentValues.put("d1",dlop.getD1());
        contentValues.put("d2",dlop.getD2());
        contentValues.put("d3",dlop.getD3());
        contentValues.put("d4",dlop.getD4());
        contentValues.put("maLop",maLopDL);
        contentValues.put("soTietNghi",dlop.getSoTietNghi());
        contentValues.put("dTB",dlop.getdTB());
        contentValues.put("dieuKien",dlop.getDieuKien());
        contentValues.put("tenLop",tenLop);
        contentValues.put("soTC",sotc);
        openDatabases();
        long id=database.insert("dlop", null, contentValues); // chèn vào bảng dlop với dữ liệu là contentValues
        closeDatabases();
        return id;
    }
    public DiemThanhPhan getAllDLop(String ma) {
        try {
            openDatabases();
            ArrayList<ItemBangDiemThanhPhan> itemBangDiemThanhPhen=new ArrayList<>();
            String [] s={ma};
            Cursor cursor=database.query("dlop",null,"maLop=?",s,null,null,null);
//            Cursor cursor=database.query("dlop",null,null,null,null,null,null);
            //cursor.getCount();// tra ve so luong ban ghi no ghi dc
            cursor.moveToFirst(); // di chuyển con trỏ đến dòng đầu tiền trong bảng
            int msv=cursor.getColumnIndex("masv");
            int linkDiemMon=cursor.getColumnIndex("linkDiemMon");
            int tenSv=cursor.getColumnIndex("tenSv"); // trả về vị trí cột tương ứng
            int d1=cursor.getColumnIndex("d1");
            int d2=cursor.getColumnIndex("d2");
            int d3=cursor.getColumnIndex("d3");
            int d4=cursor.getColumnIndex("d4");
            int soTietNghi=cursor.getColumnIndex("soTietNghi");
            int dTB=cursor.getColumnIndex("dTB");
            int tenLop=cursor.getColumnIndex("tenLop");
            int maLop=cursor.getColumnIndex("maLop");
            int soTC=cursor.getColumnIndex("soTC");
            int dieuKien=cursor.getColumnIndex("dieuKien");
            String maLopDL=cursor.getString(maLop);
            String tenLopDL=cursor.getString(tenLop);
            String sotc=cursor.getString(soTC);
            while (!cursor.isAfterLast()){// neu no o ban ghi cuoi cung thi tra ve true
                itemBangDiemThanhPhen.add(new ItemBangDiemThanhPhan(
                        cursor.getString(msv), // trả về dữ liệu của dòng cột đang xét
                        cursor.getString(linkDiemMon),
                        cursor.getString(tenSv),
                        cursor.getString(d1),
                        cursor.getString(d2),
                        cursor.getString(d3),
                        cursor.getString(d4),
                        cursor.getString(soTietNghi),
                        cursor.getString(dTB),
                        cursor.getString(dieuKien)));
                cursor.moveToNext();
            }

            closeDatabases();
            return new DiemThanhPhan(maLopDL,
                    tenLopDL,
                    sotc,
                    itemBangDiemThanhPhen);
        }catch (CursorIndexOutOfBoundsException e) {
            return null;
        }
    }
    /**
     * chèn thêm 1  dòng vào bảng điểm thi xem theo danh sách lớp
     * @param
     * @return
     */
    public void deleteLThiLop(String maLop ) {
        openDatabases();
        String []s={maLop};
        database.delete("lthilop","maLop=?",s);
        closeDatabases();
    }
    public long insertDThiLop(String maLop,String tenLopUuTien, String soTC,ItemKetQuaThiLop dthilop){
        ContentValues contentValues=new ContentValues();
        contentValues.put("maLop",maLop);
        contentValues.put("tenLopUuTien",tenLopUuTien);
        contentValues.put("soTC",soTC);
        contentValues.put("msv",dthilop.getMsv());
        contentValues.put("linkKetQuaThiTheoMon",dthilop.getLinkKetQuaThiTheoMon());
        contentValues.put("ten",dthilop.getTen());
        contentValues.put("dL1",dthilop.getdL1());
        contentValues.put("dL2",dthilop.getdL2());
        contentValues.put("ghiChu",dthilop.getGhiChu());
        openDatabases();
        long id=database.insert("dthilop", null, contentValues);
        closeDatabases();
        return id;
    }
    public KetQuaThi getAllDThiLop(String maLop) {
     try {
         openDatabases();
         ArrayList<ItemKetQuaThiLop> itemKetQuaThiLops =new ArrayList<>();
         String [] s={maLop};
         Cursor cursor=database.query("dthilop",null,"maLop=?",s,null,null,null);
         cursor.getCount();// tra ve so luong ban ghi no ghi dc
         cursor.getColumnNames();// 1 mang cac cot
         cursor.moveToFirst(); // di chuyển con trỏ đến dòng đầu tiền trong bảng
         int msv=cursor.getColumnIndex("msv");
         int linkKetQuaThiTheoMon=cursor.getColumnIndex("linkKetQuaThiTheoMon");
         int ten=cursor.getColumnIndex("ten"); // trả về vị trí cột tương ứng
         int dL1=cursor.getColumnIndex("dL1");
         int dL2=cursor.getColumnIndex("dL2");
         int tenLopUuTien=cursor.getColumnIndex("tenLopUuTien");
         int soTC=cursor.getColumnIndex("soTC");
         int ghiChu=cursor.getColumnIndex("ghiChu");
         String ten1=cursor.getString(tenLopUuTien);
         String sotc1=cursor.getString(soTC);
         while (!cursor.isAfterLast()){// neu no o ban ghi cuoi cung thi tra ve true
             itemKetQuaThiLops.add(new ItemKetQuaThiLop(
                     cursor.getString(msv), // trả về dữ liệu của dòng cột đang xét
                     cursor.getString(linkKetQuaThiTheoMon),
                     cursor.getString(ten),
                     cursor.getString(dL1),
                     cursor.getString(dL2),
                     cursor.getString(ghiChu)));
             cursor.moveToNext();
         }
         KetQuaThi ketQuaThi=new KetQuaThi(ten1,sotc1,itemKetQuaThiLops);
         closeDatabases();
         return ketQuaThi;
     }catch (CursorIndexOutOfBoundsException e){
         Log.e("duonghaui","CursorIndexOutOfBoundsException");
         return null;
     }
    }
    /**
     * chèn thêm một dòng vào bảng điểm thi xem theo danh sách môn học
     * @param dthimon
     * @return
     */
    public long insertDThiMon(DiemThiTheoMon dthimon,String maSV){
        ContentValues contentValues=new ContentValues();
        contentValues.put("linkDiemThiTheoLop",dthimon.getLinkDiemThiTheoLop());
        contentValues.put("tenMon",dthimon.getTenMon());
        contentValues.put("maSV",maSV);
        contentValues.put("dLan1",dthimon.getdLan1());
        contentValues.put("dTKLan1",dthimon.getdTKLan1());
        contentValues.put("dLan2",dthimon.getdLan2());
        contentValues.put("dTKLan2",dthimon.getdTKLan2());
        contentValues.put("dCuoiCung",dthimon.getdCuoiCung());
        contentValues.put("ngay1",dthimon.getNgay1());
        contentValues.put("ngay2",dthimon.getNgay2());
        contentValues.put("ghiChu",dthimon.getGhiChu());
        openDatabases();
        long id=database.insert("dthimon", null, contentValues);
        closeDatabases();
        return id;
    }
    public long updateDThiMon(DiemThiTheoMon dthimon,String maSV){
        ContentValues contentValues=new ContentValues();
        contentValues.put("linkDiemThiTheoLop",dthimon.getLinkDiemThiTheoLop());
        String []s={dthimon.getLinkDiemThiTheoLop(),maSV};
        contentValues.put("tenMon",dthimon.getTenMon());
        contentValues.put("maSV",maSV);
        contentValues.put("dLan1",dthimon.getdLan1());
        contentValues.put("dTKLan1",dthimon.getdTKLan1());
        contentValues.put("dLan2",dthimon.getdLan2());
        contentValues.put("dTKLan2",dthimon.getdTKLan2());
        contentValues.put("dCuoiCung",dthimon.getdCuoiCung());
        contentValues.put("ngay1",dthimon.getNgay1());
        contentValues.put("ngay2",dthimon.getNgay2());
        contentValues.put("ghiChu",dthimon.getGhiChu());
        openDatabases();
        long id=database.update("dthimon", contentValues,"linkDiemThiTheoLop=? and maSV=?",s);
        closeDatabases();
        return id;
    }
    public ArrayList<DiemThiTheoMon> getAllDThiMon(String maSV) {
        openDatabases();
        ArrayList<DiemThiTheoMon> diemThiTheoMons=new ArrayList<>();
        String []s={maSV};
        Cursor cursor=database.query("dthimon",null,"maSV=?",s,null,null,null);
        cursor.getCount();// tra ve so luong ban ghi no ghi dc
        cursor.getColumnNames();// 1 mang cac cot
        cursor.moveToFirst(); // di chuyển con trỏ đến dòng đầu tiền trong bảng
        int linkDiemThiTheoLop=cursor.getColumnIndex("linkDiemThiTheoLop");
        int tenMon=cursor.getColumnIndex("tenMon");
        int dLan1=cursor.getColumnIndex("dLan1"); // trả về vị trí cột tương ứng
        int dTKLan1=cursor.getColumnIndex("dTKLan1");
        int dLan2=cursor.getColumnIndex("dLan2");
        int dTKLan2=cursor.getColumnIndex("dTKLan2");
        int dCuoiCung=cursor.getColumnIndex("dCuoiCung");
        int ngay1=cursor.getColumnIndex("ngay1");
        int ngay2=cursor.getColumnIndex("ngay2");
        int ghiChu=cursor.getColumnIndex("ghiChu");
        while (!cursor.isAfterLast()){// neu no o ban ghi cuoi cung thi tra ve true
            diemThiTheoMons.add(new DiemThiTheoMon(
                    cursor.getString(linkDiemThiTheoLop), // trả về dữ liệu của dòng cột đang xét
                    cursor.getString(tenMon),
                    cursor.getString(dLan1),
                    cursor.getString(dLan2),
                    cursor.getString(dTKLan1),
                    cursor.getString(dTKLan2),
                    cursor.getString(dCuoiCung),
                    cursor.getString(ngay1),
                    cursor.getString(ngay2),
                    cursor.getString(ghiChu)));
            cursor.moveToNext();
        }
        closeDatabases();
        return diemThiTheoMons;
    }
    /**
     *  chèm thêm một dòng bảng lịch thi
     * @param lthi
     * @return
     */

    public long insertlthi(LichThi lthi,String maSV){
        ContentValues contentValues=new ContentValues();
        contentValues.put("maSV",maSV);
        contentValues.put("mon",lthi.getMon());
        contentValues.put("thu",lthi.getThu());
        contentValues.put("ngay",lthi.getNgay());
        contentValues.put("gio",lthi.getGio());
        contentValues.put("sbd",lthi.getSbd());
        contentValues.put("lanthi",lthi.getLanthi());
        contentValues.put("phong",lthi.getPhong());
        contentValues.put("diaDiem",lthi.getDiaDiem());
        contentValues.put("ghiChu",lthi.getGhiChu());
        openDatabases();
        long id=database.insert("lthi", null, contentValues);
        closeDatabases();
        return id;
    }
    public ArrayList<LichThi> getAllLThi(String id) {
        openDatabases();
        ArrayList<LichThi> lichThis=new ArrayList<>();
        String [] s={id};
        Cursor cursor=database.query("lthi",null,"maSV=?",s,null,null,null);
        cursor.getCount();// tra ve so luong ban ghi no ghi dc
        cursor.getColumnNames();// 1 mang cac cot
        cursor.moveToFirst(); // di chuyển con trỏ đến dòng đầu tiền trong bảng
        int mon=cursor.getColumnIndex("mon");
        int thu=cursor.getColumnIndex("thu"); // trả về vị trí cột tương ứng
        int ngay=cursor.getColumnIndex("ngay");
        int gio=cursor.getColumnIndex("gio");
        int sbd=cursor.getColumnIndex("sbd");
        int lanthi=cursor.getColumnIndex("lanthi");
        int phong=cursor.getColumnIndex("phong");
        int diaDiem=cursor.getColumnIndex("diaDiem");
        int ghiChu=cursor.getColumnIndex("ghiChu");
        int maSV=cursor.getColumnIndex("maSV");
        while (!cursor.isAfterLast()){// neu no o ban ghi cuoi cung thi tra ve true
            lichThis.add(new LichThi(
                    // trả về dữ liệu của dòng cột đang xét
                    cursor.getString(mon),
                    cursor.getString(thu),
                    cursor.getString(ngay),
                    cursor.getString(gio),
                    cursor.getString(sbd),
                    cursor.getString(lanthi),
                    cursor.getString(phong),
                    cursor.getString(diaDiem),
                    cursor.getString(ghiChu)));
            cursor.moveToNext();
        }
        closeDatabases();
        return lichThis;
    }
    /**
     * chèm thêm một dòng bảng lịch thi theo lớp học
     * @param lthilop
     * @return
     */
    public long insertlthilop(LichThiLop lthilop){
        ContentValues contentValues=new ContentValues();
        contentValues.put("maLop",lthilop.getMaLop());
        contentValues.put("tenMon",lthilop.getTenMon());
        contentValues.put("ngayThi",lthilop.getNgayThi());
        contentValues.put("gioThi",lthilop.getGioThi());
        contentValues.put("lanThi",lthilop.getLanThi());
        contentValues.put("tenLop",lthilop.getTenLop());
        contentValues.put("khoa",lthilop.getKhoa());
        openDatabases();
        long id=database.insert("lthilop", null, contentValues);
        closeDatabases();
        return id;
    }
    public ArrayList<LichThiLop> getAllLThiLop(String ma) {
        openDatabases();
        ArrayList<LichThiLop> lichThiLops=new ArrayList<>();
        String[] s={ma};
        Cursor cursor=database.query("lthilop",null,"maLop=?",s,null,null,null);
        cursor.getCount();// tra ve so luong ban ghi no ghi dc
        cursor.getColumnNames();// 1 mang cac cot
        cursor.moveToFirst(); // di chuyển con trỏ đến dòng đầu tiền trong bảng
        int maLop=cursor.getColumnIndex("maLop");
        int tenMon=cursor.getColumnIndex("tenMon"); // trả về vị trí cột tương ứng
        int ngayThi=cursor.getColumnIndex("ngayThi");
        int gioThi=cursor.getColumnIndex("gioThi");
        int lanThi=cursor.getColumnIndex("lanThi");
        int tenLop=cursor.getColumnIndex("tenLop");
        int khoa=cursor.getColumnIndex("khoa");
        while (!cursor.isAfterLast()){// neu no o ban ghi cuoi cung thi tra ve true
            lichThiLops.add(new LichThiLop(
                    // trả về dữ liệu của dòng cột đang xét
                    cursor.getString(maLop),
                    cursor.getString(tenMon),
                    cursor.getString(ngayThi),
                    cursor.getString(gioThi),
                    cursor.getString(lanThi),
                    cursor.getString(tenLop),
                    cursor.getString(khoa)));
            cursor.moveToNext();
        }
        closeDatabases();
        return lichThiLops;
    }


}
