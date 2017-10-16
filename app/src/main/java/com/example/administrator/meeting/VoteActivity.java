package com.example.administrator.meeting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class VoteActivity extends AppCompatActivity implements View.OnClickListener{

    String title = "12121";
    String ans[] ={"sasa","dsas","das","dasdas"};
    int choose = 0;
    TextView ans1, ans2, ans3, ans4, voteOk;
    private tabMeet meetShow;
    private meetSurvey meetSurveyShow;
    private int na01, na02, na03, na04;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_vote);
        final TextView votetitle = (TextView) findViewById(R.id.vote_title);
        ans1 = (TextView) findViewById(R.id.vote_ans1);
        ans2 = (TextView) findViewById(R.id.vote_ans2);
        ans3 = (TextView) findViewById(R.id.vote_ans3);
        ans4 = (TextView) findViewById(R.id.vote_ans4);
        voteOk = (TextView) findViewById(R.id.vote_ok);
        ans1.setOnClickListener(this);
        ans2.setOnClickListener(this);
        ans3.setOnClickListener(this);
        ans4.setOnClickListener(this);
        voteOk.setOnClickListener(this);

        //Get meetShow from StartActivity.
        meetShow = (tabMeet) this.getIntent().getSerializableExtra("meetShow");
        Log.i("XING","VoteActivity, " + meetShow.getMeetName().toString());
        //Get question from Bmob.
        final BmobQuery<meetSurvey> meetSurveyBmobQuery = new BmobQuery<meetSurvey>();
        meetSurveyBmobQuery.addWhereEqualTo("meetSur", new BmobPointer(meetShow));
        meetSurveyBmobQuery.include("Question, Ans01, Ans02, Ans03, Ans04");
        meetSurveyBmobQuery.findObjects(new FindListener<meetSurvey>() {
            @Override
            public void done(List<meetSurvey> meetSurveyList, BmobException e) {
                if (e == null){
                    meetSurveyShow = meetSurveyList.get(0);
                    title = meetSurveyShow.getQuestion().toString();
                    ans[0] = meetSurveyShow.getAns01().toString();
                    ans[1] = meetSurveyShow.getAns02().toString();
                    ans[2] = meetSurveyShow.getAns03().toString();
                    ans[3] = meetSurveyShow.getAns04().toString();
                    na01 = meetSurveyShow.getNA01().intValue();
                    na02 = meetSurveyShow.getNA02().intValue();
                    na03 = meetSurveyShow.getNA03().intValue();
                    na04 = meetSurveyShow.getNA04().intValue();
                    votetitle.setText(title);
                    ans1.setText(ans[0]);
                    ans2.setText(ans[1]);
                    ans3.setText(ans[2]);
                    ans4.setText(ans[3]);
                } else {
                    Log.i("XING", "VoteActivity, " + e.toString());
                }
            }
        });

    }
    public void onClick(View view){
        switch(view.getId()){
            case R.id.vote_ans1:
                choose = 1;
                ans1.setBackgroundResource(R.color.choose);
                ans2.setBackgroundResource(R.color.white);
                ans3.setBackgroundResource(R.color.white);
                ans4.setBackgroundResource(R.color.white);
                meetSurveyShow.setNA01(new Integer(na01 + 1));
                break;
            case R.id.vote_ans2:
                choose = 2;
                ans2.setBackgroundResource(R.color.choose);
                ans1.setBackgroundResource(R.color.white);
                ans3.setBackgroundResource(R.color.white);
                ans4.setBackgroundResource(R.color.white);
                meetSurveyShow.setNA02(new Integer(na02 + 1));
                break;
            case R.id.vote_ans3:
                choose = 3;
                ans3.setBackgroundResource(R.color.choose);
                ans2.setBackgroundResource(R.color.white);
                ans1.setBackgroundResource(R.color.white);
                ans4.setBackgroundResource(R.color.white);
                meetSurveyShow.setNA03(new Integer(na03 + 1));
                break;
            case R.id.vote_ans4:
                choose = 4;
                ans4.setBackgroundResource(R.color.choose);
                ans2.setBackgroundResource(R.color.white);
                ans3.setBackgroundResource(R.color.white);
                ans1.setBackgroundResource(R.color.white);
                meetSurveyShow.setNA04(new Integer(na04 + 1));
                break;
            case R.id.vote_ok:
                //获取答案编号choose，后台+1；
                meetSurveyShow.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            if(choose != 0) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(VoteActivity.this);
                                builder.setMessage("已成功投票！");
                                builder.setIcon(R.mipmap.ic_choose);
                                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent_vote = new Intent(VoteActivity.this, StartActivity.class);
                                        intent_vote.putExtra("meetIDEr", meetShow.getObjectId().toString());
                                        startActivity(intent_vote);
                                    }
                                });
                                builder.create().show();
                            }else{
                                Toast.makeText(VoteActivity.this, "您还未进行投票！", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(VoteActivity.this, "投票失败！", Toast.LENGTH_LONG).show();
                            Log.i("XING", "VoteActivity, " + e.toString());
                        }
                    }
                });
                break;
        }
    }

}
