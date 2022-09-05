package com.rahat.pharmsafebd.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rahat.pharmsafebd.R;
import com.rahat.pharmsafebd.services.model.AddToCart;
import com.rahat.pharmsafebd.services.model.MedicineModelClass;

import java.util.ArrayList;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<AddToCart> cartList;
    private DatabaseReference medicineRef = FirebaseDatabase.getInstance().getReference("medicines");
    private ArrayList<MedicineModelClass> medicineList = new ArrayList<>();

    public CartListAdapter(Context context, ArrayList<AddToCart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.adapter_cart_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AddToCart addToCart = cartList.get(position);

        medicineRef.orderByChild("id").equalTo(addToCart.getMedicineId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    MedicineModelClass medicineModelClass = dataSnapshot.getValue(MedicineModelClass.class);
                    holder.medicineName.setText(medicineModelClass.getMedicineName());
                    holder.power.setText(medicineModelClass.getPower());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.price.setText(addToCart.getPrice());
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView medicineName, power, price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            medicineName = itemView.findViewById(R.id.cart_list_medicine_name_id);
            power = itemView.findViewById(R.id.cart_list_power_id);
            price = itemView.findViewById(R.id.cart_price_id);
        }
    }
}
