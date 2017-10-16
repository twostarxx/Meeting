package com.example.administrator.meeting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017-02-20.
 */
public class FindFragment extends Fragment {
    @Nullable

    int []image={R.drawable.ic_find1,R.drawable.ic_find2,R.drawable.ic_find3,
            R.drawable.ic_find4,R.drawable.ic_find5,R.drawable.ic_find6};
    String []title={"美云智数产品发布会即将在深圳召开","Google的常胜法宝，为什么成了你的绊脚石？","比尔·盖茨专访：离任CEO之后还在微软做些什么",
            "美股500强前5名首次被科技企业包揽，苹果最强","2017年，这11场金融财经行业会议不可错过！","高通创投是如何在VR、无人机、人工智能里“寻猎”与“淘金”的"};
    String []resource={"工业制造","i黑马","GeekWire","财经天下","活动家资讯","虎嗅网"};
    private ArrayList<Map<String,Object>> list;
    SimpleAdapter listItemAdapter;
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find, container, false);
        listView = (ListView)view.findViewById(R.id.listFind);
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
            map.put("resource","文 / "+resource[i]);
            list.add(map);
        }

        listItemAdapter = new SimpleAdapter(getActivity(),list,R.layout.list_find_item,
                new String[]{"image","title","resource"},
                new int[]{R.id.img_find,R.id.text_find_title,R.id.text_find_resource});
        listView.setAdapter(listItemAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
                Intent intent = new Intent(getActivity(),FindDetailActivity.class);
                Bundle find = new Bundle();
                find.putString("find",String.valueOf(arg2));
                intent.putExtras(find);
                startActivity(intent);
            }
        });

    }

    public static FindFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        FindFragment fragment = new FindFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
