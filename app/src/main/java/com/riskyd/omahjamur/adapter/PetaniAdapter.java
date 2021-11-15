package com.riskyd.omahjamur.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.riskyd.omahjamur.R;
import com.riskyd.omahjamur.api.ApiInterface;
import com.riskyd.omahjamur.api.response.BaseResponse;
import com.riskyd.omahjamur.api.response.JenisProdukResponse;
import com.riskyd.omahjamur.api.response.PetaniResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetaniAdapter extends RecyclerView.Adapter<PetaniAdapter.ViewHolder> {
    private static List<PetaniResponse.PetaniModel> list;
    Context context;
    ApiInterface apiInterface;

    public PetaniAdapter(List<PetaniResponse.PetaniModel> list, Context context, ApiInterface apiInterface) {
        PetaniAdapter.list = list;
        this.context = context;
        this.apiInterface = apiInterface;
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
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_petani, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Toast.makeText(context, "ptenai", Toast.LENGTH_SHORT).show();
            }
        });

        holder.nama.setText(list.get(position).getNama());
        holder.alamat.setText(list.get(position).getAlamat());

        Glide.with(context)
                .load(context.getString(R.string.base_url) + context.getString(R.string.profile_link) + list.get(position).getFoto())
                .centerCrop()
                .into(holder.iv);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nama, alamat;
        private ImageView iv;
        private MaterialCardView cv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.namaTv);
            alamat = itemView.findViewById(R.id.alamatTv);
            iv = itemView.findViewById(R.id.Iv);
            cv = itemView.findViewById(R.id.jpCv);
        }
    }

    public static List<PetaniResponse.PetaniModel> getList() {
        return list;
    }
}
