package com.riskyd.omahjamur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.riskyd.omahjamur.activity.AdminDaftarPetaniActivity;
import com.riskyd.omahjamur.activity.CustomerDaftarPetaniActivity;
import com.riskyd.omahjamur.activity.CustomerKeranjangActivity;
import com.riskyd.omahjamur.activity.DaftarProdukPetaniActivity;
import com.riskyd.omahjamur.activity.KatalogProdukCustomerActivity;
import com.riskyd.omahjamur.activity.LaporanPetaniActivity;
import com.riskyd.omahjamur.activity.PengaturanActivity;
import com.riskyd.omahjamur.activity.ProfilActivity;
import com.riskyd.omahjamur.activity.TransaksiActivity;
import com.riskyd.omahjamur.api.ApiClient;
import com.riskyd.omahjamur.api.ApiInterface;
import com.riskyd.omahjamur.api.response.PenggunaResponse;
import com.riskyd.omahjamur.databinding.ActivityMainBinding;
import com.riskyd.omahjamur.preference.AppPreference;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    FirebaseAuth mAuth;
    Button signOutBtn;

    boolean belumValidasi = false;
    ApiInterface apiInterface;
    PenggunaResponse.PenggunaModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();
        apiInterface = ApiClient.getClient();
        user = AppPreference.getUser(getApplicationContext());

        binding.warningCv.setVisibility(View.GONE);

        binding.textViewVersion.setText("Ver " + BuildConfig.VERSION_NAME);

        getNama();
        setView();

//        signOutBtn = findViewById(R.id.signOutBtn);
//
//        signOutBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mAuth.signOut();
//                startActivity(new Intent(MainActivity.this, LoginActivity.class));
//            }
//        });
    }

    private void setView() {
        String peran = user.peran;

        if (peran.equals("admin")) {
            binding.peranPenggunaChip.setText("Admin");

            //CV a
            binding.aTv.setText("Petani");
            binding.aIv.setImageResource(R.drawable.umkm);
            binding.aCv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, AdminDaftarPetaniActivity.class));
                }
            });

            //cv b
            binding.bTv.setText("Katalog Produk");
            binding.bIv.setImageResource(R.drawable.jamur);
            binding.bCv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, KatalogProdukCustomerActivity.class));
                }
            });

            //cv c
            binding.cTv.setText("Transaksi");
            binding.cIv.setImageResource(R.drawable.transaksi);
            binding.cCv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, TransaksiActivity.class));
                }
            });


            //CV d
            binding.dTv.setText("Laporan");
            binding.dIv.setImageResource(R.drawable.laporan);
            binding.dCv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, LaporanPetaniActivity.class));
                }
            });

            //cv e
            binding.eTv.setText("Pengaturan");
            binding.eIv.setImageResource(R.drawable.pengaturan);
            binding.eCv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, PengaturanActivity.class));
                }
            });

            //cv f
            binding.fTv.setText("Profil");
            binding.fIv.setImageResource(R.drawable.profil);
            binding.fCv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, ProfilActivity.class));
                }
            });
        } else if(peran.equals("petani")) {
            binding.peranPenggunaChip.setText("Petani");

            //CV a
            binding.aTv.setText("Produk");
            binding.aIv.setImageResource(R.drawable.jamur);
            binding.aCv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (belumValidasi) {
                        Toast.makeText(getApplicationContext(), "Maaf! Anda belum bisa mengelola produk. Tunggu hingga validasi selesai.", Toast.LENGTH_SHORT).show();
                    } else {
                        startActivity(new Intent(MainActivity.this, DaftarProdukPetaniActivity.class));
                    }

                }
            });

            //cv b
            binding.bTv.setText("Transaksi");
            binding.bIv.setImageResource(R.drawable.transaksi);
            binding.bCv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (belumValidasi) {
                        Toast.makeText(getApplicationContext(), "Maaf! Anda belum bisa mengelola transaksi. Tunggu hingga validasi selesai.", Toast.LENGTH_SHORT).show();
                    } else {
                        startActivity(new Intent(MainActivity.this, TransaksiActivity.class));
                    }

                }
            });

            //cv c
            binding.cTv.setText("Laporan");
            binding.cIv.setImageResource(R.drawable.laporan);
            binding.cCv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (belumValidasi) {
                        Toast.makeText(getApplicationContext(), "Maaf! Anda belum bisa mengelola laporan. Tunggu hingga validasi selesai.", Toast.LENGTH_SHORT).show();
                    } else {
                        startActivity(new Intent(MainActivity.this, LaporanPetaniActivity.class));
                    }
                }
            });

            //CV d
            binding.dTv.setText("Profil");
            binding.dIv.setImageResource(R.drawable.profil);
            binding.dCv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, ProfilActivity.class));
                }
            });

            //cv e
            binding.eCv.setVisibility(View.GONE);

            //cv f
            binding.fCv.setVisibility(View.GONE);
        } else {
            binding.peranPenggunaChip.setText("Customer");

            //CV a
            binding.aTv.setText("Produk");
            binding.aIv.setImageResource(R.drawable.jamur);
            binding.aCv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        startActivity(new Intent(MainActivity.this, KatalogProdukCustomerActivity.class));
                }
            });

            //cv b
            binding.bTv.setText("Petani");
            binding.bIv.setImageResource(R.drawable.umkm);
            binding.bCv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        startActivity(new Intent(MainActivity.this, CustomerDaftarPetaniActivity.class));
                }
            });

            //cv c
            binding.cTv.setText("Keranjang");
            binding.cIv.setImageResource(R.drawable.keranjang);
            binding.cCv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, CustomerKeranjangActivity.class));
                }
            });

            //CV d
            binding.dTv.setText("Transaksi");
            binding.dIv.setImageResource(R.drawable.transaksi);
            binding.dCv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, TransaksiActivity.class));
                }
            });

            //cv e
            binding.eTv.setText("Profil");
            binding.eIv.setImageResource(R.drawable.profil);
            binding.eCv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, ProfilActivity.class));
                }
            });

            //cv f
            binding.fCv.setVisibility(View.GONE);
        }
    }

    private void getNama() {

        binding.namaUserTv.setText(user.getUsername());

        binding.peranPenggunaChip.setText(user.getPeran());

        Glide.with(getApplicationContext())
                .load(getString(R.string.base_url) + getString(R.string.profile_link) + user.getFoto())
                .centerCrop()
                .placeholder(R.drawable.gambar)
                .into(binding.fotoUserIv);

    }
}