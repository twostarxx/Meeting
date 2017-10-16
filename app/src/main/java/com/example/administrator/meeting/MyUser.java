package com.example.administrator.meeting;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Twostar on 2017/2/18.
 */

public class MyUser extends BmobUser {
    private Boolean sex;
    private Integer age;
    private BmobFile imgUser;

    public boolean getSex() {
        return this.sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }


    public BmobFile getImgUser() {
        return imgUser;
    }

    public void setImgUser(BmobFile imgUser) {
        this.imgUser = imgUser;
    }
}
