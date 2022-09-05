package com.rahat.pharmsafebd.view.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rahat.pharmsafebd.R;
import com.rahat.pharmsafebd.services.model.AmbulanceModelClass;

public class AddNewAmbulanceActivity extends AppCompatActivity {

    private EditText hospitalNameEd, hotlineEd;
    private Button saveButton;
    private DatabaseReference ambulanceRef;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_default_monochrome_black);
        getSupportActionBar().setTitle("");
        setContentView(R.layout.activity_add_new_ambulance);

        hospitalNameEd = findViewById(R.id.add_new_ambulance_hospital_name_id);
        hotlineEd = findViewById(R.id.add_new_ambulance_hotline_id);
        saveButton = findViewById(R.id.add_new_ambulance_save_button_id);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog_box, null));
        builder.setCancelable(false);
        alertDialog = builder.create();
        ambulanceRef = FirebaseDatabase.getInstance().getReference("ambulances");
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddNewAmbulanceActivity.this);
                builder.setTitle("New Ambulance");
                builder.setMessage("Are you sure?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.show();
                        String hospitalName = hospitalNameEd.getText().toString();
                        String hotline = hotlineEd.getText().toString();

                        ambulanceRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String id = ambulanceRef.push().getKey();
                                AmbulanceModelClass ambulanceModelClass = new AmbulanceModelClass(id, hospitalName, hotline);
                                ambulanceRef.child(id).setValue(ambulanceModelClass);
                                Toast.makeText(AddNewAmbulanceActivity.this, "New Ambulance Added", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AddNewAmbulanceActivity.this, AdminPanelActivity.class);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.show();
            }
        });
    }
}