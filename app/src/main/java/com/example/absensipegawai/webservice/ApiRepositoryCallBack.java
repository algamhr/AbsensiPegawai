package com.example.absensipegawai.webservice;

public interface ApiRepositoryCallBack<T> {
    void onGetResponse (T response);

    void onGetError();
}
