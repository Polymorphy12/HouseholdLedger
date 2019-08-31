package com.example.sumin.sharedpreferencepractice;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;

    boolean isIncome;
    String whatDate;
    String history, category;
    String methodsOfPayment;
    String memo;
    long money=0;

    public MainActivity() {
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Toast.makeText(this, Integer.toString(requestCode) , Toast.LENGTH_LONG).show();
        //Log.d("뭐 받았니", Integer.toString(RESULT_OK));
        if(resultCode == RESULT_OK)
        {
            isIncome = data.getBooleanExtra("boolean_from_dialog",false);
            whatDate = data.getStringExtra("date_from_dialog");
            history = data.getStringExtra("history_from_dialog");
            category = data.getStringExtra("category_from_dialog");
            methodsOfPayment = data.getStringExtra("methodsOfPayment_from_dialog");
            memo = data.getStringExtra("memo_from_dialog");
            money = data.getLongExtra("money_from_dialog",0);

            Log.d("뭐 받았니", whatDate +" " + history + " " + category + " " + methodsOfPayment + " " + memo + " " + money);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("전체"));
        tabLayout.addTab(tabLayout.newTab().setText("일별"));
        tabLayout.addTab(tabLayout.newTab().setText("월별"));
        tabLayout.addTab(tabLayout.newTab().setText("미니게임"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.pager);

        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                pagerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
        });

    }
}
