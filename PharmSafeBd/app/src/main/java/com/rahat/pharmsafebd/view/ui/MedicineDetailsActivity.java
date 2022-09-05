package com.rahat.pharmsafebd.view.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rahat.pharmsafebd.R;
import com.rahat.pharmsafebd.services.model.AddToCart;
import com.rahat.pharmsafebd.services.model.MedicineModelClass;
import com.rahat.pharmsafebd.services.model.Order;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;

public class MedicineDetailsActivity extends AppCompatActivity {

    private MedicineModelClass medicineModelClass;
    private String from, searchString;

    private TextView medicineNameTv, groupNameTv, mgTv, companyNameTv, unitPriceTv, boxPriceTv;
    private NumberPicker quantityPicker;
    private TextView quantityTitleTv;
    private int quantity;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef, orderRef, medicineRef, addToCartRef;
    private String status;
    private Button addToCartButton, orderNowButton;
    private EditText locationEd;
    private String name, email, phone;
    private String location;
    private int price;
    private String getPrice;
    private AlertDialog alertDialog;
    private ArrayList<MedicineModelClass> medicineList;

    private ImageView imageView;
    private TextView tabletTv, locationTv;
    int checker = 0;
    TextView powerTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_default_monochrome_black);
        getSupportActionBar().setTitle("");
        setContentView(R.layout.activity_medicine_details);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog_box, null));
        builder.setCancelable(false);
        alertDialog = builder.create();

        imageView = findViewById(R.id.image);
        tabletTv = findViewById(R.id.tablet);
        locationTv = findViewById(R.id.location);

        medicineList = new ArrayList<>();

        medicineNameTv = findViewById(R.id.medicine_details_medicine_name_id);
        groupNameTv = findViewById(R.id.medicine_details_medicine_group_id);
        mgTv = findViewById(R.id.medicine_details_medicine_mg_id);
        companyNameTv = findViewById(R.id.medicine_details_medicine_company_id);
        unitPriceTv = findViewById(R.id.medicine_details_medicine_unit_price_id);
        boxPriceTv = findViewById(R.id.medicine_details_medicine_box_price_id);
        quantityTitleTv = findViewById(R.id.medicine_details_medicine_quatity_title_id);

        quantityPicker = findViewById(R.id.medicine_details_medicine_quatity_id);
        addToCartButton = findViewById(R.id.medicine_details_medicine_add_to_cart_btn_id);
        orderNowButton = findViewById(R.id.medicine_details_medicine_order_now_btn_id);
        locationEd = findViewById(R.id.medicine_details_medicine_location_id);
        powerTv = findViewById(R.id.medicine_details_medicine_power_id);

        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference("users");
        orderRef = FirebaseDatabase.getInstance().getReference("orders");
        medicineRef = FirebaseDatabase.getInstance().getReference("medicines");
        addToCartRef = FirebaseDatabase.getInstance().getReference("carts");

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            medicineModelClass = (MedicineModelClass) getIntent().getSerializableExtra("singleMedicineDetails");
            from = bundle.getString("from");
            searchString = bundle.getString("searchString");
        }


        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCartRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MedicineDetailsActivity.this);
                        builder.setTitle("Add to cart");
                        builder.setMessage("Are you sure");

                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                alertDialog.show();
                                String id = addToCartRef.push().getKey();
                                price = quantity * Integer.parseInt(getPrice);
                                AddToCart addToCart = new AddToCart(id, mAuth.getUid(), medicineModelClass.getId(), "active", String.valueOf(quantity), String.valueOf(price));
                                addToCartRef.child(id).setValue(addToCart);
                                Toast.makeText(MedicineDetailsActivity.this, "Medicine added in your cart", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            }
                        });
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                        builder.show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        if(from.equals("groupList")){
            medicineRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        medicineModelClass = dataSnapshot.getValue(MedicineModelClass.class);
                        if(medicineModelClass.getMedicineName().toLowerCase(Locale.ROOT).equals(searchString.toLowerCase(Locale.ROOT))){
                            checker = 1;
                            break;
                        }
                    }

                    if(checker == 1){
                        medicineNameTv.setText(medicineModelClass.getMedicineName());
                        groupNameTv.setText(medicineModelClass.getGroup());
                        companyNameTv.setText(medicineModelClass.getCompanyName());
                        unitPriceTv.setText("Unit price:"+medicineModelClass.getUnitPrice()+"   ");
                        boxPriceTv.setText("Box price:"+medicineModelClass.getBoxPrice());
                        powerTv.setText(medicineModelClass.getPower());
                    }
                    else if(checker == 0){
                        medicineNameTv.setText("No medicine found");
                        groupNameTv.setVisibility(View.GONE);
                        unitPriceTv.setVisibility(View.GONE);
                        boxPriceTv.setVisibility(View.GONE);
                        quantityPicker.setVisibility(View.GONE);
                        companyNameTv.setVisibility(View.GONE);
                        imageView.setVisibility(View.GONE);
                        tabletTv.setVisibility(View.GONE);
                        locationTv.setVisibility(View.GONE);
                        quantityTitleTv.setVisibility(View.GONE);
                        locationEd.setVisibility(View.GONE);
                        addToCartButton.setVisibility(View.GONE);
                        orderNowButton.setVisibility(View.GONE);
                        powerTv.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
       if(from.equals("adapter")) {
            medicineNameTv.setText(medicineModelClass.getMedicineName());
            groupNameTv.setText(medicineModelClass.getGroup());
            companyNameTv.setText(medicineModelClass.getCompanyName());
            unitPriceTv.setText("Unit price:"+medicineModelClass.getUnitPrice()+"   ");
            boxPriceTv.setText("Box price:"+medicineModelClass.getBoxPrice());
           powerTv.setText(medicineModelClass.getPower());
        }

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                status = snapshot.child(mAuth.getUid()).child("status").getValue(String.class);
                email = snapshot.child(mAuth.getUid()).child("email").getValue(String.class);
                phone = snapshot.child(mAuth.getUid()).child("phone").getValue(String.class);
                if (status.equals("merchant")){
                    quantityTitleTv.setText("Quantity (Box)");
                    name = snapshot.child(mAuth.getUid()).child("pharmacyName").getValue(String.class);
                    medicineRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            getPrice = snapshot.child(medicineModelClass.getId()).child("boxPrice").getValue(String.class);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else if(status.equals("client")){
                    quantityTitleTv.setText("Quantity (unit)");
                    name = snapshot.child(mAuth.getUid()).child("name").getValue(String.class);
                    medicineRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            getPrice = snapshot.child(medicineModelClass.getId()).child("unitPrice").getValue(String.class);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if(quantityPicker != null){
            quantityPicker.setMinValue(1);
            quantityPicker.setMaxValue(50);
            quantityPicker.setWrapSelectorWheel(true);
            quantityPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    quantity = newVal;
                }
            });
        }
        orderNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MedicineDetailsActivity.this);
                builder.setTitle("Oder Confirmation");
                builder.setMessage("Are you sure?");
                builder.setCancelable(false);
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.show();
                        location = locationEd.getText().toString();
                        orderRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String id = orderRef.push().getKey();
                                price = quantity * Integer.parseInt(getPrice);
                                Order order = new Order(id, medicineModelClass.getId(), mAuth.getUid(), String.valueOf(quantity), location, String.valueOf(price));
                                orderRef.child(mAuth.getUid()).setValue(order);

                                final String userName = "rahat15-2253@diu.edu.bd";
                                final String password = "gcjgklepgoedntiw";

                                Properties properties = new Properties();
                                properties.put("mail.smtp.auth", "true");
                                properties.put("mail.smtp.starttls.enable", "true");
                                properties.put("mail.smtp.host", "smtp.gmail.com");
                                properties.put("mail.smtp.port", "587");

                                Session session = Session.getInstance(properties, new javax.mail.Authenticator(){
                                    @Override
                                    protected PasswordAuthentication getPasswordAuthentication(){

                                        return new PasswordAuthentication(userName, password);
                                    }
                                });

                                try {
                                    Message message = new MimeMessage(session);
                                    message.setFrom(new InternetAddress(userName));
                                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(" jalal15-2240@diu.edu.bd"));
                                    message.setSubject("New Order");
                                    message.setText("Name : " + name + "\n" + "Email : " + email + "\n" + "Address : " + location + "\n" + "Phone : " + phone + "\n" + "Medicine Group : " + medicineModelClass.getGroup() + "\n" + "Medicine name : " + medicineModelClass.getMedicineName() + "\n" + "Total Price : " + price+ "\n" + "Quantity : " + quantity);
                                    Transport.send(message);
                                    if(status.equals("merchant")){
                                        Intent intent = new Intent(MedicineDetailsActivity.this, MerchantDashboardActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        Intent intent = new Intent(MedicineDetailsActivity.this, ClientDashboardActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                    Toast.makeText(MedicineDetailsActivity.this, "order successfull", Toast.LENGTH_SHORT).show();
                                    alertDialog.dismiss();
                                }
                                catch (MessagingException e){
                                    throw new RuntimeException(e);
                                }
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
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
}