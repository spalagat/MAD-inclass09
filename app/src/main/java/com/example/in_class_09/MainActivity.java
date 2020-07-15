package com.example.in_class_09;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
   Button login,signup;
    FirebaseAuth mAuth;
    EditText email;
    EditText password;
    String emailID,pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Login");
        login=findViewById(R.id.logBut);
        signup=findViewById(R.id.button2);
        mAuth = FirebaseAuth.getInstance();
       email = findViewById(R.id.email);
       password= findViewById(R.id.editText9);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailID = email.getText().toString();
                pwd=password.getText().toString();
                Log.d("email",emailID+"");
                if (TextUtils.isEmpty(emailID)) {
                    Toast.makeText(MainActivity.this, "email should not be empty", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(pwd)) {
                    Toast.makeText(MainActivity.this, "password should not be empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    checkUser();
                }

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new  Intent(MainActivity.this,Signup.class);
                startActivity(i);
            }
        });

    }

    public void checkUser()
    {
        mAuth.signInWithEmailAndPassword(emailID, pwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("demo", "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();

                    Intent i=new  Intent(MainActivity.this,Contactslist.class);
                    startActivity(i);
                } else {
                    Toast.makeText(MainActivity.this, "invalid user", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
