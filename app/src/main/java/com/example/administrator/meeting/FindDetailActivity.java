package com.example.administrator.meeting;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class FindDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ImageView image = (ImageView) findViewById(R.id.img_find_detail);
        TextView text = (TextView) findViewById(R.id.text_find_detail);

        Bundle find = this.getIntent().getExtras();
        int num = Integer.parseInt(find.getString("find"));
        switch (num) {
            case 0:
                image.setImageResource(R.drawable.ic_find1);
                text.setText(R.string.text_find1);
                break;
            case 1:
                image.setImageResource(R.drawable.ic_find2);
                text.setText(R.string.text_find2);
                break;
            case 2:
                image.setImageResource(R.drawable.ic_find3);
                text.setText(R.string.text_find3);
                break;
            case 3:
                image.setImageResource(R.drawable.ic_find4);
                text.setText(R.string.text_find4);
                break;
            case 4:
                image.setImageResource(R.drawable.ic_find5);
                text.setText(R.string.text_find5);
                break;
            case 5:
                image.setImageResource(R.drawable.ic_find6);
                text.setText(R.string.text_find6);
                break;
        }

    }
}
