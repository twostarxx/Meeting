package com.example.administrator.meeting;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class ConfApplyActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextMeetApplyName;
    private EditText editTextMeetApplyTime;
    private EditText editTextMeetApplyAddress;
    private EditText editTextMeetApplyPrice;
    private EditText editTextMeetApplyContent;
    private EditText editTextMeetApplySponsor;
    private EditText editTextMeetApplyIntroduction;
    private EditText editTextMeetApplyGuest;
    private Button buttonMeetApply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_conf_apply);
        MyUser user01 = BmobUser.getCurrentUser(MyUser.class);
        editTextMeetApplyName = (EditText) findViewById(R.id.editMeetApplyName);
        editTextMeetApplyTime = (EditText) findViewById(R.id.editMeetApplyTime);
        editTextMeetApplyAddress = (EditText) findViewById(R.id.editMeetApplyAddress);
        editTextMeetApplyPrice = (EditText) findViewById(R.id.editMeetApplyPrice);
        editTextMeetApplyContent = (EditText) findViewById(R.id.editMeetApplyContent);
        editTextMeetApplySponsor = (EditText) findViewById(R.id.editMeetApplySponsor);
        editTextMeetApplyIntroduction = (EditText) findViewById(R.id.editMeetApplyIntroduction);
        editTextMeetApplyGuest = (EditText) findViewById(R.id.editMeetApplyGuest);

        buttonMeetApply = (Button) findViewById(R.id.buttonMeetApply);
        buttonMeetApply.setOnClickListener(this);
    }


    public void onClick(View btn){
        tabMeet tabMeetApply = new tabMeet();
        if (editTextMeetApplyName.getText().toString().length() != 0 &&
                editTextMeetApplyTime.getText().toString().length() != 0 &&
                editTextMeetApplyAddress.getText().toString().length() != 0  &&
                editTextMeetApplyPrice.getText().toString().length() != 0){
            //Save data to tabMeet.
            Log.i("XING",editTextMeetApplyName.getText().toString());
            tabMeetApply.setMeetName(editTextMeetApplyName.getText().toString());
            tabMeetApply.setMeetTime(editTextMeetApplyTime.getText().toString());
            tabMeetApply.setMeetLocation(editTextMeetApplyAddress.getText().toString());
            tabMeetApply.setMeetPrice(Double.parseDouble(editTextMeetApplyPrice.getText().toString()));
            tabMeetApply.setMeetContent(editTextMeetApplyContent.getText().toString());
            tabMeetApply.setMeetHolder(editTextMeetApplySponsor.getText().toString());
            tabMeetApply.setMeetGuest(editTextMeetApplyGuest.getText().toString());
            tabMeetApply.setMeetType(1);
            tabMeetApply.setMeetState(1);

            tabMeetApply.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null){
                        Toast.makeText(ConfApplyActivity.this, "会议申请提交成功，待管理员审核.", Toast.LENGTH_LONG).show();
                        //-------------XING: Need intent.

                    } else {
                        Toast.makeText(ConfApplyActivity.this, "发生异常错误，管理员正在抢修.", Toast.LENGTH_LONG).show();
                        Log.i("XING", e.toString());
                    }
                }
            });


            //Intent to SurveyActivity.
            Intent intent_01 = new Intent(ConfApplyActivity.this, SurveyActivity.class);
            intent_01.putExtra("meetNameApply", editTextMeetApplyName.getText().toString());
            startActivity(intent_01);

        }else {
            new AlertDialog.Builder(ConfApplyActivity.this)
                    .setTitle("提示")
                    .setMessage("会议名称，时间，地点必填！")
                    .setPositiveButton("确定",null)
                    .show();
        }
    }
}


