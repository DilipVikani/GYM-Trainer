package com.example.gymtrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class shoulderssixth extends AppCompatActivity {

    String buttonvalue;
    Button startbtn;
    private CountDownTimer countDownTimer;
    TextView vtextview;
    private boolean vtimeRunning;
    private long VTimeLeftinmills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoulderssixth);


        Intent intent = getIntent();
        buttonvalue = intent.getStringExtra("value");


        int intvalue = Integer.valueOf(buttonvalue);

        switch (intvalue) {

            case 1:
                setContentView(R.layout.activity_p16);
                break;
            case 2:
                setContentView(R.layout.activity_p17);
                break;
            case 3:
                setContentView(R.layout.activity_p18);
                break;
        }
        startbtn = findViewById(R.id.statbutton);
        vtextview = findViewById(R.id.time);


        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(vtimeRunning)
                {
                    stoptimer();

                }

                else {

                    startTimer();
                }
            }
        });

    }



    private  void  stoptimer()

    {

        countDownTimer.cancel();
        vtimeRunning=false;
        startbtn.setText("START");

    }


    private void startTimer()

    {
        final CharSequence value1 = vtextview.getText();
        String num1 = value1.toString();
        String num2 = num1.substring(0,2);
        String num3 = num1.substring(3,5);


        final  int number = Integer.valueOf(num2)* 60+Integer.valueOf(num3);
        VTimeLeftinmills = number*1000;


        countDownTimer = new CountDownTimer(VTimeLeftinmills,1000) {
            @Override
            public void onTick(long milliUnitFinished) {

                VTimeLeftinmills = milliUnitFinished;
                updateTimer();

            }

            @Override
            public void onFinish() {
                int newvalue = Integer.valueOf(buttonvalue)+1;
                if (newvalue<=7){
                    Intent intent = new Intent(shoulderssixth.this,shoulderssixth.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("value",String.valueOf(newvalue));
                    startActivity(intent);
                }

                else {

                    newvalue = 1;
                    Intent intent = new Intent (shoulderssixth.this,shoulderssixth.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("value",String.valueOf(newvalue));
                    startActivity(intent);


                }

            }
        }.start();
        startbtn.setText("Pause");
        vtimeRunning=true;
    }

    private  void updateTimer()
    {
        int minutes = (int) VTimeLeftinmills/60000;
        int seconds = (int) VTimeLeftinmills%60000 /1000;

        String timeLeftText="";
        if(minutes<10)
            timeLeftText="0";
        timeLeftText = timeLeftText+minutes+":";
        if(seconds<10)
            timeLeftText+="0";
        timeLeftText+=seconds;
        vtextview.setText(timeLeftText);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}