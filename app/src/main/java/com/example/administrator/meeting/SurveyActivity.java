package com.example.administrator.meeting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class SurveyActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextSurveyQ;
    private EditText editTextSurveyA01;
    private EditText editTextSurveyA02;
    private EditText editTextSurveyA03;
    private EditText editTextSurveyA04;
    private Button buttonSurvey;
    private tabMeet meetShow;
    private meetOrganize meetOrganize01;
    private MyUser user01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_survey);


        //Get meetName intent from ConfApplyActivity. Get meetShow from Bmob.
        String meetNameApply = (String) this.getIntent().getSerializableExtra("meetNameApply");
        BmobQuery<tabMeet> bmobQueryMeet = new BmobQuery<tabMeet>();
        bmobQueryMeet.addWhereEqualTo("meetName", meetNameApply);
        bmobQueryMeet.findObjects(new FindListener<tabMeet>() {
            @Override
            public void done(List<tabMeet> tabMeetList, BmobException e) {
                if (e == null){
                    meetShow = tabMeetList.get(0);
                    Log.i("XING", "SurveyActivity, " + meetShow.getMeetName().toString());
                }else{
                    Log.i("XING", "SurveyActivity, " + e.toString());
                }
            }
        });

        editTextSurveyQ = (EditText)findViewById(R.id.editSurveyQuestion01);
        editTextSurveyA01 = (EditText) findViewById(R.id.editSurveyQ01A01);
        editTextSurveyA02 = (EditText) findViewById(R.id.editSurveyQ01A02);
        editTextSurveyA03 = (EditText) findViewById(R.id.editSurveyQ01A03);
        editTextSurveyA04 = (EditText) findViewById(R.id.editSurveyQ01A04);
        buttonSurvey = (Button) findViewById(R.id.buttonSurvey);

        buttonSurvey.setOnClickListener(this);

    }

    public void onClick(View btn){
        meetSurvey meetsurvey = new meetSurvey();
        if (editTextSurveyQ.getText().toString().length() != 0 &&
                editTextSurveyA01.getText().toString().length() != 0){
            meetsurvey.setQuestion(editTextSurveyQ.getText().toString());
            meetsurvey.setAns01(editTextSurveyA01.getText().toString());
            meetsurvey.setAns02(editTextSurveyA02.getText().toString());
            meetsurvey.setAns03(editTextSurveyA03.getText().toString());
            meetsurvey.setAns04(editTextSurveyA04.getText().toString());
            meetsurvey.setMeetSur(meetShow);
            meetsurvey.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null){
                        Toast.makeText(SurveyActivity.this, "问卷提交成功.", Toast.LENGTH_LONG).show();
                        Log.i("HSING","Sign up success!");
                    }else {
                        Toast.makeText(SurveyActivity.this, "问题提交失败.", Toast.LENGTH_LONG).show();
                        Log.i("HSING",e.toString());
                    }
                }
            });

            //Intent to ErweimaActivity.
            Intent intent_01 = new Intent(SurveyActivity.this, ErweimaActivity.class);
            intent_01.putExtra("meetShow",meetShow);
            startActivity(intent_01);
        }
    }
}
