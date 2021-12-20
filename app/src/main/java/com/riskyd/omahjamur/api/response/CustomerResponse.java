package com.riskyd.omahjamur.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerResponse extends BaseResponse {
    @SerializedName("data")
    public List<CustomerModel> data;

    public static class CustomerModel {
        @SerializedName("id_petani")
        public String idPetani;

        @SerializedName("id_pengguna")
        public String idPengguna;

        @SerializedName("nama")
        public String nama;

        @SerializedName("alamat")
        public String alamat;

        @SerializedName("foto")
        public String foto;

        @SerializedName("status")
        public String status;

        public CustomerModel(String idPetani, String idPengguna, String nama, String alamat, String foto, String status) {
            this.idPetani = idPetani;
            this.idPengguna = idPengguna;
            this.nama = nama;
            this.alamat = alamat;
            this.foto = foto;
            this.status = status;
        }

        public String getIdPetani() {
            return idPetani;
        }

        public void setIdPetani(String idPetani) {
            this.idPetani = idPetani;
        }

        public String getIdPengguna() {
            return idPengguna;
        }

        public void setIdPengguna(String idPengguna) {
            this.idPengguna = idPengguna;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getAlamat() {
            return alamat;
        }

        public void setAlamat(String alamat) {
            this.alamat = alamat;
        }

        public String getFoto() {
            return foto;
        }

        public void setFoto(String foto) {
            this.foto = foto;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}