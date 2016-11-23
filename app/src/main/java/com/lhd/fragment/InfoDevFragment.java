package com.lhd.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by Duong on 11/23/2016.
 */

public class InfoDevFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String str="<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<meta charset=\"utf-8\">" +
                "<style type=\"text/css\" media=\"screen\">" +
                "h2{" +
                "color: #FF4081;" +
                "text-align:justify" +
                "" +
                "}" +
                "p{" +
                "font-family: Sans-serif;" +
                "text-indent: 10px;" +
                "text-align:justify" +
                "" +
                "}" +
                "#footer{" +
                "text-align: center;" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<h2>Gà Công Nghiệp</h2>" +
                "" +
                "<p >Phần mềm cập nhật thông báo điểm, tra cứu kết quả học tập, lịch thi và một số tiện ích khác hỗ trợ các bạn sinh viên ĐH Công nghiệp Hà Nội một cách nhanh nhất." +
                "<p> Xin chân thành cảm ơn các bạn đã sử dụng ứng dụng!" +
                "<p id=\"footer\">" +
                "<em>Copyright &copy Hà Nội<em> 2016</em>, Lê Hồng Dương K9.</em>" +
                "" +
                "</p>" +
                "</body>" +
                "</html>";
        WebView webView=new WebView(getActivity());
        webView.loadDataWithBaseURL(null,str, "text/html", "utf-8",null);
        return webView;
    }
}
