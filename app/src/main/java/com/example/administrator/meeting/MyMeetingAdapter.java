package com.example.administrator.meeting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2017-02-24.
 */
public class MyMeetingAdapter extends BaseAdapter implements View.OnClickListener{
    public static final int TYPE_End = 0;
    public static final int TYPE_UNEND = 1;
    private Context context;
    private int[] img;
    private String[] title, time, address, state;  // 题目、时间、地点、状态
    private ToResults results;

    public interface ToResults {
        public void click(View v);
    }

    public MyMeetingAdapter(Context context, int[] img, String[] title,String[] time,String[] address,String[] state,ToResults results) {
        super();
        this.context = context;
        this.img = img;
        this.title = title;
        this.time = time;
        this.address = address;
        this.state = state;
        this.results = results;
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (state[position] == "已结束") {
            return TYPE_End;
        } else {
            return TYPE_UNEND;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EndViewHolder endHolder;
        UnEndViewHolder unendHolder;
        switch (getItemViewType(position)) {
            case TYPE_End:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.list_meeting_two, null);
                    endHolder = new EndViewHolder();
                    endHolder.texttitle = (TextView) convertView.findViewById(R.id.text_title);
                    endHolder.texttime = (TextView) convertView.findViewById(R.id.text_time);
                    endHolder.textaddress = (TextView) convertView.findViewById(R.id.text_location);
                    endHolder.textstate = (TextView) convertView.findViewById(R.id.text_state);
                    endHolder.textresult = (TextView) convertView.findViewById(R.id.text_result);
                    endHolder.textresult.setOnClickListener(this);
                    endHolder.textresult.setTag(position);
                    endHolder.imgmeet = (ImageView) convertView.findViewById(R.id.img_meet);
                    convertView.setTag(endHolder);
                } else {
                    endHolder = (EndViewHolder) convertView.getTag();
                }
                endHolder.imgmeet.setImageResource(img[position]);
                endHolder.texttitle.setText(title[position]);
                endHolder.texttime.setText(time[position]);
                endHolder.textaddress.setText(address[position]);
                endHolder.textstate.setText(state[position]);
                break;
            case TYPE_UNEND:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.list_meeting_one, null);
                    unendHolder = new UnEndViewHolder();
                    unendHolder.texttitle = (TextView) convertView.findViewById(R.id.text_title);
                    unendHolder.texttime = (TextView) convertView.findViewById(R.id.text_time);
                    unendHolder.textaddress = (TextView) convertView.findViewById(R.id.text_location);
                    unendHolder.textstate = (TextView) convertView.findViewById(R.id.text_state);
                    if (state[position] == "进行中"){
                        unendHolder.textstate.setBackgroundResource(R.color.orange);
                    }
                    unendHolder.imgmeet = (ImageView) convertView.findViewById(R.id.img_meet);
                    convertView.setTag(unendHolder);
                } else {
                    unendHolder = (UnEndViewHolder) convertView.getTag();
                }
                unendHolder.imgmeet.setImageResource(img[position]);
                unendHolder.texttitle.setText(title[position]);
                unendHolder.texttime.setText(time[position]);
                unendHolder.textstate.setText(state[position]);
                unendHolder.textaddress.setText(address[position]);
                break;
        }
        return convertView;
    }

    class EndViewHolder {
        ImageView imgmeet;
        TextView texttitle;
        TextView texttime;
        TextView textaddress;
        TextView textstate;
        TextView textresult;
    }

    class UnEndViewHolder {
        ImageView imgmeet;
        TextView texttitle;
        TextView texttime;
        TextView textaddress;
        TextView textstate;
    }

    public void onClick(View v){
        results.click(v);
    }
}
