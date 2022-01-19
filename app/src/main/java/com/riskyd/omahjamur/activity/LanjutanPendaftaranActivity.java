package com.riskyd.omahjamur.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.riskyd.omahjamur.LoginActivity;
import com.riskyd.omahjamur.MainActivity;
import com.riskyd.omahjamur.api.ApiClient;
import com.riskyd.omahjamur.api.ApiInterface;
import com.riskyd.omahjamur.api.RajaOngkirApiClient;
import com.riskyd.omahjamur.api.RajaOngkirApiInterface;
import com.riskyd.omahjamur.api.response.BaseResponse;
import com.riskyd.omahjamur.api.response.PenggunaResponse;
import com.riskyd.omahjamur.api.response.city.DataCity;
import com.riskyd.omahjamur.api.response.city.ResponseCity;
import com.riskyd.omahjamur.databinding.ActivityLanjutanPendaftaranBinding;
import com.riskyd.omahjamur.preference.AppPreference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LanjutanPendaftaranActivity extends AppCompatActivity {
    ActivityLanjutanPendaftaranBinding binding;
    private Uri imgUser;
    String email;
    String peran;
    String nama;
    String alamat;

    ApiInterface apiInterface;
    Context context;

    ProgressDialog progressDialog;

    RajaOngkirApiInterface apiEndpoint;

    ArrayList<String> cityList = new ArrayList<>();
    ArrayList<String> cityIdList = new ArrayList<>();

    String selectedIdKota, selectedNamaKota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLanjutanPendaftaranBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        apiInterface = ApiClient.getClient();
        apiEndpoint = RajaOngkirApiClient.getClient();

        email = getIntent().getStringExtra("email");
        peran = getIntent().getStringExtra("peran");

        if (peran.equals("customer")) {
            binding.lat.setVisibility(View.GONE);
            binding.longi.setVisibility(View.GONE);
        } else {
            binding.spinnerLayout.setVisibility(View.GONE);
        }

        apiEndpoint.getCity().enqueue(new Callback<ResponseCity>() {
            @Override
            public void onResponse(Call<ResponseCity> call, Response<ResponseCity> response) {
                if (response != null) {
                    List<DataCity> dt = response.body().getRajaongkir().getResults();

                    for (int i = 0; i < dt.size(); i++) {
                        cityIdList.add(dt.get(i).cityId);
                        cityList.add(dt.get(i).type + " " + dt.get(i).cityName);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(LanjutanPendaftaranActivity.this, android.R.layout.simple_spinner_item, cityList);
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

        binding.pilihFotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(LanjutanPendaftaranActivity.this)
                        .compress(3000)
                        .start();
            }
        });

        binding.simpanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkData();
            }
        });
    }

    private void checkData() {
        boolean cekNama = true;
        boolean cekAlamat = true;
        boolean cekImg = true;

        if (binding.usernameEt.getText().toString().isEmpty()) {
            binding.usernameEt.setError("Mohon isi data berikut");
            cekNama = false;
        }

        if (binding.alamatEt.getText().toString().isEmpty()) {
            binding.alamatEt.setError("Mohon isi data berikut");
            cekAlamat = false;
        }

        if (imgUser == null) {
            Toast.makeText(LanjutanPendaftaranActivity.this, "Foto profil harus dipilih.", Toast.LENGTH_SHORT).show();
            cekImg = false;
        }

        if (cekNama && cekAlamat && cekImg) {
            if (peran.equals("customer")) {
                daftarkanCust(email,
                        binding.usernameEt.getText().toString().trim(),
                        "",
                        binding.alamatEt.getText().toString().trim(),
                        peran,
                        binding.noTelpEt.getText().toString().trim(),
                        "",
                        "");
            } else {
                daftarkanPetani(
                        email,
                        binding.usernameEt.getText().toString().trim(),
                        "",
                        binding.alamatEt.getText().toString().trim(),
                        peran,
                        binding.noTelpEt.getText().toString().trim(),
                        binding.latitudeLokasiEt.getText().toString().trim(),
                        binding.longitudeokasiEt.getText().toString().trim()
                );
            }

        }
    }

    private void daftarkanCust(String email, String username, String password, String alamat, String peran, String noTelp, String lat, String longi) {
        progressDialog = new ProgressDialog(LanjutanPendaftaranActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Pesan");
        progressDialog.setMessage("Mohon tunggu sebentar...");
        progressDialog.show();

        RequestBody emailR = RequestBody.create(MediaType.parse("text/plain"), email);
        RequestBody usernameR = RequestBody.create(MediaType.parse("text/plain"), username);
        RequestBody passwordR = RequestBody.create(MediaType.parse("text/plain"), password);
        RequestBody alR = RequestBody.create(MediaType.parse("text/plain"), alamat);
        RequestBody pR = RequestBody.create(MediaType.parse("text/plain"), peran);
        RequestBody nR = RequestBody.create(MediaType.parse("text/plain"), noTelp);
        RequestBody lR = RequestBody.create(MediaType.parse("text/plain"), lat);
        RequestBody lgR = RequestBody.create(MediaType.parse("text/plain"), longi);
        RequestBody idKotaR = RequestBody.create(MediaType.parse("text/plain"), selectedIdKota);
        RequestBody namaKotaR = RequestBody.create(MediaType.parse("text/plain"), selectedNamaKota);

        //image
        File file = new File(imgUser.getPath());
        RequestBody reqFile =  RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part f =  MultipartBody.Part.createFormData("image", file.getName(), reqFile);

        apiInterface.daftarCst(
                emailR,
                usernameR,
                passwordR,
                alR,
                pR,
                nR,
                lR,
                lgR,
                idKotaR,
                namaKotaR,
                f
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        Toast.makeText(LanjutanPendaftaranActivity.this, "Pendaftaran berhasil. Silakan masuk.", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(LanjutanPendaftaranActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LanjutanPendaftaranActivity.this, "Terjadi kesalahan.", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("daftar", t.getMessage());
            }
        });
    }


    private void daftarkanPetani(String email, String username, String password, String alamat, String peran, String noTelp, String lat, String longi) {
        progressDialog = new ProgressDialog(LanjutanPendaftaranActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Pesan");
        progressDialog.setMessage("Mohon tunggu sebentar...");
        progressDialog.show();

        RequestBody emailR = RequestBody.create(MediaType.parse("text/plain"), email);
        RequestBody usernameR = RequestBody.create(MediaType.parse("text/plain"), username);
        RequestBody passwordR = RequestBody.create(MediaType.parse("text/plain"), password);
        RequestBody alR = RequestBody.create(MediaType.parse("text/plain"), alamat);
        RequestBody pR = RequestBody.create(MediaType.parse("text/plain"), peran);
        RequestBody nR = RequestBody.create(MediaType.parse("text/plain"), noTelp);
        RequestBody lR = RequestBody.create(MediaType.parse("text/plain"), lat);
        RequestBody lgR = RequestBody.create(MediaType.parse("text/plain"), longi);
        RequestBody idKotaR = RequestBody.create(MediaType.parse("text/plain"), "0");
        RequestBody namaKotaR = RequestBody.create(MediaType.parse("text/plain"), "");

        //image
        File file = new File(imgUser.getPath());
        RequestBody reqFile =  RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part f =  MultipartBody.Part.createFormData("image", file.getName(), reqFile);

        apiInterface.daftarPtn(
                emailR,
                usernameR,
                passwordR,
                alR,
                pR,
                nR,
                lR,
                lgR,
                idKotaR,
                namaKotaR,
                f
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        Toast.makeText(LanjutanPendaftaranActivity.this, "Pendaftaran berhasil. Silakan masuk.", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(LanjutanPendaftaranActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LanjutanPendaftaranActivity.this, "Terjadi kesalahan.", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("daftar", t.getMessage());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            Uri fileUri = data.getData();
            imgUser = fileUri;
            binding.fotoPenggunaIv.setImageURI(fileUri);
        }
    }
}