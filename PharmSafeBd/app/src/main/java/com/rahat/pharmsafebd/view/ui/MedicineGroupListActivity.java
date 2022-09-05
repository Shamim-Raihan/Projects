package com.rahat.pharmsafebd.view.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rahat.pharmsafebd.R;
import com.rahat.pharmsafebd.services.model.MedicineModelClass;
import com.rahat.pharmsafebd.view.adapter.MedicineGroupListAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class MedicineGroupListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference medicineRef;
    private ArrayList<MedicineModelClass> medicineList;
    private ArrayList<MedicineModelClass> newMedicineList;
    private MedicineGroupListAdapter medicineGroupListAdapter;
    private SearchView searchView;
    private MedicineModelClass medicineModelClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_default_monochrome_black);
        getSupportActionBar().setTitle("");
        setContentView(R.layout.activity_medicine_group_list);

        medicineList = new ArrayList<>();
        newMedicineList = new ArrayList<>();
        searchView = findViewById(R.id.teacher_search_id);
        recyclerView = findViewById(R.id.recycler_view_id);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MedicineGroupListActivity.this));

        medicineRef = FirebaseDatabase.getInstance().getReference("medicines");


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(MedicineGroupListActivity.this, "" + s, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MedicineGroupListActivity.this, MedicineDetailsActivity.class);
                intent.putExtra("singleMedicineDetails", medicineModelClass);
                intent.putExtra("from", "groupList");
                intent.putExtra("searchString", s);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        medicineRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    MedicineModelClass medicineModelClass = dataSnapshot.getValue(MedicineModelClass.class);
                    medicineList.add(medicineModelClass);
                }

                int flag = 0;
                newMedicineList.add(medicineList.get(0));
                for(int i = 0; i < medicineList.size(); i++){

                    for(int j = i+1; j < medicineList.size(); i++){
                        if(medicineList.get(i).getGroup().equals(medicineList.get(j).getGroup())){
                            break;
                        }
                        else {
                            flag = 1;
                        }
                    }
                    if(flag==1){
                        newMedicineList.add(medicineList.get(i));
                        flag = 0;
                    }
                }

                medicineGroupListAdapter = new MedicineGroupListAdapter(newMedicineList, MedicineGroupListActivity.this, "group");
                recyclerView.setAdapter(medicineGroupListAdapter);
                medicineGroupListAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}