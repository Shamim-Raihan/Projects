package com.rahat.pharmsafebd.view.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rahat.pharmsafebd.R;
import com.rahat.pharmsafebd.services.model.AmbulanceModelClass;
import com.rahat.pharmsafebd.view.adapter.AmbulanceListAdapter;

import java.util.ArrayList;

public class AmbulanceListActivity extends AppCompatActivity {

    private DatabaseReference ambulanceRef;
    private ArrayList<AmbulanceModelClass> ambulanceList;
    private RecyclerView recyclerView;
    private AmbulanceListAdapter ambulanceListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_default_monochrome_black);
        getSupportActionBar().setTitle("");
        setContentView(R.layout.activity_ambulance_list);

        ambulanceRef = FirebaseDatabase.getInstance().getReference("ambulances");
        ambulanceList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view_id);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AmbulanceListActivity.this));

        ambulanceRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    AmbulanceModelClass ambulanceModelClass = dataSnapshot.getValue(AmbulanceModelClass.class);
                    ambulanceList.add(ambulanceModelClass);
                }

                ambulanceListAdapter = new AmbulanceListAdapter(ambulanceList, AmbulanceListActivity.this);
                recyclerView.setAdapter(ambulanceListAdapter);
                ambulanceListAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}