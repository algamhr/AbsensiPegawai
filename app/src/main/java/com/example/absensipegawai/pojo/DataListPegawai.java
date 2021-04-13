package com.example.absensipegawai.pojo;

import com.google.gson.annotations.SerializedName;

public class DataListPegawai {
    @SerializedName("ab.id")
    private String id;

    @SerializedName("usr.nama")
    private String nama;

    @SerializedName("usr.nidn")
    private String nidn;

    @SerializedName("ab.jam")
    private String jam;

    @SerializedName("lk.lokasi")
    private String lokasi;

    @SerializedName("ab.keterangan")
    private String keterangan;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNidn() {
        return nidn;
    }

    public void setNidn(String nidn) {
        this.nidn = nidn;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String lokasi) {
        this.keterangan = keterangan;
    }

}