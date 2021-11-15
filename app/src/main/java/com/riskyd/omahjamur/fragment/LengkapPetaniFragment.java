package com.riskyd.omahjamur.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.riskyd.omahjamur.MainActivity;
import com.riskyd.omahjamur.R;
import com.riskyd.omahjamur.adapter.JenisProdukAdapter;
import com.riskyd.omahjamur.adapter.PetaniAdapter;
import com.riskyd.omahjamur.api.ApiClient;
import com.riskyd.omahjamur.api.ApiInterface;
import com.riskyd.omahjamur.api.response.AdminResponse;
import com.riskyd.omahjamur.api.response.JenisProdukResponse;
import com.riskyd.omahjamur.api.response.PetaniResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LengkapPetaniFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView rv;
    View view;
    ApiInterface apiInterface;

    public LengkapPetaniFragment() {
        // Required empty public constructor
    }

    public static LengkapPetaniFragment newInstance(String param1, String param2) {
        LengkapPetaniFragment fragment = new LengkapPetaniFragment();
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
        view = inflater.inflate(R.layout.fragment_lengkap_petani, container, false);

        rv  = view.findViewById(R.id.rv);
        apiInterface = ApiClient.getClient();

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        tampilPetani();

        return view;
    }

    private void tampilPetani() {
        apiInterface.get_petani().enqueue(new Callback<PetaniResponse>() {
            @Override
            public void onResponse(Call<PetaniResponse> call, Response<PetaniResponse> response) {
                if (response.body().status) {
                    List<PetaniResponse.PetaniModel> list = new ArrayList<>();

                    for (int i = 0; i < response.body().data.size(); i++) {
                        if (response.body().data.get(i).status.equals("1")) {
                            list.add(response.body().data.get(i));
                        }
                    }

                    rv.setAdapter(new PetaniAdapter(list, getContext(), apiInterface));
                }
            }

            @Override
            public void onFailure(Call<PetaniResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}