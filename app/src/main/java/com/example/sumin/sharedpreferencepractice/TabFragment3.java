package com.example.sumin.sharedpreferencepractice;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by LG on 2017-06-12.
 */

public class TabFragment3 extends Fragment {


    //
    long totalIncome=0, totalSpending=0, totalSum=0;

    ListView listView;

    ListViewAdapter adapter;
    ArrayList<ListViewItem> itemArrayFromFrag1 = new ArrayList<ListViewItem>();


    MonthlyListViewAdapter myAdapter;
    ArrayList<MonthlyListViewItem> itemArray = new ArrayList<MonthlyListViewItem>();


    //OnCreate에서 불러올 녀석.
    SharedPreferences pref1;
    int numberOfItems;
    String numberKey = "numberKey";
    //



    private void setItemArray(int numberOfItems)
    {
        if(itemArrayFromFrag1.size() >0)
        {
            for(int i = itemArrayFromFrag1.size()-1; i >= 0; i--)
            {
                itemArrayFromFrag1.remove(i);
            }
        }

        SharedPreferences pref = this.getActivity().getSharedPreferences("pref1", MODE_PRIVATE);
        if(itemArrayFromFrag1.size() == 0)
        {
            String[] booleanKey = new String[numberOfItems];
            String[] titleKey = new String[numberOfItems];
            String[] descKey = new String[numberOfItems];
            String[] dateKey = new String[numberOfItems];


            for(int i = 0; i < numberOfItems; i++)
            {
                ListViewItem item = new ListViewItem();

                booleanKey[i] = "boolean"+ Integer.toString(i);
                //초기 구상 때, 현 History를 Title로 불렀다.
                titleKey[i] = "title"+ Integer.toString(i);
                descKey[i] = "desc"+ Integer.toString(i);
                dateKey[i] = "date"+ Integer.toString(i);

                String memoKey = "memo" + Integer.toString(i);
                String methodsOfPaymentKey = "methodsOfPayment" + Integer.toString(i);
                String categoryKey = "category" + Integer.toString(i);
                String timeKey = "time" + Integer.toString(i);
                String dayOfWeekKey = "dayOfWeek" + Integer.toString(i);
                String myYearKey = "myYear" +Integer.toString(i);
                String myMonthKey = "myMonth" + Integer.toString(i);

                item.isIncome = pref.getBoolean(booleanKey[i], false);
                item.setDate(pref.getString(dateKey[i],""));
                item.setDesc(pref.getString(descKey[i],""));
                //초기 구상 때, 현 History를 Title로 불렀다.
                item.setHistory(pref.getString(titleKey[i],""));


                //여기 고쳐야한당
                MyBundle myBundle = new MyBundle();

                myBundle.setYear(pref.getInt(myYearKey,0));
                myBundle.setMonth(pref.getInt(myMonthKey,0));

                myBundle.setDayOfWeek(pref.getInt(dayOfWeekKey,1));
                Log.d("요일 가져왔당", pref.getInt(dayOfWeekKey,1) +"");
                myBundle.setMethodsOfPayment(pref.getString(methodsOfPaymentKey,""));
                myBundle.setMemo(pref.getString(memoKey,""));
                myBundle.setCategory(pref.getString(categoryKey,""));
                myBundle.setTime(pref.getString(timeKey,""));

                if(item.isIncome)
                {
                    adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.noun_11161_36dp),item.getTitle(), item.getDesc(),item.getDate(), myBundle);

                    ListViewItem temp = (ListViewItem) adapter.getItem(adapter.getCount()-1);

                    itemArrayFromFrag1.add(temp);
                    temp.isIncome = true;
                }
                else
                {
                    adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.noun_11162_36dp), item.getTitle(), item.getDesc(),item.getDate(), myBundle);
                    ListViewItem temp = (ListViewItem) adapter.getItem(adapter.getCount()-1);

