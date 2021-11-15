package com.riskyd.omahjamur.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.riskyd.omahjamur.R;
import com.riskyd.omahjamur.api.ApiInterface;
import com.riskyd.omahjamur.api.response.PetaniResponse;
import com.riskyd.omahjamur.api.response.ProdukResponse;

import java.util.List;

public class ProdukAdapter extends RecyclerView.Adapter<ProdukAdapter.ViewHolder> {
    private static List<ProdukResponse.ProdukModel> list;
    Context context;

    public ProdukAdapter(List<ProdukResponse.ProdukModel> list, Context context) {
        ProdukAdapter.list = list;
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
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produk_petani, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Toast.makeText(context, "ptenai", Toast.LENGTH_SHORT).show();
            }
        });

        holder.judul.setText(list.get(position).getJudul());
        holder.stok.setText(list.get(position).getStok());
        holder.jenis.setText(list.get(position).getDeskripsiJp() + " " + list.get(position).getDeskripsiDjp());
        holder.harga.setText("Rp." + list.get(position).getHarga());

        Glide.with(context)
                .load(context.getString(R.string.base_url) + context.getString(R.string.produk_link) + list.get(position).getGambar())
                .centerCrop()
                .into(holder.iv);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView judul, harga, stok, jenis;
        private ImageView iv;
        private MaterialCardView cv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.judulTv);
            harga = itemView.findViewById(R.id.hargaTv);
            stok = itemView.findViewById(R.id.stokTv);
            jenis = itemView.findViewById(R.id.jenisTv);
            iv = itemView.findViewById(R.id.fotoProdukIv);
            cv = itemView.findViewById(R.id.cv);
        }
    }
}
