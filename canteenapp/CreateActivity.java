package com.example.canteenapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canteenapp.SQLitehelper.DataModel;

import java.util.ArrayList;
import java.util.List;

public class CreateActivity extends AppCompatActivity  {
    ArrayList<DataModel> dataholder;
    RecyclerView recyclerView;
    SQLiteDatabase mDatabase;
    MyCartAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        recyclerView = (RecyclerView) findViewById(R.id.rec);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = openOrCreateDatabase("cartdb", MODE_PRIVATE, null);

        showEmployeesFromDatabase();




    }

    private void showEmployeesFromDatabase() {
        //we used rawQuery(sql, selectionargs) for fetching all the employees
        Cursor cursorproduct = mDatabase.rawQuery("SELECT * FROM carts", null);
        ArrayList<DataModel> dataholder= new ArrayList<>();

        //if the cursor has some data
        if (cursorproduct.moveToFirst()) {
            //looping through all the records
            do {
                //pushing each record in the employee list
                dataholder.add(new DataModel(
                        cursorproduct.getInt(0),
                        cursorproduct.getString(1),
                        cursorproduct.getFloat(2),
                        cursorproduct.getInt(3)
                ));
            } while (cursorproduct.moveToNext());
        }
        if (dataholder.isEmpty()) {
            Toast.makeText(this, "No items Found in database", Toast.LENGTH_SHORT).show();
        }
        //closing the cursor
        cursorproduct.close();

        //creating the adapter object
        adapter = new MyCartAdapter(this, dataholder, mDatabase);

        //adding the adapter to listview
        recyclerView.setAdapter(adapter);

        adapter.reloadDataFromDatabase();  //this method is in prdouctadapter
    }


}