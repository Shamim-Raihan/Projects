package com.rahat.pharmsafebd.view.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.rahat.pharmsafebd.R;
import com.rahat.pharmsafebd.services.model.NetworkChangeListener;
import com.rahat.pharmsafebd.services.model.PharmacistRegisterModelClass;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;

public class MerchantRegisterActivity extends AppCompatActivity {


    private TextInputEditText pharmacyNameEd, phoneEd, passwordEd, confirmPasswordEd, emailEd;
    private TextView tradeLicenceTv, pharmasistLinceceTv;
    private Button registerButton;
    private TextView passwordCheckTv;
    private ImageView tradeLicenceErrorImageView, pharmacistLicenceErrorImageView;
    private static final int REQUEST_CAMERA_CODE = 100;
    private Uri tradeLicenceImageUri;
    private Uri pharmacistLicenceImageUri;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private String uid;
    private StorageTask storageTask;
    private String tradeLicenceImageUrl = "", pharmacistLicenceImageUrl = "";
    private String status = "merchant";
    private TextView navigateToLogInEd;
    private static final int TRADE_IMAGE_REQUEST = 1;
    private static final int PHARMACIST_IMAGE_REQUEST = 2;

    private AlertDialog alertDialog;
    private FirebaseUser user;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_default_monochrome_black);
        getSupportActionBar().setTitle("");
        setContentView(R.layout.activity_merchant_register);
        setTitle("PharmSafe BD");

        pharmacyNameEd = findViewById(R.id.pharmacy_name_edit_text_id);
        tradeLicenceTv = findViewById(R.id.trade_licence_photo_tv_id);
        pharmasistLinceceTv = findViewById(R.id.pharmacist_licence_photo_tv_id);
        phoneEd = findViewById(R.id.phone_edit_text_id);
        passwordEd = findViewById(R.id.password_edit_text_id);
        confirmPasswordEd = findViewById(R.id.confirmpassword_edit_text_id);
        passwordCheckTv = findViewById(R.id.password_check_alert_tv_id);
        registerButton = findViewById(R.id.register_button_id);
        tradeLicenceErrorImageView = findViewById(R.id.trade_licence_error_id);
        pharmacistLicenceErrorImageView = findViewById(R.id.pharmacist_licence_error_id);
        emailEd = findViewById(R.id.email_edit_text_id);
        navigateToLogInEd = findViewById(R.id.navigate_to_log_in_id);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        storageReference = FirebaseStorage.getInstance().getReference("image");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog_box, null));
        builder.setCancelable(false);
        alertDialog = builder.create();


        navigateToLogInEd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MerchantRegisterActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tradeLicenceTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(MerchantRegisterActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MerchantRegisterActivity.this, new String[]{
                            Manifest.permission.CAMERA
                    }, REQUEST_CAMERA_CODE);
                }