                    itemArrayFromFrag1.add(temp);
                }

            }
        }
    }




    //광고배너로 쓸 핸들러 재료.
    int[] adColor = {Color.parseColor("#00DDAA"), Color.parseColor("#FFAABB"), Color.parseColor("#FFAA00"), Color.parseColor("#AA00BB") };
    TabFragment3.BackgroundThread backgroundThread;
    private final TabFragment3.MyHandler mHandler = new TabFragment3.MyHandler(this);

    int[] adDrawable = {R.drawable.ad1, R.drawable.ad2, R.drawable.ad3, R.drawable.ad4};

    TextView myText;
    ImageView adImage;
    LinearLayout background;
    private boolean myTextOn = true;
    //광고 배너로 쓸 핸들러 재료 끝.


    //핸들러 및 쓰레드 구현 시작.
    private void handleMessage(Message msg)
    {
        //Log.d("메시지 뭐니", msg.toString());

        Random random = new Random();

        int index = random.nextInt(4);

        if(adImage!= null && getActivity() != null && this != null)
        {
            Log.d("메시지 뭐니",  "!!!!!!!!!!!!!!"+ index+ " " +Integer.toString(adDrawable[index]));
            adImage.setImageDrawable(ContextCompat.getDrawable(getActivity(),adDrawable[index]));
        }

        /*if(myTextOn)
        {
            myTextOn = false;
            myText.setVisibility(View.GONE);
        }
        else
        {
            myTextOn = true;
            myText.setVisibility(View.VISIBLE);
        }*/
    }

    @Override
    public void onStart() {
        super.onStart();

        if(backgroundThread == null)
        {
            backgroundThread = new TabFragment3.BackgroundThread();
            backgroundThread.setRunning(true);
            backgroundThread.start();
        }
        else
        {
            backgroundThread.setRunning(false);
            backgroundThread = null;
            backgroundThread = new TabFragment3.BackgroundThread();
            backgroundThread.setRunning(true);
            backgroundThread.start();
        }
        //Toast.makeText(getActivity(), "onStart()", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPause() {
        super.onPause();

        if(backgroundThread != null)
        {
            boolean retry = true;
            backgroundThread.setRunning(false);

            while(retry)
            {
                try
                {
                    backgroundThread.join();
                    retry = false;
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        //Toast.makeText(getActivity(), "onStop()", Toast.LENGTH_LONG).show();
    }

    //핸들러 클래스.
    private static class MyHandler extends Handler {

        private final WeakReference<TabFragment3> mActivity;

        public MyHandler(TabFragment3 activity)
        {
            mActivity = new WeakReference<TabFragment3>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            TabFragment3 activity = mActivity.get();
            if(activity != null)
            {
                activity.handleMessage(msg);
            }
        }
    }
    //핸들러 클래스 끝.


    //스레드 클래스.

    public class BackgroundThread extends Thread {
        boolean running = false;

        void setRunning(boolean b)
        {
            running = b;
        }

        @Override
        public void run() {
            while(running)
            {
                try
                {
                    sleep(1000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                mHandler.sendMessage(mHandler.obtainMessage());
            }
        }
    }

    //스레드 클래스 끝.

    //핸들러 및 쓰레드 구현 끝.


    ArrayList<Integer> implementedYear = new ArrayList<Integer>();

    //
    public void implementYearFromFragment1()
    {
        for(int i = 0; i < itemArrayFromFrag1.size(); i++)
        {

            boolean isImplemented = false;
            for(int j=0; j <implementedYear.size(); j++)
            {
                if (itemArrayFromFrag1.get(i).myYear == implementedYear.get(j))
                {
                    Log.d("악", "헹, 이미 있잖아 " + itemArrayFromFrag1.get(i).myYear);
                    isImplemented = true;
                    break;
                }
            }

            if(!isImplemented)
            {
                Log.d("악", "추가됨 " + itemArrayFromFrag1.get(i).myYear);
                implementedYear.add(itemArrayFromFrag1.get(i).myYear);
            }
        }
    }

    public void updateMonthlySum()
    {

        for(int i = 0; i < itemArray.size(); i++)
        {
            for(int j = 0; j < itemArrayFromFrag1.size(); j++)
            {
                if(itemArrayFromFrag1.get(j).myYear == itemArray.get(i).getYear() && itemArrayFromFrag1.get(j).myMonth == itemArray.get(i).getMonth() )
                {
                    if(itemArrayFromFrag1.get(j).isIncome)
                    {
                        MonthlyListViewItem temp = (MonthlyListViewItem) myAdapter.getItem(i);
                        temp.setIncome(temp.getIncome()+Long.parseLong(itemArrayFromFrag1.get(j).getDesc()));
                        totalIncome+=Long.parseLong(itemArrayFromFrag1.get(j).getDesc());
                        totalSum+=Long.parseLong(itemArrayFromFrag1.get(j).getDesc());
                        //Log.d("악", itemArrayFromFrag1.get(j).getDesc() + "으로 바꿨다." + i + "번째 아이템을."  + itemArrayFromFrag1.get(j).myYear+"년 " + itemArrayFromFrag1.get(j).myMonth + "월 수입");
                    }
                    else
                    {
                        MonthlyListViewItem temp = (MonthlyListViewItem) myAdapter.getItem(i);
                        temp.setSpending(temp.getSpending()+ Long.parseLong(itemArrayFromFrag1.get(j).getDesc()));
                        totalSpending+=Long.parseLong(itemArrayFromFrag1.get(j).getDesc());
                        totalSum -= Long.parseLong(itemArrayFromFrag1.get(j).getDesc());
                        //Log.d("악", itemArrayFromFrag1.get(j).getDesc() + "으로 바꿨다." + i + "번째 아이템을.   " + itemArrayFromFrag1.get(j).myYear+"년 " + itemArrayFromFrag1.get(j).myMonth + "월 지출");
                    }
                }
            }
        }

        myAdapter.notifyDataSetChanged();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_fragment_3, container, false);
        background = (LinearLayout) view.findViewById(R.id.advertisement);

        adImage = (ImageView) view.findViewById(R.id.adImage);

        adapter = new ListViewAdapter();
        myAdapter = new MonthlyListViewAdapter();

        listView = (ListView) view.findViewById(R.id.monthlyListView);
        listView.setAdapter(myAdapter);

        //SharedPreferences에서 리스트뷰 받아와서 살림 차리는 것.
        pref1 =this.getActivity().getSharedPreferences("pref2", MODE_PRIVATE);
        if(pref1 != null)
        {
            numberOfItems = pref1.getInt(numberKey,0);
            Log.d("몇개니", Integer.toString(numberOfItems));
            setItemArray(numberOfItems);

            implementYearFromFragment1();

            for(int i = 0; i < implementedYear.size(); i++)
            {
                for(int j = 12; j >=1; j--)
                {
                    Log.d("삭제중" , itemArray.size()+"");
                    myAdapter.addItem(implementedYear.get(i), j,0,0);
                    itemArray.add( (MonthlyListViewItem) myAdapter.getItem(myAdapter.getCount()-1) );
                    //Log.d("삭제중-추가" , itemArray.get(itemArray.size()-1).getYear()+"년 " + itemArray.get(itemArray.size()-1).getMonth() +"월" + "수입 : " + itemArray.get(itemArray.size()-1).getIncome() + ", 지출 : " + itemArray.get(itemArray.size()-1).getSpending());
                }
            }
            updateMonthlySum();

            for(int i = itemArray.size()-1; i >= 0; i--) {

               // Log.d("삭제중-시작" , itemArray.size()+"");
                if(itemArray.get(i).getIncome() ==0 && itemArray.get(i).getSpending() == 0)
                {
                    //Log.d("삭제중", itemArray.get(i).getYear()+"년 " + itemArray.get(i).getMonth() +"월" + i + "번째 삭제중");
                    itemArray.remove(i);
                    myAdapter.removeItem(i);
                }
            }

            myAdapter.notifyDataSetChanged();
            TextView income = (TextView) view.findViewById(R.id.mTotalIncome);
            income.setText(Long.toString(totalIncome));
            TextView spending = (TextView) view.findViewById(R.id.mTotalSpending);
            spending.setText(Long.toString(totalSpending));
            TextView sum = (TextView) view.findViewById(R.id.mTotalSum);
            sum.setText(Long.toString(totalSum));
        }

        return view;
    }
}
