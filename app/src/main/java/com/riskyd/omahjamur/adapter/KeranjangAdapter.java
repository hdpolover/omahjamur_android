package com.riskyd.omahjamur.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.riskyd.omahjamur.R;
import com.riskyd.omahjamur.activity.CekOutActivity;
import com.riskyd.omahjamur.activity.CustomerKeranjangActivity;
import com.riskyd.omahjamur.activity.DetailPetaniActivity;
import com.riskyd.omahjamur.api.ApiClient;
import com.riskyd.omahjamur.api.ApiInterface;
import com.riskyd.omahjamur.api.Helper;
import com.riskyd.omahjamur.api.response.BaseResponse;
import com.riskyd.omahjamur.api.response.KeranjangResponse;
import com.riskyd.omahjamur.api.response.PenggunaResponse;
import com.riskyd.omahjamur.api.response.ProdukResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KeranjangAdapter extends RecyclerView.Adapter<KeranjangAdapter.ViewHolder> {
    private static List<KeranjangResponse.KeranjangModel> list;
    Context context;
    ApiInterface apiInterface;

    public KeranjangAdapter(List<KeranjangResponse.KeranjangModel> list, Context context) {
        KeranjangAdapter.list = list;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_keranjang, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        apiInterface = ApiClient.getClient();

        apiInterface.getDetailProduk(
                list.get(position).idProduk
        ).enqueue(new Callback<ProdukResponse>() {
            @Override
            public void onResponse(Call<ProdukResponse> call, Response<ProdukResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        ProdukResponse.ProdukModel p = response.body().data.get(0);

                        holder.namaProduk.setText(p.getNama());
                        holder.stok.setText(p.getStok() + " item tersisa");
                        holder.hargaProduk.setText(Helper.formatRupiah(Integer.parseInt(p.getHargaSatuan())));

                        Glide.with(context)
                                .load(context.getString(R.string.base_url) + context.getString(R.string.produk_link) + p.getGambar())
                                .centerCrop()
                                .into(holder.gambarProduk);

                        apiInterface.getDetailPengrajin(
                                p.getIdPengguna()
                        ).enqueue(new Callback<PenggunaResponse>() {
                            @Override
                            public void onResponse(Call<PenggunaResponse> call, Response<PenggunaResponse> response) {
                                if (response != null) {
                                    if (response.body().status) {
                                        PenggunaResponse.PenggunaModel p = response.body().data.get(0);

                                        holder.namaPengrajin.setText(p.getUsername());
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<PenggunaResponse> call, Throwable t) {
                                Log.e("login", t.getMessage());
                            }
                        });

                        holder.pengLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent i = new Intent(context, DetailPetaniActivity.class);
                                i.putExtra("id_pengguna", p.getIdPengguna());
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(i);
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<ProdukResponse> call, Throwable t) {
                Log.e("login", t.getMessage());
            }
        });

        holder.beliBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, CekOutActivity.class);
                i.putExtra("id_produk", list.get(position).getIdProduk());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

        holder.hapusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiInterface.hapusKeranjang(
                        list.get(position).getIdKeranjang()
                ).enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response != null) {
                            if (response.body().status) {
                                Toast.makeText(context, "Item di keranjang berhasil dihapus", Toast.LENGTH_SHORT).show();

                                Intent i = new Intent(context, CustomerKeranjangActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(i);
                                ((Activity)view.getContext()).finish();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        Log.e("login", t.getMessage());
                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView namaPengrajin, namaProduk, hargaProduk, stok;
        private ImageView gambarProduk;
        MaterialButton beliBtn, hapusBtn;
        LinearLayout pengLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaPengrajin = itemView.findViewById(R.id.namaPengrajinTv);
            gambarProduk = itemView.findViewById(R.id.gambarProduk);
            namaProduk = itemView.findViewById(R.id.judulProduk);
            hargaProduk = itemView.findViewById(R.id.harga);
            stok = itemView.findViewById(R.id.stokTv);
            beliBtn = itemView.findViewById(R.id.beliBtn);
            hapusBtn = itemView.findViewById(R.id.hapusKeranjang);
            pengLayout = itemView.findViewById(R.id.pengrajinLayout);
        }
    }
}

