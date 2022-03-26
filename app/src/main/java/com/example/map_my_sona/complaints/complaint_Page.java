package com.example.map_my_sona.complaints;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.INotificationSideChannel;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.animation.content.Content;
import com.example.map_my_sona.ExampleService;
import com.example.map_my_sona.R;
import com.example.map_my_sona.dashboard;
import com.example.map_my_sona.manualentry;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class complaint_Page extends AppCompatActivity {

    private TextView sn, make, model, procurement, powerRating, wexpiry, wperiod, ins_by, ins_date, mob;
    private EditText complaint_qrcode, complainted_by_name, complainted_by_mob, complainted_by_dep;
    private Button complaint_subBtn;
    CheckBox vhigh ,high ,low;



    private String complainted_by_dep_str, complainted_by_name_str, complainted_by_mob_str, sn_str, make_str, model_str, procurement_str, powerRating_str, wexpiry_str, wperiod_str, ins_by_str, ins_date_str, mob_str;
    private String complaint_txt;
    String status = "Pending";
    DatabaseReference databaseReference;
    String s;
    DatabaseReference dbRef;

    TextInputLayout complaint_content;
    AutoCompleteTextView complaint_content_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_page);

        //
//        complaint_content=findViewById(R.id.complaint_content);
//        complaint_content_text=findViewById(R.id.complaint_Qrcode);

        sn = (TextView) findViewById(R.id.sn_unit);
        make = (TextView) findViewById(R.id.make_unit);
        model = (TextView) findViewById(R.id.model_unit);
        powerRating = (TextView) findViewById(R.id.powerRating_unit);
        procurement = (TextView) findViewById(R.id.procurement_unit);
        wperiod = (TextView) findViewById(R.id.warranty_unit);
        wexpiry = (TextView) findViewById(R.id.warranty_exp_unit);
        ins_by = (TextView) findViewById(R.id.ins_by_unit);
        ins_date = (TextView) findViewById(R.id.ins_date_unit);
        mob = (TextView) findViewById(R.id.mob_unit);
        complaint_subBtn = (Button) findViewById(R.id.button_complaint_submit);

        complaint_qrcode=(EditText)findViewById(R.id.complaint_Qrcode);
        complainted_by_name = (EditText) findViewById(R.id.scan_qr_com_name);
        complainted_by_mob = (EditText) findViewById(R.id.scan_qr_com_mob);
        complainted_by_dep = (EditText) findViewById(R.id.scan_qr_com_dep);

        vhigh =(CheckBox)findViewById(R.id.veryhighpriority);
        high=(CheckBox)findViewById(R.id.highpriority);
        low =(CheckBox)findViewById(R.id.lowpriority);

        LinearLayout priority = (LinearLayout) findViewById(R.id.prioritylayout);


//        TextView scanText = (TextView) findViewById(R.id.textView);
        s = getIntent().getStringExtra("SCAN_RESULT");



        databaseReference = FirebaseDatabase.getInstance().getReference("Datas").child(s);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                mob_str = snapshot.child("mob").getValue(String.class);
                sn_str = snapshot.child("sn_no").getValue(String.class);
                make_str = snapshot.child("make").getValue(String.class);
                model_str = snapshot.child("model").getValue(String.class);
                procurement_str = snapshot.child("procurement").getValue(String.class);
                powerRating_str = snapshot.child("power_rating").getValue(String.class);
                wexpiry_str = snapshot.child("wexpiry").getValue(String.class);
                wperiod_str = snapshot.child("wperiod").getValue(String.class);
                ins_by_str = snapshot.child("ins_by").getValue(String.class);
                ins_date_str = snapshot.child("ins_date").getValue(String.class);


                sn.setText(sn_str);
                make.setText(make_str);
                model.setText(model_str);
                procurement.setText(procurement_str);
                powerRating.setText(powerRating_str);
                wexpiry.setText(wexpiry_str);
                wperiod.setText(wperiod_str);
                ins_by.setText(ins_by_str);
                ins_date.setText(ins_date_str);
                mob.setText(mob_str);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        String[] complaint = {"Light Not working", "Network issue", "Fan not working", "Bathroom Problem", "Furniture defects"};
//        ArrayAdapter<String> complaintAdapter = new ArrayAdapter<>(complaint_Page.this, R.layout.dropdowncomplaintcontent, complaint);
//        complaint_content_text.setAdapter(complaintAdapter);


        complaint_subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (complaint_qrcode.getText().toString().isEmpty()) {
                    complaint_qrcode.setError("Empty");
                    complaint_qrcode.requestFocus();
                } else if (complainted_by_name.getText().toString().isEmpty()) {
                    complainted_by_name.setError("Empty");
                    complainted_by_name.requestFocus();
                } else if (complainted_by_mob.getText().toString().isEmpty()) {
                    complainted_by_mob.setError("Empty");
                    complainted_by_mob.requestFocus();
                } else if (complainted_by_dep.getText().toString().isEmpty()) {
                    complainted_by_dep.setError("Empty");
                    complainted_by_dep.requestFocus();
                }
//                else if (vhigh.getText().toString().isEmpty()) {
//                    vhigh.setError("Empty");
//                    vhigh.requestFocus();
//                }
                else {
                    submitComplaint();
                }

            }

        });
        //scanText.setText(s);
        String input = complaint_qrcode.getText().toString();
        Intent serviceIntent = new Intent(this, ExampleService.class);
        serviceIntent.putExtra("inputExtra", input);
        ContextCompat.startForegroundService(this, serviceIntent);
    }


    private void submitComplaint() {

        dbRef = FirebaseDatabase.getInstance().getReference().child("complaints");
        final String uniqueKey = dbRef.push().getKey();

        complaint_txt = complaint_qrcode.getText().toString();
        complainted_by_name_str = complainted_by_name.getText().toString();
        complainted_by_mob_str = complainted_by_mob.getText().toString();
        complainted_by_dep_str = complainted_by_dep.getText().toString();

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yy");
        String date = currentDate.format(calForDate.getTime());

        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        String time = currentTime.format(calForTime.getTime());


        Complaint_details complaint_details = new Complaint_details(complainted_by_name_str, complainted_by_mob_str, complainted_by_dep_str, complaint_txt, sn_str, make_str, model_str, procurement_str, powerRating_str, wperiod_str, wexpiry_str, ins_by_str, ins_date_str, mob_str, date, time, uniqueKey, s, status);

        dbRef.child(uniqueKey).setValue(complaint_details).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(complaint_Page.this, "Complaint Registered Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(complaint_Page.this, dashboard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(complaint_Page.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
//
//    public void startService(View v) {
//        String input = complaint_qrcode.getText().toString();
////        String input = "Hello Vicky";
//        Intent serviceIntent = new Intent(this, ExampleService.class);
//         serviceIntent.putExtra("inputExtra", input);
//
//        ContextCompat.startForegroundService(this, serviceIntent);
//    }
}