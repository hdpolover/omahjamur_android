package com.riskyd.omahjamur.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PembayaranResponse extends BaseResponse {
    @SerializedName("data")
    public List<PembayaranModel> data;

    public static class PembayaranModel {
        @SerializedName("id_pembayaran")
        public String idPembayaran;

        @SerializedName("id_transaksi")
        public String idTransaksi;

        @SerializedName("bukti")
        public String bukti;

        @SerializedName("tgl_transfer")
        public String tglTransfer;

        @SerializedName("bank_pengirim")
        public String bankPengirim;

        @SerializedName("nama_akun_pengirim")
        public String namaAkunPengirim;

        @SerializedName("status")
        public String status;

        @SerializedName("nominal_transfer")
        public String nominalTransfer;

        public PembayaranModel(String idPembayaran, String idTransaksi, String bukti, String tglTransfer, String bankPengirim, String namaAkunPengirim, String status, String nominalTransfer) {
            this.idPembayaran = idPembayaran;
            this.idTransaksi = idTransaksi;
            this.bukti = bukti;
            this.tglTransfer = tglTransfer;
            this.bankPengirim = bankPengirim;
            this.namaAkunPengirim = namaAkunPengirim;
            this.status = status;
            this.nominalTransfer = nominalTransfer;
        }

        public String getIdPembayaran() {
            return idPembayaran;
        }

        public void setIdPembayaran(String idPembayaran) {
            this.idPembayaran = idPembayaran;
        }

        public String getIdTransaksi() {
            return idTransaksi;
        }

        public void setIdTransaksi(String idTransaksi) {
            this.idTransaksi = idTransaksi;
        }

        public String getBukti() {
            return bukti;
        }

        public void setBukti(String bukti) {
            this.bukti = bukti;
        }

        public String getTglTransfer() {
            return tglTransfer;
        }

        public void setTglTransfer(String tglTransfer) {
            this.tglTransfer = tglTransfer;
        }

        public String getBankPengirim() {
            return bankPengirim;
        }

        public void setBankPengirim(String bankPengirim) {
            this.bankPengirim = bankPengirim;
        }

        public String getNamaAkunPengirim() {
            return namaAkunPengirim;
        }

        public void setNamaAkunPengirim(String namaAkunPengirim) {
            this.namaAkunPengirim = namaAkunPengirim;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getNominalTransfer() {
            return nominalTransfer;
        }

        public void setNominalTransfer(String nominalTransfer) {
            this.nominalTransfer = nominalTransfer;
        }
    }
}