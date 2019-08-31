package com.example.sumin.sharedpreferencepractice;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by LG on 2017-06-27.
 */

public class DailyListViewAdapter extends BaseAdapter implements Filterable{

    private ArrayList<MonthlyListViewItem> dailyListViewItemList = new ArrayList<MonthlyListViewItem>();

    private ArrayList<MonthlyListViewItem> filteredItemList = dailyListViewItemList;

    public DailyListViewAdapter()
    {

    }

    //여기서부터 filter관련.

    Filter listFilter;

    @Override
    public Filter getFilter() {

        if(listFilter == null)
        {
            listFilter = new ListFilter();
        }

        return listFilter;
    }

    private class ListFilter extends Filter
    {
        @Override protected FilterResults performFiltering(CharSequence constraint)
        {
            Log.d("필터 perform", constraint.toString());
            FilterResults results = new FilterResults() ;
            if (constraint == null || constraint.length() == 0)
            {
                results.values = dailyListViewItemList ;
                results.count = dailyListViewItemList.size() ;
            }
            else
            {
                ArrayList<MonthlyListViewItem> itemList = new ArrayList<MonthlyListViewItem>() ;
                for (MonthlyListViewItem item : dailyListViewItemList)
                {
                    if (item.getYearMonth().toUpperCase().contains(constraint.toString().toUpperCase()) )
                    { itemList.add(item) ; }
                }
                results.values = itemList ;
                results.count = itemList.size() ;
            }
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            // update listview by filtered data list.
            filteredItemList = (ArrayList<MonthlyListViewItem>) results.values ;
            // notify
            if (results.count > 0)
            {
                notifyDataSetChanged();
            }
            else
                {
                    notifyDataSetInvalidated() ;
                }
        }
    }



    // filter관련 끝




    @Override
    public int getCount() {

        return filteredItemList.size();
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


        MonthlyListViewItem listViewItem;
        listViewItem = filteredItemList.get(position);


        //// 아이템 내 각 위젯에 데이터 반영

        monthlyYearMonth.setText(listViewItem.getYear() +"년 " + listViewItem.getMonth()+"월 " +listViewItem.getDay() +"일");

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
        return filteredItemList.get(i);
    }

    public ArrayList<MonthlyListViewItem> getFilteredItemList() { return filteredItemList;}

    public void addItem(int year, int month,int day, long income, long spending)
    {
        MonthlyListViewItem item = new MonthlyListViewItem();

        item.setIncome(income);
        item.setSpending(spending);

        item.setDay(day);
        item.setMonth(month);
        item.setYear(year);

        dailyListViewItemList.add(item);
    }

    public void removeItem(int position)
    {
        dailyListViewItemList.remove(position);
    }
}