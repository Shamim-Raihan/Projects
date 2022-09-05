package com.rahat.pharmsafebd.view.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rahat.pharmsafebd.R;
import com.rahat.pharmsafebd.services.model.LogInModelClass;
import com.rahat.pharmsafebd.services.model.NetworkChangeListener;

public class LogInActivity extends AppCompatActivity {

    private TextInputEditText emailEd, passwordEd;
    private Button logInButton;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private TextView navigateToRegister;
    private TextView forgotPasswordTv;
    private AlertDialog alertDialog;
    private DatabaseReference databaseReference;
    String status;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_default_monochrome_black);
        getSupportActionBar().setTitle("");
        setContentView(R.layout.activity_log_in);
        setTitle("PharmSafe BD");

        emailEd = findViewById(R.id.email_edit_text_id);
        passwordEd = findViewById(R.id.password_edit_text_id);
        logInButton = findViewById(R.id.log_in_button_id);
        navigateToRegister = findViewById(R.id.navigate_to_register_id);
        forgotPasswordTv = findViewById(R.id.forgot_password_id);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog_box, null));
        builder.setCancelable(false);
        alertDialog = builder.create();
        mAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        forgotPasswordTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText resetPasswordMail = new EditText(view.getContext());
                AlertDialog.Builder resetPasswordDialog = new AlertDialog.Builder(view.getContext());
                resetPasswordDialog.setTitle("Reset Password");
                resetPasswordDialog.setMessage("Enter your Email to received reset Link");
                resetPasswordDialog.setView(resetPasswordMail);
                resetPasswordDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String mail = resetPasswordMail.getText().toString();
                        mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(LogInActivity.this, "Reset Link Sent To Your Email", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LogInActivity.this, "Reset Link is not Sent", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                resetPasswordDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                resetPasswordDialog.create().show();
            }
        });

        navigateToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogInActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEd.getText().toString();
                String password = passwordEd.getText().toString();
                if (email.isEmpty()){
                    emailEd.setError("Please enter email");
                    emailEd.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    emailEd.setError("Please enter valid email address");
                    emailEd.requestFocus();
                    return;
                }
                if (password.isEmpty()){
                    passwordEd.setError("Please Enter password");
                    passwordEd.requestFocus();
                    return;
                }
                LogInModelClass logInModelClass = new LogInModelClass(email, password);
                alertDialog.show();

                mAuth.signInWithEmailAndPassword(logInModelClass.getEmail(), logInModelClass.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            if (mAuth.getCurrentUser().isEmailVerified()){

                                databaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        status = snapshot.child(mAuth.getUid()).child("status").getValue(String.class);

                                        if (status.equals("client")){
                                            alertDialog.dismiss();
                                            finish();
                                            Intent intent = new Intent(LogInActivity.this, ClientDashboardActivity.class);
                                            startActivity(intent);
                                        }

                                        if (status.equals("merchant")){
                                            alertDialog.dismiss();
                                            finish();
                                            Intent intent = new Intent(LogInActivity.this, MerchantDashboardActivity.class);
                                            startActivity(intent);
                                        }
                                        if(status.equals("admin")){
                                            alertDialog.dismiss();
                                            finish();
                                            Intent intent = new Intent(LogInActivity.this, AdminPanelActivity.class);
                                            startActivity(intent);
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                    }
                                });

                            }
                            else {
                                Toast.makeText(LogInActivity.this, "Please verify your email address.", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            }
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
}
























