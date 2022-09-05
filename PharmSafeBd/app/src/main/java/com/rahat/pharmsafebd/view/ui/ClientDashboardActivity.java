package com.rahat.pharmsafebd.view.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rahat.pharmsafebd.R;
import com.rahat.pharmsafebd.services.model.AmbulanceModelClass;

import java.util.ArrayList;

public class ClientDashboardActivity extends AppCompatActivity {

    private CardView orderNowCardVeiw, ambulanceCardView;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_default_monochrome_black);
        getSupportActionBar().setTitle("");
        setContentView(R.layout.activity_client_dashboard);
        mAuth = FirebaseAuth.getInstance();


        orderNowCardVeiw = findViewById(R.id.client_order_now_id);
        ambulanceCardView = findViewById(R.id.client_emergency_ambulance_id);

        orderNowCardVeiw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClientDashboardActivity.this, MedicineGroupListActivity.class);
                startActivity(intent);
            }
        });

        ambulanceCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClientDashboardActivity.this, AmbulanceListActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.log_out_id:
                mAuth.signOut();
                Intent intent = new Intent(ClientDashboardActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.my_order_id:
                Intent intent1 = new Intent(ClientDashboardActivity.this, MyOrderListActivity.class);
                startActivity(intent1);
                break;
            case R.id.my_cart_id:
                Intent intent2 = new Intent(ClientDashboardActivity.this, MyCartListActivity.class);
                startActivity(intent2);
                break;
        }
        return true;
    }
}