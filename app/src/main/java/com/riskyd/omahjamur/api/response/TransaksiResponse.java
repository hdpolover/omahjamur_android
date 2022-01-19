package com.riskyd.omahjamur.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransaksiResponse extends BaseResponse {
    @SerializedName("data")
    public List<TransaksiModel> data;

    public static class TransaksiModel {
        @SerializedName("id_pengguna")
        public String idPengguna;

        @SerializedName("id_transaksi")
        public String idTransaksi;

        @SerializedName("total")
        public String total;

        @SerializedName("tgl_transaksi")
        public String tglTransaksi;

        @SerializedName("no_resi")
        public String noResi;

        @SerializedName("kurir")
        public String kurir;

        @SerializedName("status_transaksi")
        public String statusTransaksi;

        @SerializedName("status_pengiriman")
        public String statusPengiriman;

        @SerializedName("ongkir")
        public String ongkir;

        public TransaksiModel(String idPengguna, String idTransaksi, String total, String tglTransaksi, String noResi, String kurir, String statusTransaksi, String statusPengiriman, String ongkir) {
            this.idPengguna = idPengguna;
            this.idTransaksi = idTransaksi;
            this.total = total;
            this.tglTransaksi = tglTransaksi;
            this.noResi = noResi;
            this.kurir = kurir;
            this.statusTransaksi = statusTransaksi;
            this.statusPengiriman = statusPengiriman;
            this.ongkir = ongkir;
        }

        public String getIdPengguna() {
            return idPengguna;
        }

        public void setIdPengguna(String idPengguna) {
            this.idPengguna = idPengguna;
        }

        public String getIdTransaksi() {
            return idTransaksi;
        }

        public void setIdTransaksi(String idTransaksi) {
            this.idTransaksi = idTransaksi;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getTglTransaksi() {
            return tglTransaksi;
        }

        public void setTglTransaksi(String tglTransaksi) {
            this.tglTransaksi = tglTransaksi;
        }

        public String getNoResi() {
            return noResi;
        }

        public void setNoResi(String noResi) {
            this.noResi = noResi;
        }

        public String getKurir() {
            return kurir;
        }

        public void setKurir(String kurir) {
            this.kurir = kurir;
        }

        public String getStatusTransaksi() {
            return statusTransaksi;
        }

        public void setStatusTransaksi(String statusTransaksi) {
            this.statusTransaksi = statusTransaksi;
        }

        public String getStatusPengiriman() {
            return statusPengiriman;
        }

        public void setStatusPengiriman(String statusPengiriman) {
            this.statusPengiriman = statusPengiriman;
        }

        public String getOngkir() {
            return ongkir;
        }

        public void setOngkir(String ongkir) {
            this.ongkir = ongkir;
        }
    }
}