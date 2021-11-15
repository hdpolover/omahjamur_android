package com.riskyd.omahjamur.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailJenisProdukResponse extends BaseResponse {
    @SerializedName("data")
    public List<DetailJenisProdukModel> data;

    public static class DetailJenisProdukModel {
        @SerializedName("id_detail_jenis_produk")
        public String idDetailJenisProduk;

        @SerializedName("id_jenis_produk")
        public String idJenisProduk;

        @SerializedName("deskripsi_jp")
        public String deskripsiJp;

        @SerializedName("deskripsi_djp")
        public String deskripsiDjp;

        public DetailJenisProdukModel(String idDetailJenisProduk, String idJenisProduk, String deskripsiJp, String deskripsiDjp) {
            this.idDetailJenisProduk = idDetailJenisProduk;
            this.idJenisProduk = idJenisProduk;
            this.deskripsiJp = deskripsiJp;
            this.deskripsiDjp = deskripsiDjp;
        }

        public String getIdDetailJenisProduk() {
            return idDetailJenisProduk;
        }

        public void setIdDetailJenisProduk(String idDetailJenisProduk) {
            this.idDetailJenisProduk = idDetailJenisProduk;
        }

        public String getIdJenisProduk() {
            return idJenisProduk;
        }

        public void setIdJenisProduk(String idJenisProduk) {
            this.idJenisProduk = idJenisProduk;
        }

        public String getDeskripsiJp() {
            return deskripsiJp;
        }

        public void setDeskripsiJp(String deskripsiJp) {
            this.deskripsiJp = deskripsiJp;
        }

        public String getDeskripsiDjp() {
            return deskripsiDjp;
        }

        public void setDeskripsiDjp(String deskripsiDjp) {
            this.deskripsiDjp = deskripsiDjp;
        }
    }
}