package com.example.blogapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private ImageView detailsImage;
    private TextView detailsTitle, detailsDesc;

    private String postKey;

    private DatabaseReference mRef;

    private ArrayList<Model> arrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mRef= FirebaseDatabase.getInstance().getReference();

        postKey= getIntent().getExtras().getString("Position");


        detailsDesc=findViewById(R.id.detailsDesc);
        detailsTitle=findViewById(R.id.detailsTitle);
        detailsImage=findViewById(R.id.detailsImage);

        mRef.child("Blog").child(postKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String postTitle=(String) snapshot.child("title").getValue();
                String postDesc=(String) snapshot.child("desc").getValue();
                String postImage=(String) snapshot.child("image").getValue();

                detailsTitle.setText(postTitle);
                detailsDesc.setText(postDesc);

                Picasso.with(DetailsActivity.this).load(postImage).into(detailsImage);

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });

        /*String postTitle= arrayList.get(Integer.

                parseInt(position)).getTitle();
        String postDesc= arrayList.get(Integer.parseInt(position)).getDesc();
        String postImage= arrayList.get(Integer.parseInt(position)).getImage();

        detailsTitle.setText(postTitle);
        detailsDesc.setText(postDesc);

        Picasso.with(DetailsActivity.this).load(postImage).into(detailsImage);*/



    }
}