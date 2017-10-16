package com.example.administrator.meeting;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017-02-20.
 */
public class HomeFragment extends Fragment implements BottomNavigationBar.OnTabSelectedListener,View.OnClickListener{
    private ArrayList<Fragment> fragmenthome;
    ImageButton btnscan;
    //搜索
    SearchView sv = null;
    ListView lv = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //扫描
        btnscan = (ImageButton) view.findViewById(R.id.btn_scan);
        btnscan.setOnClickListener(this);

        sv = (SearchView) view.findViewById(R.id.searchView);
        sv.setIconifiedByDefault(false);
        sv.setSubmitButtonEnabled(true);
        //通过反射，修改默认的样式，可以从android的search_view.xml中找到需要的组件
        int id = sv.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) sv.findViewById(id);
        textView.setTextColor(Color.WHITE);  textView.setHintTextColor(Color.parseColor("#CCCCCC"));
        try {
            Field field = sv.getClass().getDeclaredField("mSubmitButton");
            field.setAccessible(true);
            ImageView iv = (ImageView) field.get(sv);
            iv.setImageDrawable(this.getResources().getDrawable(
                    R.drawable.ic_search));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Cursor cursor = this.getTestCursor();
        @SuppressWarnings("deprecation")
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(),
                R.layout.my_text, cursor, new String[] { "tb_name" },
                new int[] { R.id.text_search });
        sv.setSuggestionsAdapter(adapter);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String str) {
                return false;
            }
            @Override
            public boolean onQueryTextSubmit(String str) {
                Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        //顶部导航栏
        BottomNavigationBar bottomNavigationBar_top = (BottomNavigationBar) view.findViewById(R.id.top_home);
        bottomNavigationBar_top.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar_top
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC
                );

        bottomNavigationBar_top.addItem(new BottomNavigationItem(R.mipmap.ic_hot,"热门").setActiveColorResource(R.color.blue))
                .addItem(new BottomNavigationItem(R.mipmap.ic_learn, "学术").setActiveColorResource(R.color.blue))
                .addItem(new BottomNavigationItem(R.mipmap.ic_government, "政府").setActiveColorResource(R.color.blue))
                .addItem(new BottomNavigationItem(R.mipmap.ic_enterprise, "企业").setActiveColorResource(R.color.blue))
                .addItem(new BottomNavigationItem(R.mipmap.ic_free, "其他").setActiveColorResource(R.color.blue))
                .setFirstSelectedPosition(0)
                .initialise();
        fragmenthome = getHomeFragments();
        setDefaultFragment();
        bottomNavigationBar_top.setTabSelectedListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_scan:
                IntentIntegrator integrator = new IntentIntegrator(getActivity());
                integrator.setCaptureActivity(ScanActivity.class);
                integrator.setPrompt("请扫描会议签到二维码。"); //底部的提示文字，设为""可以置空
                integrator.setCameraId(0); //前置或者后置摄像头
                integrator.setBeepEnabled(false); //扫描成功的「哔哔」声，默认开启
                integrator.initiateScan();
//                //扫描二维码
//                Intent intent1 = new Intent(getActivity(),StartActivity.class);
//                startActivity(intent1);
        }
    }

    //==============二维码结果显示log====================
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if(result != null) {
            if(result.getContents() == null) {
                Log.i("HSING", "Cancelled");
//                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.i("HSING", "Scanned: " + result.getContents());
//                Toast.makeText(getActivity(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                //Intent Er meetID to StartActivity.
                String meetIDEr = result.getContents();
                Intent intent01 = new Intent(getActivity(), StartActivity.class);
                intent01.putExtra("meetIDEr", meetIDEr);
                startActivity(intent01);
            }
        }
    }

    private void setDefaultFragment() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.layFrame_home, HomeHotFragment.newInstance("Hot"));
        transaction.commit();
    }

    private ArrayList<Fragment> getHomeFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(HomeHotFragment.newInstance("Hot"));
        fragments.add(HomeLearnFragment.newInstance("Learn"));
        fragments.add(HomeGoFragment.newInstance("Government"));
        fragments.add(HomeComFragment.newInstance("Company"));
        fragments.add(HomeFreeFragment.newInstance("Freedom"));
        return fragments;
    }

    public static HomeFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onTabSelected(int position) {
        if (fragmenthome != null) {
            if (position < fragmenthome.size()) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragmenthome.get(position);
                ft.replace(R.id.layFrame_home, fragment);
                ft.commitAllowingStateLoss();
            }
        }

    }

    @Override
    public void onTabUnselected(int position) {
        if (fragmenthome != null) {
            if (position < fragmenthome.size()) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragmenthome.get(position);
                ft.remove(fragment);
                ft.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabReselected(int position) {

    }

    public Cursor getTestCursor() {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
                getActivity().getFilesDir() + "/my.db3", null);
        Cursor cursor = null;
        try {
            String insertSql = "insert into tb_test values (null,?,?)";
            db.execSQL(insertSql, new Object[] { "aa", 1 });
            db.execSQL(insertSql, new Object[] { "ab", 2 });
            db.execSQL(insertSql, new Object[] { "ac", 3 });
            db.execSQL(insertSql, new Object[] { "ad", 4 });
            db.execSQL(insertSql, new Object[] { "ae", 5 });
            String querySql = "select * from tb_test";
            cursor = db.rawQuery(querySql, null);
        } catch (Exception e) {
            String sql = "create table tb_test (_id integer primary key autoincrement,tb_name varchar(20),tb_age integer)";
            db.execSQL(sql);
            String insertSql = "insert into tb_test values (null,?,?)";
            db.execSQL(insertSql, new Object[] { "aa", 1 });
            db.execSQL(insertSql, new Object[] { "ab", 2 });
            db.execSQL(insertSql, new Object[] { "ac", 3 });
            db.execSQL(insertSql, new Object[] { "ad", 4 });
            db.execSQL(insertSql, new Object[] { "ae", 5 });
            String querySql = "select * from tb_test";
            cursor = db.rawQuery(querySql, null);
        }
        return cursor;
    }


}

