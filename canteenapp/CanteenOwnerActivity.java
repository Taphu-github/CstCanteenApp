package com.example.canteenapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CanteenOwnerActivity extends AppCompatActivity {
    Button seefeed,log,sign,btn1,btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_canteen_owner);
            seefeed = findViewById(R.id.feedBtn);
            log = findViewById(R.id.logId);
            sign = findViewById(R.id.signId);
            btn1=findViewById(R.id.btn_0);
            btn2=findViewById(R.id.btn_1);

            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(CanteenOwnerActivity.this,OrderActivity.class);
                    startActivity(intent);
                }
            });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CanteenOwnerActivity.this,CreditActivity.class);
                startActivity(intent);
            }
        });
            seefeed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(CanteenOwnerActivity.this,AddFeedbackList.class);
                    startActivity(i);
                }
            });

            log.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(CanteenOwnerActivity.this, LoginActivity2.class);
                    startActivity(i);
                }
            });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CanteenOwnerActivity.this,RegsiterStaffActivity.class);
                startActivity(i);
            }
        });
    }
}