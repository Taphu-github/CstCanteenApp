package com.example.canteenapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.MyViewHolder> {

    Context context;
    ArrayList<DataMenuModel> list;

    public FeedbackAdapter(Context context, ArrayList<DataMenuModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.feedbacklist,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

               DataMenuModel user = list.get(position);
                holder.getFeeds.setText(user.getFeedback());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView getFeeds;

        public MyViewHolder(@NonNull View itemView){

            super(itemView);

            getFeeds = itemView.findViewById(R.id.tvfeed);
        }
    }
}
