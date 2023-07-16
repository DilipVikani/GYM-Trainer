package com.example.gymtrainer;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.gymtrainer.databinding.BackPressDialogBoxBinding;
import com.google.firebase.auth.FirebaseAuth;

public class homepage extends AppCompatActivity {
        private Button button;
        private Button button1;
        private Button button2;
        private Button button3;
        private Toolbar toolbar;
    private FirebaseAuth mAuth;

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        toolbar = findViewById(R.id.homeToolbar);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
        getSupportActionBar();


        Button btn1 = findViewById(R.id.button1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Welcome to Gym Exercise.",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(homepage.this, EXERCISE.class);
                startActivity(intent);
            }
        });

        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Welcome to Yoga.",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(homepage.this, YOGA.class);
                startActivity(intent);
            }
        });

        Button btn2 = findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Welcome to nutration.",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(homepage.this, foodActivity.class);
                startActivity(intent);
            }
        });
        Button btn3 = findViewById(R.id.button3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Welcome to weight loosing exersise.",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(homepage.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        final Dialog dialog = new Dialog(this);
        BackPressDialogBoxBinding binding = BackPressDialogBoxBinding.inflate(getLayoutInflater());
        dialog.setContentView(binding.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();
        Log.d("DialogShowTAG", String.valueOf(dialog));
        binding.dialogBtnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
                moveTaskToBack(true);
            }
        });
        binding.dialogBtnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_top_menu,menu);
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.id_privacy) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://iotexpert1.blogspot.com/2020/10/weight-loss-privacy-ploicy-page.html"));
            startActivity(intent);
            return true;
        }
        if (id == R.id.id_term) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://iotexpert1.blogspot.com/2020/10/weight-loss-terms-and-conditions-page.html"));
            startActivity(intent);
            return true;
        }
        if (id == R.id.rate) {
            try{
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=\"+getPackageName()")));

            }catch(Exception es){
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id =" + getPackageName())));
            }

            return true;
        }
        if (id == R.id.More) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=Leap+Fitness+Group&hl=en_US&gl=US"));
            startActivity(intent);
            return true;
        }
        if (id == R.id.Share) {
            Intent myIntent = new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            String sharebody = "This is the best for gym \n By this app you streach your body. \n This is health conscious app.\n" + "https://play.google.com/store/apps/details?id=com.example.gymtrainer&hl=en";
            String sharehub ="Gym Trainer";
            myIntent.putExtra(Intent.EXTRA_SUBJECT,sharehub);
            myIntent.putExtra(Intent.EXTRA_TEXT,sharebody);
            startActivity(Intent.createChooser(myIntent,"share using"));
            return true;
        }
        switch (item.getItemId()){
            case R.id.logOut:
                mAuth.signOut();
                startActivity(new Intent(homepage.this,Login.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}