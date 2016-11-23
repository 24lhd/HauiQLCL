package com.lhd.fragment;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ken.hauiclass.R;
import com.lhd.activity.MainActivity;

/**
 * Created by Duong on 11/21/2016.
 */

public class MoreFragment extends Fragment implements AdapterView.OnItemClickListener{
    private WebView textView;
    private MainActivity mainActivity;
    private ListView listView;

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
            "Thời khóa biểu cá nhân",
            "Thông báo",
            "Ý kiến phản hồi",
            "Thông tin phát triển",
            "Ứng dụng khác",
            "Nhập lại mã sinh viên",
            "Thoát"
    };

    public void setCurrenView(int currenView) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        mainActivity.setCurrenItem(currenView);
        switch (currenView){
            case 0:
                mainActivity.setTitleTab(PENS[currenView]);
                GioLyThuyetFragment gioLyThuyetFragment=new GioLyThuyetFragment();
                fragmentTransaction.replace(R.id.fm_more,gioLyThuyetFragment);
                fragmentTransaction.commit();

                break;
            case 1:

                break;
            case 2:

                break;
            case 3:
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
            case 4:
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
                       "<em>Copyright &copy Hà Nội<em> 2016</em>, Lê Hồng Dương K9.</em>" +
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
            case 5:

                break;
            case 6:
                mainActivity.startLogin();

                break;
            case 7:
                mainActivity.finish();
                break;
            case 8:
                getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.fm_more)).commit();

                break;
        }
    }
}
