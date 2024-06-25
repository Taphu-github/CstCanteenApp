package com.example.canteenapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canteenapp.cart.CartModel;
import com.example.canteenapp.cart.MyCartAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartActivity extends AppCompatActivity {

    ArrayList<CartModel> dataholder;
    RecyclerView recyclerView;
    SQLiteDatabase mDatabase;
    MyCartAdapter adapter;
    TextView txt;
    AppCompatButton btn,btn1;
    Post post;
    ArrayList<String>arrayList;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Intent intent=getIntent();
        String val=intent.getStringExtra("total");
        Log.d("val",String.valueOf(val));



        recyclerView = (RecyclerView) findViewById(R.id.rec);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = openOrCreateDatabase("cartdb", MODE_PRIVATE, null);

        btn=findViewById(R.id.button2);
        showEmployeesFromDatabase();
        btn1=findViewById(R.id.button);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                int tots=Total();
                builder.setTitle("Your Total Amount is Nu."+tots+". DO YOU WANT TO ORDER WITH CREDIT?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String email=FirebaseAuth.getInstance().getCurrentUser().getEmail();
                        Map<String,Object> map= new HashMap<>();
                        map.put("email", email);
                        map.put("items", Items());
                        map.put("amount",String.valueOf(Total()));
                        map.put("state","not paid");


                        Map<String,Object> maps= new HashMap<>();
                        map.put("email", email);
                        map.put("items", Items());
                        map.put("amount",String.valueOf(Total()));
                        map.put("state","not accepted");

                        FirebaseDatabase.getInstance().getReference().child("credit").push()
                                .setValue(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(CartActivity.this,"succesful",Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(CartActivity.this,"failed",Toast.LENGTH_SHORT).show();
                                    }
                                });


                        FirebaseDatabase.getInstance().getReference().child("order").push()
                                .setValue(maps)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(CartActivity.this,"successful",Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(CartActivity.this,"failed",Toast.LENGTH_SHORT).show();
                                    }
                                });


                        Snackbar.make(view, "ORDERED SUCCESSFULLY", Snackbar.LENGTH_SHORT).show();



                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();



            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                int tots=Total();
                builder.setTitle("Your Total Amount is Nu."+tots+". DO YOU WANT TO ORDER ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String email=FirebaseAuth.getInstance().getCurrentUser().getEmail();
                        int tot=Total();



                        Map<String,Object> map= new HashMap<>();
                        map.put("email", email);
                        map.put("items", Items());
                        map.put("amount",String.valueOf(Total()));
                        map.put("state","not accepted");

                        FirebaseDatabase.getInstance().getReference().child("order").push()
                                .setValue(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(CartActivity.this,"succesful",Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(CartActivity.this,"failed",Toast.LENGTH_SHORT).show();
                                    }
                                });
                        Snackbar.make(view, "ORDERED SUCCESSFULLY", Snackbar.LENGTH_SHORT).show();



                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });


    }
    public int Total(){
        Cursor cursorproduct = mDatabase.rawQuery("SELECT * FROM carts", null);
        int total=0;
        //if the cursor has some data
        if (cursorproduct.moveToFirst()) {
            //looping through all the records
            do {
                //pushing each record in the employee list
                total=total+(cursorproduct.getInt(2)*cursorproduct.getInt(3));

            } while (cursorproduct.moveToNext());
        }
        //closing the cursor
        cursorproduct.close();
        Log.d("TOTALS",String.valueOf(total));
        return total;
    }

    public String Items(){
        Cursor cursorproduct = mDatabase.rawQuery("SELECT * FROM carts", null);
        String total="";
        //if the cursor has some data
        if (cursorproduct.moveToFirst()) {
            //looping through all the records
            do {//+cursorproduct.getInt(2);
                //pushing each record in the employee list
                int i=cursorproduct.getInt(3);
                if(i!=0){
                    total=total.concat(cursorproduct.getString(1)+"*"+cursorproduct.getInt(3)+", ");
                    Log.d("VALUES",total);
                }

            } while (cursorproduct.moveToNext());
        }
        //closing the cursor
        cursorproduct.close();
        Log.d("total",String.valueOf(total));
        return total;
    }


    public ArrayList<HashMap<String, Object>> recArrayList(DataSnapshot snapshot){
        ArrayList<HashMap<String,Object>>list = new ArrayList<>();
        if(snapshot == null){
            return list;
        }

        Object fieldsObj= new Object();
        HashMap fldObj;

        for(DataSnapshot shot : snapshot.getChildren()){
            try{
                fldObj=(HashMap) shot.getValue(fieldsObj.getClass());

            }catch(Exception e){
                continue;
            }

            fldObj.put("recKeyID",shot.getKey());
            list.add(fldObj);
        }

        return list;
    }

    private void showEmployeesFromDatabase() {
        //we used rawQuery(sql, selectionargs) for fetching all the employees
        Cursor cursorproduct = mDatabase.rawQuery("SELECT * FROM carts", null);
        ArrayList<CartModel> dataholder = new ArrayList<>();

        //if the cursor has some data
        if (cursorproduct.moveToFirst()) {
            //looping through all the records
            do {
                //pushing each record in the employee list
                dataholder.add(new CartModel(
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

    static class Post{
        String Name,Phone,UserName,emailId,userType;

        public Post(){}

        public Post(String name, String phone, String userName, String emailId, String userType) {
            Name = name;
            Phone = phone;
            UserName = userName;
            this.emailId = emailId;
            this.userType = userType;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String phone) {
            Phone = phone;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String userName) {
            UserName = userName;
        }

        public String getEmailId() {
            return emailId;
        }

        public void setEmailId(String emailId) {
            this.emailId = emailId;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }
    }

}