package com.example.canteenapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.SearchView;

import com.example.canteenapp.daily.DailyAdapter;
import com.example.canteenapp.grocery.GroceryAdapter;
import com.example.canteenapp.odaily.OdailyModel;
import com.example.canteenapp.ogrocery.OgroceryModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class GroceryActivity extends AppCompatActivity {
    RecyclerView recylerview;
    GroceryAdapter adapter;
    SearchView searchView;
    SQLiteDatabase mdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);


        mdata = openOrCreateDatabase("cartdb", MODE_PRIVATE, null);

        recylerview=findViewById(R.id.Erec);
        recylerview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<OgroceryModel> option =
                new FirebaseRecyclerOptions.Builder<OgroceryModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("grocery"), OgroceryModel.class)
                        .build();

        adapter=  new GroceryAdapter(option, mdata);
        adapter.startListening();
        recylerview.setAdapter(adapter);

        /*
        * FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        //DataBase raf
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        myRef.keepSynced(true);*/


        searchView=findViewById(R.id.EsearchView);
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
        adapter= new GroceryAdapter(options, mdata);
        adapter.startListening();
        recylerview.setAdapter(adapter);
    }
}