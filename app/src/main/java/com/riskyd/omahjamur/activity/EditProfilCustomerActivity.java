package com.riskyd.omahjamur.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.riskyd.omahjamur.R;
import com.riskyd.omahjamur.api.ApiClient;
import com.riskyd.omahjamur.api.ApiInterface;
import com.riskyd.omahjamur.api.RajaOngkirApiClient;
import com.riskyd.omahjamur.api.RajaOngkirApiInterface;
import com.riskyd.omahjamur.api.response.BaseResponse;
import com.riskyd.omahjamur.api.response.PenggunaResponse;
import com.riskyd.omahjamur.api.response.city.DataCity;
import com.riskyd.omahjamur.api.response.city.ResponseCity;
import com.riskyd.omahjamur.databinding.ActivityEditProfilCustomerBinding;
import com.riskyd.omahjamur.preference.AppPreference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfilCustomerActivity extends AppCompatActivity {

    ActivityEditProfilCustomerBinding binding;
    ApiInterface apiInterface;
    RajaOngkirApiInterface apiEndpoint;

    ArrayList<String> cityList = new ArrayList<>();
    ArrayList<String> cityIdList = new ArrayList<>();

    String selectedIdKota, selectedNamaKota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfilCustomerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        apiInterface = ApiClient.getClient();

        setSupportActionBar(binding.toolbar);

        apiInterface = ApiClient.getClient();
        apiEndpoint = RajaOngkirApiClient.getClient();

        PenggunaResponse.PenggunaModel p = AppPreference.getUser(this);

        if (p.peran.equals("admin")) {
            binding.lat.setVisibility(View.GONE);
            binding.al.setVisibility(View.GONE);
            binding.longi.setVisibility(View.GONE);
            binding.spinnerLayout.setVisibility(View.GONE);
            binding.telp.setVisibility(View.GONE);
        } else if (p.peran.equals("petani")) {
            binding.spinnerLayout.setVisibility(View.GONE);

        } else {
            binding.lat.setVisibility(View.GONE);
            binding.longi.setVisibility(View.GONE);
        }

        binding.usernameEt.setText(p.username);
        binding.alamatEt.setText(p.alamat);
        binding.noTelpEt.setText(p.noTelp);
        binding.emailEt.setText(p.email);
        binding.latitudeLokasiEt.setText(p.latitude);
        binding.longitudeokasiEt.setText(p.longitude);
        binding.passwordEt.setText(p.password);

        Glide.with(getApplicationContext())
                .load(getString(R.string.base_url) + getString(R.string.profile_link) + p.foto)
                .centerCrop()
                .into(binding.fotoPenggunaIv);

        apiEndpoint.getCity().enqueue(new Callback<ResponseCity>() {
            @Override
            public void onResponse(Call<ResponseCity> call, retrofit2.Response<ResponseCity> response) {
                if (response != null) {
                    List<DataCity> dt = response.body().getRajaongkir().getResults();

                    for (int i = 0; i < dt.size(); i++) {
                        cityIdList.add(dt.get(i).cityId);
                        cityList.add(dt.get(i).type + " " + dt.get(i).cityName);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditProfilCustomerActivity.this, android.R.layout.simple_spinner_item, cityList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.spinner.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ResponseCity> call, Throwable t) {
                Log.e("login", t.getMessage());
            }
        });

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedIdKota = cityIdList.get(i);
                selectedNamaKota = cityList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.simpanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (p.peran.equals("customer")) {
                    updateCust();
                } else if (p.peran.equals("petani")) {
                    updatePengrajin();
                } else {
                    updateAdmin();
                }
            }
        });
    }

    void updateAdmin() {
        apiInterface.editAdmin(
                AppPreference.getUser(this).idPengguna,
                binding.usernameEt.getText().toString().trim(),
                binding.passwordEt.getText().toString().trim(),
                binding.emailEt.getText().toString().trim()
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        apiInterface.getPenggunaByEmail(
                                binding.emailEt.getText().toString().trim()
                        ).enqueue(new Callback<PenggunaResponse>() {
                            @Override
                            public void onResponse(Call<PenggunaResponse> call, Response<PenggunaResponse> response) {
                                if (response != null) {
                                    if (response.body().status) {
                                        PenggunaResponse.PenggunaModel p = response.body().data.get(0);

                                        AppPreference.saveUser(EditProfilCustomerActivity.this, p);

                                        Toast.makeText(getApplicationContext(), "Edit Profil berhasil", Toast.LENGTH_SHORT).show();
                                        onBackPressed();
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Edit Profil gagal", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<PenggunaResponse> call, Throwable t) {
                                Log.e("login", t.getMessage());
                            }
                        });
                    } else {
                        Toast.makeText(EditProfilCustomerActivity.this, "Terjadi kesalahan.", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("daftar", t.getMessage());
            }
        });
    }

    void updatePengrajin() {
        apiInterface.editPengrajin(
                AppPreference.getUser(this).idPengguna,
                binding.usernameEt.getText().toString().trim(),
                binding.alamatEt.getText().toString().trim(),
                binding.passwordEt.getText().toString().trim(),
                binding.emailEt.getText().toString().trim(),
                binding.noTelpEt.getText().toString().trim(),
                binding.latitudeLokasiEt.getText().toString().trim(),
                binding.longitudeokasiEt.getText().toString().trim()
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        apiInterface.getPenggunaByEmail(
                                binding.emailEt.getText().toString().trim()
                        ).enqueue(new Callback<PenggunaResponse>() {
                            @Override
                            public void onResponse(Call<PenggunaResponse> call, Response<PenggunaResponse> response) {
                                if (response != null) {
                                    if (response.body().status) {
                                        PenggunaResponse.PenggunaModel p = response.body().data.get(0);

                                        AppPreference.saveUser(EditProfilCustomerActivity.this, p);

                                        Toast.makeText(getApplicationContext(), "Edit Profil berhasil", Toast.LENGTH_SHORT).show();
                                        onBackPressed();
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Edit Profil gagal", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<PenggunaResponse> call, Throwable t) {
                                Log.e("login", t.getMessage());
                            }
                        });
                    } else {
                        Toast.makeText(EditProfilCustomerActivity.this, "Terjadi kesalahan.", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("daftar", t.getMessage());
            }
        });
    }

    void updateCust() {
        apiInterface.editCust(
                AppPreference.getUser(this).idPengguna,
                binding.usernameEt.getText().toString().trim(),
                binding.alamatEt.getText().toString().trim(),
                binding.passwordEt.getText().toString().trim(),
                binding.emailEt.getText().toString().trim(),
                binding.noTelpEt.getText().toString().trim(),
                selectedIdKota,
                selectedNamaKota
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        apiInterface.getPenggunaByEmail(
                                binding.emailEt.getText().toString().trim()
                        ).enqueue(new Callback<PenggunaResponse>() {
                            @Override
                            public void onResponse(Call<PenggunaResponse> call, Response<PenggunaResponse> response) {
                                if (response != null) {
                                    if (response.body().status) {
                                        PenggunaResponse.PenggunaModel p = response.body().data.get(0);

                                        AppPreference.saveUser(EditProfilCustomerActivity.this, p);

                                        Toast.makeText(getApplicationContext(), "Edit Profil berhasil", Toast.LENGTH_SHORT).show();
                                        onBackPressed();
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Edit Profil gagal", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<PenggunaResponse> call, Throwable t) {
                                Log.e("login", t.getMessage());
                            }
                        });
                    } else {
                        Toast.makeText(EditProfilCustomerActivity.this, "Terjadi kesalahan.", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("daftar", t.getMessage());
            }
        });
    }

}