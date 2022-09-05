package com.rahat.pharmsafebd.view.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import com.rahat.pharmsafebd.view.adapter.CartListAdapter;

import java.security.cert.PolicyNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MyCartListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button orderNowButton;
    private FirebaseAuth mAuth;
    private DatabaseReference cartRef, medicineRef, orderRef,userRef;
    private ArrayList<AddToCart> cartList;
    private CartListAdapter cartListAdapter;
    private EditText locationEd;

    private String name, status, email, phone;
    private String medicineNames;

    AlertDialog alertDialog;
    private String price, quantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_default_monochrome_black);
        getSupportActionBar().setTitle("");
        setContentView(R.layout.activity_my_cart_list);

        recyclerView = findViewById(R.id.recycler_view_id);
        orderNowButton = findViewById(R.id.my_cart_list_order_now_button_id);
        locationEd = findViewById(R.id.my_cart_address_id);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyCartListActivity.this));

        medicineRef = FirebaseDatabase.getInstance().getReference("medicines");
        orderRef = FirebaseDatabase.getInstance().getReference("orders");
        cartRef = FirebaseDatabase.getInstance().getReference("carts");
        userRef = FirebaseDatabase.getInstance().getReference("users");
        mAuth = FirebaseAuth.getInstance();

        cartList = new ArrayList<>();


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog_box, null));
        builder.setCancelable(false);
        alertDialog = builder.create();


        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                status = snapshot.child(mAuth.getUid()).child("status").getValue(String.class);
                email = snapshot.child(mAuth.getUid()).child("email").getValue(String.class);
                phone = snapshot.child(mAuth.getUid()).child("phone").getValue(String.class);
                if (status.equals("merchant")){
                    name = snapshot.child(mAuth.getUid()).child("pharmacyName").getValue(String.class);
                }
                else if(status.equals("client")){
                    name = snapshot.child(mAuth.getUid()).child("name").getValue(String.class);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        cartRef.orderByChild("userId").equalTo(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    AddToCart addToCart = dataSnapshot.getValue(AddToCart.class);
                    if(addToCart.getStatus().equals("active")){
                        cartList.add(addToCart);
                    }
                }
                cartListAdapter = new CartListAdapter(MyCartListActivity.this, cartList);
                recyclerView.setAdapter(cartListAdapter);
                cartListAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        orderNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder = new AlertDialog.Builder(MyCartListActivity.this);
                builder.setTitle("Oder Confirmation");
                builder.setMessage("Are you sure?");
                builder.setCancelable(false);


                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        alertDialog.show();

                        String location = locationEd.getText().toString();
                        int j = 0;
                        while (j < cartList.size()){
                            int finalI = j;
                            orderRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String id = orderRef.push().getKey();
                                    Order order = new Order(id, cartList.get(finalI).getMedicineId(), cartList.get(finalI).getUserId(), cartList.get(finalI).getQuantity(), location, cartList.get(finalI).getPrice());
                                    orderRef.child(id).setValue(order);

                                    price = cartList.get(finalI).getPrice();
                                    quantity = cartList.get(finalI).getQuantity();

                                    HashMap updateStatus = new HashMap();
                                    updateStatus.put("status", "deactive");

                                    cartRef.child(cartList.get(finalI).getId()).updateChildren(updateStatus).addOnCompleteListener(new OnCompleteListener() {
                                        @Override
                                        public void onComplete(@NonNull Task task) {

                                        }
                                    });

                                    medicineRef.orderByChild("id").equalTo(cartList.get(finalI).getMedicineId()).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                                MedicineModelClass medicineModelClass = dataSnapshot.getValue(MedicineModelClass.class);
                                                medicineNames = medicineModelClass.getMedicineName();

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
                                                    message.setText("Name : " + name + "\n" + "Email : " + email + "\n" + "Address : " + location + "\n" + "Phone : " + phone + "\n" + "\n" + "Medicine name : " + medicineNames + "\n" + "Total Price : " + price+ "\n" + "Quantity : " + quantity);
                                                    Transport.send(message);
                                                    if(status.equals("merchant")){
                                                        Toast.makeText(MyCartListActivity.this, "order successfull", Toast.LENGTH_SHORT).show();
                                                        alertDialog.dismiss();
                                                        Intent intent = new Intent(MyCartListActivity.this, MerchantDashboardActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                    else {
                                                        Toast.makeText(MyCartListActivity.this, "order successfull", Toast.LENGTH_SHORT).show();
                                                        alertDialog.dismiss();
                                                        Intent intent = new Intent(MyCartListActivity.this, ClientDashboardActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }


                                                }
                                                catch (MessagingException e){
                                                    throw new RuntimeException(e);
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });



                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            j++;

                        }






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