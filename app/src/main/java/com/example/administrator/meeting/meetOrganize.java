package com.example.administrator.meeting;

import cn.bmob.v3.BmobObject;

/**
 * Created by Twostar on 2017/2/25.
 */

public class meetOrganize extends BmobObject{
    private tabMeet meetOrg;
    private MyUser userOrg;

    public tabMeet getMeetOrg() {
        return meetOrg;
    }

    public void setMeetOrg(tabMeet meetOrg) {
        this.meetOrg = meetOrg;
    }

    public MyUser getUserOrg() {
        return userOrg;
    }

    public void setUserOrg(MyUser userOrg) {
        this.userOrg = userOrg;
    }
}
