package com.example.gymtrainer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.nio.file.FileStore;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    public static final String TAG="TAG";
    EditText vEmail,vPassword,vConfirmPassword;
    Button vregisterbtn;
    TextView vloginbtn;
    ProgressBar progbar;
    FirebaseFirestore fstore;
    String userid;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        vEmail=findViewById(R.id.email);
        vPassword=findViewById(R.id.password);
        vConfirmPassword=findViewById(R.id.confirmpassword);
        vregisterbtn=findViewById(R.id.registerbtn);
        vloginbtn=findViewById(R.id.createtext);
        progbar=findViewById(R.id.progbar);

        fAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();



        vloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });


        vregisterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = vEmail.getText().toString().trim();
                String password = vPassword.getText().toString().trim();
                String confpassword = vConfirmPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    vEmail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    vPassword.setError("Password is required");
                    return;
                }
                if (password.length()<6){
                    vPassword.setError("Password must be >=6 character");
                    return;
                }

                if (TextUtils.isEmpty(confpassword)) {
                    vConfirmPassword.setError("Con-Password is required");
                    return;
                }
                if (confpassword.length()<6){
                    vConfirmPassword.setError("Con-Password must be >=6 character");
                    return;
                }
                progbar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email, password ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser fUser = fAuth.getCurrentUser();
                            fUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(), "Register Successfully", Toast.LENGTH_SHORT).show();
                                    progbar.setVisibility(View.GONE);
                                    vEmail.clearComposingText();
                                    vPassword.clearComposingText();
                                    vConfirmPassword.clearComposingText();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG,"Onfailure:Email Not Sent" + e.getMessage());
                                }
                            });

                            Toast.makeText(getApplicationContext(), "User Created", Toast.LENGTH_SHORT).show();
                            userid = fAuth.getCurrentUser().getUid();
                            DocumentReference documentreference = fstore.collection("user").document(userid);
                            Map<String,Object> user = new HashMap<>();
                            user.put("E-mail",email);
                            documentreference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG,"onsuccess: user profile is created for" + userid);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG,"onFailure: " + e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(),Login.class));
                            finish();
                        }
                        else{

                            Toast.makeText(Register.this, "Error" + task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            progbar.setVisibility(View.GONE);
                            finish();
                        }
                    }
                });
            }
        });

    }
}