package com.example.canteenapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canteenapp.daily.DailyAdapter;
import com.example.canteenapp.odaily.OdailyAdapter;
import com.example.canteenapp.odaily.OdailyModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class DailyActivity extends AppCompatActivity {

    RecyclerView recylerview;
    DailyAdapter adapter;
    SearchView searchView;
    SQLiteDatabase mdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);
        getIntent();


        mdata = openOrCreateDatabase("cartdb", MODE_PRIVATE, null);

        recylerview=findViewById(R.id.Crec);
        recylerview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<OdailyModel> options =
                new FirebaseRecyclerOptions.Builder<OdailyModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("dailymenu"), OdailyModel.class)
                        .build();

        adapter=  new DailyAdapter(options, mdata);
        recylerview.setAdapter(adapter);



        searchView=findViewById(R.id.CsearchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                txtSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                txtSearch(s);
                return false;
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.startListening();
    }

    private void txtSearch(String str){
        FirebaseRecyclerOptions<OdailyModel>options=new FirebaseRecyclerOptions.Builder<OdailyModel>().
                setQuery(FirebaseDatabase.getInstance().getReference().child("dailymenu").
                        orderByChild("name").startAt(str).endAt(str+"~"), OdailyModel.class).build();
        adapter= new DailyAdapter(options, mdata);
        adapter.startListening();
        recylerview.setAdapter(adapter);
    }
}