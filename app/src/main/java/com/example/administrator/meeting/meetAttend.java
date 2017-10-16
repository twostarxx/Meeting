package com.example.administrator.meeting;

import cn.bmob.v3.BmobObject;

/**
 * Created by Twostar on 2017/2/25.
 */

public class meetAttend extends BmobObject {
    private tabMeet meetAtt;
    private MyUser userAtt;

    public tabMeet getMeetAtt() {
        return meetAtt;
    }

    public void setMeetAtt(tabMeet meetAtt) {
        this.meetAtt = meetAtt;
    }

    public MyUser getUserAtt() {
        return userAtt;
    }

    public void setUserAtt(MyUser userAtt) {
        this.userAtt = userAtt;
    }
}
