package com.example.canteenapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    TextView txt1,txt2;
    ImageView img1,img2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);


        txt1=findViewById(R.id.dailytext);
        txt2=findViewById(R.id.grocerytext);
        img2=findViewById(R.id.profile);

        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, DailyActivity.class);
                startActivity(intent);
            }
        });

        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, CanteenOwnerActivity.class);
                startActivity(intent);

            }
        });

        img1=findViewById(R.id.menu);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(MainActivity.this,img1);
                popupMenu.getMenuInflater().inflate(R.menu.mainmenu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Toast.makeText(MainActivity.this,"You Clicked"+menuItem.getTitle(),Toast.LENGTH_LONG).show();

                        if(menuItem.getItemId()==R.id.feed){
                            Intent ints=new Intent(MainActivity.this, FeedbackActivity.class);
                            startActivity(ints);

                        }else if(menuItem.getItemId()==R.id.customer){
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            if(user!=null){
                                Intent ints=new Intent(MainActivity.this,CartActivity.class);
                                startActivity(ints);

                            }else{
                                Toast.makeText(MainActivity.this,"login to see your cart",Toast.LENGTH_SHORT).show();

                                Intent intent=new Intent(MainActivity.this, LoginActivity2.class);
                                startActivity(intent);
                            }

                        }else{
                            FirebaseAuth.getInstance().signOut();
                            Intent i1 = new Intent(MainActivity.this, LoginActivity2.class);
                            startActivity(i1);
                            Toast.makeText(MainActivity.this, "Logout Successfully!", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }
}