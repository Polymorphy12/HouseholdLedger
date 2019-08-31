package com.example.sumin.sharedpreferencepractice;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by LG on 2017-06-12.
 */

public class TabFragment2 extends Fragment implements UpdateSecondFragment  {


    ListView listView;

    ListViewAdapter adapter;
    ArrayList<ListViewItem> itemArrayFromFrag1 = new ArrayList<ListViewItem>();


    DailyListViewAdapter myAdapter;
    ArrayList<MonthlyListViewItem> itemArray = new ArrayList<MonthlyListViewItem>();

    int searchCount = 0;

    TextView totalIncome;
    TextView totalSpending;
    TextView totalSum;
    /*//TextWatcher로부터 override

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    //TextWatcher 끝*/



    //OnCreate에서 불러올 녀석

    SharedPreferences pref1;
    int numberOfItems;
    String numberKey = "numberKey";


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
                String myDayKey = "myDay" + Integer.toString(i);


                item.isIncome = pref.getBoolean(booleanKey[i], false);
                item.setDate(pref.getString(dateKey[i],""));
                item.setDesc(pref.getString(descKey[i],""));
                //초기 구상 때, 현 History를 Title로 불렀다.
                item.setHistory(pref.getString(titleKey[i],""));


                //여기 고쳐야한당
                MyBundle myBundle = new MyBundle();
                myBundle.setDay(pref.getInt(myDayKey,0));
                Log.d("몇일 받아왔니", myBundle.getDay()+" " +myDayKey);
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
                    Log.d("몇일 받아왔니 temp", temp.myDay+" " +myDayKey);
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

    //끝

