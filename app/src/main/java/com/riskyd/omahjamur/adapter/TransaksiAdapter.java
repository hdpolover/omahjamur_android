package com.riskyd.omahjamur.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.riskyd.omahjamur.R;
import com.riskyd.omahjamur.activity.TransaksiDetailActivity;
import com.riskyd.omahjamur.api.Helper;
import com.riskyd.omahjamur.api.response.TransaksiResponse;

import java.util.List;

public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.ViewHolder> {
    private static List<TransaksiResponse.TransaksiModel> list;
    Context context;

    public TransaksiAdapter(List<TransaksiResponse.TransaksiModel> list, Context context) {
        TransaksiAdapter.list = list;
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
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaksi, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, TransaksiDetailActivity.class);
                i.putExtra("id_transaksi", list.get(position).getIdTransaksi());
                i.putExtra("status", list.get(position).getStatusPengiriman());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

        holder.id.setText(list.get(position).getIdTransaksi());
        holder.tgl.setText(list.get(position).getTglTransaksi());
        holder.total.setText(Helper.formatRupiah(Integer.parseInt(list.get(position).getTotal())));

        int status = Integer.parseInt(list.get(position).getStatusPengiriman());

        String detailStatus = "";
        switch (status) {
            case 0:
                detailStatus = "proses pembayaran";
                break;
            case 1:
                detailStatus = "sedang diproses";
                break;
            case 2:
                detailStatus = "dalam pengiriman";
                break;
            case 3:
                detailStatus = "selesai";
                break;
        }

        holder.chip.setText(detailStatus);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView id, tgl, total;
        private MaterialCardView cv;
        Chip chip;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.idTransaksiTv);
            tgl = itemView.findViewById(R.id.tglTransaksiTv);
            chip = itemView.findViewById(R.id.chipStatusPengiriman);
            total = itemView.findViewById(R.id.totalTv);
            cv = itemView.findViewById(R.id.cv);
        }
    }
}

