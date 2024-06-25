package com.example.canteenapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import com.example.canteenapp.credit.CreditAdapter;
import com.example.canteenapp.credit.CreditModel;
import com.example.canteenapp.daily.DailyAdapter;
import com.example.canteenapp.odaily.OdailyModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class CreditActivity extends AppCompatActivity {
    RecyclerView recylerview;
    CreditAdapter adapter;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);

        recylerview=findViewById(R.id.Erec);
        recylerview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<CreditModel> options =
                new FirebaseRecyclerOptions.Builder<CreditModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("credit"), CreditModel.class)
                        .build();

        adapter=  new CreditAdapter(options);
        recylerview.setAdapter(adapter);



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
        FirebaseRecyclerOptions<CreditModel>options=new FirebaseRecyclerOptions.Builder<CreditModel>().
                setQuery(FirebaseDatabase.getInstance().getReference().child("credit").
                        orderByChild("email").startAt(str).endAt(str+"~"), CreditModel.class).build();
        adapter= new CreditAdapter(options);
        adapter.startListening();
        recylerview.setAdapter(adapter);
    }
}