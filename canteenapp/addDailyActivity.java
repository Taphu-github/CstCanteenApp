package com.example.canteenapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.canteenapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class addDailyActivity extends AppCompatActivity {

    EditText name,available,price,url;
    AppCompatButton btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_daily);

        name=findViewById(R.id.Aname);
        available=findViewById(R.id.Aavailable);
        price=findViewById(R.id.Aprice);
        url=findViewById(R.id.Aurl);

        btn=findViewById(R.id.button4);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
            }
        });
    }

    private void insertData(){
        Map<String,Object> map= new HashMap<>();
        map.put("name", name.getText().toString() );
        map.put("price", price.getText().toString());
        map.put("available", available.getText().toString());
        map.put("purl", url.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("dailymenu").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(addDailyActivity.this,"succesful",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(addDailyActivity.this,"failed",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}