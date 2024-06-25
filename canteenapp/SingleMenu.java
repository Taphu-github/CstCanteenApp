package com.example.canteenapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

public class SingleMenu extends AppCompatActivity {
    ImageView img,img1,img2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_menu);
        getIntent();

        img=findViewById(R.id.imageView);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intss=new Intent(SingleMenu.this, CanteenOwnerActivity.class);
                startActivity(intss);
            }
        });

        img1=findViewById(R.id.menu);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(SingleMenu.this,img1);
                popupMenu.getMenuInflater().inflate(R.menu.mainmenu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Toast.makeText(SingleMenu.this,"You Clicked"+menuItem.getTitle(),Toast.LENGTH_LONG).show();

                        if(menuItem.getItemId()==R.id.feed){
                            Intent ints=new Intent(SingleMenu.this, FeedbackActivity.class);
                            startActivity(ints);

                        }else{
                            Intent ints=new Intent(SingleMenu.this, Customer.class);
                            startActivity(ints);
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        img2=findViewById(R.id.profile);
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SingleMenu.this,LoginActivity2.class);
                startActivity(i);
            }
        });
    }
}