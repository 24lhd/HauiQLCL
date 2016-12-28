package com.lhd.fragment;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import com.lhd.object.UIFromHTML;
import com.startapp.android.publish.ads.banner.Banner;

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
        Banner banner = (com.startapp.android.publish.ads.banner.Banner) view.findViewById(R.id.startAppBanner);
        banner.showBanner();
        mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.setAdUnitId(MainActivity.AD_UNIT_ID_BANNER_MOREFRAGMENT);
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
            "Ý kiến đóng góp",
            "Thông tin phát triển",
            "Ứng dụng khác",
            "Nhập lại mã sinh viên",
            "Thoát",
    };
    public void setCurrenView(final int currenView) {
        webView=new WebView(getContext());
        builder = new AlertDialog.Builder(getActivity());
        switch (currenView){
            case 0:
                builder.setTitle(PENS[currenView]);
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
                builder.setTitle(PENS[currenView]);
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
                        } catch (android.content.ActivityNotFoundException ex) {
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
            case 3:
                builder.setTitle(PENS[currenView]);
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
            case 4:

                return;
            case 5:
                mainActivity.startLogin(getActivity());
                return;
            case 6:
                mainActivity.finish();
                mainActivity.overridePendingTransition(R.anim.left_end, R.anim.right_end);
                return;
            case 1:
                builder.setTitle(PENS[currenView]);
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
        builder.setNeutralButton("IMG",null);
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

}
