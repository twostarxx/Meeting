package com.example.administrator.meeting;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.BmobWrapper;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by Administrator on 2017-02-20.
 */
public class MeFragment extends Fragment {
    private MyUser user01;
    ImageView imageViewUser;
    TextView textViewUserName, textViewUserEmail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        textViewUserName = (TextView) view.findViewById(R.id.me_name);
        textViewUserEmail = (TextView) view.findViewById(R.id.text_member);
        imageViewUser = (ImageView) view.findViewById(R.id.img_me) ;
        user01 = BmobUser.getCurrentUser(MyUser.class);
        textViewUserName.setText(user01.getUsername().toString());
        textViewUserEmail.setText(user01.getEmail().toString());
        new imageDown(imageViewUser).execute(user01.getImgUser().getFileUrl());

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public static MeFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        MeFragment fragment = new MeFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
