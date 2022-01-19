package com.riskyd.omahjamur.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LaporanResponse extends BaseResponse {
    @SerializedName("data")
    public List<LaporanModel> data;

    public static class LaporanModel {
        @SerializedName("total_transaksi")
        public String totalTransaksi;

        @SerializedName("total_pendapatan")
        public String totalPendapatan;

        @SerializedName("total_jamur")
        public String totalJamur;

        @SerializedName("total_bibit")
        public String totalBibit;

        @SerializedName("total_baglog")
        public String totalBaglog;

        @SerializedName("total_olahan_makanan")
        public String totalOlahanMakanan;

        public LaporanModel(String totalTransaksi, String totalPendapatan, String totalJamur, String totalBibit, String totalBaglog, String totalOlahanMakanan) {
            this.totalTransaksi = totalTransaksi;
            this.totalPendapatan = totalPendapatan;
            this.totalJamur = totalJamur;
            this.totalBibit = totalBibit;
            this.totalBaglog = totalBaglog;
            this.totalOlahanMakanan = totalOlahanMakanan;
        }

        public String getTotalTransaksi() {
            return totalTransaksi;
        }

        public void setTotalTransaksi(String totalTransaksi) {
            this.totalTransaksi = totalTransaksi;
        }

        public String getTotalPendapatan() {
            return totalPendapatan;
        }

        public void setTotalPendapatan(String totalPendapatan) {
            this.totalPendapatan = totalPendapatan;
        }

        public String getTotalJamur() {
            return totalJamur;
        }

        public void setTotalJamur(String totalJamur) {
            this.totalJamur = totalJamur;
        }

        public String getTotalBibit() {
            return totalBibit;
        }

        public void setTotalBibit(String totalBibit) {
            this.totalBibit = totalBibit;
        }

        public String getTotalBaglog() {
            return totalBaglog;
        }

        public void setTotalBaglog(String totalBaglog) {
            this.totalBaglog = totalBaglog;
        }

        public String getTotalOlahanMakanan() {
            return totalOlahanMakanan;
        }

        public void setTotalOlahanMakanan(String totalOlahanMakanan) {
            this.totalOlahanMakanan = totalOlahanMakanan;
        }
    }
}