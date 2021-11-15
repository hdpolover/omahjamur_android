package com.riskyd.omahjamur.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdminResponse extends BaseResponse {
    @SerializedName("data")
    public List<AdminModel> data;

    public static class AdminModel {
        @SerializedName("id_admin")
        public String idAdmin;

        @SerializedName("id_pengguna")
        public String idPengguna;

        @SerializedName("foto")
        public String foto;

        @SerializedName("nama")
        public String nama;

        public AdminModel(String idAdmin, String idPengguna, String foto, String nama) {
            this.idAdmin = idAdmin;
            this.idPengguna = idPengguna;
            this.foto = foto;
            this.nama = nama;
        }

        public String getIdAdmin() {
            return idAdmin;
        }

        public void setIdAdmin(String idAdmin) {
            this.idAdmin = idAdmin;
        }

        public String getIdPengguna() {
            return idPengguna;
        }

        public void setIdPengguna(String idPengguna) {
            this.idPengguna = idPengguna;
        }

        public String getFoto() {
            return foto;
        }

        public void setFoto(String foto) {
            this.foto = foto;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }
    }
}