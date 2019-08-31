package com.example.sumin.sharedpreferencepractice;

/**
 * Created by LG on 2017-06-25.
 */

public class MonthlyListViewItem {

    private int year, month, day;
    private long income, spending;

    public void setDay(int d)
    {
        day = d;
    }

    public void setYear(int y)
    {
        year = y;
    }

    public void setMonth(int m)
    {
        month = m;
    }

    public void setIncome(long income1)
    {
        income = income1;
    }

    public void setSpending(long spending1)
    {
        spending = spending1;
    }


    public String getYearMonth(){ return year +"년 " + month +"월 " +day+"일";}

    public int getDay(){ return day;}

    public long getIncome() {
        return income;
    }

    public int getMonth() {
        return month;
    }

    public long getSpending() {
        return spending;
    }

    public int getYear() {
        return year;
    }
}
