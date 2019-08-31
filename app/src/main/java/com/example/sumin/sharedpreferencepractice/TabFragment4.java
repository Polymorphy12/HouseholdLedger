package com.example.sumin.sharedpreferencepractice;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.Random;

import static java.lang.Thread.sleep;

/**
 * Created by LG on 2017-06-14.
 */

public class TabFragment4 extends Fragment {


    LinearLayout[] patch = new LinearLayout[12];

    //gray, red, green
    String[] patchColor = new String[12];

    Button startButton;
    TextView lifeRemainText;
    TextView yourScoreText;
    TextView gameStatusText;

    boolean playing = false;

    String gameStatus = " ";

    int lifeRemain=10,yourScore=0, touchedGreenCount=0;


    //광고배너로 쓸 핸들러 재료.
    TabFragment4.BackgroundThread backgroundThread;
    private final TabFragment4.MyHandler mHandler = new TabFragment4.MyHandler(this);

    TextView myText;
    LinearLayout background;
    private boolean myTextOn = true;
    //광고 배너로 쓸 핸들러 재료 끝.


    //핸들러 및 쓰레드 구현 시작.
    private void handleMessage()
    {
        //Log.d("메시지 뭐니", msg.toString());
        yourScoreText.setText("점 수 = " + Integer.toString(yourScore));
        lifeRemainText.setText("남은 라이프 수 = " + Integer.toString(lifeRemain));


        if(lifeRemain <=0)
        {
            playing = false;
            gameStatusText.setText("Game Over");

            Animation rotate = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_rotate);
            gameStatusText.startAnimation(rotate);

        }
        else
        {

        }

