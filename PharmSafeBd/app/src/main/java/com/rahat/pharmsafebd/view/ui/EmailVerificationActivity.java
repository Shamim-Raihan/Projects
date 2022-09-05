package com.rahat.pharmsafebd.view.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rahat.pharmsafebd.R;

public class EmailVerificationActivity extends AppCompatActivity {

    private TextView verifyMessageTv, navigateToLogInTv;
    private Button resendLinkButton;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference userRef;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_default_monochrome_black);
        getSupportActionBar().setTitle("");
        setContentView(R.layout.activity_email_verification);
        setTitle("PharmSafe BD");

        verifyMessageTv = findViewById(R.id.verification_msg_id);
        resendLinkButton = findViewById(R.id.resend_link_btn_id);
        navigateToLogInTv = findViewById(R.id.navigate_to_log_in_id);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userRef = FirebaseDatabase.getInstance().getReference("users");

        verifyMessageTv.setText(user.getEmail());

        resendLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user != null && !user.isEmailVerified()) {
                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            userRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    email = snapshot.child(mAuth.getUid()).child("email").getValue(String.class);
                                    verifyMessageTv.setText(email);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            Toast.makeText(EmailVerificationActivity.this, "Verification Email has been sent", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EmailVerificationActivity.this, "Verification Email not sent", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        navigateToLogInTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmailVerificationActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}