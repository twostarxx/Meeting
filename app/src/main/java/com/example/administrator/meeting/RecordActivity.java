package com.example.administrator.meeting;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RecordActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        EditText title = (EditText) findViewById(R.id.edit_title);
        EditText context = (EditText) findViewById(R.id.edit_record);
        Button btnrecord = (Button) findViewById(R.id.btn_record);
        btnrecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RecordActivity.this,"已保存",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RecordActivity.this,StartActivity.class);
                startActivity(intent);
            }
        });
    }
}
