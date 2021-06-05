package com.example.blogapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends FirebaseRecyclerAdapter<Model ,Adapter.MyViewHolder> {

    Context context;
    ArrayList<Model> modelList=new ArrayList<>();

   /* public Adapter(@NonNull FirebaseRecyclerOptions<Model> options) {
        super(options);
    }
*/
    public Adapter(@NonNull FirebaseRecyclerOptions<Model> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull  Adapter.MyViewHolder holder, int position, @NonNull   Model model) {

        final String postKey=getRef(position).getKey();

        holder.titleText.setText(model.getTitle());
        holder.descText.setText(model.getDesc());

        Picasso.with(holder.imageView.getContext()).load(model.getImage()).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("Position",postKey);
                context.startActivity(intent);
            }
        });

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item,parent,false);
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView titleText, descText;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.detailsImage);
            titleText=itemView.findViewById(R.id.detailsTitle);
            descText=itemView.findViewById(R.id.detailsDesc);

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   Intent intent=new Intent(context,DetailsActivity.class);


                }
            });*/

        }
    }
}
