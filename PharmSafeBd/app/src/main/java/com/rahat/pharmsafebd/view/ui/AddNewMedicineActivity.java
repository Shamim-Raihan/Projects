package com.rahat.pharmsafebd.view.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rahat.pharmsafebd.R;
import com.rahat.pharmsafebd.services.model.MedicineModelClass;

public class AddNewMedicineActivity extends AppCompatActivity {

    private Spinner selectMedicineGroupSpinner, selectMedicineNameSpinner;
    private String[] medicineGroupList, aspirinMedicineNameList, bilastineMedicineNameList, calciumCarbonateMedicineNameList, doxophyllineMedicineNameList, ebastineMedicineNameList, fenofibrateMedicineNameList, gliclazideMedicineNameList, hypromelloseMedicineNameList, itraconazoleMedicineNameList, lansoprazoleMedicineNameList;
    private String[] amlodipineBesilateAtenololMedicineNameList, bisoprololHemifumarateMedicineNameList, cetirizineHydrochlorideMedicineNameList, calciumLactateGluconateMedicineNameList, domperidoneMaleateMedicineNameList, fluconazoleMedicineNameList, omeprazoleMedicineNameList, paracetamolMedicineNameList, vitaminCVitaminD3FolicAcidMedicineNameList, zafirlukastMedicineNameList;
    private String group, medicineName;
    ArrayAdapter medicineNameAdapter;
    private EditText companyNameEd, unitPriceEd, boxPriceEd;
    private Button savebutton;
    private String availablity = "Stock Available";
    private DatabaseReference medicineRef;
    private AlertDialog alertDialog;
    private TextView textView;
    private EditText powerEd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_default_monochrome_black);
        getSupportActionBar().setTitle("");
        setContentView(R.layout.activity_add_new_medicine);

        selectMedicineGroupSpinner = findViewById(R.id.add_new_medicine_medicine_group_id);
        selectMedicineNameSpinner = findViewById(R.id.add_new_medicine_medicine_name_id);
        textView = findViewById(R.id.add_new_medicine_title_id);

        companyNameEd = findViewById(R.id.add_new_medicine_company_name_id);
        unitPriceEd = findViewById(R.id.add_new_medicine_unit_price_id);
        boxPriceEd = findViewById(R.id.add_new_medicine_box_price_id);
        savebutton = findViewById(R.id.add_new_medicine_save_button_id);
        powerEd = findViewById(R.id.add_new_medicine_power_id);

        medicineGroupList = getResources().getStringArray(R.array.group_list);

        aspirinMedicineNameList = getResources().getStringArray(R.array.aspirin_medicine_list);
        amlodipineBesilateAtenololMedicineNameList = getResources().getStringArray(R.array.amlodipine_besilate_atenolol_medicine_list);
        bilastineMedicineNameList = getResources().getStringArray(R.array.bilastine_medicine_list);
        bisoprololHemifumarateMedicineNameList = getResources().getStringArray(R.array.bisoprolol_hemifumarate_medicine_list);
        calciumCarbonateMedicineNameList = getResources().getStringArray(R.array.calcium_carbonate_medicine_list);
        cetirizineHydrochlorideMedicineNameList = getResources().getStringArray(R.array.cetirizine_hydrochloride_medicine_list);
        calciumLactateGluconateMedicineNameList = getResources().getStringArray(R.array.calcium_lactate_gluconate_medicine_list);
        doxophyllineMedicineNameList = getResources().getStringArray(R.array.doxophylline_medicine_list);
        domperidoneMaleateMedicineNameList = getResources().getStringArray(R.array.domperidone_maleate_medicine_list);
        ebastineMedicineNameList = getResources().getStringArray(R.array.ebastine_medicine_list);
        fenofibrateMedicineNameList = getResources().getStringArray(R.array.fenofibrate_medicine_list);
        fluconazoleMedicineNameList = getResources().getStringArray(R.array.fluconazole_medicine_list);
        gliclazideMedicineNameList = getResources().getStringArray(R.array.gliclazide_medicine_list);
        hypromelloseMedicineNameList = getResources().getStringArray(R.array.hypromellose_medicine_list);
        itraconazoleMedicineNameList = getResources().getStringArray(R.array.itraconazole_medicine_list);
        lansoprazoleMedicineNameList = getResources().getStringArray(R.array.lansoprazole_medicine_list);

        omeprazoleMedicineNameList = getResources().getStringArray(R.array.omeprazole_medicine_list);
        paracetamolMedicineNameList = getResources().getStringArray(R.array.paracetamol_medicine_list);
        vitaminCVitaminD3FolicAcidMedicineNameList = getResources().getStringArray(R.array.vitamin_c_vitamin_d3_folic_acid_medicine_list);
        zafirlukastMedicineNameList = getResources().getStringArray(R.array.zafirlukast_medicine_list);

        medicineRef = FirebaseDatabase.getInstance().getReference("medicines");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog_box, null));
        builder.setCancelable(false);
        alertDialog = builder.create();

        ArrayAdapter groupAdapter = new ArrayAdapter(this, R.layout.spinner_sample_layout, R.id.spinnerSampleID, medicineGroupList);
        selectMedicineGroupSpinner.setAdapter(groupAdapter);
        selectMedicineGroupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                group =adapterView.getItemAtPosition(i).toString();
                if(group.equals("Select Group")){
                    selectMedicineNameSpinner.setVisibility(View.GONE);
                }
                else {
                    selectMedicineNameSpinner.setVisibility(View.VISIBLE);
                    if(group.equals("Aspirin")){
                        medicineNameAdapter = new ArrayAdapter(AddNewMedicineActivity.this, R.layout.spinner_sample_layout, R.id.spinnerSampleID, aspirinMedicineNameList);
                    }
                    if(group.equals("Amlodipine Besilate + Atenolol")){
                        medicineNameAdapter = new ArrayAdapter(AddNewMedicineActivity.this, R.layout.spinner_sample_layout, R.id.spinnerSampleID, amlodipineBesilateAtenololMedicineNameList);
                    }
                    if(group.equals("Bilastine")){
                        medicineNameAdapter = new ArrayAdapter(AddNewMedicineActivity.this, R.layout.spinner_sample_layout, R.id.spinnerSampleID, bilastineMedicineNameList);
                    }
                    if(group.equals("Bisoprolol Hemifumarate")){
                        medicineNameAdapter = new ArrayAdapter(AddNewMedicineActivity.this, R.layout.spinner_sample_layout, R.id.spinnerSampleID, bisoprololHemifumarateMedicineNameList);
                    }
                    if(group.equals("Calcium Carbonate")){
                        medicineNameAdapter = new ArrayAdapter(AddNewMedicineActivity.this, R.layout.spinner_sample_layout, R.id.spinnerSampleID, calciumCarbonateMedicineNameList);
                    }
                    if(group.equals("Cetirizine Hydrochloride")){
                        medicineNameAdapter = new ArrayAdapter(AddNewMedicineActivity.this, R.layout.spinner_sample_layout, R.id.spinnerSampleID, cetirizineHydrochlorideMedicineNameList);
                    }
                    if(group.equals("Calcium Lactate Gluconate + Calcium Carbonate + Vitamin C + Vitamin D3")){
                        medicineNameAdapter = new ArrayAdapter(AddNewMedicineActivity.this, R.layout.spinner_sample_layout, R.id.spinnerSampleID, calciumLactateGluconateMedicineNameList);
                    }
                    if(group.equals("Doxophylline")){
                        medicineNameAdapter = new ArrayAdapter(AddNewMedicineActivity.this, R.layout.spinner_sample_layout, R.id.spinnerSampleID, doxophyllineMedicineNameList);
                    }
                    if(group.equals("Domperidone Maleate")){
                        medicineNameAdapter = new ArrayAdapter(AddNewMedicineActivity.this, R.layout.spinner_sample_layout, R.id.spinnerSampleID, domperidoneMaleateMedicineNameList);
                    }
                    if(group.equals("Ebastine")){
                        medicineNameAdapter = new ArrayAdapter(AddNewMedicineActivity.this, R.layout.spinner_sample_layout, R.id.spinnerSampleID, ebastineMedicineNameList);
                    }
                    if(group.equals("Fenofibrate")){
                        medicineNameAdapter = new ArrayAdapter(AddNewMedicineActivity.this, R.layout.spinner_sample_layout, R.id.spinnerSampleID, fenofibrateMedicineNameList);
                    }
                    if(group.equals("Fluconazole")){
                        medicineNameAdapter = new ArrayAdapter(AddNewMedicineActivity.this, R.layout.spinner_sample_layout, R.id.spinnerSampleID, fluconazoleMedicineNameList);
                    }
                    if(group.equals("Gliclazide")){
                        medicineNameAdapter = new ArrayAdapter(AddNewMedicineActivity.this, R.layout.spinner_sample_layout, R.id.spinnerSampleID, gliclazideMedicineNameList);
                    }
                    if(group.equals("Hypromellose")){
                        medicineNameAdapter = new ArrayAdapter(AddNewMedicineActivity.this, R.layout.spinner_sample_layout, R.id.spinnerSampleID, hypromelloseMedicineNameList);
                    }
                    if(group.equals("Itraconazole")){
                        medicineNameAdapter = new ArrayAdapter(AddNewMedicineActivity.this, R.layout.spinner_sample_layout, R.id.spinnerSampleID, itraconazoleMedicineNameList);
                    }
                    if(group.equals("Lansoprazole")){
                        medicineNameAdapter = new ArrayAdapter(AddNewMedicineActivity.this, R.layout.spinner_sample_layout, R.id.spinnerSampleID, lansoprazoleMedicineNameList);
                    }
                    if(group.equals("Omeprazole")){
                        medicineNameAdapter = new ArrayAdapter(AddNewMedicineActivity.this, R.layout.spinner_sample_layout, R.id.spinnerSampleID, omeprazoleMedicineNameList);
                    }
                    if(group.equals("Paracetamol")){
                        medicineNameAdapter = new ArrayAdapter(AddNewMedicineActivity.this, R.layout.spinner_sample_layout, R.id.spinnerSampleID, paracetamolMedicineNameList);
                    }
                    if(group.equals("Vitamin C + Vitamin D3 + Folic Acid")){
                        medicineNameAdapter = new ArrayAdapter(AddNewMedicineActivity.this, R.layout.spinner_sample_layout, R.id.spinnerSampleID, vitaminCVitaminD3FolicAcidMedicineNameList);
                    }
                    if(group.equals("Zafirlukast")){
                        medicineNameAdapter = new ArrayAdapter(AddNewMedicineActivity.this, R.layout.spinner_sample_layout, R.id.spinnerSampleID, zafirlukastMedicineNameList);
                    }
                    selectMedicineNameSpinner.setAdapter(medicineNameAdapter);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        selectMedicineNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                medicineName =adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AddNewMedicineActivity.this);
                builder.setMessage("Are You sure?");
                builder.setTitle("Add new medicine");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.show();
                        String power = powerEd.getText().toString();
                        String companyName = companyNameEd.getText().toString();
                        String unitPrice = unitPriceEd.getText().toString();
                        String boxPrice = boxPriceEd.getText().toString();

                        if(power.isEmpty()){
                            powerEd.setError("Power is required");
                            powerEd.requestFocus();
                            return;
                        }

                        if(companyName.isEmpty()){
                            companyNameEd.setError("Company name is required");
                            companyNameEd.requestFocus();
                            return;
                        }
                        if(unitPrice.isEmpty()){
                            unitPriceEd.setError("Unit Price is required");
                            unitPriceEd.requestFocus();
                            return;
                        }
                        if(boxPrice.isEmpty()){
                            boxPriceEd.setError("Box Price is required");
                            boxPriceEd.requestFocus();
                            return;
                        }
                        if(group.equals("Select Group")){
                            Toast.makeText(AddNewMedicineActivity.this, "Plese select a group", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(group.equals("Select Medicine")){
                            Toast.makeText(AddNewMedicineActivity.this, "Plese select a medicine name", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        saveData(power, companyName, unitPrice, boxPrice);
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

    private void saveData(String power, String companyName, String unitPrice, String boxPrice) {
        medicineRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String id = medicineRef.push().getKey();
                MedicineModelClass medicineModelClass = new MedicineModelClass(id, group, medicineName, power, companyName, unitPrice, boxPrice, availablity);
                medicineRef.child(id).setValue(medicineModelClass);
                alertDialog.dismiss();
                Toast.makeText(AddNewMedicineActivity.this, "New Medicine added", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddNewMedicineActivity.this, AdminPanelActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}