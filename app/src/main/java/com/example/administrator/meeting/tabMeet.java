package com.example.administrator.meeting;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;

/**
 * Created by Twostar on 2017/2/21.
 */

public class tabMeet extends BmobObject {
    private String meetName;
    private String meetHolder;
    private String meetTime;
    private int meetType;                   //1:Learn; 2:Government; 3:Company; 4:Free;
    private int meetState;
    private double meetPrice;
    private String meetContent;
    private String meetGuest;
    private String meetLocation;
    private String meetLabel;
    private BmobFile meetSchedule;

    public String getMeetName() {
        return meetName;
    }

    public void setMeetName(String meetName) {
        this.meetName = meetName;
    }

    public String getMeetHolder() {
        return meetHolder;
    }

    public void setMeetHolder(String meetHolder) {
        this.meetHolder = meetHolder;
    }

    public String getMeetTime() {
        return meetTime;
    }

    public void setMeetTime(String meetTime) {
        this.meetTime = meetTime;
    }

    public int getMeetType() {
        return meetType;
    }

    public void setMeetType(int meetType) {
        this.meetType = meetType;
    }

    public int getMeetState() {
        return meetState;
    }

    public void setMeetState(int meetState) {
        this.meetState = meetState;
    }

    public double getMeetPrice() {
        return meetPrice;
    }

    public void setMeetPrice(double meetPrice) {
        this.meetPrice = meetPrice;
    }

    public String getMeetContent() {
        return meetContent;
    }

    public void setMeetContent(String meetContent) {
        this.meetContent = meetContent;
    }

    public String getMeetGuest() {
        return meetGuest;
    }

    public void setMeetGuest(String meetGuest) {
        this.meetGuest = meetGuest;
    }

    public String getMeetLocation() {
        return meetLocation;
    }

    public void setMeetLocation(String meetLocation) {
        this.meetLocation = meetLocation;
    }

    public String getMeetLabel() {
        return meetLabel;
    }

    public void setMeetLabel(String meetLabel) {
        this.meetLabel = meetLabel;
    }

    public BmobFile getMeetSchedule() {
        return meetSchedule;
    }

    public void setMeetSchedule(BmobFile meetSchedule) {
        this.meetSchedule = meetSchedule;
    }
}
