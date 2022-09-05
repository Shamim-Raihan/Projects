package com.rahat.pharmsafebd.view.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rahat.pharmsafebd.R;
import com.rahat.pharmsafebd.services.model.MedicineModelClass;
import com.rahat.pharmsafebd.services.model.Order;
import com.rahat.pharmsafebd.view.adapter.OrderListAdapter;

import java.util.ArrayList;

public class MyOrderListActivity extends AppCompatActivity {

    private DatabaseReference orderRef, medicineRef;
    private FirebaseAuth mAuth;
    private ArrayList<Order> orderList;
    private ArrayList<MedicineModelClass> medicineList;
    private OrderListAdapter orderListAdapter;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_default_monochrome_black);
        getSupportActionBar().setTitle("");
        setContentView(R.layout.activity_my_order_list);

        recyclerView = findViewById(R.id.recycler_view_id);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyOrderListActivity.this));


        orderList = new ArrayList<>();
        medicineList = new ArrayList<>();
        orderRef = FirebaseDatabase.getInstance().getReference("orders");
        medicineRef = FirebaseDatabase.getInstance().getReference("medicines");
        mAuth = FirebaseAuth.getInstance();

        orderRef.orderByChild("uesrId").equalTo(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Order order = dataSnapshot.getValue(Order.class);
                    orderList.add(order);
                }

                orderListAdapter = new OrderListAdapter(orderList, MyOrderListActivity.this, medicineList);
                recyclerView.setAdapter(orderListAdapter);
                orderListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}