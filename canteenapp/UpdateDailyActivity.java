package com.example.canteenapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UpdateDailyActivity extends AppCompatActivity {
    EditText name,available,price,url;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_daily);

        name=findViewById(R.id.Bname);
        available=findViewById(R.id.Bavailable);
        price=findViewById(R.id.Bprice);
        url=findViewById(R.id.Burl);


        btn=findViewById(R.id.Bbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });
    }

    private void updateData(){

    }
}