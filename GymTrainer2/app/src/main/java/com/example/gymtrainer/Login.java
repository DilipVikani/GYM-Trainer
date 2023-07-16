package com.example.gymtrainer;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {


    EditText vEmail,vpassword;
    Button vLoginbtn;
    TextView vCreatebtn;
    ProgressBar progressBar;


    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        vEmail = findViewById(R.id. email1);
        vpassword = findViewById(R.id. password1);
        progressBar = findViewById(R.id. progbar1);
        vLoginbtn = findViewById(R.id.Loginbtn1);
        vCreatebtn = findViewById(R.id.createtext1);


        fAuth = FirebaseAuth.getInstance();


        vCreatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
                finish();
            }
        });

        vLoginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = vEmail.getText().toString().trim();
                String password = vpassword.getText().toString().trim();

                if(TextUtils.isEmpty(email))
                {
                    vEmail.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(password))
                {
                    vpassword.setError("password is required");
                    return;
                }

                if (password.length() < 6)
                {
                    vpassword.setError("Password must be >=6 character");
                    return;
                }
                progressBar.setVisibility((View.VISIBLE));


                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(), "Log In Succesful", Toast.LENGTH_SHORT).show();
                            {
                                progressBar.setVisibility(View.GONE);
                                startActivity(new Intent(getApplicationContext(),homepage.class));
                                finish();
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "error" + task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                            finish();
                        }
                    }
                });
            }
        });

    }
}