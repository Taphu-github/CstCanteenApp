package com.example.canteenapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import com.example.canteenapp.odaily.OdailyAdapter;
import com.example.canteenapp.odaily.OdailyModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class OdailyActivity extends AppCompatActivity {

    RecyclerView recylerview;
    OdailyAdapter adapter;
    FloatingActionButton fab;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_odaily);
        recylerview=findViewById(R.id.Crec);
        fab=findViewById(R.id.Cfab);
        recylerview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<OdailyModel> options =
                new FirebaseRecyclerOptions.Builder<OdailyModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("dailymenu"), OdailyModel.class)
                        .build();

        adapter=  new OdailyAdapter(options);
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
                Intent intent=new Intent(OdailyActivity.this, addDailyActivity.class);
                startActivity(intent);
            }
        });

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

        adapter= new OdailyAdapter(options);
        adapter.startListening();
        recylerview.setAdapter(adapter);
    }
}