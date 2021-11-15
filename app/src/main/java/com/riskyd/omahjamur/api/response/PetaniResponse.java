package com.riskyd.omahjamur.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PetaniResponse extends BaseResponse {
    @SerializedName("data")
    public List<PetaniModel> data;

    public static class PetaniModel {
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

        @SerializedName("latitude")
        public String latitude;

        @SerializedName("longitude")
        public String longitude;

        @SerializedName("status")
        public String status;

        public PetaniModel(String idPetani, String idPengguna, String nama, String alamat, String foto, String latitude, String longitude, String status) {
            this.idPetani = idPetani;
            this.idPengguna = idPengguna;
            this.nama = nama;
            this.alamat = alamat;
            this.foto = foto;
            this.latitude = latitude;
            this.longitude = longitude;
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

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}