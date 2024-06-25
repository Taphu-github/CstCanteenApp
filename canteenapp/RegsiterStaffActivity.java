package com.example.canteenapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegsiterStaffActivity extends AppCompatActivity {
    private Button btn;
    private FirebaseAuth firebaseAuth;
    private EditText nameW, emailW, passW,num,userW;
    private ProgressDialog progressDialog;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference df;

    String name,con,mail,uName,uPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regsiter_staff);


        userW = findViewById(R.id.userId);
        nameW = findViewById(R.id.stdName);
        emailW = findViewById(R.id.eId);
        passW = findViewById(R.id.passId);
        num = findViewById(R.id.contactId);
        btn=findViewById(R.id.reg_Id);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpUser();
                progressDialog = new ProgressDialog(RegsiterStaffActivity.this);
                progressDialog.setMessage("Registering...");
                progressDialog.show();

            }
        });



    }


    private void signUpUser() {


        firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.createUserWithEmailAndPassword(emailW.getText().toString(), passW.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.d("work","here in onsuccess");

                        name=nameW.getText().toString();
                        con=num.getText().toString();
                        mail=emailW.getText().toString();
                        uName=userW.getText().toString();
                        uPass=passW.getText().toString();

                       // Toast.makeText(RegsiterStaffActivity.this, "Registered", Toast.LENGTH_SHORT).show();

                        df = FirebaseDatabase.getInstance().getReference().child("sUser").child(uName);

                        HashMap userInfo = new HashMap();
                        userInfo.put("emailId",mail);
                        userInfo.put("Name",name);
                        userInfo.put("Phone",con);
                        userInfo.put("UserName",uName);


                        df.updateChildren(userInfo).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                Toast.makeText(RegsiterStaffActivity.this,"Successful",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(),  MainActivity.class));

                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(RegsiterStaffActivity.this, e.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                });

    }

}