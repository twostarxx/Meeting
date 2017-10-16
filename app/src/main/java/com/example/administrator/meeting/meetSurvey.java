package com.example.administrator.meeting;

import cn.bmob.v3.BmobObject;

/**
 * Created by Twostar on 2017/2/24.
 */

public class meetSurvey extends BmobObject{
    private tabMeet meetSur;
    private String Question;
    private String Ans01;
    private String Ans02;
    private String Ans03;
    private String Ans04;
    private Integer NA01;
    private Integer NA02;
    private Integer NA03;
    private Integer NA04;

    public tabMeet getMeetSur() {
        return meetSur;
    }

    public void setMeetSur(tabMeet meetSur) {
        this.meetSur = meetSur;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getAns01() {
        return Ans01;
    }

    public void setAns01(String ans01) {
        Ans01 = ans01;
    }

    public String getAns02() {
        return Ans02;
    }

    public void setAns02(String ans02) {
        Ans02 = ans02;
    }

    public String getAns03() {
        return Ans03;
    }

    public void setAns03(String ans03) {
        Ans03 = ans03;
    }

    public String getAns04() {
        return Ans04;
    }

    public void setAns04(String ans04) {
        Ans04 = ans04;
    }

    public Integer getNA01() {
        return NA01;
    }

    public void setNA01(Integer NA01) {
        this.NA01 = NA01;
    }

    public Integer getNA02() {
        return NA02;
    }

    public void setNA02(Integer NA02) {
        this.NA02 = NA02;
    }

    public Integer getNA03() {
        return NA03;
    }

    public void setNA03(Integer NA03) {
        this.NA03 = NA03;
    }

    public Integer getNA04() {
        return NA04;
    }

    public void setNA04(Integer NA04) {
        this.NA04 = NA04;
    }
}
