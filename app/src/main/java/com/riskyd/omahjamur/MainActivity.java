package com.riskyd.omahjamur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.riskyd.omahjamur.activity.DaftarPetaniActivity;
import com.riskyd.omahjamur.activity.DaftarProdukPetaniActivity;
import com.riskyd.omahjamur.activity.KatalogProdukCustomerActivity;
import com.riskyd.omahjamur.activity.LaporanPetaniActivity;
import com.riskyd.omahjamur.activity.PengaturanActivity;
import com.riskyd.omahjamur.activity.ProfilActivity;
import com.riskyd.omahjamur.activity.TransaksiActivity;
import com.riskyd.omahjamur.api.ApiClient;
import com.riskyd.omahjamur.api.ApiInterface;
import com.riskyd.omahjamur.api.response.AdminResponse;
import com.riskyd.omahjamur.api.response.CustomerResponse;
import com.riskyd.omahjamur.api.response.PenggunaResponse;
import com.riskyd.omahjamur.api.response.PetaniResponse;
import com.riskyd.omahjamur.databinding.ActivityMainBinding;
import com.riskyd.omahjamur.preference.AppPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        String idRole = user.idRole;

        if (idRole.equals("1")) {
            binding.peranPenggunaChip.setText("Admin");

            //CV a
            binding.aTv.setText("Petani");
            binding.aCv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, DaftarPetaniActivity.class));
                }
            });

            //cv b
            binding.bTv.setText("Katalog Produk");

            //cv c
            binding.cTv.setText("Transaksi");
            binding.cCv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, TransaksiActivity.class));
                }
            });

            //CV d
            binding.dTv.setText("Laporan");

            //cv e
            binding.eTv.setText("Pengaturan");
            binding.eCv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, PengaturanActivity.class));
                }
            });

            //cv f
            binding.fTv.setText("Profil");
            binding.fCv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, ProfilActivity.class));
                }
            });
        } else if(idRole.equals("2")) {
            binding.peranPenggunaChip.setText("Petani");

            //CV a
            binding.aTv.setText("Produk");
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
            binding.aCv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        startActivity(new Intent(MainActivity.this, KatalogProdukCustomerActivity.class));
                }
            });

            //cv b
            binding.bTv.setText("Petani");
            binding.bCv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        startActivity(new Intent(MainActivity.this, TransaksiActivity.class));
                }
            });

            //cv c
            binding.cTv.setText("Keranjang");
            binding.cCv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, LaporanPetaniActivity.class));
                }
            });

            //CV d
            binding.dTv.setText("Transaksi");
            binding.dCv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, TransaksiActivity.class));
                }
            });

            //cv e
            binding.eTv.setText("Profil");
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

        String id = user.idPengguna;

        String idRole = user.idRole;

        if (idRole.equals("1")) {
            apiInterface.detail_admin(id).enqueue(new Callback<AdminResponse>() {
                @Override
                public void onResponse(Call<AdminResponse> call, Response<AdminResponse> response) {
                    if (response.body().status) {
                        binding.namaUserTv.setText(response.body().data.get(0).nama);

                        Glide.with(getApplicationContext())
                                .load(getString(R.string.base_url) + getString(R.string.profile_link) + response.body().data.get(0).foto)
                                .centerCrop()
                                .placeholder(R.drawable.gambar)
                                .into(binding.fotoUserIv);
                    }
                }

                @Override
                public void onFailure(Call<AdminResponse> call, Throwable t) {
                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else if (idRole.equals("2")) {
            apiInterface.getDetailPetani(id).enqueue(new Callback<PetaniResponse>() {
                @Override
                public void onResponse(Call<PetaniResponse> call, Response<PetaniResponse> response) {
                    if (response.body().status) {
                        binding.namaUserTv.setText(response.body().data.get(0).nama);

                        Glide.with(getApplicationContext())
                                .load(getString(R.string.base_url) + getString(R.string.profile_link) + response.body().data.get(0).foto)
                                .centerCrop()
                                .placeholder(R.drawable.gambar)
                                .into(binding.fotoUserIv);

                        if (response.body().data.get(0).status.equals("0")) {
                            belumValidasi = true;
                            binding.warningCv.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<PetaniResponse> call, Throwable t) {
                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            apiInterface.getDetailCustomer(id).enqueue(new Callback<CustomerResponse>() {
                @Override
                public void onResponse(Call<CustomerResponse> call, Response<CustomerResponse> response) {
                    if (response.body().status) {
                        binding.namaUserTv.setText(response.body().data.get(0).nama);

                        Glide.with(getApplicationContext())
                                .load(getString(R.string.base_url) + getString(R.string.profile_link) + response.body().data.get(0).foto)
                                .centerCrop()
                                .placeholder(R.drawable.gambar)
                                .into(binding.fotoUserIv);
                    }
                }

                @Override
                public void onFailure(Call<CustomerResponse> call, Throwable t) {
                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }


    }
}