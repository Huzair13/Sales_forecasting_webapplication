package com.example.map_my_sona;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class Asset_Laptop_detail_view extends AppCompatActivity {

    private DatabaseReference dbRef;

    String Amount_str,dop_str,suppAdd_str,Sup_name_str,model_str,ref_no_str;
    int billNo_str,quantity_str;
    TextView Amount,billNo,dop,quantity,suppAdd,Sup_name,model,ref_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_laptop_detail_view);

        Amount=findViewById(R.id.amount_asset_lap);
        billNo=findViewById(R.id.bill_asset_lap);
        dop=findViewById(R.id.dop_asset_lap);
        quantity=findViewById(R.id.quantity_asset_lap);
        suppAdd=findViewById(R.id.supp_add_assets_lap);
        Sup_name=findViewById(R.id.supp_name_asset_lap);
        model=findViewById(R.id.model_asset_lap);
        ref_no=findViewById(R.id.ref_asset_lap);

        Intent intent=getIntent();
        String lap_id_new=intent.getStringExtra("Lap_ID");

        dbRef= FirebaseDatabase.getInstance()
                .getReference("Assets").child("Laptops").child(lap_id_new);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AssetDeatils1 assetDeatils1=snapshot.getValue(AssetDeatils1.class);
                Amount_str=assetDeatils1.getAmount().toString();
                billNo_str=assetDeatils1.getBill_No();
                dop_str=assetDeatils1.getDOP().toString();
                quantity_str=assetDeatils1.getQuantity();
                Sup_name_str=assetDeatils1.getSupplier_Name().toString();
                suppAdd_str=assetDeatils1.getSupplier_Address().toString();
                model_str=assetDeatils1.getModel().toString();
                ref_no_str=assetDeatils1.getRef_no().toString();

                Amount.setText(Amount_str);
                //billNo.setText(billNo_str);
                //quantity.setText(quantity_str);
                Sup_name.setText(Sup_name_str);
                suppAdd.setText(suppAdd_str);
                model.setText(model_str);
                ref_no.setText(ref_no_str);
                dop.setText(dop_str);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}