package com.example.absensipegawai;

public interface ApiRepositoryCallBack<T> {
    void onGetResponse (T response);

    void onGetError();
}
