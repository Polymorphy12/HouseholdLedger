package com.example.sumin.sharedpreferencepractice;


import android.app.Activity;
import android.app.DialogFragment;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by LG on 2017-06-20.
 */

public class ItemAddDialogActivity extends Activity  {

    private boolean isAdd = false;
    private boolean isIncome = false;
    private LinearLayout incomeButton, spendingButton;
    private TextView incomeButtonText, spendingButtonText;

    private EditText whatDate, whatTime;

    private EditText moneyText, history;

    private EditText category, methodsOfPayment;

    private EditText memo;

    private Button save, cancel;

    long money=0;

    int checked = -1;

    int myYear=0, myMonth=0, myDayOfMonth=0, myHour=0, myMinute=0, myDayOfWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.item_add_dialog_activity);

        Intent intent = getIntent();
        isAdd = intent.getBooleanExtra("isAdd", false);
        checked = intent.getIntExtra("checked",-1);

        /*
        if(isAdd)
            Log.d("인텐트 받았니" , "true" );
        else
            Log.d("인텐트 받았니" , "not true");
            */
        //onClick
        incomeButton = (LinearLayout) findViewById(R.id.incomeButton);
        incomeButtonText = (TextView) findViewById(R.id.incomeButtonText);

        incomeButton.setOnClickListener(new LinearLayout.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(!isIncome)
                {
                    incomeButton.setBackgroundColor(Color.parseColor("#4466FF"));
                    incomeButtonText.setTextColor(Color.parseColor("#FFFFFF"));

                    spendingButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    spendingButtonText.setTextColor(Color.parseColor("#000000"));
                    isIncome = true;
                }
            }
        });

        //onClick
        spendingButton = (LinearLayout) findViewById(R.id.spendingButton);
        spendingButtonText = (TextView) findViewById(R.id.spendingButtonText);

        spendingButton.setOnClickListener(new LinearLayout.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(isIncome)
                {
                    spendingButton.setBackgroundColor(Color.parseColor("#FF4455"));
                    spendingButtonText.setTextColor(Color.parseColor("#FFFFFF"));

                    incomeButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    incomeButtonText.setTextColor(Color.parseColor("#000000"));
                    isIncome = false;
                }
            }
        });


        Calendar c = Calendar.getInstance();
        whatDate = (EditText) findViewById(R.id.whatDate);
        if(isAdd)
        {

            myDayOfWeek = c.get(Calendar.DAY_OF_WEEK);

            myYear = c.get(Calendar.YEAR);
            myMonth = c.get(Calendar.MONTH)+1;
            Log.d("오늘 몇월", Integer.toString(myMonth) +"월");
            myDayOfMonth = c.get(Calendar.DAY_OF_MONTH);

            String tempDate="";
            if(myDayOfMonth<10 )
            {
                if(myMonth <10)
                {
                    whatDate.setText(c.get(Calendar.YEAR) + ".0" + myMonth + ".0" + c.get(Calendar.DAY_OF_MONTH));
                    tempDate = c.get(Calendar.YEAR) + "0" + myMonth + "0" + c.get(Calendar.DAY_OF_MONTH);
                }
                else
                {
                    whatDate.setText(c.get(Calendar.YEAR) + "." + myMonth + ".0" + c.get(Calendar.DAY_OF_MONTH));
                    tempDate = c.get(Calendar.YEAR) + myMonth + "0" + c.get(Calendar.DAY_OF_MONTH);
                }
            }
            else
            {
                if(myMonth <10)
                {
                    whatDate.setText(c.get(Calendar.YEAR) + ".0" + myMonth + "." + c.get(Calendar.DAY_OF_MONTH));
                    tempDate = c.get(Calendar.YEAR) + "0" + myMonth + c.get(Calendar.DAY_OF_MONTH);
                }
                else
                {
                    whatDate.setText(c.get(Calendar.YEAR) + "." + myMonth + "." + c.get(Calendar.DAY_OF_MONTH));
                    tempDate = c.get(Calendar.YEAR)+ "" + myMonth + c.get(Calendar.DAY_OF_MONTH);
                }
            }

            try{

                Calendar tempCalendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
                Date nDate = dateFormat.parse(tempDate);
                tempCalendar.setTime(nDate);
                myDayOfWeek = tempCalendar.get(Calendar.DAY_OF_WEEK);

            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
        }

        whatDate.setFocusable(false);


        whatDate.setOnClickListener(new EditText.OnClickListener(){
            @Override
            public void onClick(View v) {

                final Calendar now = Calendar.getInstance();

                DatePickerDialog dpd = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                        myYear = year;
                        myMonth = monthOfYear+1;
                        myDayOfMonth = dayOfMonth;

                        TextView editText = (TextView) findViewById(R.id.whatDate);

                        String tempDate = "";
                        if(myDayOfMonth<10 )
                        {
                            if(myMonth <10)
                            {
                                editText.setText(year + ".0" + myMonth + ".0" + dayOfMonth);
                                tempDate = year + "0" + myMonth + "0" + dayOfMonth;
                            }
                            else
                            {
                                editText.setText(year + "." + myMonth + ".0" + dayOfMonth);
                                tempDate = year + myMonth + "0" + dayOfMonth;
                            }
                        }
                        else
                        {
                            if(myMonth <10)
                            {
                                editText.setText(year + ".0" + myMonth + "." + dayOfMonth);
                                tempDate = year + "0" + myMonth + dayOfMonth;
                            }
                            else
                            {
                                editText.setText(year + "." + myMonth + "." + dayOfMonth);
                                tempDate = year+ "" + myMonth + dayOfMonth;
                            }
                        }

                        try{

                            Calendar tempCalendar = Calendar.getInstance();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
                            Date nDate = dateFormat.parse(tempDate);
                            tempCalendar.setTime(nDate);
                            myDayOfWeek = tempCalendar.get(Calendar.DAY_OF_WEEK);

                        }
                        catch (ParseException e)
                        {
                            e.printStackTrace();
                        }

                        Log.d("오늘 몇월", Integer.toString(myMonth) +"월");

                    }
                },  now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));

                dpd.setVersion(DatePickerDialog.Version.VERSION_2);

                dpd.show(getFragmentManager(), "date dialog");

            }
        });

        whatTime = (EditText) findViewById(R.id.whatTime);
        int minute =  c.get(Calendar.MINUTE), hour = c.get(Calendar.HOUR_OF_DAY);
        if(minute<10 )
        {
            if(hour <10)
            {
                whatTime.setText("0"+c.get(Calendar.HOUR_OF_DAY) + ":0" + c.get(Calendar.MINUTE));
            }
            else
                whatTime.setText(c.get(Calendar.HOUR_OF_DAY) + ":0" + c.get(Calendar.MINUTE));
        }
        else
        {
            if(hour <10)
                whatTime.setText("0"+c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));
            else
                whatTime.setText(c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));
        }
        whatTime.setFocusable(false);

        myHour = c.get(Calendar.HOUR_OF_DAY);
        myMinute = c.get(Calendar.MINUTE);


        whatTime.setOnClickListener(new EditText.OnClickListener(){
            @Override
            public void onClick(View v) {

                final Calendar now = Calendar.getInstance();

                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        new TimePickerDialog.OnTimeSetListener(){

                            @Override
                            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

                                TextView editText = (TextView) findViewById(R.id.whatTime);

                                if(minute<10 )
                                {
                                    if(hourOfDay <10)
                                    {
                                        editText.setText("0"+hourOfDay + ":0" + minute);
                                    }
                                    else
                                        editText.setText(hourOfDay + ":0" + minute);
                                }
                                else
                                {
                                    if(hourOfDay <10)
                                        editText.setText("0"+hourOfDay + ":" + minute);
                                    else
                                        editText.setText(hourOfDay + ":" + minute);
                                }

                                myHour = hourOfDay;
                                myMinute = minute;
                            }
                        },
                        now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true
                );

                tpd.setVersion(TimePickerDialog.Version.VERSION_2);

                tpd.show(getFragmentManager(), "time dialog");
            }
        });

        moneyText = (EditText) findViewById(R.id.money);
        history = (EditText) findViewById(R.id.history);

        category = (EditText) findViewById(R.id.category);
        category.setFocusable(false);

        category.setOnClickListener(new EditText.OnClickListener(){
            @Override
            public void onClick(View view) {

                final List<String> ListItems = new ArrayList<>();
                ListItems.add("없음");
                ListItems.add("식비");
                ListItems.add("문화생활비");
                ListItems.add("건강관리비");
                ListItems.add("의류미용비");
                ListItems.add("차량유지비");
                ListItems.add("주거생활비");
                ListItems.add("학비");
                ListItems.add("사회생활비");
                ListItems.add("유흥비");
                ListItems.add("금융보험비");
                ListItems.add("저축");
                ListItems.add("기타");

                final CharSequence[] items =  ListItems.toArray(new String[ ListItems.size()]);

                AlertDialog.Builder builder = new AlertDialog.Builder(ItemAddDialogActivity.this);
                builder.setTitle("카테고리");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int pos) {
                        category.setText(items[pos].toString());
                    }
                });
                builder.show();
            }
        });

        methodsOfPayment = (EditText) findViewById(R.id.methodsOfPayment);
        methodsOfPayment.setFocusable(false);

        methodsOfPayment.setOnClickListener(new EditText.OnClickListener(){
            @Override
            public void onClick(View view) {

                final List<String> ListItems = new ArrayList<>();
                ListItems.add("현금");
                ListItems.add("신용카드/체크카드");
                ListItems.add("계좌이체");


                final CharSequence[] items =  ListItems.toArray(new String[ ListItems.size()]);

                AlertDialog.Builder builder = new AlertDialog.Builder(ItemAddDialogActivity.this);
                builder.setTitle("결제수단");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int pos) {
                        methodsOfPayment.setText(items[pos].toString());
                    }
                });
                builder.show();
            }
        });


        memo = (EditText) findViewById(R.id.memo);

        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if(history.getText().toString().equals(""))
                {
                    Animation shake = AnimationUtils.loadAnimation(ItemAddDialogActivity.this, R.anim.shake);
                    history.startAnimation(shake);
                    Toast.makeText(ItemAddDialogActivity.this, "수입/지출 내역을 입력해주십시오.", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(category.getText().toString().equals(""))
                {
                    Animation shake = AnimationUtils.loadAnimation(ItemAddDialogActivity.this, R.anim.shake);
                    category.startAnimation(shake);
                    Toast.makeText(ItemAddDialogActivity.this, "카테고리를 입력해주십시오.", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(methodsOfPayment.getText().toString().equals(""))
                {
                    Animation shake = AnimationUtils.loadAnimation(ItemAddDialogActivity.this, R.anim.shake);
                    methodsOfPayment.startAnimation(shake);
                    Toast.makeText(ItemAddDialogActivity.this, "결제 수단을 입력해주십시오.", Toast.LENGTH_LONG).show();
                    return;
                }

                Log.d("리절트오케이뭐니", Integer.toString(1));
                Intent intent = new Intent();

                intent.putExtra("myDay", myDayOfMonth);
                intent.putExtra("myMonth", myMonth);
                intent.putExtra("myYear", myYear);
                intent.putExtra("myDayOfWeek", myDayOfWeek);
                intent.putExtra("checked", checked);
                intent.putExtra("isAdd", isAdd);
                intent.putExtra("memo_from_dialog", memo.getText().toString());
                intent.putExtra("methodsOfPayment_from_dialog", methodsOfPayment.getText().toString());
                intent.putExtra("category_from_dialog", category.getText().toString());
                intent.putExtra("history_from_dialog", history.getText().toString());
                intent.putExtra("date_from_dialog", whatDate.getText().toString());
                intent.putExtra("time_from_dialog",whatTime.getText().toString());
                intent.putExtra("boolean_from_dialog", isIncome);

                if(!moneyText.getText().toString().equals(""))
                    money = Long.parseLong(moneyText.getText().toString());

                intent.putExtra("money_from_dialog", money);


                if(isAdd)
                    Log.d("인텐트 다시 보냈니" , "true");
                else
                    Log.d("인텐트 다시 보냈니" , "not true");

                setResult(RESULT_OK, intent);
                finish();
            }
        });

        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(!isAdd)
        {
            isIncome = intent.getBooleanExtra("modifyIsIncome", false);

            if(isIncome)
            {
                incomeButton.setBackgroundColor(Color.parseColor("#4466FF"));
                incomeButtonText.setTextColor(Color.parseColor("#FFFFFF"));

                spendingButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
                spendingButtonText.setTextColor(Color.parseColor("#000000"));
            }
            else
            {
                spendingButton.setBackgroundColor(Color.parseColor("#FF4455"));
                spendingButtonText.setTextColor(Color.parseColor("#FFFFFF"));

                incomeButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
                incomeButtonText.setTextColor(Color.parseColor("#000000"));
            }

            whatDate.setText(intent.getStringExtra("modifyWhatDate"));

            try{

                Calendar tempCalendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);
                Date nDate = dateFormat.parse(whatDate.getText().toString());
                tempCalendar.setTime(nDate);
                myDayOfWeek = tempCalendar.get(Calendar.DAY_OF_WEEK);
                myYear = tempCalendar.get(Calendar.YEAR);
                myMonth = tempCalendar.get(Calendar.MONTH)+1;
                myDayOfMonth = tempCalendar.get(Calendar.DAY_OF_MONTH);
                Log.d("무슨요일", myDayOfWeek+"");

            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }


            whatTime.setText(intent.getStringExtra("modifyWhatTime"));

            long temp = intent.getLongExtra("modifyMoney",0);
            moneyText.setText(Long.toString(temp));
            history.setText(intent.getStringExtra("modifyHistory"));
            category.setText(intent.getStringExtra("modifyCategory"));
            methodsOfPayment.setText(intent.getStringExtra("modifyMethod"));
            memo.setText(intent.getStringExtra("modifyMemo"));
        }

    }


}
