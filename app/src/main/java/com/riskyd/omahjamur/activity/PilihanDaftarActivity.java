package com.riskyd.omahjamur.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.riskyd.omahjamur.R;
import com.riskyd.omahjamur.databinding.ActivityPilihanDaftarBinding;

public class PilihanDaftarActivity extends AppCompatActivity {
    ActivityPilihanDaftarBinding binding;
    String id_role = "3";
    String peran = "Customer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPilihanDaftarBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        String email = getIntent().getStringExtra("email");

        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radioButtonCustomer:
                        id_role = "3";
                        peran = "Customer";
                        break;
                    case R.id.radioButtonPetani:
                        id_role = "2";
                        peran = "Petani";
                        break;
                }
            }
        });

        binding.pilihDaftarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PilihanDaftarActivity.this);
                builder.setTitle("Daftar sebagai " + peran + "?");
                builder.setMessage("Anda telah memilih peran sebagai " + peran + ". Anda tidak dapat mengubahnya lagi setelah proses ini. Lanjut?");
                builder.setPositiveButton("LANJUTKAN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent in = new Intent(PilihanDaftarActivity.this, PendaftaranPetaniActivity.class);
                        in.putExtra("email", email);
                        in.putExtra("id_role", id_role);
                        startActivity(in);
                    }
                });
                builder.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(PilihanDaftarActivity.this, "Dibatalkan", Toast.LENGTH_SHORT).show();
                    }
                });

                //NOW, WE CREATE THE ALERT DIALG OBJECT
                AlertDialog ad = builder.create();

                //FINALLY, WE SHOW THE DIALOG
                ad.show();

            }
        });
    }
}