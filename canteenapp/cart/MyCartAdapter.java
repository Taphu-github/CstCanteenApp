package com.example.canteenapp.cart;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canteenapp.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyCartAdapter extends  RecyclerView.Adapter<MyCartAdapter.MyViewHolder> {
    ArrayList<CartModel> dataholder;
    Context context;
    SQLiteDatabase mdata;




    public MyCartAdapter(Context context, ArrayList<CartModel> dataholder, SQLiteDatabase mdata ) {
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

        final CartModel product = dataholder.get(position);
        holder.itemname.setText(dataholder.get(position).getItemName());
        holder.quantity.setText(String.valueOf(dataholder.get(position).getQuantity()));
        int quan=dataholder.get(position).getQuantity();
        int pri=(int)dataholder.get(position).getPrice();
        int amo=quan*pri;
        holder.amount.setText(String.valueOf(amo));
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

    public void reloadDataFromDatabase(){
        Cursor cursorproduct1 = mdata.rawQuery("SELECT * FROM carts", null);
        if (cursorproduct1.moveToFirst()) {
            dataholder.clear();
            do {
                dataholder.add(new CartModel(
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

        TextView itemname,quantity,amount;
        Button delete;
        EditText edt;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            itemname=itemView.findViewById(R.id.itemname);
            quantity=itemView.findViewById(R.id.quantity);
            amount=itemView.findViewById(R.id.total);
            delete=itemView.findViewById(R.id.delete);
            edt=itemView.findViewById(R.id.inquan);

            /* add=itemView.findViewById(R.id.add);
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
                        //int single=Integer.parseInt(amount.getText().toString());
                       // total=total+single;



                        // write all the data entered by the user in SharedPreference and apply
                        myedit.putInt("tot", Integer.parseInt(amounts));
                        myedit.apply();





                    }else{
                        Toast.makeText(context,"Cannot subtract more, its already 0",Toast.LENGTH_LONG).show();


                    }
                }
            });
            delete=itemView.findViewById(R.id.delete);
            */



        }





    }



}
