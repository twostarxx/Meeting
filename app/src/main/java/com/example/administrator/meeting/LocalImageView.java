package com.example.administrator.meeting;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;

/**
 * Created by Administrator on 2017-02-21.
 */
public class LocalImageView implements Holder<Integer> {

    private ImageView imageView;


    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, Integer data) {
        imageView.setImageResource(data);
        /**
         * 加载网络图片
         */
        //imageView.setImageURI(Uri.parse("http://..."));
    }
}

