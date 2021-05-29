package com.example.blogapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PostActivity extends AppCompatActivity {

    private ImageButton imageButton;
    private EditText titleText,descText;
    private Button submitButton;
    private static final int GALLERY_CODE=2;
    private Uri uri= null;

    private ProgressDialog progressDialog;

    private StorageReference mRef;
    private DatabaseReference mDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        imageButton=findViewById(R.id.imageButtonId);
        titleText=findViewById(R.id.titleId);
        descText=findViewById(R.id.descId);
        submitButton=findViewById(R.id.submitBtn);

        progressDialog=new ProgressDialog(this);

        mRef= FirebaseStorage.getInstance().getReference();
        mDB= FirebaseDatabase.getInstance().getReference().child("Blog");

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent =new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_CODE);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }
        });
    }

    private void startPosting() {

        progressDialog.setMessage("Posting Blogs....");
        progressDialog.show();

        final String titleValue = titleText.getText().toString();
        final String descValue = descText.getText().toString();

        if(!TextUtils.isEmpty(titleValue) && !TextUtils.isEmpty(descValue) && uri != null) {
            StorageReference filePath= mRef.child("Blog Images").child(uri.getLastPathSegment());

            filePath.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful())
                    {
                        task.getResult().getStorage().getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String downloadUrl= uri.toString();

                                        DatabaseReference newPost=mDB.push();
                                        newPost.child("title").setValue(titleValue);
                                        newPost.child("desc").setValue(descValue);
                                        newPost.child("image").setValue(downloadUrl);
                                        progressDialog.dismiss();
                                        startActivity(new Intent(PostActivity.this,MainActivity.class));
                                        finish();
                                    }
                                });
                    }
                    else
                    {

                    }
                }

            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== GALLERY_CODE && resultCode == RESULT_OK){
            uri =data.getData();

            imageButton.setImageURI(uri);
        }
    }
}