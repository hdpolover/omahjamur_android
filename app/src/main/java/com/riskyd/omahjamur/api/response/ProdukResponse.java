package com.riskyd.omahjamur.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProdukResponse extends BaseResponse {
    @SerializedName("data")
    public List<ProdukModel> data;

    public static class ProdukModel {
        @SerializedName("id_pengguna")
        public String idPengguna;

        @SerializedName("id_produk")
        public String idProduk;

        @SerializedName("nama")
        public String nama;

        @SerializedName("deskripsi")
        public String deskripsi;

        @SerializedName("stok")
        public String stok;

        @SerializedName("harga_satuan")
        public String hargaSatuan;

        @SerializedName("gambar")
        public String gambar;

        @SerializedName("berat")
        public String berat;

        @SerializedName("kategori")
        public String kategori;

        public ProdukModel(String idPengguna, String idProduk, String nama, String deskripsi, String stok, String hargaSatuan, String gambar, String berat, String kategori) {
            this.idPengguna = idPengguna;
            this.idProduk = idProduk;
            this.nama = nama;
            this.deskripsi = deskripsi;
            this.stok = stok;
            this.hargaSatuan = hargaSatuan;
            this.gambar = gambar;
            this.berat = berat;
            this.kategori = kategori;
        }

        public String getIdPengguna() {
            return idPengguna;
        }

        public void setIdPengguna(String idPengguna) {
            this.idPengguna = idPengguna;
        }

        public String getIdProduk() {
            return idProduk;
        }

        public void setIdProduk(String idProduk) {
            this.idProduk = idProduk;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getDeskripsi() {
            return deskripsi;
        }

        public void setDeskripsi(String deskripsi) {
            this.deskripsi = deskripsi;
        }

        public String getStok() {
            return stok;
        }

        public void setStok(String stok) {
            this.stok = stok;
        }

        public String getHargaSatuan() {
            return hargaSatuan;
        }

        public void setHargaSatuan(String hargaSatuan) {
            this.hargaSatuan = hargaSatuan;
        }

        public String getGambar() {
            return gambar;
        }

        public void setGambar(String gambar) {
            this.gambar = gambar;
        }

        public String getBerat() {
            return berat;
        }

        public void setBerat(String berat) {
            this.berat = berat;
        }

        public String getKategori() {
            return kategori;
        }

        public void setKategori(String kategori) {
            this.kategori = kategori;
        }
    }
}