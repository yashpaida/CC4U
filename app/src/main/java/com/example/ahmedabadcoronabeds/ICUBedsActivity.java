package com.example.ahmedabadcoronabeds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmedabadcoronabeds.Models.Hospital;
import com.example.ahmedabadcoronabeds.ViewHolder.HospitalHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ICUBedsActivity extends AppCompatActivity
{

    private DatabaseReference hospitalRef;
    private RecyclerView r_icu;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_c_u_beds);

        hospitalRef= FirebaseDatabase.getInstance().getReference().child("Hospital");
        r_icu=findViewById(R.id.r_icu);
        r_icu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        r_icu.setLayoutManager(layoutManager);



    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<Hospital> options = new FirebaseRecyclerOptions.Builder<Hospital>()
                .setQuery(hospitalRef,Hospital.class)
                .build();

        FirebaseRecyclerAdapter<Hospital, HospitalHolder> adapter= new FirebaseRecyclerAdapter<Hospital, HospitalHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull HospitalHolder holder, int position, @NonNull Hospital model)
            {


                holder.l_name.setText(model.getHospital_name());
                holder.l_available.setText(model.getIcu_b());
                holder.total_available.setText(model.getTotal_icu_b());
                holder.l_time.setText(model.getTime_icu_b());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        AlertDialog.Builder infoDialog = new AlertDialog.Builder(ICUBedsActivity.this);
                        infoDialog.setTitle(model.getHospital_name());
                        infoDialog.setMessage("Address:-"+model.getHospital_address()+"\n"+"Phone number:-"+model.getHospital_contactno());


                       // Toast.makeText(ICUBedsActivity.this, "Hello", Toast.LENGTH_SHORT).show();

                        infoDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                dialogInterface.cancel();

                            }
                        });

                        infoDialog.show();

                    }
                });

                







            }

            @NonNull
            @Override
            public HospitalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hospital,parent,false);
                HospitalHolder holder = new HospitalHolder(view);
                return  holder;
            }
        };

        r_icu.setAdapter(adapter);
        adapter.startListening();


    }
}