package com.example.administrator.meeting;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.net.Inet4Address;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class ErweimaActivity extends AppCompatActivity {
    private TextView textViewMeetName;
    private Button buttonMain;
    private ImageView imageViewErweima;
    private tabMeet meetShow;
    private MyUser user01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erweima);
        //Get intent tabMeet from SurveyActivity.
        meetShow = (tabMeet) this.getIntent().getSerializableExtra("meetShow");
        user01 = BmobUser.getCurrentUser(MyUser.class);

        textViewMeetName = (TextView) findViewById(R.id.textErMeetName) ;
        buttonMain= (Button) findViewById(R.id.buttonApplyConfirm);
        imageViewErweima = (ImageView) findViewById(R.id.Erweima);
        textViewMeetName.setText(meetShow.getMeetName().toString());

        Log.i("XING", "ErweimaActivity, " + meetShow.getMeetName().toString());
        // 转换为BitmapDrawable对象
        BitmapDrawable meetMap = new BitmapDrawable(generateQRCode(meetShow.getObjectId().toString()));
        // 显示位图
        imageViewErweima.setImageDrawable(meetMap);

        //点击按钮，返回主页.
        buttonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save data to meetOrganize.
                meetOrganize meetOrganize01 = new meetOrganize();
                meetOrganize01.setMeetOrg(meetShow);
                meetOrganize01.setUserOrg(user01);
                meetOrganize01.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null){
                            Log.i("XING", "ConfApplyActivity, meetOrganize save success!");
                        }else {
                            Log.i("XING", "ConfApplyActivity, " + e.toString());
                        }
                    }
                });
                //Intent to MainActivity.
                Intent intent_01 = new Intent(ErweimaActivity.this, MainActivity.class);
                startActivity(intent_01);
            }
        });
    }

    //===============生成二维码=======================
    private Bitmap generateQRCode(String qrCodeString){
        Bitmap bmp = null;    //二维码图片
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(qrCodeString, BarcodeFormat.QR_CODE, 512, 512); //参数分别表示为: 条码文本内容，条码格式，宽，高
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

            //绘制每个像素
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bmp;
    }
}
