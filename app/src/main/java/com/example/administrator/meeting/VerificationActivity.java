package com.example.administrator.meeting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class VerificationActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText ver_email;
    private EditText ver_password;
    private EditText ver_nickname;
    private Button btn_ver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_verification);

        ver_email = (EditText) findViewById(R.id.edit_ver_email);
        ver_password = (EditText) findViewById(R.id.edit_ver_password);
        ver_nickname = (EditText) findViewById(R.id.edit_ver_nickName);
        btn_ver = (Button) findViewById(R.id.btn_Ver);
        btn_ver.setOnClickListener(this);

    }

    public void onClick(View btn){
        BmobUser userLogIn = new BmobUser();
        userLogIn.setUsername(ver_nickname.getText().toString());
        userLogIn.setPassword(ver_password.getText().toString());
        userLogIn.setEmail(ver_email.getText().toString());
        userLogIn.signUp(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                if (e==null){
                    Toast.makeText(VerificationActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                    Intent intent_verfication = new Intent(VerificationActivity.this,LoginActivity.class);
                    startActivity(intent_verfication);
                }else{
                    Log.i("HSING", "注册失败！");
                    Toast.makeText(VerificationActivity.this, "注册失败，管理员正蹲在马路边抢修！", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
