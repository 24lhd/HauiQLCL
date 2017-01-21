package com.lhd.fragment;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.ken.hauiclass.R;
import com.lhd.activity.MainActivity;
import com.lhd.object.KetQuaHocTap;
import com.lhd.object.UIFromHTML;
import com.lhd.task.ParserKetQuaHocTap;

/**
 * Created by Duong on 11/21/2016.
 */

public class MoreFragment extends Fragment implements AdapterView.OnItemClickListener{
    private MainActivity mainActivity;
    private ListView listView;
    private AlertDialog.Builder builder;
    private WebView webView;
    private AlertDialog mAlertDialog;
    private AdView mAdView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_more,container,false);
         mainActivity= (MainActivity) getActivity();
        listView= (ListView) view.findViewById(R.id.list_menu_more);
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(mainActivity,android.R.layout.simple_list_item_1, PENS);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(this);
        mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        return view;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        setCurrenView(position);
    }
    static final String[] PENS = new String[]{
            "Giờ học lý thuyết",
            "Thang điểm chữ",
            "Cập nhật phiên bản",
            "Ý kiến đóng góp",
            "Thông tin & phát triển",
            "Ứng dụng khác",
            "Xem sinh viên",
            "Nhập lại mã sinh viên",
            "Thoát",
    };
    public void setCurrenView( int currenView) {
        webView=new WebView(getContext());
        builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(PENS[currenView]);
        switch (currenView){
            case 0:
                webView.loadDataWithBaseURL(null,UIFromHTML.uiTime, "text/html", "utf-8",null);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setView(webView);
                break;
            case 2:
                try {
                    mainActivity.checkUpdate(1);
                } catch (Exception e) {}
                return;
            case 3:
                View view=getActivity().getLayoutInflater().inflate(R.layout.feedback_layout,null);
                final EditText editText= (EditText) view.findViewById(R.id.et_feedback);
                builder.setPositiveButton("Gửi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("message/rfc822");
                        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"gacongnghiep.sv@gmail.com"});
                        i.putExtra(Intent.EXTRA_SUBJECT, "Phản hồi Gà Công Nghiệp");
                        i.putExtra(Intent.EXTRA_TEXT   , editText.getText().toString());
                        try {
                            startActivity(Intent.createChooser(i, "Send mail..."));
                        } catch (ActivityNotFoundException ex) {
                            Toast.makeText(getActivity(), "Bạn chưa cài ứng dụng Emails.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setView(view);
                builder.show();
                return;
            case 4:
                webView.setBackgroundColor(getResources().getColor(R.color.bg_text));
                webView.loadDataWithBaseURL(null,UIFromHTML.uiCopyright,"text/html","utf-8",null);
                builder.setView(webView);
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
                return;
            case 5:

                return;
            case 6:
                  final EditText etMSV=new EditText(getActivity());
                etMSV.setInputType(InputType.TYPE_CLASS_NUMBER);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    etMSV.setTextAppearance(R.style.AppTheme);
                }
                etMSV.setTextSize(getResources().getDimension(R.dimen.activity_vertical_margin));
                etMSV.setTextColor(getResources().getColor(R.color.text_color_green));
                etMSV.setBackgroundColor(Color.WHITE);
                etMSV.setHint("Mã sinh viên");
                builder.setPositiveButton("Xem", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String id=etMSV.getText().toString();
                        if (id.isEmpty()) Toast.makeText(getActivity(),"Không được để trống bạn ê",Toast.LENGTH_SHORT).show();
                        else if (id.length()<10)
                            Toast.makeText(getActivity(),"Đừng đùa thể chứ x_x \n" +
                                            " Nhập đúng mã sinh viên đi bạn êi",Toast.LENGTH_SHORT).show();
                        else sreachSV(id);
                    }
                });
                builder.setView(etMSV);
                builder.show();
                return;
            case 7:
                mainActivity.startLogin(getActivity());
                return;
            case 8:
                mainActivity.finish();
                mainActivity.overridePendingTransition(R.anim.left_end, R.anim.right_end);
                return;
            case 1:
                webView.loadDataWithBaseURL(null, UIFromHTML.uiDiemChu, "text/html", "utf-8",null);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setView(webView);
                break;
        }
        builder.setNeutralButton("Chia sẻ ảnh",null);
        mAlertDialog = builder.create();
        mAlertDialog.show();
        Button diemchu = mAlertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        diemchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.sreenShort(view,getActivity());
            }
        });
    }

    private void sreachSV(final String s) {
        final ParserKetQuaHocTap ketQuaHocTapTheoMon=new ParserKetQuaHocTap(new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==2){
                    if (MainActivity.isOnline(getActivity()))
                        Toast.makeText(getActivity(),"Mã sinh viên không đúng bạn êi",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getActivity(),"Không có kêt nối Iternet!",Toast.LENGTH_SHORT).show();
                    return;
                }else if (msg.obj instanceof KetQuaHocTap){
                    mainActivity.getSV(s);
                    return;
                }
            }
        });
        ketQuaHocTapTheoMon.execute(s);
    }

}
