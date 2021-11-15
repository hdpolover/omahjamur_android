package com.riskyd.omahjamur.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.riskyd.omahjamur.R;
import com.riskyd.omahjamur.api.ApiInterface;
import com.riskyd.omahjamur.api.response.BaseResponse;
import com.riskyd.omahjamur.api.response.JenisProdukResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JenisProdukAdapter extends RecyclerView.Adapter<JenisProdukAdapter.ViewHolder> {
    private static List<JenisProdukResponse.JenisProdukModel> list;
    Context context;
    ApiInterface apiInterface;

    public JenisProdukAdapter(List<JenisProdukResponse.JenisProdukModel> list, Context context, ApiInterface apiInterface) {
        JenisProdukAdapter.list = list;
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
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_jenis_produk, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et = new EditText(context);
                new AlertDialog.Builder(context)
                        .setTitle("Edit Jenis Produk")
                        .setMessage("Tuliskan Deskripsi Jenis Produk")
                        .setView(et)
                        .setPositiveButton("SIMPAN", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String editTextInput = et.getText().toString();

                                apiInterface.edit_jp(list.get(position).getIdJenisProduk(), editTextInput).enqueue(new Callback<BaseResponse>() {
                                    @Override
                                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                                        if (response.body().status) {
                                            Toast.makeText(context, editTextInput + " berhasil diupdate", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("BATAL", null)
                        .create()
                        .show();
            }
        });

        holder.deskripsiJp.setText(list.get(position).getDeskripsi());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView deskripsiJp;
        private MaterialCardView cv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            deskripsiJp = itemView.findViewById(R.id.deskripsiJpTv);
            cv = itemView.findViewById(R.id.jpCv);
        }
    }

    public static List<JenisProdukResponse.JenisProdukModel> getList() {
        return list;
    }
}
