package com.riskyd.omahjamur.api;

import com.riskyd.omahjamur.api.response.BaseResponse;
import com.riskyd.omahjamur.api.response.DetailTransaksiResponse;
import com.riskyd.omahjamur.api.response.KeranjangResponse;
import com.riskyd.omahjamur.api.response.LaporanResponse;
import com.riskyd.omahjamur.api.response.PembayaranResponse;
import com.riskyd.omahjamur.api.response.PenggunaResponse;
import com.riskyd.omahjamur.api.response.ProdukResponse;
import com.riskyd.omahjamur.api.response.TransaksiResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("auth/daftar")
    Call<BaseResponse> daftar(
            @Field("email") String email,
            @Field("username") String username,
            @Field("password") String password,
            @Field("alamat") String alamat,
            @Field("peran") String peran,
            @Field("no_telp") String noTelp,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude
    );


    @Multipart
    @POST("auth/daftar_cust")
    Call<BaseResponse> daftarCst(
            @Part("email") RequestBody email,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("alamat") RequestBody alamat,
            @Part("peran") RequestBody peran,
            @Part("no_telp") RequestBody noTelp,
            @Part("latitude") RequestBody latitude,
            @Part("longitude") RequestBody longitude,
            @Part("id_kota") RequestBody idKota,
            @Part("nama_kota") RequestBody namaKota,
            @Part MultipartBody.Part image
    );

    @Multipart
    @POST("auth/daftar_petani")
    Call<BaseResponse> daftarPtn(
            @Part("email") RequestBody email,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("alamat") RequestBody alamat,
            @Part("peran") RequestBody peran,
            @Part("no_telp") RequestBody noTelp,
            @Part("latitude") RequestBody latitude,
            @Part("longitude") RequestBody longitude,
            @Part("id_kota") RequestBody idKota,
            @Part("nama_kota") RequestBody namaKota,
            @Part MultipartBody.Part image
    );

    @GET("petani/lengkap_petani")
    Call<PenggunaResponse> getPetaniLengkap(
    );

    @GET("petani/pengajuan_petani")
    Call<PenggunaResponse> getPetaniPengajuan(
    );

    @GET("pengguna")
    Call<PenggunaResponse> getPenggunaByEmail(
            @Query("email") String email
    );

    @GET("auth/login")
    Call<PenggunaResponse> login(
            @Query("email") String email,
            @Query("password") String password
    );

    @GET("petani")
    Call<PenggunaResponse> getDaftarPengrajin();

    @GET("pengguna/get_detail")
    Call<PenggunaResponse> getDetailPengrajin(
            @Query("id_pengguna") String idPengguna
    );

    @GET("produk")
    Call<ProdukResponse> getProdukPengrajin(
            @Query("id_pengguna") String idPengguna
    );

    @GET("produk/get_detail_produk")
    Call<ProdukResponse> getDetailProduk(
            @Query("id_produk") String idProduk
    );

    @GET("produk/get_kategori")
    Call<ProdukResponse> getKategoriProduk(
            @Query("kategori") String kategori
    );

    @Multipart
    @POST("produk/tambah")
    Call<BaseResponse> tambahProduk(
            @Part("nama") RequestBody nama,
            @Part("deskripsi") RequestBody deskripsi,
            @Part("stok") RequestBody stok,
            @Part("harga_satuan") RequestBody harga_satuan,
            @Part("berat") RequestBody berat,
            @Part("kategori") RequestBody kategori,
            @Part("id_pengguna") RequestBody id_pengguna,
            @Part MultipartBody.Part image
    );

    @FormUrlEncoded
    @POST("transaksi/simpan")
    Call<BaseResponse> simpanTransaksi(
            @Field("id_transaksi") String idTransaksi,
            @Field("id_pengguna") String idPengguna,
            @Field("total") String total,
            @Field("ongkir") String ongkir
    );

    @FormUrlEncoded
    @POST("detail_transaksi/simpan")
    Call<BaseResponse> simpanDetailTransaksi(
            @Field("id_transaksi") String idTransaksi,
            @Field("id_produk") String idProduk,
            @Field("subtotal") String subtotal,
            @Field("jumlah") String jumlah
    );

    @Multipart
    @POST("pembayaran/simpan")
    Call<BaseResponse> simpanPembayaran(
            @Part("id_transaksi") RequestBody idTransaksi,
            @Part("nama_akun_pengirim") RequestBody namaAkunPengirim,
            @Part("bank_pengirim") RequestBody bankPengirim,
            @Part("nominal_transfer") RequestBody nominalTransfer,
            @Part("tgl_transfer") RequestBody tglTransfer,
            @Part MultipartBody.Part image
    );

    @GET("transaksi/get_tr_berlangsung")
    Call<TransaksiResponse> getTrBerlangsung(
            @Query("id_pengguna") String idPengguna
    );

    @GET("transaksi/get_tr_selesai")
    Call<TransaksiResponse> getTrSelesai(
            @Query("id_pengguna") String idPengguna
    );

    @GET("transaksi/get_detail_transaksi")
    Call<TransaksiResponse> getTransaksi(
            @Query("id_transaksi") String idTransaksi
    );

    @GET("detail_transaksi/get_detail_transaksi")
    Call<DetailTransaksiResponse> getDetailTransaksi(
            @Query("id_transaksi") String idTransaksi
    );

    @GET("pembayaran/get_pembayaran")
    Call<PembayaranResponse> getPembayaran(
            @Query("id_transaksi") String idTransaksi
    );

    @GET("pembayaran/validasi")
    Call<BaseResponse> validasiPembayaran(
            @Query("id_pembayaran") String idPembayaran,
            @Query("id_transaksi") String idTransaksi
    );

    @GET("transaksi/update_resi")
    Call<BaseResponse> updateResi(
            @Query("no_resi") String noResi,
            @Query("id_transaksi") String idTransaksi
    );

    @GET("pengguna/edit_cust")
    Call<BaseResponse> editCust(
            @Query("id_pengguna") String idPengguna,
            @Query("username") String username,
            @Query("alamat") String alamat,
            @Query("password") String password,
            @Query("email") String email,
            @Query("no_telp") String noTelp,
            @Query("id_kota") String idKota,
            @Query("nama_kota") String namaKota
    );

    @GET("keranjang/get_keranjang")
    Call<KeranjangResponse> getKeranjang(
            @Query("id_pengguna") String idPengguna
    );

    @GET("keranjang/hapus")
    Call<BaseResponse> hapusKeranjang(
            @Query("id_keranjang") String idKeranjang
    );

    @FormUrlEncoded
    @POST("keranjang/simpan")
    Call<BaseResponse> simpanKeranjang(
            @Field("id_pengguna") String idPengguna,
            @Field("id_produk") String idProduk
    );


    @GET("transaksi/pesanan_sampai")
    Call<BaseResponse> pesananSampai(
            @Query("id_transaksi") String idTransaksi
    );

    @GET("pengguna")
    Call<PenggunaResponse> checkPengguna(
            @Query("email") String email
    );

    @FormUrlEncoded
    @POST("petani/validasi")
    Call<BaseResponse> validasiPetani(
            @Field("id_pengguna") String idPengguna
    );

    @GET("produk/update_stok")
    Call<BaseResponse> updateStok(
            @Query("id_produk") String idProduk,
            @Query("stok") String stok
    );

    @GET("pengguna/edit_pengrajin")
    Call<BaseResponse> editPengrajin(
            @Query("id_pengguna") String idPengguna,
            @Query("username") String username,
            @Query("alamat") String alamat,
            @Query("password") String password,
            @Query("email") String email,
            @Query("no_telp") String noTelp,
            @Query("latitude") String latitude,
            @Query("longitude") String longitu
    );

    @GET("pengguna/edit_admin")
    Call<BaseResponse> editAdmin(
            @Query("id_pengguna") String idPengguna,
            @Query("username") String username,
            @Query("password") String password,
            @Query("email") String email
    );

    @GET("produk/edit_produk")
    Call<BaseResponse> editProduk(
            @Query("id_produk") String idProduk,
            @Query("nama") String nama,
            @Query("deskripsi") String deskripsi,
            @Query("stok") String stok,
            @Query("harga_satuan") String harga_satuan,
            @Query("berat") String berat,
            @Query("kategori") String kategori
    );

    @GET("laporan/get_report")
    Call<LaporanResponse> getReport(
            @Query("bulan") String bulan
    );

}
