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
import com.example.canteenapp.ogrocery.OgroceryAdapter;
import com.example.canteenapp.ogrocery.OgroceryModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class OgroceryActivity extends AppCompatActivity {

    RecyclerView recylerview;
    OgroceryAdapter adapter;
    FloatingActionButton fab;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogrocery);
        recylerview=findViewById(R.id.Drec);
        fab=findViewById(R.id.Dfab);
        recylerview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<OgroceryModel> options =
                new FirebaseRecyclerOptions.Builder<OgroceryModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("grocery"), OgroceryModel.class)
                        .build();

        adapter=  new OgroceryAdapter(options);
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
                Intent intent=new Intent(OgroceryActivity.this, addGroceryActivity.class);
                startActivity(intent);
            }
        });

        searchView=findViewById(R.id.DsearchView);
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
        FirebaseRecyclerOptions<OgroceryModel>options=new FirebaseRecyclerOptions.Builder<OgroceryModel>().
                setQuery(FirebaseDatabase.getInstance().getReference().child("grocery").
                        orderByChild("name").startAt(str).endAt(str+"~"), OgroceryModel.class).build();

        adapter= new OgroceryAdapter(options);
        adapter.startListening();
        recylerview.setAdapter(adapter);
    }
}