package com.example.canteenapp;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canteenapp.SQLitehelper.DataModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MyCartAdapter extends  RecyclerView.Adapter<MyCartAdapter.MyViewHolder> {
    ArrayList<DataModel> dataholder;
    Context context;
    SQLiteDatabase mdata;
    float total=0;


    public MyCartAdapter(Context context, ArrayList<DataModel> dataholder, SQLiteDatabase mdata) {
        this.dataholder = dataholder;
        this.context=context;
        this.mdata=mdata;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.singlecart,parent,false);

        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final DataModel product = dataholder.get(position);
        holder.itemname.setText(dataholder.get(position).getItemName());
        holder.quantity.setText(String.valueOf(dataholder.get(position).getQuantity()));
        holder.amount.setText("0");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String sql = "DELETE FROM carts WHERE id = ?";
                        mdata.execSQL(sql, new Integer[]{product.getId()});
                        Snackbar.make(view, "Deleted" + product.getItemName(), Snackbar.LENGTH_SHORT).show();
                        Toast.makeText(context, "Deleted successfully!", Toast.LENGTH_SHORT).show();

                        reloadDataFromDatabase(); //Reload List
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

    void reloadDataFromDatabase(){
        Cursor cursorproduct1 = mdata.rawQuery("SELECT * FROM carts", null);
        if (cursorproduct1.moveToFirst()) {
            dataholder.clear();
            do {
                dataholder.add(new DataModel(
                        cursorproduct1.getInt(0),
                        cursorproduct1.getString(1),
                        cursorproduct1.getFloat(2),
                        cursorproduct1.getInt(3)
                                )
                        );
            } while (cursorproduct1.moveToNext());
        }
        cursorproduct1.close();
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return dataholder.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder {

        private MyCartAdapter adapter;
        TextView itemname,quantity,amount;
        Button delete;
        Button add;
        Button minus;
        float grandtotal=0;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
           itemname=itemView.findViewById(R.id.itemname);
           quantity=itemView.findViewById(R.id.quantity);
           amount=itemView.findViewById(R.id.total);
           add=itemView.findViewById(R.id.add);
           add.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   int x=Integer.parseInt(quantity.getText().toString());
                   x++;
                   String y=String.valueOf(x);
                   quantity.setText(y);
                   String amounts=String.valueOf(x*dataholder.get(getAdapterPosition()).getPrice());
                   amount.setText(amounts);

               }
           });

           minus=itemView.findViewById(R.id.minus);

           minus.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   int x=Integer.parseInt(quantity.getText().toString());
                   if(x>0){
                       x--;
                       String y=String.valueOf(x);
                       quantity.setText(y);
                       String amounts=String.valueOf(x*dataholder.get(getAdapterPosition()).getPrice());
                       amount.setText(amounts);

                   }else{
                       Toast.makeText(context,"Cannot subtract more, its already 0",Toast.LENGTH_LONG).show();

                   }
               }
           });
           delete=itemView.findViewById(R.id.delete);
           float single=Float.parseFloat(amount.getText().toString());
           grandtotal=grandtotal+single;



        }

        public float totalAmount(){
            return grandtotal;
        }



    }



}
