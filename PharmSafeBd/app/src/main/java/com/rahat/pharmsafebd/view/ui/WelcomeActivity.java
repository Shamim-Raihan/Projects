package com.rahat.pharmsafebd.view.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rahat.pharmsafebd.R;

public class WelcomeActivity extends AppCompatActivity {

    private Button client_btn, merchant_btn;
    private TextView navigateToLogInEd;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private String status;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        setTitle("PharmSafe BD");

        client_btn = findViewById(R.id.client_btn_id);
        merchant_btn = findViewById(R.id.merchant_btn_id);
        navigateToLogInEd = findViewById(R.id.navigate_to_log_in_id);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog_box, null));
        builder.setCancelable(false);
        alertDialog = builder.create();

        navigateToLogInEd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        client_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, ClientRegisterActivity.class);
                startActivity(intent);
            }
        });
        merchant_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, MerchantRegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null && firebaseUser.isEmailVerified()) {

            alertDialog.show();
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    status = snapshot.child(mAuth.getUid()).child("status").getValue(String.class);
                    if (status.equals("client")){
                        alertDialog.dismiss();
                        finish();
                        Intent intent = new Intent(WelcomeActivity.this, ClientDashboardActivity.class);
                        startActivity(intent);
                    }

                    if (status.equals("merchant")){
                        alertDialog.dismiss();
                        finish();
                        Intent intent = new Intent(WelcomeActivity.this, MerchantDashboardActivity.class);
                        startActivity(intent);
                    }
                    if (status.equals("admin")){
                        alertDialog.dismiss();
                        finish();
                        Intent intent = new Intent(WelcomeActivity.this, AdminPanelActivity.class);
                        startActivity(intent);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        } if(firebaseUser != null && !firebaseUser.isEmailVerified()) {
            Intent intent = new Intent(WelcomeActivity.this, EmailVerificationActivity.class);
            startActivity(intent);
            finish();
        }
        else {

        }
    }
}
















