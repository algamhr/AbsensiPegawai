package com.example.absensipegawai.webservice;

import com.example.absensipegawai.pojo.ListPegawaiResponse;
import com.example.absensipegawai.pojo.LoginResponse;
import com.example.absensipegawai.pojo.PostResponse;
import com.example.absensipegawai.pojo.ProfileResponse;
import com.example.absensipegawai.pojo.UpdatePasswordResponse;
import com.example.absensipegawai.pojo.UpdateUserResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServices {

    @FormUrlEncoded
    @POST("proseslogin.php")
    Call<LoginResponse> login(@Field("username") String username, @Field("password") String password, @Field("fcm_token") String fcmToken);

    @FormUrlEncoded
    @POST("savemasuk.php")
    Call<PostResponse> masuk(@Field("username") String username, @Field("latitude") double latitude, @Field("longitude") double longitude);

    @FormUrlEncoded
    @POST("savekerja.php")
    Call<PostResponse> kerja(@Field("username") String username, @Field("latitude") double latitude, @Field("longitude") double longitude, @Field("token_absen") String tokenAbsen);

    @FormUrlEncoded
    @POST("savekeluar.php")
    Call<PostResponse> pulang(@Field("username") String username, @Field("latitude") double latitude, @Field("longitude") double longitude);

    @GET("listpegawai.php")
    Call<ListPegawaiResponse> listpegawai();

    @GET("profile.php")
    Call<ProfileResponse> profile(@Query("id") String id);

    @FormUrlEncoded
    @POST("updateuser.php")
    Call<UpdateUserResponse> updateuser(@Field("id") String id, @Field("nip") String nip, @Field("nidn") String nidn, @Field("nama")
            String nama, @Field("email") String email, @Field("fakultas") String fakultas, @Field("jurusan") String jurusan, @Field("no_hp") String no_hp);

    @FormUrlEncoded
    @POST("updatepassword.php")
    Call<UpdatePasswordResponse> updatepassword(@Field("id") String id, @Field("oldpassword") String oldpassword, @Field("password") String password);
}