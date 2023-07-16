package com.example.gymtrainer;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.database.annotations.Nullable;

import java.util.Locale;

public class ThirdActivity extends AppCompatActivity {

    String buttonvalue;
    Button startBtn;
    private CountDownTimer countDownTimer;
    TextView mtextview;
    TextToSpeech tx1;
    private boolean MTimeRunning;
    private long MTimeLeftmilisecond;


    @Override
    protected void onPause() {
        if(tx1 != null){
            tx1.stop();
            tx1.shutdown();
        }
        super.onPause();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        Intent intent=getIntent();
        buttonvalue=intent.getStringExtra("value");

        int intvalue=Integer.valueOf(buttonvalue);

        switch (intvalue){
            case 1:
                setContentView(R.layout.activity_act1);
                startBtn = findViewById(R.id.startbtn);
//                mtextview = findViewById(R.id.text8);
//                tx1 =new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
//                    @Override
//                    public void onInit(int status) {
//                        if(status != TextToSpeech.ERROR){
//                            tx1.setLanguage(Locale.UK);
//                        }
//                    }
//                });
//                startBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        String toSpeak=mtextview.getText().toString();
//                        Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
//                        tx1.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null);
//                    }
//                });


                break;
            case 2:
                setContentView(R.layout.activity_act2);
                break;
            case 3:
                setContentView(R.layout.activity_act3);
                break;
            case 4:
                setContentView(R.layout.activity_act4);
                break;
            case 5:
                setContentView(R.layout.activity_act5);
                break;
            case 6:
                setContentView(R.layout.activity_act6);
                break;
            case 7:
                setContentView(R.layout.activity_act7);
                break;
            case 8:
                setContentView(R.layout.activity_act8);
                break;
            case 9:
                setContentView(R.layout.activity_act9);
                break;
            case 10:
                setContentView(R.layout.activity_act10);
                break;
            case 11:
                setContentView(R.layout.activity_act11);
                break;
            case 12:
                setContentView(R.layout.activity_act12);
                break;
            case 13:
                setContentView(R.layout.activity_act13);
                break;
            case 14:
                setContentView(R.layout.activity_act14);
                break;
            case 15:
                setContentView(R.layout.activity_act15);
                break;
        }

        startBtn=findViewById(R.id.startbtn);
        mtextview=findViewById(R.id.time);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MTimeRunning)
                {
                    stoptimer();
                }
                else
                {
                    startTimer();
                }
            }
        });


    }

    private void stoptimer()
    {
        countDownTimer.cancel();
        MTimeRunning=false;
        startBtn.setText("START");
    }
    private void startTimer()
    {
        final CharSequence value1=mtextview.getText();
        String num1=value1.toString();
        String num2=num1.substring(0,2);
        String num3=num1.substring(3,5);

        final int number=Integer.valueOf(num2)*60+Integer.valueOf(num3);
        MTimeLeftmilisecond=number*1000;

        countDownTimer=new CountDownTimer(MTimeLeftmilisecond,1000) {
            @Override
            public void onTick(long l) {

                MTimeLeftmilisecond=l;
                updateTimer();

            }

            @Override
            public void onFinish() {

                int newvalue=Integer.valueOf(buttonvalue)+1;
                if (newvalue<=7){
                    Intent intent=new Intent(ThirdActivity.this,ThirdActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("value",String.valueOf(newvalue));
                    startActivity(intent);
                }
                else {

                    newvalue=1;
                    Intent intent=new Intent(ThirdActivity.this,ThirdActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("value",String.valueOf(newvalue));
                    startActivity(intent);
                }

            }
        }.start();
        startBtn.setText("PAUSE");
        MTimeRunning=true;

    }
    private void updateTimer(){
        int minutes=(int) MTimeLeftmilisecond/60000;
        int seconds=(int) MTimeLeftmilisecond%60000/1000;

        String timeLeftText="";
        if (minutes<10)
            timeLeftText="0";
        timeLeftText=timeLeftText+minutes+":";
        if (seconds<10)
            timeLeftText="0";
        timeLeftText+=seconds;
        mtextview.setText(timeLeftText);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}