//                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(MerchantRegisterActivity.this);
                tradeLicenceImageFileChooser();
                tradeLicenceErrorImageView.setVisibility(View.GONE);

            }
        });

        pharmasistLinceceTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(MerchantRegisterActivity.this);
                pharmacistLicenceImageFileChooser();
                pharmacistLicenceErrorImageView.setVisibility(View.GONE);
            }

        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pharmacyName = pharmacyNameEd.getText().toString();
                String phone = phoneEd.getText().toString();
                String email = emailEd.getText().toString();
                String password = passwordEd.getText().toString();
                String confirmPassword = confirmPasswordEd.getText().toString();

                if (pharmacyName.isEmpty()) {
                    pharmacyNameEd.setError("Please enter pharnacy name");
                    pharmacyNameEd.requestFocus();
                    return;
                }

                if (pharmacyName.length() > 30) {
                    pharmacyNameEd.setError("Pharmacy name should be less then 20 characters");
                    pharmacyNameEd.requestFocus();
                    return;
                }


                if (tradeLicenceImageUri == null) {
                    Toast.makeText(MerchantRegisterActivity.this, "Please select trade licence image", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (pharmacistLicenceImageUri == null) {
                    Toast.makeText(MerchantRegisterActivity.this, "Please select pharmacist licence image", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (phone.isEmpty()) {
                    phoneEd.setError("Please enter phone number");
                    phoneEd.requestFocus();
                    return;
                }

                if (phone.length() > 11) {
                    phoneEd.setError("Phone number should be less then 11 characters");
                    phoneEd.requestFocus();
                    return;
                }

                if (email.isEmpty()) {
                    emailEd.setError("Please enter email");
                    emailEd.requestFocus();
                    return;
                }

                if (email.length() > 30) {
                    emailEd.setError("Email should be less then 30 characters");
                    emailEd.requestFocus();
                    return;
                }


                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailEd.setError("Please enter valid email address");
                    emailEd.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    passwordEd.setError("Please enter password");
                    passwordEd.requestFocus();
                    return;
                }

                if (password.length() > 20) {
                    passwordEd.setError("Password should be less then 20 characters");
                    passwordEd.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    passwordEd.setError("password should be at least 6 characters");
                    passwordEd.requestFocus();
                    return;
                }

                if (confirmPassword.isEmpty()) {
                    confirmPasswordEd.setError("Please enter confirm password");
                    confirmPasswordEd.requestFocus();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    passwordCheckTv.setVisibility(View.VISIBLE);
                    return;
                }

                merchantRegistration(pharmacyName, tradeLicenceImageUri, pharmacistLicenceImageUri, phone, email, password);

            }
        });
    }

    private void pharmacistLicenceImageFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PHARMACIST_IMAGE_REQUEST);
    }

    private void tradeLicenceImageFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, TRADE_IMAGE_REQUEST);
    }

    private void merchantRegistration(String pharmacyName, Uri tradeLicenceImageUri, Uri pharmacistLicenceImageUri, String phone, String email, String password) {
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
                                Toast.makeText(MerchantRegisterActivity.this, "Verification Email has been sent", Toast.LENGTH_SHORT).show();
                                if (storageTask != null && storageTask.isInProgress()) {
                                    Toast.makeText(MerchantRegisterActivity.this, "Registration in progress", Toast.LENGTH_SHORT).show();
                                } else {
                                    saveData(pharmacyName, tradeLicenceImageUri, pharmacistLicenceImageUri, phone, email, password, uid);
                                }
                            }
                            else {
                                Toast.makeText(MerchantRegisterActivity.this, "Verification Email is not sent", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(getApplicationContext(), "Registration failed" + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveData(String pharmacyName, Uri tradeLicenceImageUri, Uri pharmacistLicenceImageUri, String phone, String email, String password, String uid) {
        final StorageReference ref = storageReference.child("image" + tradeLicenceImageUri.getLastPathSegment() + "." + getFileExtension1(tradeLicenceImageUri));

        ref.putFile(tradeLicenceImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        tradeLicenceImageUrl = String.valueOf(uri);


                        final StorageReference ref_2 = storageReference.child("image" + pharmacistLicenceImageUri.getLastPathSegment() + "." + getFileExtension2(pharmacistLicenceImageUri));

                        ref_2.putFile(pharmacistLicenceImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                ref_2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        pharmacistLicenceImageUrl = String.valueOf(uri);
                                        PharmacistRegisterModelClass pharmacistRegisterModelClass = new PharmacistRegisterModelClass(pharmacyName, tradeLicenceImageUrl, pharmacistLicenceImageUrl, phone, email, password, uid, status);
                                        databaseReference.child(uid).setValue(pharmacistRegisterModelClass);
                                        mAuth.signOut();
                                        alertDialog.dismiss();
                                        Intent intent = new Intent(MerchantRegisterActivity.this, EmailVerificationActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MerchantRegisterActivity.this, "failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MerchantRegisterActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension2(Uri pharmacistLicenceImageUri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(pharmacistLicenceImageUri));
    }

    private String getFileExtension1(Uri tradeLicenceImageUri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(tradeLicenceImageUri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//                if (tradeLicenceImageUri == null) {
//                    tradeLicenceImageUri = result.getUri();
//                }
//                if (tradeLicenceImageUri != null) {
//                    pharmacistLicenceImageUri = result.getUri();
//                }
//            }
//        }

        if (requestCode == TRADE_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            tradeLicenceImageUri = data.getData();
        }

        if (requestCode == PHARMACIST_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            pharmacistLicenceImageUri = data.getData();
        }
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























