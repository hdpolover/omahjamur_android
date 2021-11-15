package com.riskyd.omahjamur.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PenggunaResponse extends BaseResponse {
    @SerializedName("data")
    public List<PenggunaModel> data;

    public static class PenggunaModel {
        @SerializedName("id_pengguna")
        public String idPengguna;

        @SerializedName("id_role")
        public String idRole;

        @SerializedName("email")
        public String email;

        @SerializedName("password")
        public String password;

        @SerializedName("tanggal_dibuat")
        public String tanggalDibuat;

        @SerializedName("status")
        public String status;

    }
}