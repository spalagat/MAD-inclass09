package com.example.in_class_09;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class CreateContact extends AppCompatActivity {

    ImageView photo;
    TextView name, email, phone;
    Button submit;
    int REQUEST_CAMERA = 1;
    contact con = new contact();

    FirebaseAuth auth;

    FirebaseStorage storageUpload = FirebaseStorage.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    int imagevalue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        setTitle("Create New Contact");
        photo = findViewById(R.id.image);
        name = findViewById(R.id.editText6);
        email = findViewById(R.id.editText7);
        phone = findViewById(R.id.editText8);
        submit = findViewById(R.id.button3);

        auth = FirebaseAuth.getInstance();
        myRef = database.getReference(auth.getCurrentUser().getUid());

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(name.getText().toString())){
                    Toast.makeText(CreateContact.this, "Enter Name", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(email.getText().toString())){
                    Toast.makeText(CreateContact.this, "Enter email", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(phone.getText().toString())){
                    Toast.makeText(CreateContact.this, "phone", Toast.LENGTH_SHORT).show();
                }
                else{

                    if(imagevalue == 0) {
                        photo.setImageResource(R.drawable.default_photo);
                    }

                    con.fname = name.getText().toString();
                    con.email = email.getText().toString();
                    con.phone = phone.getText().toString();
                    con.id=myRef.push().getKey();

                    final String[] url = {""};
                    photo.setDrawingCacheEnabled(true);
                    photo.buildDrawingCache();
                    Bitmap bitmap = photo.getDrawingCache();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] data = baos.toByteArray();

                    String path =  name.getText().toString()+".png";
                    StorageReference firememeref = storageUpload.getReference(path);

                    StorageMetadata metadata = new StorageMetadata.Builder()
                            .setCustomMetadata("text",name.getText().toString())
                            .build();
                    UploadTask uploadTask = firememeref.putBytes(data,metadata);

                    uploadTask.addOnSuccessListener(CreateContact.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String op;
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            Log.d("image","success");
                            op = downloadUrl.toString();
                            con.url = op;
                            myRef.child(con.getId()).setValue(con);
                            finish();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            int errorCode = ((StorageException) exception).getErrorCode();
                            String errorMessage = exception.getMessage();
                            Toast.makeText(CreateContact.this, "Image upload Failed",
                                    Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }
        });


    }

    private void SelectImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == REQUEST_CAMERA) {

                Bundle bundle = data.getExtras();
                final Bitmap bmp = (Bitmap) bundle.get("data");
                photo.setVisibility(View.VISIBLE);
                photo.setImageBitmap(bmp);
                imagevalue = 1;
            }
        }
    }
}
