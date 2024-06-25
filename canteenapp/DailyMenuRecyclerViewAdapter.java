package com.example.canteenapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class DailyMenuRecyclerViewAdapter extends FirebaseRecyclerAdapter<DailyMenuModel,DailyMenuRecyclerViewAdapter.MyViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public DailyMenuRecyclerViewAdapter(@NonNull FirebaseRecyclerOptions<DailyMenuModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull DailyMenuModel model) {
       holder.title.setText(model.getName());
       holder.price.setText(model.getPrice());
       holder.availability.setText(model.getAvailable());

        Glide.with(holder.img.getContext()).load(model.getPurl()).
                placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark).
                error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal).
                into(holder.img);
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.updatedaily))
                        .setExpanded(true,1300)
                        .create();

                //dialogPlus.show();
                // FirebaseDatabase.getInstance().getReference().child("dailymenu").child(getRef(holder.getAdapterPosition()).getKey()).removeValue();
                View views=dialogPlus.getHolderView();
                EditText name=views.findViewById(R.id.Bname);
                EditText price=views.findViewById(R.id.Bprice);
                EditText avail=views.findViewById(R.id.Bavailable);
                EditText url=views.findViewById(R.id.Burl);

                Button bn=views.findViewById(R.id.Bbtn);

                name.setText(model.getName());
                price.setText(model.getPrice());
                avail.setText(model.getAvailable());
                url.setText(model.getPurl());

                dialogPlus.show();

                bn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> map= new HashMap<>();
                        map.put("name",name.getText().toString());
                        map.put("price",price.getText().toString());
                        map.put("available",avail.getText().toString());
                        map.put("purl",url.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("dailymenu")
                                .child(getRef(holder.getAdapterPosition()).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.title.getContext(),"Successfully updated",Toast.LENGTH_LONG).show();
                                        dialogPlus.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.title.getContext(),"Failed due to"+e.getMessage(),Toast.LENGTH_LONG).show();
                                        dialogPlus.dismiss();

                                    }
                                });
                    }
                });
            }});
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder=new AlertDialog.Builder(holder.title.getContext());
                builder.setTitle("Are you sure");
                builder.setMessage("Delete data can't be undo");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("dailymenu").
                                child(getRef(holder.getAdapterPosition()).getKey()).removeValue();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.title.getContext(),"Cancelled", Toast.LENGTH_LONG).show();

                    }
                });


            }});
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.dailyitem,parent,false);
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView title,price,availability;
        Button delete,update;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.productimg);
            title=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
            availability=itemView.findViewById(R.id.availability);
            update=itemView.findViewById(R.id.addtocart);
            delete=itemView.findViewById(R.id.button6);

        }
    }
}

/*
* FirebaseRecyclerOptions<Chat> options =
                new FirebaseRecyclerOptions.Builder<Chat>()
                        .setQuery(query, Chat.class)
                        .build();*/