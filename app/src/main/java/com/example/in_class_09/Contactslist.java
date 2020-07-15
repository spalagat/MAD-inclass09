package com.example.in_class_09;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Contactslist extends AppCompatActivity {
    Button addButton;
    ImageView logout;
    ListView listView;
    FirebaseAuth auth;
    DatabaseReference myRef;
    FirebaseDatabase database;
    ArrayList<contact> contactList;
    ContactAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactslist);

        addButton =findViewById(R.id.button4);
        Log.d("here1","1");

        setTitle("Contacts");

        auth = FirebaseAuth.getInstance();
        contactList = new ArrayList <>();

        logout = findViewById(R.id.addButton1);
        listView = findViewById(R.id.listView);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(auth.getCurrentUser().getUid());
        Log.d("authid",auth.getCurrentUser().getUid());

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("here2","2");
                Intent i=new  Intent(getBaseContext(),CreateContact.class);
                startActivity(i);
            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                contactList.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    contact contacts = new contact();
                    contacts.setId(postSnapshot.child("id").getValue().toString());
                    contacts.setFname(postSnapshot.child("fname").getValue().toString());
                    contacts.setEmail(postSnapshot.child("email").getValue().toString());
                    contacts.setPhone(postSnapshot.child("phone").getValue().toString());
                    contacts.setUrl(postSnapshot.child("url").getValue().toString());
                    contactList.add(contacts);

                }
                if(contactList.isEmpty()){
                    Toast.makeText(Contactslist.this, "Empty", Toast.LENGTH_SHORT).show();
                }
                else{

                    adapter = new ContactAdapter(Contactslist.this, R.layout.item,contactList);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView <?> parent, View view, int position, long id) {
                myRef.child(contactList.get(position).id).removeValue();
                adapter.notifyDataSetChanged();
                return false;
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected()){
                    auth.signOut();
                Intent i = new Intent(Contactslist.this, MainActivity.class);
                startActivity(i);
                finish();
            }
                else {
                    Toast.makeText(Contactslist.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ) {
            return false;
        }
        return true;
    }
}

