package com.example.canteenapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class addGroceryActivity extends AppCompatActivity {

    EditText name,available,price,url;
    AppCompatButton btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grocery);

        name=findViewById(R.id.Dname);
        available=findViewById(R.id.Davailable);
        price=findViewById(R.id.Dprice);
        url=findViewById(R.id.Durl);

        btn=findViewById(R.id.Dbutton);
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

        FirebaseDatabase.getInstance().getReference().child("grocery").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(addGroceryActivity.this,"succesful",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(addGroceryActivity.this,"failed",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}