package com.riskyd.omahjamur.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KeranjangResponse extends BaseResponse {
    @SerializedName("data")
    public List<KeranjangModel> data;

    public static class KeranjangModel {
        @SerializedName("id_pengguna")
        public String idPengguna;

        @SerializedName("id_keranjang")
        public String idKeranjang;

        @SerializedName("id_produk")
        public String idProduk;

        public KeranjangModel(String idPengguna, String idKeranjang, String idProduk) {
            this.idPengguna = idPengguna;
            this.idKeranjang = idKeranjang;
            this.idProduk = idProduk;
        }

        public String getIdPengguna() {
            return idPengguna;
        }

        public void setIdPengguna(String idPengguna) {
            this.idPengguna = idPengguna;
        }

        public String getIdKeranjang() {
            return idKeranjang;
        }

        public void setIdKeranjang(String idKeranjang) {
            this.idKeranjang = idKeranjang;
        }

        public String getIdProduk() {
            return idProduk;
        }

        public void setIdProduk(String idProduk) {
            this.idProduk = idProduk;
        }
    }
}