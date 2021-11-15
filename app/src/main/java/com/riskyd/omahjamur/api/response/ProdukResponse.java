package com.riskyd.omahjamur.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProdukResponse extends BaseResponse {
    @SerializedName("data")
    public List<ProdukModel> data;

    public static class ProdukModel {
        @SerializedName("id_produk")
        public String idProduk;

        @SerializedName("id_petani")
        public String idPetani;

        @SerializedName("id_detail_jenis_produk")
        public String idDetailJenisProduk;

        @SerializedName("judul")
        public String judul;

        @SerializedName("deskripsi")
        public String deskripsi;

        @SerializedName("harga")
        public String harga;

        @SerializedName("stok")
        public String stok;

        @SerializedName("gambar")
        public String gambar;

        @SerializedName("deskripsi_djp")
        public String deskripsiDjp;

        @SerializedName("deskripsi_jp")
        public String deskripsiJp;

        public ProdukModel(String idProduk, String idPetani, String idDetailJenisProduk, String judul, String deskripsi, String harga, String stok, String gambar, String deskripsiDjp, String deskripsiJp) {
            this.idProduk = idProduk;
            this.idPetani = idPetani;
            this.idDetailJenisProduk = idDetailJenisProduk;
            this.judul = judul;
            this.deskripsi = deskripsi;
            this.harga = harga;
            this.stok = stok;
            this.gambar = gambar;
            this.deskripsiDjp = deskripsiDjp;
            this.deskripsiJp = deskripsiJp;
        }

        public String getIdProduk() {
            return idProduk;
        }

        public void setIdProduk(String idProduk) {
            this.idProduk = idProduk;
        }

        public String getIdPetani() {
            return idPetani;
        }

        public void setIdPetani(String idPetani) {
            this.idPetani = idPetani;
        }

        public String getIdDetailJenisProduk() {
            return idDetailJenisProduk;
        }

        public void setIdDetailJenisProduk(String idDetailJenisProduk) {
            this.idDetailJenisProduk = idDetailJenisProduk;
        }

        public String getJudul() {
            return judul;
        }

        public void setJudul(String judul) {
            this.judul = judul;
        }

        public String getDeskripsi() {
            return deskripsi;
        }

        public void setDeskripsi(String deskripsi) {
            this.deskripsi = deskripsi;
        }

        public String getHarga() {
            return harga;
        }

        public void setHarga(String harga) {
            this.harga = harga;
        }

        public String getStok() {
            return stok;
        }

        public void setStok(String stok) {
            this.stok = stok;
        }

        public String getGambar() {
            return gambar;
        }

        public void setGambar(String gambar) {
            this.gambar = gambar;
        }

        public String getDeskripsiDjp() {
            return deskripsiDjp;
        }

        public void setDeskripsiDjp(String deskripsiDjp) {
            this.deskripsiDjp = deskripsiDjp;
        }

        public String getDeskripsiJp() {
            return deskripsiJp;
        }

        public void setDeskripsiJp(String deskripsiJp) {
            this.deskripsiJp = deskripsiJp;
        }
    }
}