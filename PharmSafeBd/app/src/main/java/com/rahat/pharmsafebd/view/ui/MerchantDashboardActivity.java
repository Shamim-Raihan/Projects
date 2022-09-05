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
import com.rahat.pharmsafebd.R;

public class MerchantDashboardActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private CardView merchantOrderNowCardview, merchantAmbulanceCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_default_monochrome_black);
        getSupportActionBar().setTitle("");
        setContentView(R.layout.activity_merchant_dashboard);
        setTitle("PharmSafe BD");

        merchantOrderNowCardview = findViewById(R.id.merchant_order_now_id);
        merchantAmbulanceCardView = findViewById(R.id.merchant_emergency_ambulance_id);
        mAuth = FirebaseAuth.getInstance();

        merchantOrderNowCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MerchantDashboardActivity.this, MedicineGroupListActivity.class);
                startActivity(intent);
            }
        });

        merchantAmbulanceCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MerchantDashboardActivity.this, AmbulanceListActivity.class);
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
                Intent intent = new Intent(MerchantDashboardActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.my_order_id:
                Intent intent1 = new Intent(MerchantDashboardActivity.this, MyOrderListActivity.class);
                startActivity(intent1);
                break;
            case R.id.my_cart_id:
                Intent intent2 = new Intent(MerchantDashboardActivity.this, MyCartListActivity.class);
                startActivity(intent2);
                break;
        }
        return true;
    }
}