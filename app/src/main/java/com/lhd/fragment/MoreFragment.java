package com.lhd.fragment;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ken.hauiclass.R;
import com.lhd.activity.ListActivity;
import com.lhd.activity.MainActivity;
import com.lhd.database.SQLiteManager;
import com.lhd.item.DiemThiTheoMon;
import com.lhd.item.ItemNotiDTTC;
import com.lhd.task.ParserNotiDTTC;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Duong on 11/21/2016.
 */

public class MoreFragment extends Fragment implements AdapterView.OnItemClickListener{
    private WebView textView;
    private MainActivity mainActivity;
    private ListView listView;
    private RecyclerView recyclerView;
    private SQLiteManager sqLiteManager;
    private ArrayList<ItemNotiDTTC> itemNotiDTTCs;
    private AlertDialog.Builder builderNoti;

    @TargetApi(Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_more,container,false);
         mainActivity= (MainActivity) getActivity();
        listView= (ListView) view.findViewById(R.id.list_menu_more);
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(mainActivity,android.R.layout.simple_list_item_1, PENS);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(this);
        return view;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        setCurrenView(position);
    }
    static final String[] PENS = new String[]{
            "Giờ học lý thuyết",
            "Ý kiến đóng góp",
            "Thông tin phát triển",
            "Ứng dụng khác",
            "Nhập lại mã sinh viên",
            "Thoát"
    };

    public void setCurrenView(final int currenView) {
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        mainActivity.setCurrenItem(currenView);
        switch (currenView){
            case 0:
                final AlertDialog.Builder builderlich = new AlertDialog.Builder(getActivity());
                View view2=getActivity().getLayoutInflater().inflate(R.layout.layout_gio_ly_thuyet,null);
                textView= (WebView) view2.findViewById(R.id.text_more);
                String str3;
                builderlich.setTitle(PENS[currenView]);
                str3="<!DOCTYPEhtml><html>" +
                        "<head>" +
                        "<meta charset=\"utf-8\"/>" +
                        "<style type=\"text/css\" media=\"screen\">" +
                        "*{" +
                        "margin:0;padding: 0;" +
                        "}" +
                        "table{" +
                        "width:100%;border:2px solid white;text-align:center;border-collapse:collapse;background-color:#E8F5E9;" +
                        "}" +
                        "th{" +
                        "padding:10px;background-color:#42A5F5;color:white;padding:10px;" +
                        "}" +
                        "td{" +
                        "color:#42A5F5;padding:5px;" +
                        "}" +
                        "</style>" +
                        "</head>" +
                        "<body>" +
                        "<table border=\"1px\"><tr><th>Tiết</th><th>Giờ Học</th></tr><tr><td>1</td><td>7h00 - 7h45</td></tr><tr><td>2</td><td>7h50 - 8h35</td></tr><tr><td>3</td><td>8h40 - 9h25</td></tr><tr><td>4</td><td>9h35 - 10h20</td></tr><tr><td>5</td><td>10h25-11h10</td></tr><tr><td>6</td><td>11h15 - 12h00</td></tr><tr><td>7</td><td>12h30 - 13h15</td></tr><tr><td>8</td><td>13h20 - 14h05</td></tr><tr><td>9</td><td>14h10 - 14h55</td></tr><tr><td>10</td><td>15h05 - 15h50</td></tr><tr><td>11</td><td>15h55 - 16h40</td></tr><tr><td>12</td><td>16h45 - 17h30</td></tr><tr><td>13</td><td>18h00 - 18h45</td></tr><tr><td>14</td><td>18h45 - 19h30</td></tr><tr><td>15</td><td>19h45 - 20h30</td></tr><tr><td>16</td><td>20h30 - 21h15</td></tr>" +
                        "</table>" +
                        "</body>" +
                        "</html>";
                textView.loadDataWithBaseURL(null,str3, "text/html", "utf-8",null);
                builderlich.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builderlich.setView(view2);
                builderlich.show();
                break;
            case 1:
                AlertDialog.Builder builderFeedback = new AlertDialog.Builder(getActivity());
                builderFeedback.setTitle(PENS[currenView]);
                View view=getActivity().getLayoutInflater().inflate(R.layout.feedback_layout,null);
                final EditText editText= (EditText) view.findViewById(R.id.et_feedback);
                builderFeedback.setPositiveButton("Gửi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("message/rfc822");
                        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"24duong@gmail.com"});
                        i.putExtra(Intent.EXTRA_SUBJECT, "Phản hồi Gà Công Nghiệp");
                        i.putExtra(Intent.EXTRA_TEXT   , editText.getText().toString());
                        try {
                            startActivity(Intent.createChooser(i, "Send mail..."));

                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(getActivity(), "Bạn chưa cài ứng dụng Emails.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builderFeedback.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });
                builderFeedback.setView(view);
                builderFeedback.show();
                break;
            case 2:
                String str="<!DOCTYPE html>" +
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
                        "<p >Phần mềm cập nhật thông báo điểm, tra cứu kết quả học tập, lịch thi và một số tiện ích khác hỗ trợ các bạn sinh viên ĐH Công nghiệp Hà Nội trong học tập và trong thi cử một cách nhanh nhất." +
                        "<p> Xin chân thành cảm ơn sự ủng hộ của các bạn!" +
                        "<p id=\"footer\">" +
                        "<em>Copyright &copy<em> 2016</em>, Lê Hồng Dương.</em>" +
                        "</p>" +
                        "</body>" +
                        "</html>";
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(PENS[currenView]);
                WebView webView=new WebView(getActivity());
                webView.setBackgroundColor(getResources().getColor(R.color.bg_text));
                webView.loadDataWithBaseURL(null,str,"text/html","utf-8",null);
                builder.setView(webView);
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });
                builder.show();
                break;
            case 3:

                break;
            case 4:
                mainActivity.startLogin();
                break;
            case 5:
                mainActivity.finish();
                break;
        }
    }

}
