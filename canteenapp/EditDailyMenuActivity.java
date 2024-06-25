package com.example.canteenapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class EditDailyMenuActivity extends AppCompatActivity {

    RecyclerView recylerview;
    DailyMenuRecyclerViewAdapter adapter;
    FloatingActionButton fab;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_daily_menu);

        recylerview=findViewById(R.id.rec);
        fab=findViewById(R.id.fab);
        recylerview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<DailyMenuModel> options =
                new FirebaseRecyclerOptions.Builder<DailyMenuModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("dailymenu"), DailyMenuModel.class)
                        .build();

        adapter=  new DailyMenuRecyclerViewAdapter(options);
        recylerview.setAdapter(adapter);
        /*
        * FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        //DataBase raf
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        myRef.keepSynced(true);*/
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EditDailyMenuActivity.this,addDailyActivity.class);
                startActivity(intent);
            }
        });

        searchView=findViewById(R.id.searchView);
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
        FirebaseRecyclerOptions<DailyMenuModel>options=new FirebaseRecyclerOptions.Builder<DailyMenuModel>().
                setQuery(FirebaseDatabase.getInstance().getReference().child("dailymenu").
                        orderByChild("name").startAt(str).endAt(str+"~"), DailyMenuModel.class).build();

        adapter= new DailyMenuRecyclerViewAdapter(options);
        adapter.startListening();
        recylerview.setAdapter(adapter);
    }
}