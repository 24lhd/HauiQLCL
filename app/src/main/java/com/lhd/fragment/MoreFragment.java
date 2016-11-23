package com.lhd.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
            "Gửi ý kiến đóng góp",
            "Thông tin phát triển",
            "Ứng dụng khác",
            "Nhập lại mã sinh viên",
            "Thoát"
    };

    public void setCurrenView(int currenView) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        mainActivity.setCurrenItem(currenView);
        listView.setOnItemClickListener(null);
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
                mainActivity.setTitleTab(PENS[currenView]);
                FeedbackFragment feedbackFragment=new FeedbackFragment();
                fragmentTransaction.replace(R.id.fm_more,feedbackFragment);
                fragmentTransaction.commit();
                break;
            case 4:
                mainActivity.setTitleTab(PENS[currenView]);
                InfoDevFragment infoDevFragment =new InfoDevFragment();
                fragmentTransaction.replace(R.id.fm_more, infoDevFragment);
                fragmentTransaction.commit();
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
                listView.setOnItemClickListener(this);

                break;
        }
    }
}
