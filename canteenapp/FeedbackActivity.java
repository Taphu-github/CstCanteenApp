package com.example.canteenapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class FeedbackActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    ImageView img1,img2;
    EditText feedbackView,nameView;
    Button sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);



        nameView = findViewById(R.id.nameId);
        feedbackView = findViewById(R.id.feedbackV);
        sendBtn = findViewById(R.id.feed_btn);

        img1=findViewById(R.id.menu);
        img1.setOnClickListener(view -> {
            PopupMenu popupMenu=new PopupMenu(FeedbackActivity.this,img1);
            popupMenu.getMenuInflater().inflate(R.menu.mainmenu,popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                Toast.makeText(FeedbackActivity.this,"You Clicked"+menuItem.getTitle(),Toast.LENGTH_LONG).show();

                if(menuItem.getItemId()==R.id.feed){
                    Intent ints=new Intent(FeedbackActivity.this, FeedbackActivity.class);
                    startActivity(ints);

                }else{
                    Intent ints=new Intent(FeedbackActivity.this, Customer.class);
                    startActivity(ints);
                }
                return true;
            });
            popupMenu.show();
        });
        img2=findViewById(R.id.profile);
        img2.setOnClickListener(view -> {
            Intent i=new Intent(FeedbackActivity.this,LoginActivity2.class);
            startActivity(i);
        });


        sendBtn.setOnClickListener(v -> {

            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("Feedback");

            String feedback = feedbackView.getText().toString();
            String name = nameView.getText().toString();

            DataMenuModel userFeed = new DataMenuModel(feedback,name);
            databaseReference.child(name).setValue(userFeed);

//
        });
    }
}