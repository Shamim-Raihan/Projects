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
import com.rahat.pharmsafebd.services.model.MedicineModelClass;
import com.rahat.pharmsafebd.view.adapter.MedicineGroupListAdapter;

import java.util.ArrayList;

public class MedicineNameListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MedicineModelClass medicineModelClass;
    private DatabaseReference medicineRef;
    private ArrayList<MedicineModelClass> medicineNameList;
    private MedicineGroupListAdapter medicineGroupListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_default_monochrome_black);
        getSupportActionBar().setTitle("");
        setContentView(R.layout.activity_medicine_name_list);

        recyclerView = findViewById(R.id.recycler_view_id);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MedicineNameListActivity.this));
        medicineRef = FirebaseDatabase.getInstance().getReference("medicines");
        medicineNameList = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            medicineModelClass = (MedicineModelClass) getIntent().getSerializableExtra("singleMedicineDetails");
        }




        medicineRef.orderByChild("group").equalTo(medicineModelClass.getGroup()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    MedicineModelClass medicineModelClass = dataSnapshot.getValue(MedicineModelClass.class);
                    medicineNameList.add(medicineModelClass);
                }
                medicineGroupListAdapter = new MedicineGroupListAdapter(medicineNameList, MedicineNameListActivity.this, "name");
                recyclerView.setAdapter(medicineGroupListAdapter);
                medicineGroupListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }
}