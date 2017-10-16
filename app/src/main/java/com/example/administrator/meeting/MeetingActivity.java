package com.example.administrator.meeting;

import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import junit.framework.Test;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

import c.b.BP;
import c.b.PListener;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import static android.R.attr.value;

public class MeetingActivity extends AppCompatActivity implements View.OnClickListener {
    String Title,Time,Address,State,Label,Holder,Guest,Content, extra;
    Double Price;
    TextView purchase;
    TextView dialog_price, all_price, down_extra;
    EditText amount;
    ImageButton close, sub, plus, collect;
    TextView pay;
    tabMeet meetItem;
    String meetItemID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);
        //accept meetID from homefragment
        meetItem = (tabMeet)  this.getIntent().getSerializableExtra("meetItem");
        meetItemID = (String)  this.getIntent().getSerializableExtra("meetItemID");

        collect = (ImageButton) findViewById(R.id.btn_collect) ;


        ImageView img_meet = (ImageView) findViewById(R.id.img_detail);
        TextView title = (TextView) findViewById(R.id.meet_title);
        TextView time = (TextView) findViewById(R.id.meet_time);
        TextView address = (TextView) findViewById(R.id.meet_address);
        TextView price = (TextView) findViewById(R.id.meet_price);
        TextView state = (TextView) findViewById(R.id.meet_state);
        TextView label = (TextView) findViewById(R.id.meet_label);
        TextView holder = (TextView) findViewById(R.id.holder_detail);
        TextView guest = (TextView) findViewById(R.id.guest_detail);
        TextView content = (TextView) findViewById(R.id.content_detail);
        TextView down_extra = (TextView) findViewById(R.id.extra_detail);
        purchase = (TextView) findViewById(R.id.text_purchase);
        purchase.setOnClickListener(this);

        load();

        //下载资料
        down_extra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobFile bmobFile = new BmobFile(meetItem.getMeetSchedule().getFilename().toString(), "", extra);
                bmobFile.download(new DownloadFileListener() {
                    @Override
                    public void done(String savePath, BmobException e) {
                        if(e==null){
                            Toast.makeText(MeetingActivity.this, "下载成功,保存路径为：" + savePath, Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(MeetingActivity.this, "下载失败："+e.getErrorCode()+","+e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onProgress(Integer integer, long l) {
//                        Log.i("bmob","下载进行中...");
                    }
                });
            }
        });

        //补呈现图片
        img_meet.setImageResource(getResourceByReflect(meetItemID.toLowerCase()));
        title.setText(Title);
        time.setText(Time);
        address.setText(Address);
        price.setText(String.valueOf(Price) + "0元");
        state.setText(State);
        label.setText(Label);
        holder.setText(Holder);
        guest.setText(Guest);
        content.setText(Content);
        if (State == "销售中"){
            state.setBackgroundResource(R.color.blue);
            purchase.setBackgroundResource(R.color.blue);
        }else {
            state.setBackgroundResource(R.color.hui);
            purchase.setBackgroundResource(R.color.hui);
        }

        //分享功能
        ImageButton img_share = (ImageButton) findViewById(R.id.btn_share);
        img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
            }
        });
    }


    @Override
    public void onClick(View btn) {
        if (btn.getId() == R.id.btn_collect){
            Toast.makeText(MeetingActivity.this, "收藏成功 ！", Toast.LENGTH_LONG).show();
        }
        if(btn.getId()==R.id.text_purchase){
            if(State == "销售中"){
                final PurchaseDialog purchaseDialog = new PurchaseDialog(MeetingActivity.this, Price);
                purchaseDialog.show();
                purchaseDialog.setClicklistener(new PurchaseDialog.ClickListenerInterface(){
                    @Override
                    public void doPay() {
                        purchaseDialog.dismiss();
                        double allprice = purchaseDialog.getAllprice();

                        // Pay by Alibabab, Bmob
                        new BP().pay(meetItem.getMeetName().toString(), "请于" + meetItem.getMeetTime().toString() + "及时参会！欢迎您再次使用会一会。",
                                allprice, true, new PListener() {
                                    @Override
                                    public void orderId(String s) {
                                        Log.i("HSING","orderId: " + s);
                                    }

                                    @Override
                                    public void succeed() {
                                        Toast.makeText(MeetingActivity.this, "支付成功", Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void fail(int i, String s) {
                                        Toast.makeText(MeetingActivity.this, "支付失败", Toast.LENGTH_LONG).show();
                                        Log.i("HSING","Pay fail! " + String.valueOf(i) + s);
                                    }

                                    @Override
                                    public void unknow() {
                                        Toast.makeText(MeetingActivity.this, "支付失败", Toast.LENGTH_LONG).show();
                                        Log.i("HSING","Pay unknow!");
                                    }
                                });
                    }
                    @Override
                    public void doClose() {
                        purchaseDialog.dismiss();
                    }
                });
            }else{
                Toast.makeText(getApplicationContext(), "不是购票时间 ！",Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**读取会议信息：图片、名称、时间、地点、价格、状态、标签、主办方、嘉宾、内容、指南
     *
      */
    public void load(){
        Title = meetItem.getMeetName().toString();
        Time = meetItem.getMeetTime().toString();
        Address = meetItem.getMeetLocation().toString();
        Price = meetItem.getMeetPrice();
        if (meetItem.getMeetState() == 1){
            State = "销售中";
        }else if (meetItem.getMeetState() == -1){
            State = "已结束";
        }
        Label = meetItem.getMeetLabel().toString();
        Holder = meetItem.getMeetHolder().toString();
        Guest = meetItem.getMeetGuest().toString();
        Content = meetItem.getMeetContent().toString();
        extra = meetItem.getMeetSchedule().getFileUrl();
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

    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
    // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("会一会");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("欢迎使用会一会APP，“" + Title + "”期待你的参与。");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath(getResourceByReflect(meetItemID.toLowerCase()));//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
//        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("会一会！");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
        // 启动分享GUI
        oks.show(this);
    }

}
