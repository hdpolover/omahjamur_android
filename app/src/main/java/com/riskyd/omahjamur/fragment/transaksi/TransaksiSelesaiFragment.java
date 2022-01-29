package com.riskyd.omahjamur.fragment.transaksi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.riskyd.omahjamur.R;
import com.riskyd.omahjamur.adapter.TransaksiAdapter;
import com.riskyd.omahjamur.api.ApiClient;
import com.riskyd.omahjamur.api.ApiInterface;
import com.riskyd.omahjamur.api.response.PenggunaResponse;
import com.riskyd.omahjamur.api.response.TransaksiResponse;
import com.riskyd.omahjamur.preference.AppPreference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransaksiSelesaiFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView rv;
    View view;
    ApiInterface apiInterface;

    LinearLayout noData;

    public TransaksiSelesaiFragment() {
        // Required empty public constructor
    }

    public static TransaksiSelesaiFragment newInstance(String param1, String param2) {
        TransaksiSelesaiFragment fragment = new TransaksiSelesaiFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_container_transaksi_selesai, container, false);

        rv  = view.findViewById(R.id.rv);
        apiInterface = ApiClient.getClient();

        noData = view.findViewById(R.id.noDataLayout);

        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setHasFixedSize(true);

        PenggunaResponse.PenggunaModel p = AppPreference.getUser(getContext());

        if (p.peran.equals("admin")) {
            tampilTr("");
        } else if (p.peran.equals("petani")) {
            tampilTrPetani(p.idPengguna);
        } else {
            tampilTr(p.idPengguna);
        }

        return view;
    }

    private void tampilTrPetani(String id) {
        apiInterface.getTrSelesaiPetani(id).enqueue(new Callback<TransaksiResponse>() {
            @Override
            public void onResponse(Call<TransaksiResponse> call, Response<TransaksiResponse> response) {
                if (response.body().status) {
                    List<TransaksiResponse.TransaksiModel> list = new ArrayList<>();

                    list.addAll(response.body().data);

                    rv.setAdapter(new TransaksiAdapter(list, getContext()));

                    if (list.isEmpty()) {
                        noData.setVisibility(View.VISIBLE);
                        rv.setVisibility(View.GONE);
                    } else {
                        noData.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<TransaksiResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void tampilTr(String id) {
        apiInterface.getTrSelesai(id).enqueue(new Callback<TransaksiResponse>() {
            @Override
            public void onResponse(Call<TransaksiResponse> call, Response<TransaksiResponse> response) {
                if (response.body().status) {
                    List<TransaksiResponse.TransaksiModel> list = new ArrayList<>();

                    list.addAll(response.body().data);

                    rv.setAdapter(new TransaksiAdapter(list, getContext()));

                    if (list.isEmpty()) {
                        noData.setVisibility(View.VISIBLE);
                        rv.setVisibility(View.GONE);
                    } else {
                        noData.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<TransaksiResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}