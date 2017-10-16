package com.example.administrator.meeting;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class PieChartActivity extends AppCompatActivity {

    private tabMeet meetShow;
    private PieChart mChart;
    private TextView textViewMeetNamePie, textViewMeetIntroductionPie;
    private meetSurvey meetSurveyShow;
    private String ans01, ans02, ans03, ans04, ques;
    private int na01, na02, na03, na04;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        //accept tabMeet from MeetFragment
        meetShow = (tabMeet) this.getIntent().getSerializableExtra("meetClick");
        Log.i("XING","PreviewLineChartActivity, meetShow: " + meetShow.getMeetName().toString());

        textViewMeetNamePie = (TextView) findViewById(R.id.meetNamePie);
        textViewMeetIntroductionPie = (TextView) findViewById(R.id.meetIntroductionPie);
        textViewMeetNamePie.setText(meetShow.getMeetName().toString());
        mChart = (PieChart) findViewById(R.id.spread_pie_chart);

        // Get data from Bmob.
        final BmobQuery<meetSurvey> meetsurvey= new BmobQuery<meetSurvey>();
        meetsurvey.addWhereEqualTo("meetSur", new BmobPointer(meetShow));
        meetsurvey.include("Question, Ans01, Ans02, Ans03, Ans04, NA01, NA02, NA03, NA04");
        meetsurvey.findObjects(new FindListener<meetSurvey>() {
            @Override
            public void done(List<meetSurvey> meetSurveyList, BmobException e) {
                if (e == null){
                    meetSurveyShow = meetSurveyList.get(0);
                    ques = meetSurveyShow.getQuestion().toString();
                    ans01 = meetSurveyShow.getAns01().toString();
                    ans02 = meetSurveyShow.getAns02().toString();
                    ans03 = meetSurveyShow.getAns03().toString();
                    ans04 = meetSurveyShow.getAns04().toString();
                    na01 = meetSurveyShow.getNA01().intValue();
                    na02 = meetSurveyShow.getNA02().intValue();
                    na03 = meetSurveyShow.getNA03().intValue();
                    na04 = meetSurveyShow.getNA04().intValue();
                    PieData mPieData = getPieData(ans01,ans02, ans03, ans04, 4, na01, na02, na03, na04);    // num of Ans.; Ans 01,..
                    showChart(mChart, mPieData, ques);
                    Log.i("XING", "PieChartActivity " + ans01 + "  " + String.valueOf(na01));
                }else {
                    Log.i("XING", "PieChartActivity " + e.toString());
                }
            }
        });

    }

    private void showChart(PieChart pieChart, PieData pieData, String question) {
        pieChart.setHoleColorTransparent(true);

        pieChart.setHoleRadius(60f);  //半径
        pieChart.setTransparentCircleRadius(64f); // 半透明圈
        //pieChart.setHoleRadius(0)  //实心圆

        pieChart.setDescription(" ");

        // mChart.setDrawYValues(true);
        pieChart.setDrawCenterText(true);  //饼状图中间可以添加文字

        pieChart.setDrawHoleEnabled(true);

        pieChart.setRotationAngle(90); // 初始旋转角度

        // draws the corresponding description value into the slice
        // mChart.setDrawXValues(true);

        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true); // 可以手动旋转

        // display percentage values
        pieChart.setUsePercentValues(true);  //显示成百分比
//        pieChart.setUsePercentValues(false);
        // mChart.setUnit(" €");
//         mChart.setDrawUnitsInChart(true);
        pieChart.setDescriptionTextSize(20);

        // add a selection listener
//      mChart.setOnChartValueSelectedListener(this);
        // mChart.setTouchEnabled(false);

//      mChart.setOnAnimationListener(this);

        pieChart.setCenterText(question);  //饼状图中间的文字

        //设置数据
        pieChart.setData(pieData);

        // undo all highlights
//      pieChart.highlightValues(null);
//      pieChart.invalidate();

        Legend mLegend = pieChart.getLegend();  //设置比例图
        mLegend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);  //最右边显示
//      mLegend.setForm(LegendForm.LINE);  //设置比例图的形状，默认是方形
        mLegend.setXEntrySpace(5f);
        mLegend.setYEntrySpace(5f);

        pieChart.animateXY(1000, 1000);  //设置动画
        // mChart.spin(2000, 0, 360);
    }

    /**
     *
     * @param count 分成几部分
//     * @param range
     */
    private PieData getPieData(String a01, String a02, String a03, String a04, int count, int quarterly1, int quarterly2, int quarterly3, int quarterly4) {

        ArrayList<String> xValues = new ArrayList<String>();  //xVals用来表示每个饼块上的内容

        xValues.add("答案1：" + a01);
        xValues.add("答案2：" + a02);
        xValues.add("答案3：" + a03);
        xValues.add("答案4：" + a04);
        Log.i("XING", a01);

        ArrayList<Entry> yValues = new ArrayList<Entry>();  //yVals用来表示封装每个饼块的实际数据

        // 饼图数据
        yValues.add(new Entry(quarterly1, 0));
        yValues.add(new Entry(quarterly2, 1));
        yValues.add(new Entry(quarterly3, 2));
        yValues.add(new Entry(quarterly4, 3));

        //y轴的集合
        PieDataSet pieDataSet = new PieDataSet(yValues, "(%)"/*显示在比例图上*/);
        pieDataSet.setSliceSpace(0f); //设置个饼状图之间的距离

        ArrayList<Integer> colors = new ArrayList<Integer>();

        // 饼图颜色
        colors.add(Color.rgb(205, 205, 205));
        colors.add(Color.rgb(114, 188, 223));
        colors.add(Color.rgb(255, 123, 124));
        colors.add(Color.rgb(57, 135, 200));

        pieDataSet.setColors(colors);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); // 选中态多出的长度

        PieData pieData = new PieData(xValues, pieDataSet);

        return pieData;
    }
}