    //광고배너로 쓸 핸들러 재료.
    int[] adColor = {Color.parseColor("#00DDAA"), Color.parseColor("#FFAABB"), Color.parseColor("#FFAA00"), Color.parseColor("#AA00BB") };
    TabFragment2.BackgroundThread backgroundThread;
    private final TabFragment2.MyHandler mHandler = new TabFragment2.MyHandler(this);

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
            backgroundThread = new TabFragment2.BackgroundThread();
            backgroundThread.setRunning(true);
            backgroundThread.start();
        }
        else
        {
            backgroundThread.setRunning(false);
            backgroundThread = null;
            backgroundThread = new TabFragment2.BackgroundThread();
            backgroundThread.setRunning(true);
            backgroundThread.start();
        }
        //Toast.makeText(getActivity(), "Frag2 onStart()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();

        //Toast.makeText(getActivity(), "Frag2 onPause()", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStop() {
        super.onStop();

        if(backgroundThread != null)
        {
            boolean retry = true;
            backgroundThread.setRunning(false);
            Log.d("스레드", "멈췄당");
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
        //Toast.makeText(getActivity(), "onStop()", Toast.LENGTH_LONG).show();
    }

    //핸들러 클래스.
    private static class MyHandler extends Handler {

        private final WeakReference<TabFragment2> mActivity;

        public MyHandler(TabFragment2 activity)
        {
            mActivity = new WeakReference<TabFragment2>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            TabFragment2 activity = mActivity.get();
            if(activity != null)
            {
                activity.handleMessage(msg);
                activity.updateSum();
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
                try{
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
    ArrayList<Integer> implementedMonth = new ArrayList<Integer>();

    //
    public void implementYearMonthFromFragment1()
    {
        for(int i = 0; i < itemArrayFromFrag1.size(); i++)
        {
            Log.d("악" , "??" + implementedMonth.size() + " " + implementedYear.size());
            Log.d("악" , "??" );
            boolean isMonthImplemented = false;
            boolean isYearImplemented = false;
            for(int j=0; j <implementedYear.size(); j++)
            {
                if (itemArrayFromFrag1.get(i).myYear == implementedYear.get(j))
                {
                    //Log.d("악", "헹, 이미 있잖아 " + itemArrayFromFrag1.get(i).myYear);
                    isYearImplemented = true;
                    break;
                }
            }

            if(!isYearImplemented)
            {
                Log.d("악", "추가됨 " + itemArrayFromFrag1.get(i).myYear);
                implementedYear.add(itemArrayFromFrag1.get(i).myYear);
            }

            for(int j = 0; j <implementedMonth.size(); j++)
            {
                if (itemArrayFromFrag1.get(i).myMonth == implementedMonth.get(j))
                {
                    //Log.d("악", "헹, 이미 있잖아 " + itemArrayFromFrag1.get(i).myMonth);
                    isMonthImplemented = true;
                    break;
                }
            }

            if(!isMonthImplemented)
            {
                Log.d("악", "추가됨 " + itemArrayFromFrag1.get(i).myMonth);
                implementedMonth.add(itemArrayFromFrag1.get(i).myMonth);
            }
        }
    }

    public void updateSum()
    {

        totalIncome.setText("0");
        totalSpending.setText("0");
        totalSum.setText("0");

        long income = 0;
        long spending = 0;
        long sum = 0;
        ArrayList<MonthlyListViewItem> filteredList = myAdapter.getFilteredItemList();
        for(int i = 0; i < filteredList.size(); i++)
        {
            //Log.d("왜이러징", filteredList.get(i).getYearMonth());
            income += filteredList.get(i).getIncome();
            spending += filteredList.get(i).getSpending();
        }
        sum += income - spending;

        totalIncome.setText(Long.toString(income));
        totalSpending.setText(Long.toString(spending));
        totalSum.setText(Long.toString(sum));

    }

    @Override
    public void update() {
        Log.d("불러왔니?", "여긴 프래그먼트2");

        totalIncome.setText("0");
        totalSpending.setText("0");
        totalSum.setText("0");
        pref1 =this.getActivity().getSharedPreferences("pref2", MODE_PRIVATE);
        if(pref1 != null) {
            numberOfItems = pref1.getInt(numberKey, 0);
            Log.d("몇개니", Integer.toString(numberOfItems));
            setItemArray(numberOfItems);
        }


            if(itemArray.size()>0)
            {
                for(int i = itemArray.size()-1; i >= 0; i--) {
                    itemArray.remove(i);
                    myAdapter.removeItem(i);
                }
            }

            for(int i = 0; i < itemArrayFromFrag1.size(); i++)
            {
                //Log.d("삭제중-추가" , "!!!!"+itemArrayFromFrag1.get(i).myYear+"년 " +itemArrayFromFrag1.get(i).myMonth +"월 " +itemArrayFromFrag1.get(i).myDay +"일 "+ itemArrayFromFrag1.get(i).getDesc());
                if(itemArray.size() ==0)
                {
                    //여기서 total income이나 total spending에 빼고 더해야한다
                    if(itemArrayFromFrag1.get(i).isIncome)
                    {
                        myAdapter.addItem(   itemArrayFromFrag1.get(i).myYear,    itemArrayFromFrag1.get(i).myMonth,   itemArrayFromFrag1.get(i).myDay,    Long.parseLong(itemArrayFromFrag1.get(i).getDesc()),    0);

                        itemArray.add( ((MonthlyListViewItem) myAdapter.getItem(myAdapter.getCount()-1))  );

                        //Log.d("삭제중-추가" , itemArray.get(itemArray.size()-1).getYear()+"년 " + itemArray.get(itemArray.size()-1).getMonth() +"월" + "수입 : " + itemArray.get(itemArray.size()-1).getIncome() + ", 지출 : " + itemArray.get(itemArray.size()-1).getSpending());
                    }
                    else
                    {
                        myAdapter.addItem(   itemArrayFromFrag1.get(i).myYear,    itemArrayFromFrag1.get(i).myMonth,   itemArrayFromFrag1.get(i).myDay,    0,    Long.parseLong(itemArrayFromFrag1.get(i).getDesc()));
                        itemArray.add( ((MonthlyListViewItem) myAdapter.getItem(myAdapter.getCount()-1))  );
                        //Log.d("삭제중-추가" , itemArray.get(itemArray.size()-1).getYear()+"년 " + itemArray.get(itemArray.size()-1).getMonth() +"월" + "수입 : " + itemArray.get(itemArray.size()-1).getIncome() + ", 지출 : " + itemArray.get(itemArray.size()-1).getSpending());
                    }
                }
                else
                {
                    //Log.d("삭제중-추가" ,"여기로 들어와야겠지?");
                    boolean isAdded = false;
                    for(int j = 0; j < itemArray.size(); j++)
                    {
                        if(itemArray.get(j).getDay() == itemArrayFromFrag1.get(i).myDay && itemArray.get(j).getMonth() == itemArrayFromFrag1.get(i).myMonth && itemArray.get(j).getYear() == itemArrayFromFrag1.get(i).myYear)
                        {
                            isAdded = true;
                            if(itemArrayFromFrag1.get(i).isIncome)
                            {
                                itemArray.get(j).setIncome( itemArray.get(j).getIncome() +  Long.parseLong(itemArrayFromFrag1.get(i).getDesc()));
                               // Log.d("1삭제중-추가" , itemArray.get(j).getYear()+"년 " + itemArray.get(j).getMonth() +"월" + "수입 : " + itemArray.get(j).getIncome() + ", 지출 : " + itemArray.get(j).getSpending());
                            }
                            else
                            {
                                itemArray.get(j).setSpending(  itemArray.get(j).getSpending() + Long.parseLong(itemArrayFromFrag1.get(i).getDesc()));
                                //Log.d("2삭제중-추가" , itemArray.get(j).getYear()+"년 " + itemArray.get(j).getMonth() +"월" + "수입 : " + itemArray.get(j).getIncome() + ", 지출 : " + itemArray.get(j).getSpending());
                            }

                        }
                    }
                    if(!isAdded)
                    {
                        if(itemArrayFromFrag1.get(i).isIncome)
                        {
                            myAdapter.addItem(   itemArrayFromFrag1.get(i).myYear,    itemArrayFromFrag1.get(i).myMonth,   itemArrayFromFrag1.get(i).myDay,    Long.parseLong(itemArrayFromFrag1.get(i).getDesc()),    0);

                            itemArray.add( ((MonthlyListViewItem) myAdapter.getItem(myAdapter.getCount()-1))  );
                           // Log.d("3삭제중-추가" , itemArray.get(itemArray.size()-1).getYear()+"년 " + itemArray.get(itemArray.size()-1).getMonth() +"월" + "수입 : " + itemArray.get(itemArray.size()-1).getIncome() + ", 지출 : " + itemArray.get(itemArray.size()-1).getSpending());
                        }
                        else
                        {
                            myAdapter.addItem(   itemArrayFromFrag1.get(i).myYear,    itemArrayFromFrag1.get(i).myMonth,   itemArrayFromFrag1.get(i).myDay,    0,    Long.parseLong(itemArrayFromFrag1.get(i).getDesc()));
                            itemArray.add( ((MonthlyListViewItem) myAdapter.getItem(myAdapter.getCount()-1))  );
                           // Log.d("4삭제중-추가" , itemArray.get(itemArray.size()-1).getYear()+"년 " + itemArray.get(itemArray.size()-1).getMonth() +"월" + "수입 : " + itemArray.get(itemArray.size()-1).getIncome() + ", 지출 : " + itemArray.get(itemArray.size()-1).getSpending());
                        }
                    }
                }
            }

        myAdapter.notifyDataSetChanged();
        updateSum();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_fragment_2, container, false);
        background = (LinearLayout) view.findViewById(R.id.advertisement);

        backgroundThread = new TabFragment2.BackgroundThread();
        backgroundThread.setRunning(true);
        backgroundThread.start();

        adImage = (ImageView) view.findViewById(R.id.adImage);

        adapter = new ListViewAdapter();
        myAdapter = new DailyListViewAdapter();

        totalIncome = (TextView) view.findViewById(R.id.dTotalIncome);
        totalSpending = (TextView) view.findViewById(R.id.dTotalSpending);
        totalSum = (TextView) view.findViewById(R.id.dTotalSum);

        listView = (ListView) view.findViewById(R.id.weeklyListView);
        listView.setAdapter(myAdapter);

        ImageView lenz = (ImageView) view.findViewById(R.id.lenz);
        lenz.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.lenz));

        final EditText myYearSearch = (EditText) view.findViewById(R.id.myYearSearch);
        /*myYearSearch.setFocusable(false);

        implementYearMonthFromFragment1();
        myYearSearch.setOnClickListener(new EditText.OnClickListener() {
            @Override
            public void onClick(View view) {
                final List<String> ListItems = new ArrayList<>();

                for(int i = 0; i < implementedYear.size(); i++)
                {
                    for(int j = 0; j <implementedMonth.size(); j++)
                    {
                        Log.d("년월" , implementedYear.get(i) +"년 "+implementedMonth.get(j)+"월");
                        ListItems.add(implementedYear.get(i) +"년 "+implementedMonth.get(j)+"월" );
                    }
                }

                final CharSequence[] items =  ListItems.toArray(new String[ ListItems.size()]);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("카테고리");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int pos) {
                        myYearSearch.setText(items[pos].toString());
                    }
                });
                builder.show();
            }
        });*/

        myYearSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable editable) {
                String filterText = editable.toString() ;
                /*if (filterText.length() > 0)
                {
                    Log.d("필터?" , filterText);
                    listView.setFilterText(filterText) ;
                }
                else
                {
                    listView.clearTextFilter() ;
                }*/

                ((DailyListViewAdapter) listView.getAdapter()).getFilter().filter(filterText);
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int after) {

            }
        });

        pref1 =this.getActivity().getSharedPreferences("pref2", MODE_PRIVATE);
        if(pref1 != null) {
            numberOfItems = pref1.getInt(numberKey, 0);
            Log.d("몇개니", Integer.toString(numberOfItems));
            setItemArray(numberOfItems);

            update();

            myAdapter.notifyDataSetChanged();
        }



        return view;
    }


}
