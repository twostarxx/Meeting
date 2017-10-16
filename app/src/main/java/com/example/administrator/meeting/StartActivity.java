package com.example.administrator.meeting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

public class StartActivity extends AppCompatActivity implements View.OnClickListener{

    /**二维码扫描后得到本场会议图片，
     * 后面需要：签到+1，get会议的投票题目答案，对此次会议的讨论
     * */
    //会议图片
    int image = R.drawable.yibang;
    private tabMeet meetShow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //Get meetID from Erweima.
        final String meetIDEr = (String) this.getIntent().getSerializableExtra("meetIDEr") ;
        BmobQuery<tabMeet> meetBmobQuery = new BmobQuery<tabMeet>();
        meetBmobQuery.getObject(meetIDEr, new QueryListener<tabMeet>() {
            @Override
            public void done(tabMeet tabMeet01, BmobException e) {
                if (e == null){
                    meetShow = tabMeet01;
                    ImageView img_meet = (ImageView) findViewById(R.id.img_start);
                    String meetIDEr_b = meetIDEr + "b";
                    img_meet.setImageResource(getResourceByReflect(meetIDEr_b.toLowerCase()));
                    Log.i("XING", "StartActivity, " + meetShow.getMeetName().toString());
//                    Toast.makeText(StartActivity.this, "StartActivity, "+ meetShow.getMeetName().toString(), Toast.LENGTH_LONG).show();
                }else{
                    Log.i("XING", "StartActivity, " + e.toString());
                }
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "确认退出会议？", Snackbar.LENGTH_LONG)
                        .setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent_exit = new Intent(StartActivity.this,MainActivity.class);
                                startActivity(intent_exit);
                            }
                        }).show();
            }
        });

        ImageButton btnstart = (ImageButton) findViewById(R.id.btn_start);
        ImageButton btnvote = (ImageButton) findViewById(R.id.btn_vote);
        ImageButton btntalk = (ImageButton) findViewById(R.id.btn_talk);
        ImageButton btnrecord = (ImageButton) findViewById(R.id.btn_record);
        btnstart.setOnClickListener(this);
        btnvote.setOnClickListener(this);
        btntalk.setOnClickListener(this);
        btnrecord.setOnClickListener(this);

    }

    public void onClick(View view){
        switch(view.getId()){
            case R.id.btn_start:
                //后台签到人数+1
                AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
                builder.setMessage("已成功签到！");
                builder.setIcon(R.mipmap.ic_check);
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                         dialog.dismiss();
                    }
                });
                builder.create().show();
                break;
            case R.id.btn_vote:
                //传递会议，读取后台题目，写到下一界面
                Intent intent_vote = new Intent(this,VoteActivity.class);
                intent_vote.putExtra("meetShow",meetShow);
                startActivity(intent_vote);
                break;
            case R.id.btn_talk:
                //传递会议，进入讨论区界面
                Intent intent_talk = new Intent(this,TalkActivity.class);
                intent_talk.putExtra("meetShow",meetShow);
                startActivity(intent_talk);
                break;
            case R.id.btn_record:
                Intent intent_record = new Intent(this,RecordActivity.class);
                startActivity(intent_record);
                break;
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
