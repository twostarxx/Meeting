package com.example.administrator.meeting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by Administrator on 2017-02-20.
 */
public class MeetFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener,MyMeetingAdapter.ToResults{
    ListView listview;
    MyMeetingAdapter myAdapter;
    TextView sponsor,attend;
    ImageButton btnadd;
    MyUser user01;
    meetOrganize org01;
    meetAttend att01;
    int flag;  //1为组织，2为参加
    /**分别获取我组织的/我参加的会议
     * 放在两组数组里
     * */
    int []image1={R.drawable.ic_meet0};
    String []title1={"Wearable2017可穿戴设备及技术高峰论坛"};
    String []time1={"2017-04-21 至 2017-04-22"};
    String []address1={"深圳"};
    String []state1 = {"未开始"};
    String []id1 = {"IXMp222E"};

    int []image2={R.drawable.ic_meet0};
    String []title2={"Wearable2017可穿戴设备及技术高峰论坛"};
    String []time2={"2017-04-21 至 2017-04-22"};
    String []address2={"深圳"};
    String []state2 = {"未开始"};
    String []id2 = {"IXMp222E"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meet, container, false);
        listview = (ListView) view.findViewById(R.id.listView);
        sponsor = (TextView) view.findViewById(R.id.text_sponsor);
        attend = (TextView) view.findViewById(R.id.text_attend);
        btnadd = (ImageButton) view.findViewById(R.id.btn_add);
        sponsor.setOnClickListener(this);
        attend.setOnClickListener(this);
        btnadd.setOnClickListener(this);
        flag = 1;
        sponsor.setBackgroundResource(R.drawable.my_bottom);
        attend.setBackgroundResource(R.color.white);
        myAdapter = new MyMeetingAdapter(getActivity(), image1, title1, time1, address1, state1, this);
        listview.setAdapter(myAdapter);
        listview.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MyUser user01 = BmobUser.getCurrentUser(MyUser.class);
        Log.i("XING","MeetFragment, " + user01.getUsername().toString());

        //get meetOrganize from Bmob by user01
        final BmobQuery<meetOrganize> orgQuery = new BmobQuery<meetOrganize>();
        orgQuery.addWhereEqualTo("userOrg", new BmobPointer(user01));
        orgQuery.include("meetOrg");
        orgQuery.findObjects(new FindListener<meetOrganize>() {
            @Override
            public void done(List<meetOrganize> orgQueryList, BmobException e) {
                if (e == null){
                    image1 = new int[orgQueryList.size()];
                    title1 = new String[orgQueryList.size()];
                    time1 = new String[orgQueryList.size()];
                    address1 = new String[orgQueryList.size()];
                    state1 = new String[orgQueryList.size()];
                    int []intstate1 = new int[orgQueryList.size()];
                    id1 = new String[orgQueryList.size()];
                    for (int i01= 0; i01 < orgQueryList.size(); i01 ++){
                        tabMeet meetOrgtest = orgQueryList.get(i01).getMeetOrg();
                        image1[i01] = getResourceByReflect(meetOrgtest.getObjectId().toString().toLowerCase());
                        title1[i01] = meetOrgtest.getMeetName().toString();
                        time1[i01] = meetOrgtest.getMeetTime().toString();
                        address1[i01] = meetOrgtest.getMeetLocation().toString();
                        intstate1[i01] = meetOrgtest.getMeetState();
                        id1[i01] = meetOrgtest.getObjectId().toString();
//                        Log.i("XING","MeetFragment, meetOrg: " + meetOrgtest.getMeetName().toString());
                    }
                    for (int ii_01 = 0; ii_01 < intstate1.length; ii_01 ++){
                        if (intstate1[ii_01] == -1){
                            state1[ii_01] = "已结束";
                        } else if (intstate1[ii_01] == 0){
                            state1[ii_01] = "进行中";
                        }else if (intstate1[ii_01] == 1){
                            state1[ii_01] = "未开始";
                        }
                    }
                }else{
                Log.i("XING", "MeetFragment, BmobQuery meetOrg fail! " + e.toString());
                }
            }
        });

