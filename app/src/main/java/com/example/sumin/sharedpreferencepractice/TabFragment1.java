package com.example.sumin.sharedpreferencepractice;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by LG on 2017-06-12.
 */

public class TabFragment1 extends Fragment {

    //총 수입, 지출, 합계 계산 전용.

    long totalIncome=0, totalSpending = 0, totalSum = 0;
    TextView income, spending, sum;

    private void setIncomeAndSpending()
    {
        totalIncome = 0;
        totalSpending = 0;
        for(int i = 0; i < itemArray.size(); i++)
        {
            if(itemArray.get(i).isIncome)
            {
                totalIncome += Long.parseLong(itemArray.get(i).getDesc());
            }
            else
            {
                totalSpending += Long.parseLong(itemArray.get(i).getDesc());
            }
        }

        totalSum = totalIncome-totalSpending;

        income.setText(Long.toString(totalIncome));
        spending.setText(Long.toString(totalSpending));
        sum.setText(Long.toString(totalSum));

    }

    //총수입, 지출, 합계 계산 전용 끝.
    SharedPreferences pref1;

    //리스트뷰 아이템의 수, 그리고 그것을 가져올 키
    int numberOfItems;
    String numberKey = "numberKey";


    String key = "key";



    ListView listView;
    ListViewAdapter adapter;
    ArrayList<ListViewItem> itemArray = new ArrayList<ListViewItem>();


    //SharedPreferences 시작



    //이렇게 만들면 안된다. 더 잘 생각해봐.

