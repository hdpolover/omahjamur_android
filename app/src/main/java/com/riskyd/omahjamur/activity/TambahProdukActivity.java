package com.riskyd.omahjamur.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.riskyd.omahjamur.R;
import com.riskyd.omahjamur.adapter.JenisProdukAdapter;
import com.riskyd.omahjamur.api.ApiClient;
import com.riskyd.omahjamur.api.ApiInterface;
import com.riskyd.omahjamur.api.response.BaseResponse;
import com.riskyd.omahjamur.api.response.DetailJenisProdukResponse;
import com.riskyd.omahjamur.api.response.JenisProdukResponse;
import com.riskyd.omahjamur.api.response.PenggunaResponse;
import com.riskyd.omahjamur.api.response.PetaniResponse;
import com.riskyd.omahjamur.databinding.ActivityTambahProdukBinding;
import com.riskyd.omahjamur.databinding.ActivityTransaksiBinding;
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

public class TambahProdukActivity extends AppCompatActivity {

    ActivityTambahProdukBinding binding;
    ApiInterface apiInterface;
    ArrayList<String> djp;
    ArrayList<String> djpId;

    Uri produkImg;

    int selectedItem = 0;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTambahProdukBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        djp = new ArrayList<>();
        djpId = new ArrayList<>();

        apiInterface = ApiClient.getClient();

        getSpinnerData();

        setSupportActionBar(binding.toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedItem = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.pilihFotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(TambahProdukActivity.this)
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
        String judul = binding.judulEt.getText().toString().trim();
        String desc = binding.deskripsiEt.getText().toString().trim();
        String harga = binding.hargaEt.getText().toString().trim();
        String stok = binding.stokEt.getText().toString().trim();

        if (judul.isEmpty() || desc.isEmpty() || harga.isEmpty() || stok.isEmpty() || produkImg == null) {
            Toast.makeText(TambahProdukActivity.this, "Ada field yang masih kosong. Silakan isi terlebih dahulu.", Toast.LENGTH_LONG).show();
         } else {
            progressDialog = new ProgressDialog(TambahProdukActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.setTitle("Pesan");
            progressDialog.setMessage("Mohon tunggu sebentar...");
            progressDialog.show();

                RequestBody judulR = RequestBody.create(MediaType.parse("text/plain"), judul);
                RequestBody descR = RequestBody.create(MediaType.parse("text/plain"), desc);
                RequestBody hargaR = RequestBody.create(MediaType.parse("text/plain"), harga);
                RequestBody stokR = RequestBody.create(MediaType.parse("text/plain"), stok);
            RequestBody id = RequestBody.create(MediaType.parse("text/plain"), djpId.get(selectedItem));


                //image
                File file = new File(produkImg.getPath());
                RequestBody reqFile =  RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part f =  MultipartBody.Part.createFormData("image", file.getName(), reqFile);

            PenggunaResponse.PenggunaModel u = AppPreference.getUser(TambahProdukActivity.this);
            apiInterface.getDetailPetani(u.idPengguna).enqueue(new Callback<PetaniResponse>() {
                @Override
                public void onResponse(Call<PetaniResponse> call, Response<PetaniResponse> response) {
                    if (response.body().status) {
                        PetaniResponse.PetaniModel pm = response.body().data.get(0);

                        RequestBody idPetani = RequestBody.create(MediaType.parse("text/plain"), pm.getIdPetani());

                        apiInterface.tambahProduk(
                                idPetani,
                                id,
                                judulR,
                                descR,
                                hargaR,
                                stokR,
                                f
                        ).enqueue(new Callback<BaseResponse>() {
                            @Override
                            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                                if (response != null) {
                                    if (response.body().status) {
                                        if (progressDialog.isShowing()) {
                                            progressDialog.dismiss();
                                        }

                                        onBackPressed();
                                        Toast.makeText(TambahProdukActivity.this, "Tambah Produk berhasil.", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(TambahProdukActivity.this, "Terjadi kesalahan.", Toast.LENGTH_LONG).show();
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

                @Override
                public void onFailure(Call<PetaniResponse> call, Throwable t) {
                    Toast.makeText(TambahProdukActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri fileUri = data.getData();

            produkImg = fileUri;
            binding.fotoProdukIv.setImageURI(fileUri);
        }
    }

    private void getSpinnerData() {
        apiInterface.getDjp().enqueue(new Callback<DetailJenisProdukResponse>() {
            @Override
            public void onResponse(Call<DetailJenisProdukResponse> call, Response<DetailJenisProdukResponse> response) {
                if (response.body().status) {
                    List<DetailJenisProdukResponse.DetailJenisProdukModel> djpList = response.body().data;

                    for (int i = 0; i < djpList.size(); i++) {
                        djp.add(djpList.get(i).getDeskripsiJp() + " " + djpList.get(i).getDeskripsiDjp());
                        djpId.add(djpList.get(i).getIdDetailJenisProduk());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(TambahProdukActivity.this, android.R.layout.simple_spinner_item, djp);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.spinner.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<DetailJenisProdukResponse> call, Throwable t) {
                Toast.makeText(TambahProdukActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}