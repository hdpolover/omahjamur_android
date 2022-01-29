package com.riskyd.omahjamur.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.riskyd.omahjamur.R;
import com.riskyd.omahjamur.api.ApiClient;
import com.riskyd.omahjamur.api.ApiInterface;
import com.riskyd.omahjamur.api.Helper;
import com.riskyd.omahjamur.api.RajaOngkirApiClient;
import com.riskyd.omahjamur.api.RajaOngkirApiInterface;
import com.riskyd.omahjamur.api.response.BaseResponse;
import com.riskyd.omahjamur.api.response.PenggunaResponse;
import com.riskyd.omahjamur.api.response.ProdukResponse;
import com.riskyd.omahjamur.api.response.cost.DataCost;
import com.riskyd.omahjamur.api.response.cost.DataType;
import com.riskyd.omahjamur.api.response.cost.ResponseCost;
import com.riskyd.omahjamur.databinding.ActivityCekOutBinding;
import com.riskyd.omahjamur.preference.AppPreference;

import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CekOutActivity extends AppCompatActivity {

    ActivityCekOutBinding binding;

    ApiInterface apiInterface;
    RajaOngkirApiInterface apiEndpoint;

    String idProduk;
    private int jumlahOrder = 1;
    int hargaSatuan = 0;
    String nama = "";
    String ongkir = "0", estimasi = "";
    int beratProduk = 0;

    String idTransaksi;

    int totalHarga = 0;
    int subtotal = 0;
    int stokLama = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCekOutBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.toolbar);

        apiInterface = ApiClient.getClient();
        apiEndpoint = RajaOngkirApiClient.getClient();

        PenggunaResponse.PenggunaModel p = AppPreference.getUser(this);

        idProduk = getIntent().getStringExtra("id_produk");

        cekOngkir("115", 1000);

        apiInterface.getDetailProduk(
                idProduk
        ).enqueue(new Callback<ProdukResponse>() {
            @Override
            public void onResponse(Call<ProdukResponse> call, Response<ProdukResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        ProdukResponse.ProdukModel p = response.body().data.get(0);
                        nama = p.getNama();

                        binding.namaProdukTv.setText(nama);
                        binding.hargaTv.setText(Helper.formatRupiah(Integer.parseInt(p.getHargaSatuan())));
                        binding.subTotalTv.setText(nama + " x " + jumlahOrder);
                        binding.beratSatuanTv.setText(p.getBerat() + " gram");

                        stokLama = Integer.parseInt(p.getStok());

                        hargaSatuan = Integer.parseInt(p.getHargaSatuan());
                        int harga = hargaSatuan * jumlahOrder;
                        subtotal = harga;
                        binding.hargaSubtotalTv.setText(Helper.formatRupiah(harga));

                        totalHarga = harga + Integer.parseInt(ongkir);

                        binding.totalHargaTv.setText(Helper.formatRupiah(harga + Integer.parseInt(ongkir)));

                        Glide.with(getApplicationContext())
                                .load(getString(R.string.base_url) + getString(R.string.produk_link) + p.gambar)
                                .centerCrop()
                                .into(binding.gambarProdukTv);
                    }
                }
            }

            @Override
            public void onFailure(Call<ProdukResponse> call, Throwable t) {
                Log.e("login", t.getMessage());
            }
        });

        binding.alamatTv.setText(p.getAlamat());
        binding.namaTv.setText(p.getUsername());
        binding.noHpTv.setText(p.getNoTelp());

        binding.editTextJumlahOrder.setText(String.valueOf(jumlahOrder));

        binding.materialButtonTambahJumlahOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int lama = jumlahOrder;
                jumlahOrder++;

                if (jumlahOrder > stokLama) {
                    jumlahOrder = lama;
                    Toast.makeText(getApplicationContext(), "Jumlah order melebihi stok. Tidak bisa.", Toast.LENGTH_SHORT).show();
                } else {
                    binding.editTextJumlahOrder.setText(String.valueOf(jumlahOrder));
                    int harga = hargaSatuan * jumlahOrder;
                    subtotal = harga;
                    binding.hargaSubtotalTv.setText(Helper.formatRupiah(harga));
                    binding.subTotalTv.setText(nama + " x " + jumlahOrder);

                    binding.totalHargaTv.setText(Helper.formatRupiah(harga + Integer.parseInt(ongkir)));

                    totalHarga = harga + Integer.parseInt(ongkir);
                }
            }
        });

        binding.materialButtonKurangJumlahOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jumlahOrder > 1) {
                    jumlahOrder--;
                    binding.editTextJumlahOrder.setText(String.valueOf(jumlahOrder));
                    int harga = hargaSatuan * jumlahOrder;
                    subtotal = harga;
                    binding.hargaSubtotalTv.setText("Rp." + harga);
                    binding.subTotalTv.setText(nama + " x " + jumlahOrder);

                    binding.totalHargaTv.setText(Helper.formatRupiah(harga + Integer.parseInt(ongkir)));

                    totalHarga = harga + Integer.parseInt(ongkir);
                }
            }
        });

        binding.bayarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CekOutActivity.this);
                builder.setTitle("Lanjutkan ke Pembayaran?");
                builder.setMessage("Pastikan data transaksi Anda sudah benar sebelum melanjutkan. Lanjut?");
                builder.setPositiveButton("LANJUTKAN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        simpanTr();
                    }
                });
                builder.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(CekOutActivity.this, "Dibatalkan", Toast.LENGTH_SHORT).show();
                    }
                });

                //NOW, WE CREATE THE ALERT DIALG OBJECT
                AlertDialog ad = builder.create();

                //FINALLY, WE SHOW THE DIALOG
                ad.show();
            }
        });


    }

    void cekOngkir(String idKota, int berat) {
        apiEndpoint.postCost(
                "255",
                idKota,
                berat,
                "jne"
        ).enqueue(new Callback<ResponseCost>() {
            @Override
            public void onResponse(Call<ResponseCost> call, Response<ResponseCost> response) {
                if (response != null) {
                    List<DataType> dt = response.body().getRajaongkir().getResults().get(0).getCosts();
                    List<DataCost> dc = dt.get(0).cost;

                    ongkir = dc.get(0).value.toString();
                    estimasi = dc.get(0).etd;

                    binding.ongkirTv.setText(Helper.formatRupiah(Integer.parseInt(ongkir)));
                    binding.estimasiTv.setText("Estimasi " + estimasi + " hari");
                    binding.biayaOngkirTv.setText(Helper.formatRupiah(Integer.parseInt(ongkir)));

                }
            }

            @Override
            public void onFailure(Call<ResponseCost> call, Throwable t) {
                Log.e("login", t.getMessage());
            }
        });

    }

    public static String generateString() {
        String uuid = "TR-" + UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        return uuid;
    }

    void simpanTr() {
        idTransaksi = generateString();

        apiInterface.simpanTransaksi(
                idTransaksi,
                AppPreference.getUser(this).idPengguna,
                totalHarga + "",
                ongkir
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        detailTransaksi(idTransaksi, idProduk, jumlahOrder, subtotal);
                        updateStokProduk();

                        Toast.makeText(getApplicationContext(), "Transaksi berhasil!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(CekOutActivity.this, TransaksiBayarActivity.class);
                        i.putExtra("id_transaksi", idTransaksi);
                        i.putExtra("total", totalHarga);
                        startActivity(i);
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("daftar", t.getMessage());
            }
        });
    }

    void updateStokProduk() {
        int stokBaru = stokLama - jumlahOrder;
        apiInterface.updateStok(
                idProduk,
                stokBaru + ""
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("daftar", t.getMessage());
            }
        });
    }


    void detailTransaksi(String idTransaksi, String idProduk, int jumlah, int subtotal) {
        apiInterface.simpanDetailTransaksi(
                idTransaksi,
                idProduk,
                subtotal + "",
                jumlah + ""
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response != null) {
                    if (response.body().status) {
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