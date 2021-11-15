package com.riskyd.omahjamur.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JenisProdukResponse extends BaseResponse {
    @SerializedName("data")
    public List<JenisProdukModel> data;

    public static class JenisProdukModel {
        @SerializedName("id_jenis_produk")
        public String idJenisProduk;

        @SerializedName("deskripsi")
        public String deskripsi;

        public JenisProdukModel(String idJenisProduk, String deskripsi) {
            this.idJenisProduk = idJenisProduk;
            this.deskripsi = deskripsi;
        }

        public String getIdJenisProduk() {
            return idJenisProduk;
        }

        public void setIdJenisProduk(String idJenisProduk) {
            this.idJenisProduk = idJenisProduk;
        }

        public String getDeskripsi() {
            return deskripsi;
        }

        public void setDeskripsi(String deskripsi) {
            this.deskripsi = deskripsi;
        }
    }
}