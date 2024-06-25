package com.example.canteenapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity2 extends AppCompatActivity {
    private EditText userid, password;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
     String value;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        userid = findViewById(R.id.uadmin);
        password = findViewById(R.id.padmin);
        Button button = findViewById(R.id.alogid);
        TextView register = findViewById(R.id.register);

        button.setOnClickListener(view -> {
            progressDialog = new ProgressDialog(LoginActivity2.this);
            progressDialog.setMessage("Signing In...");
            progressDialog.show();
            login();
        });

        register.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), RegsiterStaffActivity.class)));

    }

    private void login() {


        String user = userid.getText().toString();
        String pass = password.getText().toString();
        if (TextUtils.isEmpty(user)) {
            Toast.makeText(this, "Missing Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Missing Password", Toast.LENGTH_SHORT).show();
            return;
        }


        FirebaseDatabase.getInstance().getReference().child("owner").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                value = dataSnapshot.getValue(String.class);
                Log.d("MISS",value);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(user, pass)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity2.this, "success", Toast.LENGTH_SHORT).show();

                        if(user.matches("admin@gmail.com")){
                            Intent intent=new Intent(LoginActivity2.this, CanteenOwnerActivity.class);
                            startActivity(intent);
                        }else{

                            startActivity(new Intent(LoginActivity2.this, MainActivity.class));
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity2.this, e.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

