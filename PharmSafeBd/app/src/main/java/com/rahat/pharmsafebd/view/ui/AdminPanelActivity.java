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

public class AdminPanelActivity extends AppCompatActivity {

    private CardView addNewMedicineCardView, addNewAmbulanceCardView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_default_monochrome_black);
        getSupportActionBar().setTitle("");
        setContentView(R.layout.activity_admin_panel);

        mAuth = FirebaseAuth.getInstance();

        addNewMedicineCardView = findViewById(R.id.admin_panel_add_new_medicine_id);
        addNewAmbulanceCardView = findViewById(R.id.admin_panel_add_new_ambulance_id);
        addNewMedicineCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminPanelActivity.this, AddNewMedicineActivity.class);
                startActivity(intent);
            }
        });

        addNewAmbulanceCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminPanelActivity.this, AddNewAmbulanceActivity.class);
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
                Intent intent = new Intent(AdminPanelActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
        }
        return true;
    }

}