package com.example.administrator.meeting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TalkActivity extends AppCompatActivity {

    //通过用户名获得用户头像
    int image=R.drawable.logo;
    String msg;
    private ArrayList<Map<String,Object>> list;
    SimpleAdapter listItemAdapter;
    ListView listView2;
    EditText edittalk;
    ImageButton btnsend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk);
        listView2 = (ListView) findViewById(R.id.listtalk);
        edittalk = (EditText) findViewById(R.id.edit_talk);
        btnsend = (ImageButton) findViewById(R.id.btn_send);
        list = new ArrayList<Map<String,Object>>();

        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg = edittalk.getText().toString();
                if (TextUtils.isEmpty(edittalk.getText()) ){
                    Toast.makeText(TalkActivity.this,"输入内容不能为空！",Toast.LENGTH_SHORT).show();
                }else{
                    Map<String,Object> map = new HashMap<String, Object>();
                    map.put("image", image);
                    map.put("msg",msg);
                    list.add(map);
                    listItemAdapter = new SimpleAdapter(TalkActivity.this, list, R.layout.list_talk_item, new String[] { "image","msg" },
                            new int[] { R.id.img_talk, R.id.text_talk});
                    listView2.setAdapter(listItemAdapter);
                    edittalk.setText(null);
                    msg = null;
                }
            }
        });
    }
}
