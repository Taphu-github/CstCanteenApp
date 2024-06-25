package com.example.canteenapp.order;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canteenapp.R;
import com.example.canteenapp.credit.CreditAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class OrderAdapter extends FirebaseRecyclerAdapter<OrderModel,OrderAdapter.MyViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public OrderAdapter(@NonNull FirebaseRecyclerOptions<OrderModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull OrderModel model) {
        holder.email.setText(model.getEmail());
        holder.items.setText(model.getItems());
        holder.amount.setText(model.getAmount());
        holder.state.setText(model.getState());
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> map= new HashMap<>();
                map.put("email",model.getEmail());
                map.put("items",model.getItems());
                map.put("amount",model.getAmount());
                map.put("state","Accepted");

                FirebaseDatabase.getInstance().getReference().child("order")
                        .child(getRef(holder.getAdapterPosition()).getKey()).updateChildren(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(holder.email.getContext(),"Successfully updated",Toast.LENGTH_LONG).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(holder.email.getContext(),"Failed due to"+e.getMessage(),Toast.LENGTH_LONG).show();


                            }
                        });
            }
        });

        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.email.getContext());
                builder.setTitle("Are you sure");
                builder.setMessage("Cancelled order will be deleted");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("order").
                                child(getRef(holder.getAdapterPosition()).getKey()).removeValue();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.email.getContext(),"Cancelled", Toast.LENGTH_LONG).show();

                    }
                }).create().show();
            }
        });

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singleorder,parent,false);
        return new OrderAdapter.MyViewHolder(view);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView amount,email,items,state;
        Button accept,cancel;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            amount=itemView.findViewById(R.id.oamount);
            email=itemView.findViewById(R.id.oemail);
            items=itemView.findViewById(R.id.oitems);
            state=itemView.findViewById(R.id.ostate);
            accept=itemView.findViewById(R.id.oaccept);
            cancel=itemView.findViewById(R.id.ocancel);

        }
    }
}
