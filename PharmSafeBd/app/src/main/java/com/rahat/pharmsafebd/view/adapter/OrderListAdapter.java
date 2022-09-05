package com.rahat.pharmsafebd.view.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.rahat.pharmsafebd.services.model.MedicineModelClass;
import com.rahat.pharmsafebd.services.model.Order;
import com.rahat.pharmsafebd.view.ui.MyOrderDetails;

import java.util.ArrayList;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.MyViewHolder> {

    private ArrayList<Order> orderList;
    private Context context;
    private ArrayList<MedicineModelClass> medicineList;
    private DatabaseReference medicineRef = FirebaseDatabase.getInstance().getReference("medicines");

    public OrderListAdapter(ArrayList<Order> orderList, Context context, ArrayList<MedicineModelClass> medicineList) {
        this.orderList = orderList;
        this.context = context;
        this.medicineList = medicineList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.adapter_order_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Order order = orderList.get(position);
        medicineRef.orderByChild("id").equalTo(order.getMedicineId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    MedicineModelClass medicineModelClass = dataSnapshot.getValue(MedicineModelClass.class);
                    holder.medicineName.setText(medicineModelClass.getMedicineName());
                    holder.power.setText(medicineModelClass.getPower());
                    holder.group.setText(medicineModelClass.getGroup());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView medicineName, power, group;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            medicineName = itemView.findViewById(R.id.order_list_medicine_name_id);
            power = itemView.findViewById(R.id.order_list_power_id);
            group = itemView.findViewById(R.id.order_list_group_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent intent = new Intent(context, MyOrderDetails.class);
//                    context.startActivity(intent);
                }
            });
        }
    }
}
