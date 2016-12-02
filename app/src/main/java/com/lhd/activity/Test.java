package com.lhd.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.ken.hauiclass.R;
import com.lhd.item.ItemNotiDTTC;
import com.lhd.log.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Faker on 9/24/2016.
 */

public class Test extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                ArrayList<ItemNotiDTTC> itemNotiDTTCs= (ArrayList<ItemNotiDTTC>) msg.obj;
                for (ItemNotiDTTC itemNotiDTTC:itemNotiDTTCs) {
                    android.util.Log.e("faker",itemNotiDTTC.toString());
                }
            }
        };
        PS ps = new PS(handler);
        ps.execute();

    }


    //}
    class PS extends AsyncTask<String, Void, ArrayList<ItemNotiDTTC>> {

        private  Handler handler;

        public PS(Handler handler) {
            this.handler=handler;
        }

        @Override
        protected ArrayList<ItemNotiDTTC> doInBackground(String... strings) {
            ArrayList<ItemNotiDTTC> itemNotiDTTCs = new ArrayList<>();
            String link = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                    "<!-- saved from url=(0028)https://dttc.haui.edu.vn/vn/ -->\n" +
                    "<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                    "    <title>\n" +
                    "        \n" +
                    "    TRƯỜNG ĐẠI HỌC CÔNG NGHIỆP HÀ NỘI || HaUI\n" +
                    "\n" +
                    "    </title>\n" +
                    "    <link rel=\"shortcut icon\" href=\"https://egov.haui.edu.vn/dnn/i/jhimg/favicon.png\" type=\"image/x-icon\">\n" +
                    "    <link rel=\"stylesheet\" type=\"text/css\" href=\"./TRƯỜNG ĐẠI HỌC CÔNG NGHIỆP HÀ NỘI __ HaUI_files/core2.css\">\n" +
                    "    <link rel=\"stylesheet\" type=\"text/css\" href=\"./TRƯỜNG ĐẠI HỌC CÔNG NGHIỆP HÀ NỘI __ HaUI_files/ia16.css\">\n" +
                    "    <link rel=\"stylesheet\" type=\"text/css\" href=\"./TRƯỜNG ĐẠI HỌC CÔNG NGHIỆP HÀ NỘI __ HaUI_files/base.css\">\n" +
                    "    <link rel=\"stylesheet\" type=\"text/css\" href=\"./TRƯỜNG ĐẠI HỌC CÔNG NGHIỆP HÀ NỘI __ HaUI_files/tooltip.css\">\n" +
                    "    <link rel=\"stylesheet\" href=\"./TRƯỜNG ĐẠI HỌC CÔNG NGHIỆP HÀ NỘI __ HaUI_files/main8.css\" type=\"text/css\">\n" +
                    "    <link rel=\"stylesheet\" href=\"./TRƯỜNG ĐẠI HỌC CÔNG NGHIỆP HÀ NỘI __ HaUI_files/detail3.css\" type=\"text/css\">\n" +
                    "    <link rel=\"stylesheet\" type=\"text/css\" href=\"./TRƯỜNG ĐẠI HỌC CÔNG NGHIỆP HÀ NỘI __ HaUI_files/frameScreen.css\">\n" +
                    "    <link type=\"text/css\" rel=\"Stylesheet\" href=\"./TRƯỜNG ĐẠI HỌC CÔNG NGHIỆP HÀ NỘI __ HaUI_files/webChat.css\">\n" +
                    "    \n" +
                    "    <meta name=\"robots\" content=\"INDEX,FOLLOW\">\n" +
                    "    <meta http-equiv=\"REFRESH\" content=\"5400\">\n" +
                    "    <!--base begin-->\n" +
                    "    <script async=\"\" src=\"./TRƯỜNG ĐẠI HỌC CÔNG NGHIỆP HÀ NỘI __ HaUI_files/analytics.js.tải xuống\"></script><script type=\"text/javascript\">\n" +
                    "        var oAppPath = 'https://dttc.haui.edu.vn/vn/', oDnnUrl = 'https://egov.haui.edu.vn/dnn/', oTemplateApp = 'ttlc', oMediaUrl = 'https://dttc.haui.edu.vn/media/', oVersion = 'V3.0', _oVersion = 'V3.0';\n" +
                    "        var ospath = 'https://dttc.haui.edu.vn/vn/l/';\n" +
                    "        var oMustChangePass = ''; var oSessionKey = ''; var oUserName = ''; var oUserID = ''; var Logined = false;\n" +
                    "        var oFullName = '';\n" +
                    "        isLogged = false;\n" +
                    "        \n" +
                    "        oMustChangePass = 'False';\n" +
                    "        \n" +
                    "        var oFrameworkUrl = 'https://dttc.haui.edu.vn/api/';\n" +
                    "\n" +
                    "         if( screen.width < 800 ) {\n" +
                    "\n" +
                    "        //cookie name mypopup not found so display the popup\n" +
                    "       if(!getCookie(\"mypopup\")){\n" +
                    "            setCookie(\"mypopup\",true); //sets the cookie mypopup\n" +
                    "            var url=confirm(\"Bạn có muốn tải ứng dụng trên thiết bị di động?\");\n" +
                    "            if (url)\n" +
                    "            {\n" +
                    "                window.location.href = 'https://play.google.com/store/apps/details?id=vn.edu.haui.dttc'; \n" +
                    "            }\n" +
                    "       }\n" +
                    "\n" +
                    "    }\n" +
                    "\n" +
                    "    //function to retrieve cookie value\n" +
                    "    function getCookie(cname) {\n" +
                    "        var name = cname + \"=\";\n" +
                    "        var ca = document.cookie.split(';');\n" +
                    "        for(var i = 0; i <ca.length; i++) {\n" +
                    "            var c = ca[i];\n" +
                    "            while (c.charAt(0)==' ') {\n" +
                    "                c = c.substring(1);\n" +
                    "            }\n" +
                    "            if (c.indexOf(name) == 0) {\n" +
                    "                return c.substring(name.length,c.length);\n" +
                    "            }\n" +
                    "        }\n" +
                    "        return \"\";\n" +
                    "    }\n" +
                    "\n" +
                    "    //function to set the cookie\n" +
                    "    function setCookie(cname, cvalue) {\n" +
                    "        document.cookie = cname + \"=\" + cvalue + \"; \";\n" +
                    "    }\n" +
                    "    </script>\n" +
                    "\n" +
                    "    <script type=\"text/javascript\" src=\"./TRƯỜNG ĐẠI HỌC CÔNG NGHIỆP HÀ NỘI __ HaUI_files/dcore-1.10.2.min.js.tải xuống\"></script>\n" +
                    "    <script type=\"text/javascript\" src=\"./TRƯỜNG ĐẠI HỌC CÔNG NGHIỆP HÀ NỘI __ HaUI_files/dj.UI.js.tải xuống\"></script>\n" +
                    "    <script type=\"text/javascript\" src=\"./TRƯỜNG ĐẠI HỌC CÔNG NGHIỆP HÀ NỘI __ HaUI_files/meTooltip120110.js.tải xuống\"></script></head><body><div id=\"osdhtmltooltip\" style=\"VISIBILITY: hidden;\"></div><img id=\"osdhtmlpointer\" style=\"VISIBILITY: hidden;\" src=\"./TRƯỜNG ĐẠI HỌC CÔNG NGHIỆP HÀ NỘI __ HaUI_files/tooltiparrow.gif\">\n" +
                    "    <script type=\"text/javascript\" src=\"./TRƯỜNG ĐẠI HỌC CÔNG NGHIỆP HÀ NỘI __ HaUI_files/dui-2.1.min.js.tải xuống\"></script>\n" +
                    "    <script type=\"text/javascript\" src=\"./TRƯỜNG ĐẠI HỌC CÔNG NGHIỆP HÀ NỘI __ HaUI_files/homeInit14.js.tải xuống\"></script>\n" +
                    "    <script type=\"text/javascript\" src=\"./TRƯỜNG ĐẠI HỌC CÔNG NGHIỆP HÀ NỘI __ HaUI_files/meCore120110.js.tải xuống\"></script>\n" +
                    "    <!--base end-->\n" +
                    "    <script type=\"text/javascript\" src=\"./TRƯỜNG ĐẠI HỌC CÔNG NGHIỆP HÀ NỘI __ HaUI_files/d1.10.js.tải xuống\"></script>\n" +
                    "    <script type=\"text/javascript\" src=\"./TRƯỜNG ĐẠI HỌC CÔNG NGHIỆP HÀ NỘI __ HaUI_files/dmeCore-1.0.js.tải xuống\"></script>\n" +
                    "    <script type=\"text/javascript\" src=\"./TRƯỜNG ĐẠI HỌC CÔNG NGHIỆP HÀ NỘI __ HaUI_files/sysLibrary280115.js.tải xuống\"></script>\n" +
                    "    <script type=\"text/javascript\" src=\"./TRƯỜNG ĐẠI HỌC CÔNG NGHIỆP HÀ NỘI __ HaUI_files/me.core.js.tải xuống\"></script>\n" +
                    "    <script type=\"text/javascript\" src=\"./TRƯỜNG ĐẠI HỌC CÔNG NGHIỆP HÀ NỘI __ HaUI_files/sysNotification.js.tải xuống\"></script>\n" +
                    "    <script type=\"text/javascript\" src=\"./TRƯỜNG ĐẠI HỌC CÔNG NGHIỆP HÀ NỘI __ HaUI_files/sysWidget100410.js.tải xuống\"></script>\n" +
                    "    \n" +
                    "    \n" +
                    "    \n" +
                    "    <meta name=\"description\" content=\"HaUI, Hanoi University of Industry,phát triển phần mềm, đại học công nghiệp hà nội, đào tạo tín chỉ, đào tạo niên chế,làm SEO\">\n" +
                    "    <meta name=\"keywords\" content=\"HaUI, Hanoi University of Industry,phát triển phần mềm,Trung tâm ngoại ngữ tin học, trường đại học công nghiệp hà nội, trung tâm ngoại ngữ tin học, ngoại ngữ, tin học, tuyển sinh aptech, đào tạo tín chỉ, đào tạo niên chế,làm SEO\">\n" +
                    "\n" +
                    "\n" +
                    "    <script type=\"text/javascript\">\n" +
                    "        dj(document).ready(function () {\n" +
                    "            var iView = dj(\"#iView\");\n" +
                    "            \n" +
                    "        });\n" +
                    "    </script>\n" +
                    "\n" +
                    "    <meta name=\"copyright\" content=\"Copyright 2014 by http://aptech.haui.edu.vn\">\n" +
                    "    <meta name=\"abstract\" content=\"TRƯỜNG ĐẠI HỌC CÔNG NGHIỆP HÀ NỘI || HaUI\">\n" +
                    "    <meta name=\"distribution\" content=\"Global\">\n" +
                    "    <meta name=\"author\" content=\"aptech.haui.edu.vn\">\n" +
                    "    <meta name=\"verify-v1\" content=\"\">\n" +
                    "\n" +
                    "  <script>\n" +
                    "  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){\n" +
                    "  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),\n" +
                    "  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)\n" +
                    "  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');\n" +
                    "\n" +
                    "  ga('create', 'UA-53528822-1','auto');\n" +
                    "  ga('create', 'UA-81799463-1', 'auto','ducvtTracker');\n" +
                    "  ga('create', 'UA-48346684-3', 'auto','clientTracker');\n" +
                    "  ga('send', 'pageview');\n" +
                    "  ga('ducvtTracker.send', 'pageview');\n" +
                    "  ga('clientTracker.send', 'pageview');\n" +
                    "</script>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "    <script type=\"text/javascript\" src=\"./TRƯỜNG ĐẠI HỌC CÔNG NGHIỆP HÀ NỘI __ HaUI_files/sysAjaxForm110413_1.js.tải xuống\"></script>\n" +
                    "    <script type=\"text/javascript\">\n" +
                    "        var formAjaxBox;\n" +
                    "        function fabSetWidth(width) {\n" +
                    "            if (!formAjaxBox)\n" +
                    "                formAjaxBox = document.getElementById('formAjaxBox');\n" +
                    "            if (formAjaxBox)\n" +
                    "                formAjaxBox.style.width = width + 'px';\n" +
                    "        }\n" +
                    "\n" +
                    "        function fabSetIcon(iconClassName) {\n" +
                    "            jh('#formAjaxBox .icon').each(function () {\n" +
                    "                this.className = 'icon ' + iconClassName;\n" +
                    "            });\n" +
                    "        }\n" +
                    "        function mlogout(oSessionKey, ospath, oUserName) {\n" +
                    "            oDialog.ynquestion('Bạn chắc chắn muốn thoát khỏi hệ thống?', function () {\n" +
                    "                window.location = ospath + 'l/home/logout/' + oSessionKey + '/?r=' + ospath + '&u=' + oUserName;\n" +
                    "                return true;\n" +
                    "            }, function () {\n" +
                    "                return true;\n" +
                    "            }, {\n" +
                    "                title: 'Thoát khỏi hệ thống?'\n" +
                    "            });\n" +
                    "            return false;\n" +
                    "        };\n" +
                    "\n" +
                    "        if (oMustChangePass == 'True') {\n" +
                    "            MustchangePass();\n" +
                    "        }\n" +
                    "        function MustchangePass() {\n" +
                    "            oDialog.alert(\"Vui lòng đổi mật khẩu cho mật khẩu mặc định!\");\n" +
                    "            oDialog.openIframe(oAppPath + 'l/sMemberShip/changePass/', 'Đổi mật khẩu', null, {\n" +
                    "                width: 300,\n" +
                    "                height: 100,\n" +
                    "                modal: true,\n" +
                    "                closeButton: false,\n" +
                    "                okButton: 'Thay đổi',\n" +
                    "                allowSubmit: true\n" +
                    "            });\n" +
                    "            return false;\n" +
                    "        };\n" +
                    "        function ChangePass() {\n" +
                    "            oDialog.openIframe(oAppPath + 'l/sMemberShip/changePass/', 'Đổi mật khẩu', null, {\n" +
                    "                width: 300,\n" +
                    "                height: 100,\n" +
                    "                modal: true,\n" +
                    "                closeButton: true,\n" +
                    "                okButton: 'Thay đổi',\n" +
                    "                allowSubmit: true\n" +
                    "            });\n" +
                    "            return false;\n" +
                    "        };\n" +
                    "        //dj(document).ready(function () {\n" +
                    "        //    oNotification.init();\n" +
                    "        //});\n" +
                    "    </script>\n" +
                    "\n" +
                    "\n" +
                    "    <div id=\"Main\">\n" +
                    "        <div id=\"Theme\">\n" +
                    "            <div class=\"Header\">\n" +
                    "                <div class=\"miniNav\">\n" +
                    "                    <div class=\"mTime\">\n" +
                    "                        <p>Thứ Sáu, 02/12/2016</p>\n" +
                    "                    </div>\n" +
                    "                    <div class=\"lang\">\n" +
                    "                        <p>Ngôn ngữ :</p>\n" +
                    "                        <a href=\"https://dttc.haui.edu.vn/vn\">\n" +
                    "                            <img src=\"./TRƯỜNG ĐẠI HỌC CÔNG NGHIỆP HÀ NỘI __ HaUI_files/vn.jpg\" alt=\"\"></a>\n" +
                    "                        \n" +
                    "                    </div>\n" +
                    "                    <div class=\"mMenu\">\n" +
                    "                        <ul>\n" +
                    "                            \n" +
                    "                            \n" +
                    "                            <li>\n" +
                    "                                <a class=\"uInfo\">\n" +
                    "                            </a></li><a class=\"uInfo\">\n" +
                    "\t\t\t\t\t\t\t</a><li><a class=\"uInfo\"></a><a href=\"https://dttc.haui.edu.vn/core/\" target=\"_blank\">Quản trị nội dung</a></li>\n" +
                    "                        </ul>\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "                \n" +
                    "                <div class=\"banner\">\n" +
                    "                    \n" +
                    "                </div>\n" +
                    "                <div class=\"menuTop\">\n" +
                    "                    <ul id=\"ohMenu\">\n" +
                    "                        \n" +
                    "    <li><a href=\"https://dttc.haui.edu.vn/vn/\" class=\"home default\">Trang chủ</a></li>\n" +
                    "    <li><a href=\"https://dttc.haui.edu.vn/vn/#\" class=\"tra-cuu-sinh-vien\">Tra cứu SV </a>\n" +
                    "        <ul id=\"tracuuhd\">\n" +
                    "            <li>\n" +
                    "                <a href=\"https://dttc.haui.edu.vn/vn/s/sinh-vien/ket-qua-hoc-tap\" class=\"ket-qua-hoc-tap\">Kết quả học tập</a>\n" +
                    "            </li>\n" +
                    "            <li>\n" +
                    "                <a href=\"https://dttc.haui.edu.vn/vn/s/sinh-vien/tra-cuu-cong-nhan-lien-thong\" class=\"tra-cuu-cong-nhan-lien-thong\">Tiến độ học tập (Liên thông)</a>\n" +
                    "            </li>\n" +
                    "            <li>\n" +
                    "                <a href=\"https://dttc.haui.edu.vn/vn/s/sinh-vien/tien-do-hoc-tap\" class=\"tien-do-hoc-tap\">Tiến độ học tập (Tín chỉ-Niên chế)</a>\n" +
                    "            </li>\n" +
                    "            \n" +
                    "            <li>\n" +
                    "                <a href=\"https://dttc.haui.edu.vn/vn/s/sinh-vien/tkb\" class=\"tkb\">Thời khóa biểu</a>\n" +
                    "            </li>\n" +
                    "            <li>\n" +
                    "                <a href=\"https://dttc.haui.edu.vn/vn/s/sinh-vien/dien-bien-thu-phi\" class=\"dien-bien-thu-phi\">Diễn biến thu phí</a>\n" +
                    "            </li>\n" +
                    "            \n" +
                    "            <li>\n" +
                    "                <a href=\"https://dttc.haui.edu.vn/vn/s/sinh-vien/tra-cuu-mon-hoc-tuong-duong\" class=\"tra-cuu-mon-hoc-tuong-duong\">Môn học tương đương</a>\n" +
                    "            </li>\n" +
                    "        </ul>\n" +
                    "    </li>\n" +
                    "    <li><a href=\"https://dttc.haui.edu.vn/vn/#\" class=\"sinh-vien\">Sinh viên</a>\n" +
                    "        <ul>\n" +
                    "            <li>\n" +
                    "                <a href=\"https://dttc.haui.edu.vn/vn/s/sinh-vien/danh-sach-mon-dk\" class=\"danh-sach-mon-dk\">Đăng ký học phần</a>\n" +
                    "            </li>\n" +
                    "            <li>\n" +
                    "                <a href=\"https://dttc.haui.edu.vn/vn/s/sinh-vien/ds-mon-da-dang-ky\" class=\"ds-mon-da-dang-ky\">Hủy đăng ký học phần</a>\n" +
                    "            </li>\n" +
                    "            <li>\n" +
                    "                <a href=\"https://dttc.haui.edu.vn/vn/s/sinh-vien/sua-sai-thong-tin\" class=\"sua-sai-thong-tin\">Sửa sai thông tin cá nhân</a>\n" +
                    "            </li>\n" +
                    "            <li>\n" +
                    "                <a href=\"https://dttc.haui.edu.vn/vn/s/sinh-vien/so-yeu-ly-lich\" class=\"so-yeu-ly-lich\">Cập nhật sơ yếu lý lịch</a>\n" +
                    "            </li>\n" +
                    "            <li>\n" +
                    "                <a href=\"https://dttc.haui.edu.vn/vn/s/sinh-vien/xem-syll\" class=\"xem-syll\">Xem hồ sơ</a>\n" +
                    "            </li>\n" +
                    "            \n" +
                    "        </ul>\n" +
                    "    </li>\n" +
                    "    <li><a href=\"https://dttc.haui.edu.vn/vn/#\" class=\"thong-ke\">Danh sách thống kê</a>\n" +
                    "        <ul>\n" +
                    "            <li>\n" +
                    "                <a href=\"https://dttc.haui.edu.vn/vn/s/sinh-vien/so-luong-sinh-vien-lop-doc-lap\" class=\"so-luong-sinh-vien-lop-doc-lap\">Số lượng SV lớp độc lập</a>\n" +
                    "            </li>\n" +
                    "            <li>\n" +
                    "                <a href=\"https://dttc.haui.edu.vn/vn/s/sinh-vien/kl-dk-lop-on-dinh\" class=\"kl-dk-lop-on-dinh\">Khối lượng đăng ký lớp ổn định</a>\n" +
                    "            </li>\n" +
                    "            <li>\n" +
                    "                <a href=\"https://dttc.haui.edu.vn/vn/s/sinh-vien/ket-qua-lop-on-dinh\" class=\"ket-qua-lop-on-dinh\">Điểm tổng kết lớp ổn định</a>\n" +
                    "            </li>\n" +
                    "            <li>\n" +
                    "                <a href=\"https://dttc.haui.edu.vn/vn/s/sinh-vien/ds-hoc-chuong-trinh-2\" class=\"ds-hoc-chuong-trinh-2\">Danh sách học chương trình 2</a>\n" +
                    "            </li>\n" +
                    "        </ul>\n" +
                    "    </li>\n" +
                    "    <li>\n" +
                    "        <a href=\"https://dttc.haui.edu.vn/vn/s/sinh-vien/tra-cuu-van-bang\" class=\"tra-cuu-van-bang\">Tra cứu văn bằng</a>\n" +
                    "    </li>\n" +
                    "    \n" +
                    "    <li>\n" +
                    "        <a href=\"https://dttc.haui.edu.vn/vn/l/home/logon\" style=\"cursor: pointer\">Đăng nhập</a>\n" +
                    "    </li>\n" +
                    "    \n" +
                    "                    </ul>\n" +
                    "                    \n" +
                    "                </div>\n" +
                    "            </div>\n" +
                    "            \n" +
                    "    \n" +
                    "    \n" +
                    "    <div class=\"padding7\"></div>\n" +
                    "    <div class=\"content\">\n" +
                    "        \n" +
                    "   \n" +
                    "\n" +
                    "        <div class=\"Mcontent\">\n" +
                    "            \n" +
                    "\n" +
                    "    <div class=\"Ccontent\">\n" +
                    "\n" +
                    "        <div class=\"BoxTemp2\">\n" +
                    "            <h2><a href=\"https://dttc.haui.edu.vn/vn/#\" onclick=\"return false;\" style=\"text-align: center\">TẦM NHÌN - SỨ MẠNG - CHÍNH SÁCH CHẤT LƯỢNG </a><span><a href=\"https://dttc.haui.edu.vn/vn/#\" onclick=\"return showTamnhin(this);\">Xem tiếp</a></span></h2>\n" +
                    "            <div class=\"no1new\">Trường Đại Học Công Nghiệp Hà Nội có sứ mạng cung cấp dịch vụ giáo dục đào tạo nhiều ngành, nhiều trình độ và dịch vụ khoa học chất lượng cao cho thị trường, đáp ứng yêu cầu hội nhập quốc tế. Tạo cơ hội và môi trường học tập thuận lợi cho mọi đối tượng. </div>\n" +
                    "            <div class=\"no1new\">\n" +
                    "                <div class=\"showh\" style=\"display: none;\">\n" +
                    "                    <h3>Tầm nhìn\n" +
                    "            </h3>\n" +
                    "                    <p style=\"text-align: justify;\">\n" +
                    "                        Trường Đại học Công nghiệp Hà Nội là trường công lập đẳng cấp quốc tế, hàng đầu\n" +
                    "                của Việt Nam đào tạo công nghệ nhiều cấp trình độ, nhiều ngành, là Trung tâm nghiên\n" +
                    "                cứu – phát triển - chuyển giao công nghệ uy tín và tin cậy.\n" +
                    "           \n" +
                    "                    </p>\n" +
                    "                    <h3>Sứ mạng\n" +
                    "            </h3>\n" +
                    "                    <p style=\"text-align: justify;\">\n" +
                    "                        Đại học Công nghiệp Hà Nội cung cấp dịch vụ giáo dục đào tạo, khoa học – công nghệ\n" +
                    "                chất lượng cao, nhiều ngành, nhiều loại hình và là môi trường học tập thuận lợi\n" +
                    "                tạo cơ hội tiệp cận cho mọi đối tượng, đáp ứng yêu cầu công nghiệp hóa, hiện đại\n" +
                    "                hóa đất nước và hội nhập quốc tế.\n" +
                    "           \n" +
                    "                    </p>\n" +
                    "                    <h3>Chính sách chất lượng\n" +
                    "            </h3>\n" +
                    "                    <p style=\"text-align: justify;\">\n" +
                    "                        1. Xây dựng Trường trở thành cơ sở đào tạo mở, hướng tới người học và các bên quan\n" +
                    "                tâm. Đào tạo nguồn nhân lực chất lượng cao với nhiều trình độ phù hợp yêu cầu phát\n" +
                    "                triển kinh tế - xã hội.<br>\n" +
                    "                        2. Thường xuyên cải tiến phương pháp giảng dạy, lấy người học làm TRUNG TÂM; triệt\n" +
                    "                để áp dụng công nghệ thông tin vào quản lý, giảng dạy và học tập; thí điểm, tiến\n" +
                    "                tới tổ chức đào tạo hoàn toàn theo tín chỉ.<br>\n" +
                    "                        3. Mở rộng liên kết đào tạo với các trường, với các cơ sở kỹ thuật - kinh tế trong\n" +
                    "                và ngoài nước.<br>\n" +
                    "                        4. Khuyến khích học tập, sáng tạo.<br>\n" +
                    "                        5. Cam kết xây dựng, thực hiện, duy trì các hệ thống quản lý tiên tiến (ISO 9000,\n" +
                    "                TQM) để đạt được kiểm định công nhận chất lượng của Việt Nam, của các tổ chức quốc\n" +
                    "                tế.<br>\n" +
                    "                    </p>\n" +
                    "                </div>\n" +
                    "            </div>\n" +
                    "\n" +
                    "        \n" +
                    "<div class=\"BoxTemp3\">\n" +
                    "    <h2>\n" +
                    "        Thông báo\n" +
                    "        <img src=\"./TRƯỜNG ĐẠI HỌC CÔNG NGHIỆP HÀ NỘI __ HaUI_files/new.gif\" alt=\"\" class=\"new\">\n" +
                    "        <a href=\"https://dttc.haui.edu.vn/vn/thong-bao\">xem thêm</a>\n" +
                    "    </h2>\n" +
                    "    <div class=\"boxNews\">\n" +
                    "        \n" +
                    "        <ul>\n" +
                    "            \n" +
                    "            <li>» <a href=\"https://dttc.haui.edu.vn/vn/thong-bao/thong-bao-ve-viec-to-chuc-hoc-ky-phu-thu-nhat-nam-hoc-2016-2017-cho-sinh-vien-cac-lop-dai-hoc-chinh-quy-theo-he-thong-tin-chi/49776\">\n" +
                    "                Thông báo về việc tổ chức học kỳ phụ thứ nhất năm học 2016-2017 cho sinh viên các lớp Đại học chính quy theo hệ thống tín chỉ\n" +
                    "            </a></li>\n" +
                    "            \n" +
                    "            <li>» <a href=\"https://dttc.haui.edu.vn/vn/thong-bao/ke-hoach-cap-bang-tot-nghiep-cho-sv-dai-hoc-cao-dang-tot-nghiep-dot-2-nam-2016/49775\">\n" +
                    "                Kế hoạch cấp bằng tốt nghiệp cho SV Đại học, Cao đẳng tốt nghiệp đợt 2 năm 2016\n" +
                    "            </a></li>\n" +
                    "            \n" +
                    "            <li>» <a href=\"https://dttc.haui.edu.vn/vn/thong-bao/thong-bao-ve-viec-to-chuc-hoc-tap-cho-sinh-vien-lien-thong-len-dai-hoc-khoa-11-dot-2-trong-hoc-ky-1-nam-hoc-2016-2017/49774\">\n" +
                    "                Thông báo về việc tổ chức học tập cho sinh viên Liên thông lên Đại học khóa 11 (đợt 2) trong học kỳ 1 năm học 2016-2017\n" +
                    "            </a></li>\n" +
                    "            \n" +
                    "            <li>» <a href=\"https://dttc.haui.edu.vn/vn/thong-bao/danh-sach-sinh-vien-dai-hoc-khoa-7-8-9-10-duoc-huy-hoc-phan-trong-hoc-ky-1-nam-hoc-2016-2017/49773\">\n" +
                    "                Danh sách sinh viên Đại học Khóa 7,8,9,10 được hủy học phần trong học kỳ 1 năm học 2016 - 2017\n" +
                    "            </a></li>\n" +
                    "            \n" +
                    "            <li>» <a href=\"https://dttc.haui.edu.vn/vn/thong-bao/thong-bao-ve-viec-mo-khong-mo-cac-lop-doc-lap-trong-hoc-ky-1-nam-hoc-2016-2017-cho-sinh-vien-lien-thong-dai-hoc-khoa-11/49772\">\n" +
                    "                Thông báo về việc mở, không mở các lớp độc lập trong học kỳ 1 năm học 2016 - 2017 cho sinh viên Liên thông Đại học khóa 11\n" +
                    "            </a></li>\n" +
                    "            \n" +
                    "        </ul>\n" +
                    "        \n" +
                    "        <div class=\"clr\">\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "</div>\n" +
                    "\n" +
                    "<div class=\"BoxTemp3\">\n" +
                    "    <h2>\n" +
                    "        Công tác HSSV\n" +
                    "        <img src=\"./TRƯỜNG ĐẠI HỌC CÔNG NGHIỆP HÀ NỘI __ HaUI_files/new.gif\" alt=\"\" class=\"new\">\n" +
                    "        <a href=\"https://dttc.haui.edu.vn/vn/cong-tac-hssv\">xem thêm</a>\n" +
                    "    </h2>\n" +
                    "    <div class=\"boxNews\">\n" +
                    "        \n" +
                    "        <ul>\n" +
                    "            \n" +
                    "            <li>» <a href=\"https://dttc.haui.edu.vn/vn/cong-tac-hssv/dssv-duoc-cap-bu-tien-mien-giam-hoc-phi-hk1-nam-hoc-2015-2016/49759\">\n" +
                    "                DSSV được cấp bù tiền miễn giảm học phí HK1 năm học 2015-2016\n" +
                    "            </a></li>\n" +
                    "            \n" +
                    "            <li>» <a href=\"https://dttc.haui.edu.vn/vn/cong-tac-hssv/thong-bao-ve-ke-hoach-thu-nop-kinh-phi-doi-voi-sinh-vien-dh-k3-k4-k5-k6-va-cao-dang-k10-k11-k12-k13-k14-nam-hoc-2015-2016/49756\">\n" +
                    "                Thông báo về kế hoạch thu, nộp kinh phí đối với sinh viên ĐH (K3, K4, K5, K6) và cao đẳng (K10, K11, K12, K13, K14 năm học 2015-2016)\n" +
                    "            </a></li>\n" +
                    "            \n" +
                    "            <li>» <a href=\"https://dttc.haui.edu.vn/vn/cong-tac-hssv/ke-hoach-chi-tien-hoc-bong-khen-thuong-nam-hoc-2014-2015-va-tien-ho-tro-chi-phi-hoc-tap-nam-2015/49751\">\n" +
                    "                Kế hoạch chi tiền học bổng, khen thưởng năm học 2014-2015 và tiền hỗ trợ chi phí học tập năm 2015\n" +
                    "            </a></li>\n" +
                    "            \n" +
                    "            <li>» <a href=\"https://dttc.haui.edu.vn/vn/cong-tac-hssv/danh-sach-sv-duoc-nhan-tien-ho-tro-chi-phi-hoc-tap-2015/49750\">\n" +
                    "                Danh sách SV được nhận tiền hỗ trợ chi phí học tập 2015\n" +
                    "            </a></li>\n" +
                    "            \n" +
                    "            <li>» <a href=\"https://dttc.haui.edu.vn/vn/cong-tac-hssv/danh-sach-hssv-duoc-nhan-hoc-bong-va-khen-thuong-nam-hoc-2014-2015/49749\">\n" +
                    "                Danh sách HSSV được nhận học bổng và khen thưởng năm học 2014-2015\n" +
                    "            </a></li>\n" +
                    "            \n" +
                    "        </ul>\n" +
                    "        \n" +
                    "        <div class=\"clr\">\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "</div>\n" +
                    "\n" +
                    "<div class=\"BoxTemp3\">\n" +
                    "    <h2>\n" +
                    "        Công văn quyết định\n" +
                    "        <img src=\"./TRƯỜNG ĐẠI HỌC CÔNG NGHIỆP HÀ NỘI __ HaUI_files/new.gif\" alt=\"\" class=\"new\">\n" +
                    "        <a href=\"https://dttc.haui.edu.vn/vn/cong-van-quyet-dinh\">xem thêm</a>\n" +
                    "    </h2>\n" +
                    "    <div class=\"boxNews\">\n" +
                    "        \n" +
                    "        <ul>\n" +
                    "            \n" +
                    "            <li>» <a href=\"https://dttc.haui.edu.vn/vn/cong-van-quyet-dinh/quyet-dinh-mo-lop-dh-k7-trong-hoc-ky-2-nam-hoc-2012-2013/49339\">\n" +
                    "                Quyết định mở lớp ĐH-K7 trong học kỳ 2 năm học 2012-2013\n" +
                    "            </a></li>\n" +
                    "            \n" +
                    "            <li>» <a href=\"https://dttc.haui.edu.vn/vn/cong-van-quyet-dinh/quyet-dinh-mo-lop-cho-dai-hoc-k3-k4-trong-hoc-ky-phu-song-song-hoc-ky-chinh-thu-2-nam-hoc-2012-2013/49340\">\n" +
                    "                Quyết định mở lớp cho Đại học K3, K4 trong học kỳ phụ song song học kỳ chính thứ 2 năm học 2012-2013\n" +
                    "            </a></li>\n" +
                    "            \n" +
                    "            <li>» <a href=\"https://dttc.haui.edu.vn/vn/cong-van-quyet-dinh/quyet-dinh-mo-lop-cho-cac-lop-doc-lap-trong-hoc-ky-2-nam-hoc-2012-2013-cho-sv-cd-k13-k14/49349\">\n" +
                    "                Quyết định mở lớp cho các lớp độc lập trong học kỳ 2 năm học 2012 - 2013 cho SV CĐ K13,K14\n" +
                    "            </a></li>\n" +
                    "            \n" +
                    "        </ul>\n" +
                    "        \n" +
                    "        <div class=\"clr\">\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "</div>\n" +
                    "\n" +
                    "<div class=\"BoxTemp3\">\n" +
                    "    <h2>\n" +
                    "        Quy chế đào tạo\n" +
                    "        <img src=\"./TRƯỜNG ĐẠI HỌC CÔNG NGHIỆP HÀ NỘI __ HaUI_files/new.gif\" alt=\"\" class=\"new\">\n" +
                    "        <a href=\"https://dttc.haui.edu.vn/vn/quy-che-dao-tao\">xem thêm</a>\n" +
                    "    </h2>\n" +
                    "    <div class=\"boxNews\">\n" +
                    "        \n" +
                    "        <ul>\n" +
                    "            \n" +
                    "            <li>» <a href=\"https://dttc.haui.edu.vn/vn/quy-che-dao-tao/quy-che-dao-tao-dh-cd-chinh-quy-nam-2015-ban-chinh-thuc/49733\">\n" +
                    "                Quy chế đào tạo ĐH, CĐ chính quy năm 2015 (bản chính thức)\n" +
                    "            </a></li>\n" +
                    "            \n" +
                    "            <li>» <a href=\"https://dttc.haui.edu.vn/vn/quy-che-dao-tao/cac-mau-don-trong-dao-tao-tin-chi-2013/49337\">\n" +
                    "                Các mẫu đơn trong đào tạo tín chỉ 2013\n" +
                    "            </a></li>\n" +
                    "            \n" +
                    "            <li>» <a href=\"https://dttc.haui.edu.vn/vn/quy-che-dao-tao/quy-che-da-o-ta-o-trung-ca-p-nghe-cao-da-ng-nghe/49330\">\n" +
                    "                Quy chế đào tạo Trung cấp nghề, cao đẳng nghề\n" +
                    "            </a></li>\n" +
                    "            \n" +
                    "            <li>» <a href=\"https://dttc.haui.edu.vn/vn/quy-che-dao-tao/quy-che-da-o-ta-o-da-i-ho-c-cao-da-ng-vu-a-ho-c-vu-a-la-m/49343\">\n" +
                    "                Quy chế đào tạo Đại học, cao đẳng vừa học vừa làm\n" +
                    "            </a></li>\n" +
                    "            \n" +
                    "            <li>» <a href=\"https://dttc.haui.edu.vn/vn/quy-che-dao-tao/quy-che-da-o-ta-o-trung-ca-p-chuyen-nghie-p/49344\">\n" +
                    "                Quy chế đào tạo Trung cấp chuyên nghiệp\n" +
                    "            </a></li>\n" +
                    "            \n" +
                    "        </ul>\n" +
                    "        \n" +
                    "        <div class=\"clr\">\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "</div>\n" +
                    "\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "    <div class=\"Rcontent\">\n" +
                    "        \n" +
                    "<div class=\"BoxTemp\">\n" +
                    "    <h2><div class=\"icon i5\"></div><a href=\"https://dttc.haui.edu.vn/vn/#\">Tin nổi bật </a></h2>\n" +
                    "    \n" +
                    "    <ul>\n" +
                    "        <marquee behavior=\"scroll\" direction=\"up\" scrollamount=\"2\">\n" +
                    "        \n" +
                    "        <li><a href=\"https://dttc.haui.edu.vn/vn/thong-bao/ke-hoach-cap-bang-tot-nghiep-cho-sv-dai-hoc-cao-dang-tot-nghiep-dot-2-nam-2016/49775\">»\n" +
                    "            Kế hoạch cấp bằng tốt nghiệp cho SV Đại học, Cao đẳng tốt nghiệp đợt 2 năm 2016<span>( 07/11/2016)</span></a></li>\n" +
                    "        \n" +
                    "        <li><a href=\"https://dttc.haui.edu.vn/vn/quy-che-dao-tao/quy-che-dao-tao-dh-cd-chinh-quy-nam-2015-ban-chinh-thuc/49733\">»\n" +
                    "            Quy chế đào tạo ĐH, CĐ chính quy năm 2015 (bản chính thức)<span>( 22/09/2015)</span></a></li>\n" +
                    "        \n" +
                    "        <li><a href=\"https://dttc.haui.edu.vn/vn/thong-bao/danh-sach-sinh-vien-dai-hoc-khoa-6-duoc-lam-da-kltn/49419\">»\n" +
                    "            Danh sách sinh viên Đại học khóa 6 được làm ĐA/KLTN<span>( 09/02/2015)</span></a></li>\n" +
                    "        \n" +
                    "        <li><a href=\"https://dttc.haui.edu.vn/vn/thong-bao/tb-dieu-chinh-lich-dang-ky-klht-cua-dh-k6/49413\">»\n" +
                    "            TB điều chỉnh lịch đăng ký KLHT của ĐH k6<span>( 02/02/2015)</span></a></li>\n" +
                    "        \n" +
                    "        <li><a href=\"https://dttc.haui.edu.vn/vn/thong-bao/thong-bao-ve-viec-mo-khong-mo-cac-lop-doc-lap-trong-hoc-ky-phu-1-cho-sinh-vien-dai-hoc-va-hoc-thay-the-da-kltn-cho-sinh-vien-lien-thong-cd-dh-khoa-8/49376\">»\n" +
                    "            Thông báo về việc mở, không mở các lớp độc lập trong học kỳ phụ 1 cho sinh viên Đại học và học thay thế ĐA/KLTN cho sinh viên Liên thông CĐ-ĐH khóa 8<span>( 23/12/2014)</span></a></li>\n" +
                    "        \n" +
                    "        </marquee>\n" +
                    "    </ul>\n" +
                    "</div>\n" +
                    "<div class=\"BoxTemp\">\n" +
                    "    <h2><div class=\"icon i5\"></div><a href=\"https://dttc.haui.edu.vn/vn/#\">Công nhận liên thông</a></h2>\n" +
                    "       <a href=\"https://dttc.haui.edu.vn/vn/s/sinh-vien/tra-cuu-cong-nhan-lien-thong\"> <img src=\"./TRƯỜNG ĐẠI HỌC CÔNG NGHIỆP HÀ NỘI __ HaUI_files/congnhanlienthong.jpg\" alt=\"\" width=\"218\" height=\"218\"></a>\n" +
                    "</div>\n" +
                    "\n" +
                    "<div class=\"BoxTemp\">\n" +
                    "    <h2><div class=\"icon i5\"></div><a href=\"https://dttc.haui.edu.vn/vn/#\">Ứng dụng</a></h2>\n" +
                    "       <a href=\"https://play.google.com/store/apps/details?id=vn.edu.haui.dttc\"> \n" +
                    "           <img src=\"./TRƯỜNG ĐẠI HỌC CÔNG NGHIỆP HÀ NỘI __ HaUI_files/Ads.jpg\" alt=\"dttc,haui,students\" width=\"218\" height=\"218\" title=\"Ứng dụng hỗ trợ học tập đào tạo tín chỉ HaUI\">\n" +
                    "       </a>\n" +
                    "</div>\n" +
                    "    </div>\n" +
                    "\n" +
                    "            <div style=\"display: none;\" id=\"f_loading_message\" class=\"loadingView\">\n" +
                    "                <p>\n" +
                    "                    <b><b><b>\n" +
                    "                        <img alt=\"loading\" class=\"loading\" src=\"./TRƯỜNG ĐẠI HỌC CÔNG NGHIỆP HÀ NỘI __ HaUI_files/loading.gif\">\n" +
                    "                        <span>đang tải...</span>&nbsp;&nbsp;&nbsp;\n" +
                    "                    <img alt=\"cancel\" src=\"./TRƯỜNG ĐẠI HỌC CÔNG NGHIỆP HÀ NỘI __ HaUI_files/im_blocked_offline.png\">\n" +
                    "                        <a href=\"https://dttc.haui.edu.vn/vn/#\" class=\"canc\">đóng</a></b></b></b>\n" +
                    "                </p>\n" +
                    "            </div>\n" +
                    "        </div>\n" +
                    "        \n" +
                    "    <script type=\"text/javascript\">\n" +
                    "        var tamnhindong = true;\n" +
                    "        function showTamnhin(t) {\n" +
                    "            dj(\".showh\").toggle('slow', function () {\n" +
                    "                if (!tamnhindong)\n" +
                    "                    dj(t).html(\"Xem tiếp\");\n" +
                    "                else\n" +
                    "                    dj(t).html(\"Đóng lại\");\n" +
                    "                tamnhindong = !tamnhindong;\n" +
                    "            });\n" +
                    "            return false;\n" +
                    "        };\n" +
                    "        //var oiInteraction = new _interactionCore.init(1, true, true);\n" +
                    "    </script>\n" +
                    "\n" +
                    "    </div>\n" +
                    "\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "    <div id=\"Footer\">\n" +
                    "        <p class=\"copy\"><b>TRƯỜNG ĐẠI HỌC CÔNG NGHIỆP HÀ NỘI</b></p>\n" +
                    "        <p>Địa chỉ:Phường Minh Khai, Quận Bắc Từ Liêm, Thành Phố Hà Nội .</p>\n" +
                    "        <p>\n" +
                    "            Điện thoại: 84.4.37655121, Fax: 84.4.37655261, Email: <a href=\"mailto:dhcnhn@haui.edu.vn\">dhcnhn@haui.edu.vn</a> - <a href=\"https://dttc.haui.edu.vn/rss/tin-tuc\">Rss</a>\n" +
                    "        </p>\n" +
                    "        \n" +
                    "    </div>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "</body></html>";
//        try {
            Document doc = Jsoup.parse(link);
            Elements a = doc.select("div.boxNews").select("ul").select("li").select("a");
//        for (int i = 0; i < parents.size(); i++) {
//            Elements a=parents.get(i).select("ul").select("li").select("a");
            for (int j = 0; j < a.size(); j++) {
//                android.util.Log.e("faker", "" + a.get(j).html());
//                android.util.Log.e("faker", "" + a.first().attr("href"));
                itemNotiDTTCs.add(new ItemNotiDTTC(a.first().attr("href"),a.get(j).html()));

            }

//        }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
            return itemNotiDTTCs;
        }

        @Override
        protected void onPostExecute(ArrayList<ItemNotiDTTC> itemNotiDTTCs) {
            Message message=new Message();
            message.obj=itemNotiDTTCs;
            handler.sendMessage(message);
        }
    }
}