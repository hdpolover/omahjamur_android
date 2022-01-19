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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.button.MaterialButton;
import com.riskyd.omahjamur.R;
import com.riskyd.omahjamur.api.ApiClient;
import com.riskyd.omahjamur.api.ApiInterface;
import com.riskyd.omahjamur.api.response.BaseResponse;
import com.riskyd.omahjamur.api.response.ProdukResponse;
import com.riskyd.omahjamur.databinding.ActivityProdukEditBinding;
import com.riskyd.omahjamur.preference.AppPreference;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdukEditActivity extends AppCompatActivity {

    ActivityProdukEditBinding binding;
    ApiInterface apiInterface;

    MaterialButton simpanBtn, pilihFotoBtn;

    EditText namaEt, stokEt, hargaEt, deskirpsiEt, beratEt;
    Spinner spinner;
    ImageView fotoProdukIv;

    ArrayList<String> kategoriList = new ArrayList<>();

    Uri produkImg;
    int selectedItem = 0;
    String kategori;

    ProgressDialog progressDialog;

    String idProduk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProdukEditBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        apiInterface = ApiClient.getClient();

        idProduk = getIntent().getStringExtra("id_produk");

        simpanBtn = findViewById(R.id.simpanBtn);
        pilihFotoBtn = findViewById(R.id.pilihFotoBtn);
        namaEt = findViewById(R.id.namaProdukEt);
        stokEt = findViewById(R.id.stokEt);
        beratEt = findViewById(R.id.beratEt);
        hargaEt = findViewById(R.id.hargaEt);
        deskirpsiEt = findViewById(R.id.deskripsiEt);
        spinner = findViewById(R.id.spinner);
        fotoProdukIv = findViewById(R.id.fotoProdukIv);

        kategoriList.add("jamur");
        kategoriList.add("olahan makanan");
        kategoriList.add("bibit");
        kategoriList.add("baglog");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProdukEditActivity.this, android.R.layout.simple_spinner_item, kategoriList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(adapter);

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedItem = i;

                if (i == 0) {
                    kategori = "cobek";
                } else if (i == 1) {
                    kategori = "vas bunga";
                } else if (i == 2) {
                    kategori = "kendi";
                } else if (i == 3) {
                    kategori = "asbak";
                }else if (i == 4) {
                    kategori = "lampion";
                }else if (i == 5) {
                    kategori = "celengan";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.pilihFotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(ProdukEditActivity.this)
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

        getProdukDetail();

    }

    void getProdukDetail() {
        apiInterface.getDetailProduk(
                idProduk
        ).enqueue(new Callback<ProdukResponse>() {
            @Override
            public void onResponse(Call<ProdukResponse> call, Response<ProdukResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        ProdukResponse.ProdukModel p = response.body().data.get(0);

                        binding.namaProdukEt.setText(p.getNama());
                        binding.stokEt.setText(p.getStok());
                        binding.hargaEt.setText(p.getHargaSatuan());
                        binding.beratEt.setText(p.getBerat());
                        binding.deskripsiEt.setText(p.getDeskripsi());

                        kategori = p.getKategori();

                        Glide.with(getApplicationContext())
                                .load(getString(R.string.base_url) + getString(R.string.produk_link) + p.gambar)
                                .centerCrop()
                                .into(binding.fotoProdukIv);
                    }
                }
            }

            @Override
            public void onFailure(Call<ProdukResponse> call, Throwable t) {
                Log.e("login", t.getMessage());
            }
        });
    }

    private void checkData() {
        String nama = namaEt.getText().toString().trim();
        String stok = stokEt.getText().toString().trim();
        String harga = hargaEt.getText().toString().trim();
        String berat = beratEt.getText().toString().trim();
        String deskripsi = deskirpsiEt.getText().toString().trim();

        if (nama.isEmpty() || stok.isEmpty() || harga.isEmpty() || berat.isEmpty()) {
            Toast.makeText(ProdukEditActivity.this, "Ada field yang masih kosong. Silakan isi terlebih dahulu.", Toast.LENGTH_LONG).show();
        } else {
            apiInterface.editProduk(
                    idProduk,
                    nama,
                    deskripsi,
                    stok,
                    harga,
                    berat,
                    kategori
            ).enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if (response != null) {
                        if (response.body().status) {
                            onBackPressed();
                            Toast.makeText(ProdukEditActivity.this, "Edit produk berhasil.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ProdukEditActivity.this, "Terjadi kesalahan.", Toast.LENGTH_LONG).show();
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri fileUri = data.getData();

            produkImg = fileUri;
            binding.fotoProdukIv.setImageURI(fileUri);
        }
    }
}