    private void setItemArray(int numberOfItems)
    {
        SharedPreferences pref = this.getActivity().getSharedPreferences("pref1", MODE_PRIVATE);
        if(itemArray.size() == 0)
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

                    itemArray.add(temp);
                    temp.isIncome = true;
                }
                else
                {
                    adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.noun_11162_36dp), item.getTitle(), item.getDesc(),item.getDate(), myBundle);
                    ListViewItem temp = (ListViewItem) adapter.getItem(adapter.getCount()-1);

                    itemArray.add(temp);
                }
            }
        }
    }

    private void savePreferences(int index, ListViewItem value)
    {
        saveBooleanToPreferences(index, value.isIncome);
        saveTitleToPreferences(index, value.getTitle());
        saveDescToPreferences(index, value.getDesc());
        saveDateToPreferences(index, value.getDate());
        saveMyBundleToPreferences(index, value.myBundle);
    }

    private void saveAllItemsToPreferences(ArrayList<ListViewItem> values)
    {
        for(int i = 0 ; i <values.size(); i++)
        {
            savePreferences(i, values.get(i));
        }
    }

    private void clearAllItemsFromPreferences()
    {
        SharedPreferences pref = this.getActivity().getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }


    private void saveNumberOfItemToPreferences(int num){
        SharedPreferences pref = this.getActivity().getSharedPreferences("pref2", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(numberKey, num);
        editor.commit();
    }

    private void saveBooleanToPreferences(int index, boolean isIncome)
    {
        SharedPreferences pref = this.getActivity().getSharedPreferences("pref1", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        String titleKey = "boolean"+ Integer.toString(index);

        editor.putBoolean(titleKey, isIncome);
        editor.commit();
    }

    private void saveTitleToPreferences(int index, String title)
    {
        SharedPreferences pref = this.getActivity().getSharedPreferences("pref1", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        String titleKey = "title"+ Integer.toString(index);

        editor.putString(titleKey, title);
        editor.commit();
    }

    private void saveDescToPreferences(int index, String desc)
    {
        SharedPreferences pref = this.getActivity().getSharedPreferences("pref1", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        String descKey = "desc"+ Integer.toString(index);

        editor.putString(descKey, desc);
        editor.commit();
    }

    private void saveDateToPreferences(int index, String date)
    {
        SharedPreferences pref = this.getActivity().getSharedPreferences("pref1", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        String descKey = "date"+ Integer.toString(index);

        editor.putString(descKey, date);
        editor.commit();
    }

    private void saveMyBundleToPreferences(int index, MyBundle myBundle)
    {
        SharedPreferences pref = this.getActivity(). getSharedPreferences("pref1", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        String memoKey = "memo" + Integer.toString(index);
        String methodsOfPaymentKey = "methodsOfPayment" + Integer.toString(index);
        String categoryKey = "category" + Integer.toString(index);
        String timeKey = "time" +Integer.toString(index);
        String dayOfWeekKey = "dayOfWeek" + Integer.toString(index);
        String myYearKey = "myYear" +Integer.toString(index);
        String myMonthKey = "myMonth" + Integer.toString(index);
        String myDayKey = "myDay" + Integer.toString(index);

        editor.putString(memoKey, myBundle.getMemo());
        editor.putString(methodsOfPaymentKey, myBundle.getMethodsOfPayment());
        editor.putString(categoryKey, myBundle.getCategory());
        editor.putString(timeKey, myBundle.getTime());
        editor.putInt(dayOfWeekKey, myBundle.getDayOfWeek());
        editor.putInt(myYearKey, myBundle.getYear());
        editor.putInt(myMonthKey, myBundle.getMonth());
        Log.d("몇일 번들", myBundle.getDay()+" " +myDayKey);
        editor.putInt(myDayKey, myBundle.getDay());
        editor.commit();
    }


    private ArrayList<ListViewItem> getPreferences(String key)
    {
        pref1 = this.getActivity().getSharedPreferences("pref1", MODE_PRIVATE);
        String json = pref1.getString(key,null);
        Gson gson = new Gson();

        ArrayList<ListViewItem> items = gson.fromJson(json, new TypeToken<ArrayList<ListViewItem>>(){}.getType());

        if(items == null)
            items = new ArrayList<ListViewItem>();
        return items;
    }

    //SharedPreferences 끝끝



// 인텐트 받아오기
    int myDay;
    int myDayOfWeek;
    boolean isAdd;
    boolean isIncome;
    String whatDate;
    String whatTime="";
    String history, category;
    String methodsOfPayment;
    String memo;

    int myMonth=0, myYear=0;

    long money=0;

    long incomeNumber = 0, spendingNumber = 0;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK)
        {

            myDay = data.getIntExtra("myDay",0);
            Log.d("몇일", myDay+"");
            myMonth=data.getIntExtra("myMonth", 0);
            myYear =data.getIntExtra("myYear",0);

            myDayOfWeek = data.getIntExtra("myDayOfWeek",1);
            Log.d("요일", myDayOfWeek+"");
            isAdd = data.getBooleanExtra("isAdd", false);
            isIncome = data.getBooleanExtra("boolean_from_dialog",false);
            whatDate = data.getStringExtra("date_from_dialog");
            whatTime = data.getStringExtra("time_from_dialog");

            history = data.getStringExtra("history_from_dialog");
            category = data.getStringExtra("category_from_dialog");
            methodsOfPayment = data.getStringExtra("methodsOfPayment_from_dialog");
            memo = data.getStringExtra("memo_from_dialog");

            money = data.getLongExtra("money_from_dialog",0);

            Log.d("돈 뭐니", Long.toString(money));

            //이제 여기서부터 아이템 추가!!한다.
            if(isAdd)
            {
                MyBundle myBundle = new MyBundle();

                myBundle.setDay(myDay);
                myBundle.setYear(myYear);
                myBundle.setMonth(myMonth);
                myBundle.setDayOfWeek(myDayOfWeek);
                myBundle.setCategory(category);
                myBundle.setMemo(memo);
                myBundle.setMethodsOfPayment(methodsOfPayment);
                myBundle.setTime(whatTime);
                if(isIncome)
                {
                    adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.noun_11161_36dp),history, Long.toString(money) ,whatDate, myBundle);

                    ListViewItem temp = (ListViewItem) adapter.getItem(adapter.getCount()-1);
                    temp.isIncome = true;
                    Log.d("악", temp.myYear +"");

                }
                else
                {
                    adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.noun_11162_36dp),history, Long.toString(money) ,whatDate, myBundle);
                    ListViewItem temp = (ListViewItem) adapter.getItem(adapter.getCount()-1);
                    Log.d("악", temp.myYear +"");
                }

                //어댑터에 아이템을 추가하고 난 후, itemArray에도 해당 아이템을 추가해준다.
                itemArray.add( (ListViewItem) adapter.getItem(adapter.getCount()-1) );

                savePreferences(itemArray.size()-1, itemArray.get(itemArray.size()-1));
                //savePreferences에 업데이트한다.
                //Log.d("업데이트", itemArray.get(0).getDesc());
                //savePreferences(key,itemArray);
                //Log.d("업데이트", "응?");

                saveNumberOfItemToPreferences(itemArray.size());

                ListViewItem item = (ListViewItem) adapter.getItem(adapter.getCount()-1);

                if(incomeNumber !=0)
                {
                    if(item.isIncome && !item.getDesc().equals(""))
                    {
                        incomeNumber += Long.parseLong(item.getDesc());
                        // income.setText(Long.toString(incomeNumber));
                    }
                }
                else
                {
                    if(item.isIncome && !item.getDesc().equals(""))
                    {
                        incomeNumber = Long.parseLong(item.getDesc());
                        //  income.setText(Long.toString(incomeNumber));
                    }
                }

                if(spendingNumber !=0)
                {
                    if(!item.isIncome && !item.getDesc().equals(""))
                    {
                        spendingNumber += Long.parseLong(item.getDesc());
                        //  spending.setText(Long.toString(spendingNumber));
                    }
                }
                else
                {
                    if(!item.isIncome && !item.getDesc().equals(""))
                    {
                        spendingNumber = Long.parseLong(item.getDesc());
                        //  spending.setText(Long.toString(spendingNumber));
                    }
                }

                ///*
                //sorting 시작



                Comparator<ListViewItem> yearDesc = new Comparator<ListViewItem>(){

                    private final Collator collator = Collator.getInstance();

                    @Override
                    public int compare(ListViewItem listViewItem, ListViewItem t1) {

                        //솔팅!!
                       return (t1.getDate() + " " +t1.getTime()  ).compareTo(listViewItem.getDate() + " " +listViewItem.getTime());

                        //return t1.myYear-listViewItem.myYear;
                    }
                };

                Collections.sort(itemArray,yearDesc);
                Collections.sort(adapter.getItemList(),yearDesc);

                clearAllItemsFromPreferences();
                saveAllItemsToPreferences(itemArray);

                //adapter.updateItemList(itemArray);
                //*/
                // sorting 끝
                Log.d("바꿨니", "응");
                adapter.notifyDataSetChanged();

            }
            //여기서 부터 수정!! 한다
            else
            {
                //이거 고쳐야 한다.
                int checked = data.getIntExtra("checked",-1);

                final ListViewItem item =(ListViewItem) adapter.getItem(checked);

                MyBundle myBundle = new MyBundle();
                Log.d("요일", myDayOfWeek+" 수정중");
                myBundle.setDay(myDay);
                myBundle.setDayOfWeek(myDayOfWeek);
                myBundle.setYear(myYear);
                myBundle.setMonth(myMonth);
                myBundle.setCategory(category);
                myBundle.setMemo(memo);
                myBundle.setMethodsOfPayment(methodsOfPayment);
                myBundle.setTime(whatTime);

                item.setDate(whatDate);
                item.setMyBundle(myBundle);
                item.setHistory(history);
                item.setDesc(Long.toString(money));

                if(isIncome)
                {
                    item.setIcon(ContextCompat.getDrawable(getActivity(),R.drawable.noun_11161_36dp));

                    item.isIncome = true;
                }
                else
                {
                    item.setIcon(ContextCompat.getDrawable(getActivity(),R.drawable.noun_11162_36dp));

                    item.isIncome = false;
                }


                //end SetDialog

                //item.setDesc(Integer.toString(checked+1) + "번째 아이템 수정");

                item.isSelected = false;

                savePreferences(checked, item);

                ///*
                //sorting 시작



                Comparator<ListViewItem> yearDesc = new Comparator<ListViewItem>(){

                    private final Collator collator = Collator.getInstance();

                    @Override
                    public int compare(ListViewItem listViewItem, ListViewItem t1) {

                        //솔팅!!
                        return (t1.getDate() + " " +t1.getTime()  ).compareTo(listViewItem.getDate() + " " +listViewItem.getTime());

                        //return t1.myYear-listViewItem.myYear;
                    }
                };

                Collections.sort(itemArray,yearDesc);
                Collections.sort(adapter.getItemList(),yearDesc);
                //adapter.updateItemList(itemArray);
                //*/
                // sorting 끝

                saveNumberOfItemToPreferences(itemArray.size());
                clearAllItemsFromPreferences();
                saveAllItemsToPreferences(itemArray);

                adapter.notifyDataSetChanged();
            }

            setIncomeAndSpending();
            Log.d("뭐 받았니2222", whatDate +" " + history + " " + category + " " + methodsOfPayment + " " + memo + " " + money);
        }
    }
