package com.rahat.pharmsafebd.view.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.rahat.pharmsafebd.R;
import com.rahat.pharmsafebd.services.model.ClientRegisterModelClass;
import com.rahat.pharmsafebd.services.model.NetworkChangeListener;

public class ClientRegisterActivity extends AppCompatActivity {

    private TextInputEditText nameEd, phoneEd, passwordEd, confirmPasswordEd, emailEd;
    private RadioGroup genderRadioGroup;
    private RadioButton maleRadioButton, femaleRadioButton;
    private Button registerButton;
    private String gender = "";
    private TextView passwordCkeckTv;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private String uid;
    private StorageTask storageTask;
    private String status = "client";

    private TextView navigateToLogInEd;

    private AlertDialog alertDialog;
    FirebaseUser user;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_default_monochrome_black);
        getSupportActionBar().setTitle("");
        setContentView(R.layout.activity_client_register);
        setTitle("PharmSafe BD");

        nameEd = findViewById(R.id.name_edit_text_id);
        phoneEd = findViewById(R.id.phone_edit_text_id);
        passwordEd = findViewById(R.id.password_edit_text_id);
        genderRadioGroup = findViewById(R.id.gender_radio_group_id);
        maleRadioButton = findViewById(R.id.male_radio_button_id);
        femaleRadioButton = findViewById(R.id.female_radio_button_id);
        registerButton = findViewById(R.id.register_button_id);
        confirmPasswordEd = findViewById(R.id.confirm_password_edit_text_id);
        genderRadioGroup = findViewById(R.id.gender_radio_group_id);
        passwordCkeckTv = findViewById(R.id.password_check_alert_tv_id);
        emailEd = findViewById(R.id.email_edit_text_id);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        navigateToLogInEd = findViewById(R.id.navigate_to_log_in_id);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog_box, null));
        builder.setCancelable(false);
        alertDialog = builder.create();


        navigateToLogInEd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClientRegisterActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEd.getText().toString();

                int selectedId = genderRadioGroup.getCheckedRadioButtonId();
                if (selectedId == R.id.male_radio_button_id) {
                    gender = "male";
                }
                if (selectedId == R.id.female_radio_button_id) {
                    gender = "female";
                }
                String phone = phoneEd.getText().toString();
                String email = emailEd.getText().toString();
                String password = passwordEd.getText().toString();
                String confirmPassword = confirmPasswordEd.getText().toString();

                if (name.isEmpty()) {
                    nameEd.setError("Please Enter your name");
                    nameEd.requestFocus();
                    return;
                }

                if (name.length() > 20) {
                    nameEd.setError("Pharmacy name should be less then 20 characters");
                    nameEd.requestFocus();
                    return;
                }

                if (gender.equals("")) {
                    Toast.makeText(ClientRegisterActivity.this, "Please select gender", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (phone.isEmpty()) {
                    phoneEd.setError("Please Enter phone number");
                    phoneEd.requestFocus();
                    return;
                }

                if (phone.length() > 11) {
                    phoneEd.setError("Phone number should be less then 20 characters");
                    phoneEd.requestFocus();
                    return;
                }

                if (email.isEmpty()) {
                    emailEd.setError("Please enter email");
                    emailEd.requestFocus();
                    return;
                }

                if (email.length() > 30) {
                    emailEd.setError("Email should be less then 20 characters");
                    emailEd.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailEd.setError("Please enter valid email address");
                    emailEd.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    passwordEd.setError("Please Enter password");
                    passwordEd.requestFocus();
                    return;
                }

                if (password.length() > 20) {
                    passwordEd.setError("Password should be less then 20 characters");
                    passwordEd.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    passwordEd.setError("Password length should be at least 6 characters");
                    passwordEd.requestFocus();
                    return;
                }

                if (confirmPassword.isEmpty()) {
                    confirmPasswordEd.setError("Please Enter confirm password");
                    confirmPasswordEd.requestFocus();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    passwordCkeckTv.setVisibility(View.VISIBLE);
                    return;
                }
                clientRegister(name, gender, phone, email, password);
            }
        });
    }

    private void clientRegister(String name, String gender, String phone, String email, String password) {

        alertDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    uid = mAuth.getUid();
                    user = mAuth.getCurrentUser();
                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ClientRegisterActivity.this, "Verification Email has been sent", Toast.LENGTH_SHORT).show();
                                if (storageTask != null && storageTask.isInProgress()) {
                                    Toast.makeText(ClientRegisterActivity.this, "Uploading is progress", Toast.LENGTH_SHORT).show();
                                } else {
                                    saveData(name, gender, phone, email, password, uid);
                                }
                            }
                            else {
                                Toast.makeText(ClientRegisterActivity.this, "Verification Email is not sent", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveData(String name, String gender, String phone, String email, String password, String uid) {
        ClientRegisterModelClass clientRegisterModelClass = new ClientRegisterModelClass(name, gender, phone, email, password, uid, status);
        databaseReference.child(uid).setValue(clientRegisterModelClass);
        mAuth.signOut();
        alertDialog.dismiss();
        finish();
        Intent intent = new Intent(ClientRegisterActivity.this, EmailVerificationActivity.class);
        startActivity(intent);
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





















