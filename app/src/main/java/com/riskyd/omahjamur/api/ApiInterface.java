package com.riskyd.omahjamur.api;

import com.riskyd.omahjamur.api.response.AdminResponse;
import com.riskyd.omahjamur.api.response.BaseResponse;
import com.riskyd.omahjamur.api.response.DetailJenisProdukResponse;
import com.riskyd.omahjamur.api.response.JenisProdukResponse;
import com.riskyd.omahjamur.api.response.PenggunaResponse;
import com.riskyd.omahjamur.api.response.PetaniResponse;
import com.riskyd.omahjamur.api.response.ProdukResponse;

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

//    @GET("admin/login")
//    Call<AdminResponse> login(
//            @Query("username") String username,
//            @Query("password") String password
//    );

    @GET("pengguna/login")
    Call<PenggunaResponse> login(
            @Query("email") String email,
            @Query("password") String password
    );

    @GET("pengguna")
    Call<PenggunaResponse> get_id(
            @Query("email") String email
    );

    @GET("admin/detail_admin")
    Call<AdminResponse> detail_admin(
            @Query("id_pengguna") String idPengguna
    );

    @GET("pengguna")
    Call<PenggunaResponse> checkPengguna(
            @Query("email") String email
    );

    @GET("jenis_produk")
    Call<JenisProdukResponse> get_jenis_produk();

    @FormUrlEncoded
    @POST("jenis_produk/tambah")
    Call<BaseResponse> tambah_jp(
            @Field("deskripsi") String deskripsi
    );

    @FormUrlEncoded
    @POST("jenis_produk/edit")
    Call<BaseResponse> edit_jp(
            @Field("id_jenis_produk") String idJenisProduk,
            @Field("deskripsi") String deskripsi
    );

    @FormUrlEncoded
    @POST("pengguna/daftar")
    Call<BaseResponse> daftar(
            @Field("id_role") String idRole,
            @Field("email") String email,
            @Field("password") String password
    );

    //petani
    @Multipart
    @POST("petani/daftar_petani")
    Call<BaseResponse> daftar_petani(
            @Part("id_pengguna") RequestBody idPengguna,
            @Part("nama") RequestBody nama,
            @Part("alamat") RequestBody alamat,
            @Part("latitude") RequestBody latitude,
            @Part("longitude") RequestBody longitude,
            @Part MultipartBody.Part image
            );

    @GET("petani")
    Call<PetaniResponse> get_petani();

    @GET("petani/get_detail")
    Call<PetaniResponse> getDetailPetani(
            @Query("id_pengguna") String idPengguna
    );

    @GET("admin/get_detail")
    Call<AdminResponse> getDetailAdmin(
            @Query("id_pengguna") String idPengguna
    );

    @GET("produk/get_produk_petani")
    Call<ProdukResponse> getProdukPetani(
            @Query("id_petani") String idPetani
    );

    @GET("petani/lengkap_petani")
    Call<PetaniResponse> get_lengkap_petani();

    @GET("petani/pengajuan_petani")
    Call<PetaniResponse> get_pengajuan_petani();

    @GET("detail_jenis_produk")
    Call<DetailJenisProdukResponse> getDjp();

    @Multipart
    @POST("produk/tambah")
    Call<BaseResponse> tambahProduk(
            @Part("id_petani") RequestBody noIdentitas,
            @Part("id_detail_jenis_produk") RequestBody idDaftar,
            @Part("judul") RequestBody namaPendaki,
            @Part("deskripsi") RequestBody tglLahir,
            @Part("harga") RequestBody jkPendaki,
            @Part("stok") RequestBody alamatPendaki,
            @Part MultipartBody.Part image
    );

}
