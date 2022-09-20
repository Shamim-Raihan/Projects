package com.atia.tutortime.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.atia.tutortime.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LaunchingActivity extends AppCompatActivity {
    private AlertDialog alertDialog;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launching);

        mAuth = FirebaseAuth.getInstance();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog_box, null));
        builder.setCancelable(false);
        alertDialog = builder.create();

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        if(mAuth.getCurrentUser() == null){
            Intent intent = new Intent(LaunchingActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        else if (mAuth.getCurrentUser() != null && mAuth.getCurrentUser().isEmailVerified()) {
            alertDialog.show();
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String status = snapshot.child(mAuth.getUid()).child("status").getValue(String.class);
                    alertDialog.dismiss();
                    Intent intent = new Intent(LaunchingActivity.this, HomeActivity.class);
                    intent.putExtra("status", status);
                    startActivity(intent);
                    finish();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else if (mAuth.getCurrentUser() != null && !mAuth.getCurrentUser().isEmailVerified()) {
            alertDialog.show();
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String email = snapshot.child(mAuth.getUid()).child("email").getValue(String.class);
                    alertDialog.dismiss();
                    Intent intent = new Intent(LaunchingActivity.this, EmailVerificationActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    finish();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }



    }
}