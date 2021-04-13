package com.example.absensipegawai.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListPegawaiResponse {
    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private ArrayList<DataListPegawai> data;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<DataListPegawai> getData() {
        return data;
    }

    public void setData(ArrayList<DataListPegawai> data) {
        this.data = data;
    }
}
