package com.example.administrator.meeting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by Administrator on 2017-02-20.
 */
public class HomeComFragment extends Fragment {
    @Nullable
    /**需要获得图片、名称、时间、地点、价格 */
            int []image={R.drawable.ic_meet0};
    String []title={"Wearable2017可穿戴设备及技术高峰论坛"};
    String []time={"2017-04-21 至 2017-04-22"};
    String []location={"深圳"};
    String []price={"600元"};
    String []meetId = {"IXMp222E"};

    private ArrayList<Map<String,Object>> list;
    SimpleAdapter listItemAdapter;
    ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_meeting, container, false);
        listView = (ListView)view.findViewById(R.id.list_meet);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list = new ArrayList<Map<String,Object>>();
        for (int i=0; i<image.length; i++){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("image",image[i]);
            map.put("title",title[i]);
            map.put("time",time[i]);
            map.put("location",location[i]);
            map.put("price",price[i]);
            list.add(map);
        }

        listItemAdapter = new SimpleAdapter(getActivity(),list,R.layout.list_meeting_item,
                new String[]{"image","title","time","location","price"},
                new int[]{R.id.img_meet,R.id.text_title,R.id.text_time,R.id.text_location,R.id.text_money});
        listView.setAdapter(listItemAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {

            }
        });

    }

    public static HomeComFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        HomeComFragment fragment = new HomeComFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public int getResourceByReflect(String imageName){
        Class drawable  =  R.drawable.class;
        Field field = null;
        int r_id ;
        try {
            field = drawable.getField(imageName);
            r_id = field.getInt(field.getName());
        } catch (Exception e) {
            r_id=R.drawable.ic_meet0;
            Log.e("ERROR", "PICTURE NOT　FOUND！");
        }
        return r_id;
    }

}
