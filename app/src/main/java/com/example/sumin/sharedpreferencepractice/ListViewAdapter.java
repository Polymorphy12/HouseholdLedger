package com.example.sumin.sharedpreferencepractice;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by LG on 2017-05-29.
 */

public class ListViewAdapter extends BaseAdapter {

    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();

    public ListViewAdapter()
    {

    }

    @Override
    public int getCount()
    {
        return listViewItemList.size();
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
            convertView = inflater.inflate(R.layout.child_listview, parent, false);
        }

        //화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득

        TextView groupYearMonth  = (TextView) convertView.findViewById(R.id.groupYearMonth);
        TextView groupDayofWeek = (TextView) convertView.findViewById(R.id.groupDayOfWeek);
        TextView itemCategory = (TextView) convertView.findViewById(R.id.itemCategory);
        TextView itemMoney = (TextView) convertView.findViewById(R.id.itemMoney);
        TextView itemHistory = (TextView) convertView.findViewById(R.id.itemHistory);
        TextView itemTime = (TextView) convertView.findViewById(R.id.itemTime);
        TextView itemMethodsOfPayment = (TextView) convertView.findViewById(R.id.itemMethodsOfPayment);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem listViewItem = listViewItemList.get(position);

        //// 아이템 내 각 위젯에 데이터 반영
        itemCategory.setText(listViewItem.category);
        groupDayofWeek.setText(listViewItem.myDayOfWeekString);
        itemHistory.setText(listViewItem.getTitle());
        itemMoney.setText(listViewItem.getDesc());
        if(listViewItem.isIncome)
            itemMoney.setTextColor(Color.parseColor("#3333FF"));
        else
            itemMoney.setTextColor(Color.parseColor("#DD2233"));
        groupYearMonth.setText(listViewItem.getDate());
        itemTime.setText(listViewItem.getTime());
        itemMethodsOfPayment.setText(listViewItem.methodsOfPayment);
        return convertView;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public Object getItem(int position)
    {
        return listViewItemList.get(position);
    }

    public void addItem(Drawable icon, String history, String desc ,String date, MyBundle myBundle)
    {
        ListViewItem item = new ListViewItem();

        item.setIcon(icon);
        item.setHistory(history);
        item.setDesc(desc);
        item.setDate(date);

        item.setMyBundle(myBundle);

        listViewItemList.add(item);
    }

    public ArrayList<ListViewItem> getItemList()
    {
        return listViewItemList;
    }

    public void removeItem(int position)
    {
        listViewItemList.remove(position);
    }
}