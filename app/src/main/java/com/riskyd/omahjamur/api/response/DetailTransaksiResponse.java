package com.riskyd.omahjamur.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailTransaksiResponse extends BaseResponse {
    @SerializedName("data")
    public List<DetailTransaksiModel> data;

    public static class DetailTransaksiModel {
        @SerializedName("id_detail_transaksi")
        public String idDetailTransaksi;

        @SerializedName("id_transaksi")
        public String idTransaksi;

        @SerializedName("id_produk")
        public String idProduk;

        @SerializedName("jumlah")
        public String jumlah;

        @SerializedName("subtotal")
        public String subtotal;

        public DetailTransaksiModel(String idDetailTransaksi, String idTransaksi, String idProduk, String jumlah, String subtotal) {
            this.idDetailTransaksi = idDetailTransaksi;
            this.idTransaksi = idTransaksi;
            this.idProduk = idProduk;
            this.jumlah = jumlah;
            this.subtotal = subtotal;
        }

        public String getIdDetailTransaksi() {
            return idDetailTransaksi;
        }

        public void setIdDetailTransaksi(String idDetailTransaksi) {
            this.idDetailTransaksi = idDetailTransaksi;
        }

        public String getIdTransaksi() {
            return idTransaksi;
        }

        public void setIdTransaksi(String idTransaksi) {
            this.idTransaksi = idTransaksi;
        }

        public String getIdProduk() {
            return idProduk;
        }

        public void setIdProduk(String idProduk) {
            this.idProduk = idProduk;
        }

        public String getJumlah() {
            return jumlah;
        }

        public void setJumlah(String jumlah) {
            this.jumlah = jumlah;
        }

        public String getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(String subtotal) {
            this.subtotal = subtotal;
        }
    }
}