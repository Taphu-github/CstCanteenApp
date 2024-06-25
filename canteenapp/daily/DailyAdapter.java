package com.example.canteenapp.daily;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.canteenapp.CartActivity;
import com.example.canteenapp.LoginActivity2;
import com.example.canteenapp.R;
import com.example.canteenapp.odaily.OdailyModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;

public class DailyAdapter extends FirebaseRecyclerAdapter<OdailyModel,DailyAdapter.MyViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public static SQLiteDatabase mdata;

    public DailyAdapter(@NonNull FirebaseRecyclerOptions<OdailyModel> options, SQLiteDatabase mdata) {
        super(options);
        this.mdata=mdata;


    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull OdailyModel model) {
        holder.title.setText(model.getName());
        holder.price.setText(model.getPrice());
        holder.availability.setText(model.getAvailable());
        Glide.with(holder.img.getContext()).load(model.getPurl()).
                placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark).
                error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal).
                into(holder.img);



    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singledaily,parent,false);
        return new DailyAdapter.MyViewHolder(view);
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView title,price,availability;
        Button add;
        EditText imga;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img=itemView.findViewById(R.id.Cproductimg);
            title=itemView.findViewById(R.id.Cname);
            price=itemView.findViewById(R.id.Cprice);
            availability=itemView.findViewById(R.id.Cavailability);
            add=itemView.findViewById(R.id.del);
            imga=itemView.findViewById(R.id.inquan);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(FirebaseAuth.getInstance().getCurrentUser()==null){
                        Toast toast= new Toast(view.getContext());
                        toast.setText("login to add to cart");
                        toast.show();

                        Intent intent=new Intent(view.getContext(), LoginActivity2.class);
                        view.getContext().startActivity(intent);
                    }


                    String insertSQL = "INSERT INTO carts \n" +
                            "( itemname, price, quantity)\n" +
                            "VALUES \n" +
                            "(?, ?,?);";

                    //using the same method execsql for inserting values
                    //this time it has two parameters
                    //first is the sql string and second is the parameters that is to be binded with the query

                    mdata.execSQL(insertSQL, new String[]{ title.getText().toString(), price.getText().toString(),imga.getText().toString()});
                    String str="Successfully added"+title.getText().toString();
                    Toast toast= new Toast(view.getContext());
                    toast.setText(str);
                    toast.show();

                    Cursor cursorproduct = mdata.rawQuery("SELECT * FROM carts", null);
                    int total=0;
                    //if the cursor has some data
                    if (cursorproduct.moveToFirst()) {
                        //looping through all the records
                        do {
                            //pushing each record in the employee list
                            total=total+cursorproduct.getInt(2);

                        } while (cursorproduct.moveToNext());
                    }
                    //closing the cursor
                    cursorproduct.close();






                    Intent intent=new Intent(view.getContext(), CartActivity.class);
                    intent.putExtra("total",String.valueOf(total));
                    Log.d("tas",String.valueOf(total));
                    view.getContext().startActivity(intent);
                }
            });

        }
    }


}
