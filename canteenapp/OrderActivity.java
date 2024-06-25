package com.example.canteenapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import com.example.canteenapp.daily.DailyAdapter;
import com.example.canteenapp.odaily.OdailyModel;
import com.example.canteenapp.order.OrderAdapter;
import com.example.canteenapp.order.OrderModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class OrderActivity extends AppCompatActivity {

    RecyclerView recylerview;
    SearchView searchView;
    OrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        recylerview=findViewById(R.id.Drec);
        recylerview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<OrderModel> options =
                new FirebaseRecyclerOptions.Builder<OrderModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("order"), OrderModel.class)
                        .build();

        adapter=  new OrderAdapter(options);
        recylerview.setAdapter(adapter);



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
        FirebaseRecyclerOptions<OrderModel>options=new FirebaseRecyclerOptions.Builder<OrderModel>().
                setQuery(FirebaseDatabase.getInstance().getReference().child("order").
                        orderByChild("email").startAt(str).endAt(str+"~"), OrderModel.class).build();
        adapter= new OrderAdapter(options);
        adapter.startListening();
        recylerview.setAdapter(adapter);
    }
}