        for(int i = 0;i < 12; i++)
        {
            if(patchColor[i].equals("green"))
            {
                patch[i].setBackgroundColor(Color.parseColor("#00FF00"));
            }
            else if(patchColor[i].equals("gray"))
            {
                patch[i].setBackgroundColor(Color.parseColor("#AAAAAA"));
            }
            else if(patchColor[i].equals("red"))
            {
                patch[i].setBackgroundColor(Color.parseColor("#FF0000"));
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        //Toast.makeText(getActivity(), "onStart()", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();

        boolean retry = true;
        if(backgroundThread!=null)
            backgroundThread.setRunning(false);
        else return;

        while(retry)
        {
            try{
                backgroundThread.join();
                retry = false;
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        //Toast.makeText(getActivity(), "onStop()", Toast.LENGTH_LONG).show();
    }

    //핸들러 클래스.
    private static class MyHandler extends Handler {

        private final WeakReference<TabFragment4> mActivity;

        public MyHandler(TabFragment4 activity)
        {
            mActivity = new WeakReference<TabFragment4>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            TabFragment4 activity = mActivity.get();
            if(activity != null)
            {
                activity.handleMessage();
            }
        }
    }
    //핸들러 클래스 끝.


    //스레드 클래스.

    public class ChangePatchColor extends Thread{

        boolean running = false;
        int index;

        ChangePatchColor(int i)
        {
            index = i;
        }

        void setRunning(boolean b)
        {
            running = b;
        }

        @Override
        public void run() {
            while(running)
            {
                if(lifeRemain <=0)
                {
                    Log.d("게임", "패치 컬러 쓰레드 종료");
                    break;
                }
                int sleepTime1=80, sleepTime2 = 350, sleepTime3 = 200, sleepTime4 = 300;

                if(touchedGreenCount <8) {
                    sleepTime1 = 1200;
                    sleepTime2 = 350; sleepTime3 = 200; sleepTime4 = 300;
                }
                else if(touchedGreenCount <15) {
                    sleepTime1 = 1000;
                    sleepTime2 = 350; sleepTime3 = 200; sleepTime4 = 300;
                }
                else if(touchedGreenCount <30) {
                    sleepTime1 = 700;
                    sleepTime2 = 350; sleepTime3 = 200; sleepTime4 = 300;
                }
                else if(touchedGreenCount < 60)
                {
                    Log.d("게임", "세미 헬 " +", touchedGreenCount: " +touchedGreenCount);
                    sleepTime1 = 550;
                    sleepTime2 = 350; sleepTime3 = 200; sleepTime4 = 300;
                }
                else if(touchedGreenCount <110)
                {
                    Log.d("게임", "헬 " +", touchedGreenCount: " +touchedGreenCount + " 스코어 : " +yourScore);
                    sleepTime1 = 450;
                    sleepTime2 = 400;
                    sleepTime3 = 400;
                    sleepTime4 = 1000;
                }
                else if(touchedGreenCount < 160)
                {
                    Log.d("게임", "극헬 " +", touchedGreenCount: " +touchedGreenCount + " 스코어 : " +yourScore);
                    sleepTime1=290; sleepTime2 = 350; sleepTime3 = 200; sleepTime4 = 300;
                }

                else if(touchedGreenCount <400)
                {
                    sleepTime1 = 150;
                }

                try{
                    sleep(sleepTime1);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                Random random = new Random();
                int probability = random.nextInt(115);
                if(probability <=25 && patchColor[index].equals("gray"))
                {
                    Log.d("게임", "확률 : " +probability + ", touchedGreenCount: " +touchedGreenCount);
                    patchColor[index] = "green";

                    //여기에다 구간, 회색체크, 구간, 회색체크, 구간, 회색체크, 붉은색, 라이프 까기.


                    try{
                        sleep(sleepTime2);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    if(patchColor[index].equals("grey")) continue;

                    try{
                        sleep(sleepTime2);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }

                    if(patchColor[index].equals("grey")) continue;

                    try{
                        sleep(sleepTime2);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    if(patchColor[index].equals("grey")) continue;

                    try{
                        sleep(sleepTime2);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }

                    if(patchColor[index].equals("grey")) continue;

                    try{
                        sleep(sleepTime2);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    if(patchColor[index].equals("green"))
                    {
                        Log.d("게임", "터치 안했는데 레드됐넹");
                        patchColor[index] = "red";
                        lifeRemain -=1;
                        if(lifeRemain<=0)
                            lifeRemain =0;
                        try{
                            sleep(sleepTime3);
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
                else if(patchColor[index].equals("red"))
                {
                    patchColor[index] = "gray";
                    try{
                        sleep(sleepTime4);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }



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
                    sleep(10);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                mHandler.sendMessage(mHandler.obtainMessage());
                if(lifeRemain <=0)
                {
                    Log.d("게임", "백그라운드 쓰레드 종료");
                    break;
                }
            }
        }
    }

    //스레드 클래스 끝.

    //핸들러 및 쓰레드 구현 끝.


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_fragment_4, container, false);

        lifeRemainText = (TextView) view.findViewById(R.id.lifeRemain);
        yourScoreText = (TextView) view.findViewById(R.id.yourScore);
        gameStatusText = (TextView) view.findViewById(R.id.gameStatus);


        //메뉴얼 버튼 활용 시작
        Button manualButton = (Button) view.findViewById(R.id.manual);

        manualButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ab = new AlertDialog.Builder(getContext());
                ab.setMessage("1. 회색 패치들이 수시로 녹색으로 변한다.\n\n2. 회색 패치를 클릭하면 라이프가 차감된다. \n\n3. 녹색 패치를 터치하면 30점의 스코어를 얻는다.\n\n4. 시간 이내로 녹색패치를 클릭하지 못하면 라이프가 차감된다. \n\n5. 시간이 지날수록 난이도가 올라간다.");
                ab.setPositiveButton("ok", null);
                ab.show();

            }
        });

        //시작버튼 활용 시작
        startButton = (Button) view.findViewById(R.id.start);

        startButton.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {

                if(playing)  return;
                else playing = true;

                lifeRemain =10;
                yourScore = 0;
                touchedGreenCount = 0;

                gameStatusText.setText("회색 패치를 터치할 시 라이프 감소");

                for(int i = 0; i < 12; i++)
                {
                    Log.d("게임", "셋팅중 -> touchedGreenCount = " + touchedGreenCount);
                    patchColor[i] = "gray";
                }

                backgroundThread = new TabFragment4.BackgroundThread();
                backgroundThread.setRunning(true);
                backgroundThread.start();

                for(int i = 0 ; i < 12; i++)
                {

                    ChangePatchColor thread = new ChangePatchColor(i);
                    thread.setRunning(true);
                    thread.start();

                    //Log.d("트라이 들어왔니",gameStatusText.getText().toString());
                    try{
                        sleep(50);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }

                }
                gameStatus = "";
            }
        });

        //시작버튼 활용 끝


        //패치들 활용 시작

        for(int i = 0; i < 12; i++)
        {
            patchColor[i] = "gray";
        }

        patch[0] = (LinearLayout) view.findViewById(R.id.patch1);
        patch[1] = (LinearLayout) view.findViewById(R.id.patch2);
        patch[2] = (LinearLayout) view.findViewById(R.id.patch3);
        patch[3] = (LinearLayout) view.findViewById(R.id.patch4);
        patch[4] = (LinearLayout) view.findViewById(R.id.patch5);
        patch[5] = (LinearLayout) view.findViewById(R.id.patch6);
        patch[6] = (LinearLayout) view.findViewById(R.id.patch7);
        patch[7] = (LinearLayout) view.findViewById(R.id.patch8);
        patch[8] = (LinearLayout) view.findViewById(R.id.patch9);
        patch[9] = (LinearLayout) view.findViewById(R.id.patch10);
        patch[10] = (LinearLayout) view.findViewById(R.id.patch11);
        patch[11] = (LinearLayout) view.findViewById(R.id.patch12);

        for(int i = 0; i<12; i++)
        {
            final int temp = i;
            patch[i].setOnClickListener(new LinearLayout.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(lifeRemain <=0) return;
                    if(patchColor[temp].equals("green"))
                    {
                        Animation squash = AnimationUtils.loadAnimation(getActivity(), R.anim.squash);
                        patch[temp].startAnimation(squash);
                        touchedGreenCount++;
                        patchColor[temp] = "gray";
                        yourScore+= 30;
                    }
                    else if(patchColor[temp].equals("gray"))
                    {
                        Log.d("게임", "터치해서 레드됐넹");
                        patchColor[temp] = "red";
                        Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                        patch[temp].startAnimation(shake);
                        lifeRemain-=1;
                        if(lifeRemain<=0)
                            lifeRemain =0;
                    }
                }
            });
        }



        //패치들 활용 끝끝


        return view;    }
}
