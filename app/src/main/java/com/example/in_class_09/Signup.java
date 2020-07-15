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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
    Button save,cancel;
    EditText fname,lname,email,password,pwdre;
    FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("MyUsers");
    String email_value,pass_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setTitle("SignUp");
        email=findViewById(R.id.editText3);
        email_value=email.getText().toString();
        pwdre =findViewById(R.id.editText5);


        fname=findViewById(R.id.editText);
        lname=findViewById(R.id.editText2);

        password=findViewById(R.id.editText4);
        pass_value=password.getText().toString();
        mAuth=FirebaseAuth.getInstance();
        save=findViewById(R.id.button);
        cancel=findViewById(R.id.button2);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main_pass = password.getText().toString();
                String confirm_pass = pwdre.getText().toString();
                String emailid = email.getText().toString();
                email_value=email.getText().toString();
                pass_value=password.getText().toString();
                if (TextUtils.isEmpty(main_pass)) {
                    Toast.makeText(Signup.this, "password should not be empty", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(confirm_pass)) {
                    Toast.makeText(Signup.this, "password should not be empty", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(emailid)) {
                    Toast.makeText(Signup.this, "Email should not be empty", Toast.LENGTH_SHORT).show();
                } else if (!(main_pass.equals(confirm_pass))) {

                    Toast.makeText(Signup.this, "Password and Confirm Password should be same", Toast.LENGTH_SHORT).show();

                }else
                {
                    registerUser();
                    Toast.makeText(Signup.this, "User Created", Toast.LENGTH_SHORT).show();
                }

            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getBaseContext(),MainActivity.class);
                startActivity(i);
            }
        });

    }

    public void registerUser(){
        Log.d("demo","inside");
        mAuth.createUserWithEmailAndPassword(email_value,pass_value).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                Log.d("demo","success");
                    FirebaseUser fuser = mAuth.getCurrentUser();
                    User u = new User();
                    u.setfName(fname.getText().toString());
                    u.setlName(lname.getText().toString());
                    String key = myRef.push().getKey();
                    u.setId(key);
                    myRef.child(u.getId()).setValue(u);
                    Log.d("in user","create");

                    Intent intent=new Intent(getBaseContext(),MainActivity.class);
                    startActivity(intent);

                }

                else {
                    Toast.makeText(Signup.this, "Authentication failed." + task.getException(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