        //get meetAttend from Bmob by user01
        BmobQuery<meetAttend> attQuery = new BmobQuery<meetAttend>();
        attQuery.addWhereEqualTo("userAtt",new BmobPointer(user01));
        attQuery.include("meetAtt");
        attQuery.findObjects(new FindListener<meetAttend>() {
            @Override
            public void done(List<meetAttend> attQueryList, BmobException e) {
                if (e == null){
                    image2 = new int[attQueryList.size()];
                    title2 = new String[attQueryList.size()];
                    time2 = new String[attQueryList.size()];
                    address2 = new String[attQueryList.size()];
                    state2 = new String[attQueryList.size()];
                    int []intstate2 = new int[attQueryList.size()];
                    id2 = new String[attQueryList.size()];
                    for (int i01= 0; i01 < attQueryList.size(); i01 ++){
                        tabMeet meetOrgtest = attQueryList.get(i01).getMeetAtt();
                        image2[i01] = getResourceByReflect(meetOrgtest.getObjectId().toString().toLowerCase());
                        title2[i01] = meetOrgtest.getMeetName().toString();
                        time2[i01] = meetOrgtest.getMeetTime().toString();
                        address2[i01] = meetOrgtest.getMeetLocation().toString();
                        intstate2[i01] = meetOrgtest.getMeetState();
                        id2[i01] = meetOrgtest.getObjectId().toString();
//                        Log.i("XING","MeetFragment, meetOrg: " + meetOrgtest.getMeetName().toString());
                    }
                    for (int ii_02 = 0; ii_02 < intstate2.length; ii_02 ++){
                        if (intstate2[ii_02] == -1){
                            state2[ii_02] = "已结束";
                        } else if (intstate2[ii_02] == 0){
                            state2[ii_02] = "进行中";
                        }else if (intstate2[ii_02] == 1){
                            state2[ii_02] = "未开始";
                        }
                    }
                }else{
                    Log.i("XING", "MeetFragment, BmobQuery meetAtt fail! " + e.toString());
                }
            }
        });


    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.text_sponsor:
                sponsor.setBackgroundResource(R.drawable.my_bottom);
                attend.setBackgroundResource(R.color.white);
                myAdapter = new MyMeetingAdapter(getActivity(),image1,title1,time1,address1,state1,this);
                listview.setAdapter(myAdapter);
                listview.setOnItemClickListener(this);
                flag = 1;
                break;
            case R.id.text_attend:
                attend.setBackgroundResource(R.drawable.my_bottom);
                sponsor.setBackgroundResource(R.color.white);
                myAdapter = new MyMeetingAdapter(getActivity(),image2,title2,time2,address2,state2,this);
                listview.setAdapter(myAdapter);
                listview.setOnItemClickListener(this);
                flag = 2;
                break;
            case R.id.btn_add:
//                Toast.makeText(getActivity(),"加发起会议",Toast.LENGTH_SHORT).show();
                Intent meetApply = new Intent(getActivity(),ConfApplyActivity.class);
                startActivity(meetApply);
                break;
        }
    }

    /**参数为位置position，需要连接到
     * 会议具体介绍页面，
     * 代码同学术会议等列表点击*/
    @Override
    public void onItemClick(AdapterView<?> arg0, View v, final int position, long id) {
        String []idItem = {"IXMp222E"};
        if (flag == 1){
            idItem = id1;
        }else if (flag == 2){
            idItem = id2;
        }
        // find tabMeet by onjectID: meetId[arg2]
        BmobQuery<tabMeet> meetItemQuery= new BmobQuery<tabMeet>();
        final String meetItemID = idItem[position];
        meetItemQuery.getObject(meetItemID, new QueryListener<tabMeet>() {
            @Override
            public void done(tabMeet meetItem, BmobException e) {
                if (e == null){
                    // intent meetItemID to MeetActivity
                    Intent intentItemClick = new Intent(getActivity(), MeetingActivity.class);
                    intentItemClick.putExtra("meetItem", meetItem);
                    intentItemClick.putExtra("meetItemID", meetItemID);
                    startActivity(intentItemClick);
                    Log.i("XING", meetItem.getObjectId().toString() +" " + meetItem.getMeetName().toString());
                }else {
                    new android.support.v7.app.AlertDialog.Builder(getActivity())
                            .setTitle("提示")
                            .setMessage("未找到详细资料，请再到处看看。")
                            .setPositiveButton("确定",null)
                            .show();

                    Log.i("XING", "MeetingActivity get intent fail. " + e.toString());
                }
            }
        });
    }

    /**参数同上为列表位置number，关联会议编号
     * 点击后显示结果弹出框，
     * 选择框内项目进入对应的图表*/
    @Override
    public void click(View v) {
        final int number = (Integer) v.getTag();
        String[] title;
        String[] id;
        final tabMeet[] meetClick = new tabMeet[1];
        if (flag == 1){
            title = title1;
            id = id1;
        }else{
            title = title2;
            id = id2;
        }
        //get tabMeet clicked aim to intent to the Chart Activity.
        BmobQuery<tabMeet> meetClickQuery = new BmobQuery<tabMeet>();
        meetClickQuery.getObject(id[number], new QueryListener<tabMeet>() {
            @Override
            public void done(tabMeet queryMeetClick, BmobException e) {
                if (e == null){
                    meetClick[0] = queryMeetClick;
                } else {
                    Log.i("XING","MeetFragment, get tabMeet fail! " + e.toString());
                }
            }
        });

        final String[] items = { "会议热度","签到情况","投票结果" };
        AlertDialog.Builder listDialog = new AlertDialog.Builder(getActivity());
        listDialog.setTitle(title[number]);
        listDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // which表示第几个选项，0-热度，1-签到，2-投票
                Toast.makeText(getActivity(), "你点击了" + items[which], Toast.LENGTH_SHORT).show();
                switch ( which ){
                    case 0:
                        //会议热度：预览折线图
                        Intent intentChart0 = new Intent(getActivity(),PreviewLineChartActivity.class);
                        intentChart0.putExtra("meetClick", meetClick[0]);
                        startActivity(intentChart0);
                        break;
                    case 1:
                        //签到结果：饼状图
                        Intent intentChart1 = new Intent(getActivity(),PieChartActivity.class);
                        intentChart1.putExtra("meetClick", meetClick[0]);
                        startActivity(intentChart1);
                        break;
                    case 2:
                        //投票结果：柱状-折线图
                        Intent intentChart2 = new Intent(getActivity(),LineColumnDependencyActivity.class);
                        intentChart2.putExtra("meetClick", meetClick[0]);
                        startActivity(intentChart2);
                        break;
                }
            }
        });
        listDialog.show();
    }

    public static MeetFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        MeetFragment fragment = new MeetFragment();
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
