package com.example.gymtrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

public class splashscreen extends AppCompatActivity {
    private ProgressBar ProgressBar;
    private Timer timer;
    private int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        getSupportActionBar();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ProgressBar=findViewById(R.id.progressBar);
        timer=new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                if(i<100){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                    ProgressBar.setProgress(i);
                    i++;
                }else {
                    timer.cancel();
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null){
                        startActivity(new Intent(splashscreen.this,homepage.class));
                        finish();
                    }else {
                        startActivity(new Intent(splashscreen.this, Login.class));
                        finish();
                    }
                }
            }
        },0,10);

    }
}