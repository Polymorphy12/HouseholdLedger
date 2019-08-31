package com.example.sumin.sharedpreferencepractice;

/**
 * Created by LG on 2017-06-22.
 */

public class MyBundle {

    String category;
    String methodsOfPayment;
    String memo;
    String time;
    int dayOfWeek = 1;
    int myYear = 0;
    int myMonth = 0;
    int myDay = 0;

    public void setYear(int year) {myYear= year;}
    public void setMonth(int month){myMonth = month;}
    public void setDay(int day){myDay = day;}

    public int getMonth(){return myMonth;}
    public int getYear(){return myYear;}
    public int getDay(){return myDay;}

    public void setDayOfWeek(int myDayOfWeek){dayOfWeek = myDayOfWeek;}

    public void setTime(String t)
    {
        time = t;
    }

    public void setCategory(String c)
    {
        category = c;
    }


    public void setMethodsOfPayment(String s)
    {
        methodsOfPayment = s;
    }

    public void setMemo(String m)
    {
        memo = m;
    }


    public int getDayOfWeek() {return dayOfWeek;}

    public String getTime()
    {return time;}

    public String getCategory()
    {return category;}

    public String getMethodsOfPayment()
    {return methodsOfPayment;}

    public String getMemo()
    {return memo;}

}
