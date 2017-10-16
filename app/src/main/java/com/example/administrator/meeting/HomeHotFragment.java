package com.example.administrator.meeting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;

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
public class HomeHotFragment extends Fragment {
    @Nullable
    private ConvenientBanner mConvenientBanner;
    private List<Integer> localImage = new ArrayList<>();
    private Integer image[]={R.drawable.ic_meet0,R.drawable.ic_meet1,
            R.drawable.ic_meet2,R.drawable.ic_meet3,
            R.drawable.ic_meet4, R.drawable.ic_meet5};
    private String []idPic = {"vpKu2RR2","mbq8VTTV","CdcW3003","fygOK66K","iF4AK77K","DNliwnnw"};

    //推荐列表
    int []imagehot={R.drawable.ic_meet0,R.drawable.ic_meet1,R.drawable.ic_meet2,
            R.drawable.ic_meet4,R.drawable.ic_meet5};
    String []title={"Wearable2017可穿戴设备及技术高峰论坛（深圳站)","2017中国国际骨科技术与成果展","2017年先进材料研究国际学术会议",
            "2017第六届跨境目的地游创新峰会","2017第二届中国国际冬季体育产业大会"};
    String []time={"2017-04-21 至 2017-04-22","2017-04-21","2017-03-17",
            "2017-03-10","2017-06-22 至 2017-06-24"};
    String []location={"深圳","上海","济州","上海","北京"};
    String []price={"600元","700元","1200元","580元","680元"};
    String []id = {"IXMp222E"};
    private ArrayList<Map<String,Object>> list;
    SimpleAdapter listItemAdapter;
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_hot, container, false);
        mConvenientBanner = (ConvenientBanner) view.findViewById(R.id.vp_hot);
        listView = (ListView)view.findViewById(R.id.listhot);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LoageImage();
        Log.d("print", "init: "+localImage.get(0));
        mConvenientBanner.setPages(new CBViewHolderCreator<LocalImageView>() {
            @Override
            public LocalImageView createHolder() {
                return new LocalImageView();
            }
        },localImage)
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator,R.drawable.ic_page_indicator_focused})//设置指示器圆点
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)//设置指示器的位置
                .setPointViewVisible(true)//设置指示器是否可见
                .startTurning(2000)//设置自动切换（同时设置了切换时间间隔）
                .setManualPageable(true);//设置手动影响
        /**
         * 滚动播放：每个item的点击事件
         */
        mConvenientBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(final int position) {
                BmobQuery<tabMeet> meetBmobQuery02 = new BmobQuery<tabMeet>();
                meetBmobQuery02.getObject(idPic[position], new QueryListener<tabMeet>() {
                    @Override
                    public void done(tabMeet tabMeet02, BmobException e) {
                        if (e == null){
                            Intent intent = new Intent(getActivity(),MeetingActivity.class);
                            intent.putExtra("meetItemID", idPic[position]);
                            intent.putExtra("meetItem", tabMeet02);
                            startActivity(intent);
                        }else{
                            Log.i("XING", "HomeHotFragment, " + e.toString());
                        }
                    }
                });
//                Toast.makeText(getActivity(),"点击了第"+position+"张图片",Toast.LENGTH_LONG).show();
            }
        });

        /**推荐会议的列表，
         * 不是滚动播放
         * */
        //Get all tabMeet from Bmob.
        final BmobQuery<tabMeet> tabMeetBmobQuery = new BmobQuery<tabMeet>();
        tabMeetBmobQuery.setLimit(5);
        tabMeetBmobQuery.findObjects(new FindListener<tabMeet>() {
            @Override
            public void done(List<tabMeet> tabMeetList, BmobException e) {
                if (e == null){
                    imagehot = new int[tabMeetList.size()];
                    title = new String[tabMeetList.size()];
                    time = new String[tabMeetList.size()];
                    location = new String[tabMeetList.size()];
                    price = new String[tabMeetList.size()];
                    id = new String[tabMeetList.size()];
                    for (int i01 = 0; i01 < tabMeetList.size(); i01 ++){
                        tabMeet tabMeeti01 = tabMeetList.get(i01);
                        imagehot[i01] = getResourceByReflect(tabMeeti01.getObjectId().toString().toLowerCase());
                        title[i01] = tabMeeti01.getMeetName().toString();
                        time[i01] = tabMeeti01.getMeetTime().toString();
                        location[i01] = tabMeeti01.getMeetLocation().toString();
                        price[i01] = String.valueOf(tabMeeti01.getMeetPrice()) + "0元";
                        id[i01] = tabMeeti01.getObjectId().toString();
                    }
                    // Set data to View.
                    list = new ArrayList<Map<String,Object>>();
                    for (int i=0; i<imagehot.length; i++){
                        Map<String,Object> map = new HashMap<String,Object>();
                        map.put("imagehot",imagehot[i]);
                        map.put("title",title[i]);
                        map.put("time",time[i]);
                        map.put("location",location[i]);
                        map.put("price",price[i]);
                        list.add(map);
                    }
                    listItemAdapter = new SimpleAdapter(getActivity(),list,R.layout.list_hot_item,
                            new String[]{"imagehot","title","time","location","price"},
                            new int[]{R.id.img_meet_hot,R.id.text_hot_title,R.id.text_hot_time,R.id.text_hot_location,R.id.text_hot_money});
                    listView.setAdapter(listItemAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
                            /**点进去是会议具体介绍，同学术会议*/
                            BmobQuery<tabMeet> meetBmobQuery = new BmobQuery<tabMeet>();
                            meetBmobQuery.getObject(id[arg2], new QueryListener<tabMeet>() {
                                @Override
                                public void done(tabMeet tabMeet, BmobException e) {
                                    if (e == null){
                                        Intent intent = new Intent(getActivity(),MeetingActivity.class);
                                        intent.putExtra("meetItemID", id[arg2]);
                                        intent.putExtra("meetItem", tabMeet);
                                        startActivity(intent);
                                    }else {
                                        Log.i("XING", "HomeHotFragment, " + e.toString());
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });



    }

    public static HomeHotFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        HomeHotFragment fragment = new HomeHotFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void LoageImage(){
        for(int i = 0;i<6;i++){
            localImage.add(image[i]);
        }
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
