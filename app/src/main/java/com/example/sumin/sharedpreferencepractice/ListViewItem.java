package com.example.sumin.sharedpreferencepractice;

import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * Created by LG on 2017-06-14.
 */

public class ListViewItem {


    boolean isSelected = false;
    boolean isIncome = false;
    private Drawable iconDrawable ;

    //title == history 즉, 내역
    private String titleStr ;

    //descStr == 금액
    private String descStr;

    //RG?
    private String dateStr;

    private String timeStr;


    int myHour = 0;
    int myMinute = 0;
    int myYear =0;
    int myMonth =0;
    int myDay =0;

    int myDayOfWeek = 1;

    MyBundle myBundle;
    String category;
    String methodsOfPayment;
    String memo;
    String myDayOfWeekString;

    public void setTimeForSort(int y, int month, int d, int h, int minute)
    {
        myYear = y;
        myMonth = month;
        myDay = d;
        myHour = h;
        myMinute = minute;
    }

    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setHistory(String title) {
        titleStr = title ;
    }
    public void setDesc(String desc) {
        descStr = desc ;
    }
    public void setDate(String date) { dateStr = date;}

    public void setMyBundle(MyBundle bundle)
    {
        myYear = bundle.getYear();
        myMonth = bundle.getMonth();
        myDay = bundle.getDay();
        myDayOfWeek = bundle.getDayOfWeek();
        Log.d("요일", myDayOfWeek+" 설정중");
        setMyDayOfWeekString(myDayOfWeek);
        Log.d("요일", myDayOfWeekString);
        myBundle = bundle;
        category = bundle.category;
        methodsOfPayment = bundle.methodsOfPayment;
        memo = bundle.memo;
        timeStr = bundle.time;
    }

    private void setMyDayOfWeekString(int dayOfWeek)
    {
            switch (dayOfWeek){
                case 1 :
                    myDayOfWeekString ="일요일";
                    return;
                case 2 :
                    myDayOfWeekString ="월요일";
                    return;
                case 3 :
                    myDayOfWeekString ="화요일";
                    return;
                case 4 :
                    myDayOfWeekString ="수요일";
                    return;
                case 5 :
                    myDayOfWeekString ="목요일";
                    return;
                case 6 :
                    myDayOfWeekString ="금요일";
                    return;
                case 7 :
                    myDayOfWeekString ="토요일";
                    return;

            }
    }



    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public String getDesc() {
        return this.descStr ;
    }
    public String getTime() { return this.timeStr;}
    public String getDate(){
        return this.dateStr;
    }

}
