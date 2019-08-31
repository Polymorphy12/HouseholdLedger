package com.example.sumin.sharedpreferencepractice;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by LG on 2017-06-25.
 */

public class MonthlyListViewAdapter extends BaseAdapter {

    private ArrayList<MonthlyListViewItem> monthlyListViewItemList = new ArrayList<MonthlyListViewItem>();

    public MonthlyListViewAdapter()
    {

    }

    @Override
    public int getCount() {
        return monthlyListViewItemList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //위치와 context가 필요해.
        final int pos = position;
        final Context context = parent.getContext();

        //"listview_item" Layout을 inflate하여 convertView 참조 획득.
        if(convertView == null)
        {
            //LayoutInflater 는 XML로 정의 해놓은 Resource들 (레이아웃 등)을 View 형태로 변환해주는 것이다.
            //보통 popup이나 Dialog를 구현할 때 배경화면이 될 레이아웃을 만들어 놓고 View 형태로 반환받아서 액티비티에서 실행한다.
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.monthly_listview, parent, false);
        }

        //화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득

        TextView monthlyYearMonth =(TextView) convertView.findViewById(R.id.monthlyYearMonth);

        TextView totalIncome = (TextView) convertView.findViewById(R.id.monthlyTotalIncome);
        TextView totalSpending = (TextView) convertView.findViewById(R.id.monthlyTotalSpending);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        MonthlyListViewItem listViewItem = monthlyListViewItemList.get(position);

        //// 아이템 내 각 위젯에 데이터 반영

        monthlyYearMonth.setText(listViewItem.getYear() +"년 " + listViewItem.getMonth()+"월");

        totalIncome.setText(Long.toString(listViewItem.getIncome()));
        totalSpending.setText(Long.toString(listViewItem.getSpending()));

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int i) {
        return monthlyListViewItemList.get(i);
    }


    public void addItem(int year, int month, long income, long spending)
    {
        MonthlyListViewItem item = new MonthlyListViewItem();

        item.setIncome(income);
        item.setMonth(month);
        item.setYear(year);
        item.setSpending(spending);

        monthlyListViewItemList.add(item);
    }

    public void removeItem(int position)
    {
        monthlyListViewItemList.remove(position);
    }
}
