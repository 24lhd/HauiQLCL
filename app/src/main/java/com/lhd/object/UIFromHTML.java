package com.lhd.object;

import com.lhd.fragment.KetQuaThiFragment;

/**
 * Created by d on 23/12/2016.
 */

public class UIFromHTML {
    public static final String uiDiemChu="<!DOCTYPE html> <html> <head> " +
            "<meta charset=\"utf-8\"><title></title> <style type=\"text/css\"> " +
            "*{ margin: auto; text-align: center; background: white;} " +
            "table{ width: 100%; }" +
            "th { background-color: #42A5F5; color: white; padding: 5px;font-size: small;} " +
            "td{padding: 5px;background-color: #f2f2f2;color: red;} </style> </head> <body> " +

            "<table> <tr> <th>Điểm chữ </th> <th>Bắt đầu</th> <th>Kết thúc</th> </tr> <tr> <td>A</td> <td>8.5</td> <td>10</td> </tr> <tr> <td>B+</td> <td>7.7</td> <td>8.4</td> </tr> <tr> <td>B</td> <td>7.0</td> <td>7.6</td> </tr> <tr> <td>C+</td> <td>6.2</td> <td>6.9</td> </tr> <tr> <td>C</td> <td>5.5</td> <td>6.1</td> </tr> <tr> <td>D+</td> <td>4.7</td> <td>5.4</td> </tr> <tr> <td>D</td> <td>4.0</td> <td>4.6</td> </tr> <tr> <td>F</td> <td>0</td> <td>3.9</td> </tr> </table> <em>Copyright  © Gà công nghiệp</em></body> </html>";
    public static final String uiTime="<!DOCTYPEhtml><html>" +
            "<head>" +
            "<meta charset=\"utf-8\"/>" +
            "<style type=\"text/css\" media=\"screen\">" +
            "*{" +
            "margin:0;padding: 0;" +
            "text-align: center;" +
            "}" +
            "table{" +
            "width:100%;border:2px solid white;text-align:center;border-collapse:collapse;background-color:#E8F5E9;" +
            "}" +
            "th { background-color: #42A5F5; color: white; padding: 5px;font-size: small;} "+
            "td{padding: 5px;background-color: #f2f2f2;color: red;}" +
            "</style>" +
            "</head>" +
            "<body>" +
            "<table border=\"1px\"><tr><th>Tiết</th><th>Giờ Học</th></tr><tr><td>1</td><td>7h00 - 7h45</td></tr><tr><td>2</td><td>7h50 - 8h35</td></tr><tr><td>3</td><td>8h40 - 9h25</td></tr><tr><td>4</td><td>9h35 - 10h20</td></tr><tr><td>5</td><td>10h25-11h10</td></tr><tr><td>6</td><td>11h15 - 12h00</td></tr><tr><td>7</td><td>12h30 - 13h15</td></tr><tr><td>8</td><td>13h20 - 14h05</td></tr><tr><td>9</td><td>14h10 - 14h55</td></tr><tr><td>10</td><td>15h05 - 15h50</td></tr><tr><td>11</td><td>15h55 - 16h40</td></tr><tr><td>12</td><td>16h45 - 17h30</td></tr><tr><td>13</td><td>18h00 - 18h45</td></tr><tr><td>14</td><td>18h45 - 19h30</td></tr><tr><td>15</td><td>19h45 - 20h30</td></tr><tr><td>16</td><td>20h30 - 21h15</td></tr>" +
            "</table>" +
            "<em>Copyright  © Gà công nghiệp</em>"+
            "</body>" +
            "</html>";
    public static String uiCopyright="<!DOCTYPE html>" +
            "<html>" +
            "<head>" +
            "<meta charset=\"utf-8\">" +
            "<style type=\"text/css\" media=\"screen\">" +
            "h2{" +
            "color: #FF4081;" +
            "text-align:justify" +
            "}" +
            "p{" +
            "font-family: Sans-serif;" +
            "text-indent: 10px;" +
            "text-align:justify" +
            "}" +
            "#footer{" +
            "text-align: center;" +
            "}" +
            "</style>" +
            "</head>" +
            "<body>" +
            "<h2>Gà Công Nghiệp</h2>" +
            "<p >Phần mềm sử dụng dữ liệu trên trang web http://qlcl.edu.vn/ cập nhật thông báo điểm, tra cứu kết quả học tập, lịch thi và một số tiện ích khác hỗ trợ các bạn sinh viên ĐH Công nghiệp Hà Nội trong học tập và trong thi cử một cách nhanh nhất." +
            "<p>  Phát triển bởi: <br> <p>- Lê Hồng Dương (ĐH HTTT1_K9)<br><p> Xin chân thành cảm ơn sự ủng hộ của các bạn!" +
            "<p id=\"footer\">" +
            "</p>" +
            "</body>" +
            "</html>";
    public static String uiKetQuaHocTap(ItemBangKetQuaHocTap itemBangKetQuaHocTap) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<title></title>" +
                "<style type=\"text/css\" media=\"screen\">" +
                "*{" +
                "margin: auto;" +
                "text-align: center;" +
                "background: white;"+
                "}" +
                "h1{" +
                "color: #FF4081;" +
                "}" +
                "p{" +
                "color: #42A5F5;" +
                "}" +
                "table{" +
                "width: 100%;" +
                "}"+
                "th {" +
                "background-color: #42A5F5;" +
                "color: white;" +
                "padding: 5px;" +
                "font-size: small;"+
                "}" +
                "td{padding: 5px;background-color: #f2f2f2;font-weight:bold;color: red;}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<h1>" +
                itemBangKetQuaHocTap.getTenMon() +
                "</h1>" +
                "<p>" +
                "("+itemBangKetQuaHocTap.getMaMon()+")<br/>" +
                "" +
                "</p>" +
                "<small>"+"Bỏ "+itemBangKetQuaHocTap.getSoTietNghi()+" tiết - "+itemBangKetQuaHocTap.getDieuKien()+"</small>" +
                "<table>" +
                "<tr>" +
                "<th>Điểm 1</th>" +
                "<th>Điểm 2</th>" +
                "<th>Điểm 3</th>" +
                "<th>Điểm giữa kì</th>" +
                "<th>Tổng kết</th>" +
                "</tr>" +
                "<tr>" +
                "<td>"+itemBangKetQuaHocTap.getD1()+"</td>" +
                "<td>"+itemBangKetQuaHocTap.getD2()+"</td>" +
                "<td>"+itemBangKetQuaHocTap.getD3()+"</td>" +
                "<td>"+itemBangKetQuaHocTap.getdGiua()+"</td>" +
                "<td>"+itemBangKetQuaHocTap.getdTB()+"</td>" +
                "</tr>" +
                "</table>" +
                "<em>Copyright  © Gà công nghiệp</em>"+
                "</body>" +
                "</html>";
    }
    public static String uiDiemMonListAc(ItemBangDiemThanhPhan itemBangDiemThanhPhan) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<title></title>" +
                "<style type=\"text/css\" media=\"screen\">" +
                "*{" +
                "margin: auto;" +
                "text-align: center;" +
                "background: white;"+
                "}" +
                "h3{" +
                "color: #FF4081;" +
                "}" +
                "p{" +
                "color: #42A5F5;" +
                "}" +
                "table{" +
                "width: 100%;" +
                "}"+
                "th {" +
                "background-color: #42A5F5;" +
                "color: white;" +
                "padding: 5px;" +
                "font-size: small;"+
                "}" +
                "td{padding: 5px;background-color: #f2f2f2;font-weight:bold;color: red;}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<h3>" +
                itemBangDiemThanhPhan.getTenSv()+
                "</h3>" +
                "<p>" +
                "("+itemBangDiemThanhPhan.getMsv()+")<br/>" +
                "" +
                "</p>" +
                "<small>"+"Bỏ "+itemBangDiemThanhPhan.getSoTietNghi()+" tiết - "+itemBangDiemThanhPhan.getDieuKien()+"</small>" +
                "<table>" +
                "<tr>" +
                "<th>Điểm 1</th>" +
                "<th>Điểm 2</th>" +
                "<th>Điểm 3</th>" +
                "<th>Điểm 4</th>" +
                "<th>Tổng kết</th>" +
                "</tr>" +
                "<tr>" +
                "<td>"+itemBangDiemThanhPhan.getD1()+"</td>" +
                "<td>"+itemBangDiemThanhPhan.getD2()+"</td>" +
                "<td>"+itemBangDiemThanhPhan.getD3()+"</td>" +
                "<td>"+itemBangDiemThanhPhan.getD4()+"</td>" +
                "<td>"+itemBangDiemThanhPhan.getdTB()+"</td>" +
                "</tr>" +
                "</table>" +
                "<em>Copyright  © Gà công nghiệp</em>"+
                "</body>" +
                "</html>";
    }
    public static String uiDiemThiListAC(ItemKetQuaThiLop itemKetQuaThiLop) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<title></title>" +
                "<style type=\"text/css\" media=\"screen\">" +
                "*{" +
                "margin: auto;" +
                "text-align: center;" +
                "background: white;" +
                "}" +
                "h2{" +
                "color: #FF4081;" +
                "}" +
                "p{" +
                "color: #42A5F5;" +
                "}" +
                "table{" +
                "width: 100%;" +
                "}"+
                "th {" +
                "background-color: #42A5F5;" +
                "color: white;" +
                "padding: 15px;" +
                "}" +
                "td{" +
                "background-color: #f2f2f2;" +
                "font-weight:bold;" +
                "color: red;" +
                "padding: 15px;" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<h2>" +
                itemKetQuaThiLop.getTen() +
                "</h2>" +
                "<p>" +
                itemKetQuaThiLop.getMsv() +
                "</p>" +
                "<small>" +
                itemKetQuaThiLop.getGhiChu() +
                "</small>" +
                "<table>" +
                "<tr>" +
                "<th>Điểm thi</th>" +
                "<td>" +
                itemKetQuaThiLop.getdL1()+
                "</td>" +
                "</table>"+
                "<em>Copyright  © Gà công nghiệp</em>"+
                "</body>" +
                "</html>";
    }
    public static String uiKeHoachThi(LichThiLop lichThiLop, String tenMon) {
        return "<!DOCTYPE html>" +
        "<html>" +
                "<head>" +
                "<title></title>" +
                "<style type=\"text/css\" media=\"screen\">" +
                "*{" +
                "margin: auto;" +
                "text-align: center;" +
                "background: white;" +
                "}" +
                "h2{" +
                "color: #FF4081;" +
                "}" +
                "p{" +
                "color: #42A5F5;" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<h2>" +
                tenMon+
                "</h2>" +
                "<p>" +
                "("+lichThiLop.getMaLop()+")<br/></p>"+"Lớp "
                + lichThiLop.getTenLop()+"<br/> ngày "+
                lichThiLop.getNgayThi()+"<br/> ca "+lichThiLop.getGioThi()+
                "<br/>" +
                "<em>Copyright  © Gà công nghiệp</em>"+
                "</body>" +
                "</html>";
    }

    public static String uiKetQuaThi(ItemDiemThiTheoMon itemDiemThiTheoMon) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<title></title>" +
                "<style type=\"text/css\" media=\"screen\">" +
                "*{" +
                "margin: auto;" +
                "text-align: center;" +
                "background: white;" +
                "}" +
                "h2{" +
                "color: #FF4081;" +
                "}" +
                "p{" +
                "color: #42A5F5;" +
                "}" +
                "table{" +
                "width: 100%;" +
                "}"+
                "th {" +
                "background-color: #42A5F5;" +
                "color: white;" +
                "padding: 15px;" +
                "}" +
                "td{" +
                "padding: 15px;" +
                "background-color: #f2f2f2;" +
                "font-weight:bold;" +
                "color: red;" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<h2>" +
                itemDiemThiTheoMon.getTenMon() +
                "</h2>" +
                "<p>" +
                itemDiemThiTheoMon.getNgay1() +
                "</p>" +
                "<small>" +
                itemDiemThiTheoMon.getGhiChu()+
                "</small>" +
                "<table>" +
                "<tr>" +
                "" +
                "<th>Điểm thi</th>" +
                "<th>Tổng kết</th>" +
                "<th>Điểm chữ</th>" +
                "" +
                "</tr>" +
                "<tr>" +
                "<td>" +
                itemDiemThiTheoMon.getdLan1() +
                "</td>" +
                "<td>" +
                itemDiemThiTheoMon.getdTKLan1()+
                "</td>" +
                "<td>" +
                KetQuaThiFragment.charPoint(itemDiemThiTheoMon)+
                "</td>" +
                "</tr>" +
                "</table>" +
                "<em>Copyright  © Gà công nghiệp</em>"+
                "</body>" +
                "</html>";
    }
    public static String getUIWeb(String thi, String diemThi) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<title></title>" +
                "<style type=\"text/css\" media=\"screen\">" +
                "*{" +
                "margin: auto;" +
                "text-align: center;" +
                "background: white;" +
                "}" +
                "p{" +
                "color: #42A5F5;" +
                "}" +
                "table{" +
                "width: 100%;" +
                "}"+
                "th {" +
                "background-color: #42A5F5;" +
                "color: white;" +
                "padding: 15px;" +
                "}" +
                "td{" +
                "padding: 15px;" +
                "background-color: #f2f2f2;" +
                "font-weight:bold;" +
                "color: red;" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<table>" +
                "<tr>" +
                "<th>Điểm TB trên lớp</th>" +
                "<th>Điểm thi dự tính</th>" +
                "</tr>" +
                "<tr>" +
                "<td>" +
                thi+
                "</td>" +
                "<td>" +
                diemThi+
                "</td>" +
                "</tr>" +
                "</table>" +
                "<em>Copyright  © Gà công nghiệp</em>"+
                "</body>" +
                "</html>";
    }
    public static String uiLichThi(LichThi lichThi, String toi) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<meta charset=\"utf-8\">" +
                "<style type=\"text/css\" media=\"screen\">" +
                "*{" +
                "margin: auto; " +
                "text-align: center;" +
                "background-color: white;" +
                "}" +
                "h1{" +
                "color: #FF4081;" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<h1>" +
                lichThi.getSbd() +
                "</h1>" +
                "<p>tại <strong>" +
                lichThi.getPhong()+
                "</strong><br> " +
                "<strong>" +
                lichThi.getThu() +
                "</strong> lúc <strong>" +
                lichThi.getGio() +
                "</strong> ngày <strong>l" +
                lichThi.getNgay()+
                "</strong> <br><strong>" +
                toi +
                "</strong></p>" +
                "<em>Copyright  © Gà công nghiệp</em>" +
                "</body></html>";
    }
}
