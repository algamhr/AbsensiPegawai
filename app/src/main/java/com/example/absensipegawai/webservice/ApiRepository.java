package com.example.absensipegawai.webservice;

import android.util.Log;

import com.example.absensipegawai.pojo.ListPegawaiResponse;
import com.example.absensipegawai.pojo.LoginResponse;
import com.example.absensipegawai.pojo.PostResponse;
import com.example.absensipegawai.pojo.ProfileResponse;
import com.example.absensipegawai.pojo.UpdatePasswordResponse;
import com.example.absensipegawai.pojo.UpdateUserResponse;
import com.example.absensipegawai.webservice.client.UnsafeOkHttpClient;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRepository {

    private ApiServices apiServices;

    public ApiRepository() { apiServices = getRetrofit().create(ApiServices.class);}

    private static Retrofit retrofit = null;


    public static Retrofit getRetrofit(){
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://alga.simdes-bintan.id/rest/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public void login (String username, String password, String fcmToken, final ApiRepositoryCallBack<LoginResponse> callBack){
        apiServices.login(username, password, fcmToken).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d("LOGIN", response.message());
                if (response.isSuccessful()){
                    callBack.onGetResponse(response.body());
                } else {
                    callBack.onGetError();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("LOGIN", t.getMessage());
                callBack.onGetError();
            }
        });
    }

    public void masuk (String username, double latitude, double longitude, final ApiRepositoryCallBack<PostResponse> callBack){
        apiServices.masuk(username, latitude, longitude).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                Log.d("Masuk", response.message());
                if (response.isSuccessful()){
                    callBack.onGetResponse(response.body());
                } else {
                    callBack.onGetError();
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                Log.d("GagalMasuk", t.getMessage());
                callBack.onGetError();
            }
        });
    }

    public void kerja (String username, double latitude, double longitude, String tokenAbsen, final ApiRepositoryCallBack<PostResponse> callBack){
        Log.d("Kerja", tokenAbsen);
        apiServices.kerja(username, latitude, longitude, tokenAbsen).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                Log.d("Kerja", response.message());
                if (response.isSuccessful()){
                    callBack.onGetResponse(response.body());
                } else {
                    callBack.onGetError();
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                Log.d("GagalMasuk", t.getMessage());
                callBack.onGetError();
            }
        });
    }

    public void pulang (String username, double latitude, double longitude, final ApiRepositoryCallBack<PostResponse> callBack){
        apiServices.pulang(username, latitude, longitude).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                Log.d("Pulang", response.message());
                if (response.isSuccessful()){
                    callBack.onGetResponse(response.body());
                } else {
                    callBack.onGetError();
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                Log.d("GagalPulang", t.getMessage());
                callBack.onGetError();
            }
        });
    }

    public void profile (String id, final ApiRepositoryCallBack<ProfileResponse> callBack){
        apiServices.profile(id).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                Log.d("Profile", response.body().getNip());
                if (response.isSuccessful()){
                    callBack.onGetResponse(response.body());
                } else {
                    callBack.onGetError();
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Log.d("GagalTampilProfil", t.getMessage());
                callBack.onGetError();
            }
        });
    }

    public void listpegawai(final ApiRepositoryCallBack<ListPegawaiResponse> callBack){
        apiServices.listpegawai().enqueue(new Callback<ListPegawaiResponse>() {
            @Override
            public void onResponse(Call<ListPegawaiResponse> call, Response<ListPegawaiResponse> response) {
                Log.d("ListPegawai", String.valueOf(response.body().getData().size()));
                if (response.isSuccessful()){
                    callBack.onGetResponse(response.body());
                } else {
                    callBack.onGetError();
                }
            }

            @Override
            public void onFailure(Call<ListPegawaiResponse> call, Throwable t) {
                Log.d("ListPegawai", t.getMessage());
                callBack.onGetError();
            }
        });
    }

    public void updateuser (String id, String nip, String nidn, String nama, String email, String fakultas, String jurusan, String no_hp, final ApiRepositoryCallBack<UpdateUserResponse> callBack){
        apiServices.updateuser(id, nip, nidn, nama, email, fakultas, jurusan, no_hp).enqueue(new Callback<UpdateUserResponse>() {
            @Override
            public void onResponse(Call<UpdateUserResponse> call, Response<UpdateUserResponse> response) {
                if (response.isSuccessful()){
                    callBack.onGetResponse(response.body());
                } else {
                    Log.d("Response Update User", String.valueOf(response.code()));
                    callBack.onGetError();
                }
            }

            @Override
            public void onFailure(Call<UpdateUserResponse> call, Throwable t) {
                t.printStackTrace();
                Log.d("GagalUpdateUser", t.getMessage());
                callBack.onGetError();
            }
        });
    }

    public void updatepassword (String id, String oldpassword, String password, final ApiRepositoryCallBack<UpdatePasswordResponse> callBack){
        apiServices.updatepassword(id, oldpassword, password).enqueue(new Callback<UpdatePasswordResponse>() {
            @Override
            public void onResponse(Call<UpdatePasswordResponse> call, Response<UpdatePasswordResponse> response) {
                if (response.isSuccessful()){
                    callBack.onGetResponse(response.body());
                } else {
                    Log.d("ResponseUpdatePassword", String.valueOf(response.code()));
                    callBack.onGetError();
                }
            }

            @Override
            public void onFailure(Call<UpdatePasswordResponse> call, Throwable t) {
                t.printStackTrace();
                Log.d("Gagal Update Password", t.getMessage());
                callBack.onGetError();
            }
        });
    }
}