// 인텐트 받아오기 끝

    //광고배너로 쓸 핸들러 재료.
    int[] adColor = {Color.parseColor("#00DDAA"), Color.parseColor("#FFAABB"), Color.parseColor("#FFAA00"), Color.parseColor("#AA00BB") };
    BackgroundThread backgroundThread;
    private final MyHandler mHandler = new MyHandler(this);


   int[] adDrawable = {R.drawable.ad1, R.drawable.ad2, R.drawable.ad3, R.drawable.ad4};

    TextView myText;
    ImageView adImage;
    LinearLayout background;
    private boolean myTextOn = true;
    //광고 배너로 쓸 핸들러 재료 끝.


    //핸들러 및 쓰레드 구현 시작.
    private void handleMessage(Message msg)
    {


        Random random = new Random();

        int index = random.nextInt(4);

        Log.d("메시지 뭐니",  index+ " " +Integer.toString(adDrawable[index]));

        if(adImage!= null && getActivity() != null && this != null)
        {
            Log.d("메시지 뭐니",  "!!!!!!!!!!!!!!"+ index+ " " +Integer.toString(adDrawable[index]));
            adImage.setImageDrawable(ContextCompat.getDrawable(getActivity(),adDrawable[index]));
        }

        //background.setBackgroundColor(adColor[index]);

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
            backgroundThread = new TabFragment1.BackgroundThread();
            backgroundThread.setRunning(true);
            backgroundThread.start();
        }
        else
        {
            backgroundThread.setRunning(false);
            backgroundThread = null;
            backgroundThread = new TabFragment1.BackgroundThread();
            backgroundThread.setRunning(true);
            backgroundThread.start();
        }
        //Log.d("스레드", "돌고있당");
        //Toast.makeText(getActivity(), "onStart()", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();

        //Toast.makeText(getActivity(), "onResume()", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPause() {
        super.onPause();

        //Toast.makeText(getActivity(), "onPause()", Toast.LENGTH_LONG).show();
    }



    @Override
    public void onStop() {
        super.onStop();

        if(backgroundThread!=null)
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

        private final WeakReference<TabFragment1> mActivity;

        public MyHandler(TabFragment1 activity)
        {
            mActivity = new WeakReference<TabFragment1>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            TabFragment1 activity = mActivity.get();
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
                if(getActivity() == null || this ==null)
                {
                    running = false;
                    break;
                }
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


    //여기터 실질적인 프래그먼트의 뷰들 관리.
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_1 , container, false);

        income = (TextView) view.findViewById(R.id.totalIncome);
        spending = (TextView) view.findViewById(R.id.totalSpending);
        sum = (TextView) view.findViewById(R.id.totalSum);

        //핸들러 재료 설정

        adImage = (ImageView) view.findViewById(R.id.adImage);

        background = (LinearLayout) view.findViewById(R.id.advertisement);


        backgroundThread = new BackgroundThread();
        backgroundThread.setRunning(true);
        backgroundThread.start();
        //핸들러 재료 설정 끝

        adapter = new ListViewAdapter();

        //리스트뷰 참조 밑 Adapter 달기
        listView = (ListView) view.findViewById(R.id.exampleListView);
        listView.setAdapter(adapter);


        //SharedPreferences에서 리스트뷰 받아와서 살림 차리는 것.
        pref1 =this.getActivity().getSharedPreferences("pref2", MODE_PRIVATE);
        if(pref1 != null)
        {
            numberOfItems = pref1.getInt(numberKey,0);
            Log.d("몇개니", Integer.toString(numberOfItems));
            setItemArray(numberOfItems);
            setIncomeAndSpending();
        }

        /*if(pref1 != null)
            itemArray = getPreferences(key);

        if(itemArray.size() >0)
        {
            for(int i = 0; i < itemArray.size(); i++)
            {
                adapter.addItem(itemArray.get(i).getIcon(),itemArray.get(i).getTitle(),itemArray.get(i).getDesc(),itemArray.get(i).getDate());
            }
            adapter.notifyDataSetChanged();
        }*/
        //리스트 뷰에서 받아오기 끝


        Log.d("사이즈 뭐니", Integer.toString(adapter.getCount()));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position);

                int checked = position;

                String titleStr = item.getTitle();
                String descStr = item.getDesc();
                Drawable iconDrawable = item.getIcon();
                Log.d("클릭 했니", "응");
                for(int i = 0; i < itemArray.size(); i++)
                {
                    if(itemArray.get(i) == item)
                    {
                        Log.d("if문 들어갔니", Integer.toString(i));
                        itemArray.get(i).isSelected = true;
                    }
                    else
                        itemArray.get(i).isSelected = false;
                }

                //이 뒤에 수정.

                Intent intent = new Intent(getActivity(), ItemAddDialogActivity.class);

                //수정 인텐트
                boolean temp = false;
                intent.putExtra("isAdd", temp);
                intent.putExtra("modifyIsIncome", item.isIncome);
                intent.putExtra("modifyWhatDate", item.getDate());
                intent.putExtra("modifyWhatTime", item.getTime());
                intent.putExtra("modifyHistory", item.getTitle());
                intent.putExtra("modifyMoney", Long.parseLong(item.getDesc()));
                intent.putExtra("modifyCategory", item.category);
                intent.putExtra("modifyMethod", item.methodsOfPayment);
                intent.putExtra("modifyMemo", item.memo);
                Log.d("인텐트 보냈니", "응");
                intent.putExtra("checked", checked);

                startActivityForResult(intent, 1);
                getActivity().overridePendingTransition(R.anim.appear_from_center, R.anim.no_move);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final ListViewItem item = (ListViewItem) adapterView.getItemAtPosition(i);
                int temp = -1;
                for(int k = 0; k < itemArray.size(); k++)
                {
                    if(itemArray.get(k) == item)
                    {
                        Log.d("if문 들어갔니", Integer.toString(i));
                        itemArray.get(k).isSelected = true;
                        temp = k;
                    }
                    else
                        itemArray.get(i).isSelected = false;
                }

                final int checked = temp;

                // 이 뒤에 삭제.

                AlertDialog.Builder alertDlg = new AlertDialog.Builder(view.getContext());

                // '예' 버튼이 클릭되면
                alertDlg.setPositiveButton( "예", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick( DialogInterface dialog, int which )
                    {
                        long tempNum;
                        if(!item.getDesc().equals(""))
                            tempNum = Long.parseLong(item.getDesc());
                        else
                            tempNum=0;
                        if(item.isIncome)
                        {
                            incomeNumber -= tempNum;
                        }
                        else
                        {
                            spendingNumber -= tempNum;
                        }
                        //income.setText(Long.toString(incomeNumber));
                        //spending.setText(Long.toString(spendingNumber));
                        adapter.removeItem(checked);
                        itemArray.remove(checked);

                        saveNumberOfItemToPreferences(itemArray.size());
                        clearAllItemsFromPreferences();
                        saveAllItemsToPreferences(itemArray);

                        adapter.notifyDataSetChanged();
                        setIncomeAndSpending();
                        dialog.dismiss();  // AlertDialog를 닫는다.
                    }
                });

                // '아니오' 버튼이 클릭되면
                alertDlg.setNegativeButton( "아니오", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick( DialogInterface dialog, int which ) {
                        dialog.dismiss();  // AlertDialog를 닫는다.
                    }
                });


                alertDlg.setMessage( "삭제하시겠습니까?" );
                alertDlg.show();

                return true;
            }
        });


        FloatingActionButton addButton1 = (FloatingActionButton) view.findViewById(R.id.fab);
        addButton1.setOnClickListener(new FloatingActionButton.OnClickListener(){
            @Override
            public void onClick(View view) {
                //더하기!!
                int count;
                count = adapter.getCount();

                Intent intent = new Intent(getActivity(), ItemAddDialogActivity.class);

                boolean temp = true;
                intent.putExtra("isAdd", temp);
                Log.d("인텐트 보냈니", "응");
                startActivityForResult(intent, 1);
                getActivity().overridePendingTransition(R.anim.diagonal_left_up, R.anim.no_move);
            }
        });



        return view;
    }
}