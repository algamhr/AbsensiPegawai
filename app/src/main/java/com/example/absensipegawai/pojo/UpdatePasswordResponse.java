package com.example.absensipegawai.pojo;

import com.google.gson.annotations.SerializedName;

public class UpdatePasswordResponse {

    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

//    @SerializedName("id")
//    private String id;

    @SerializedName("oldpassword")
    private String oldpassword;

    @SerializedName("password")
    private String password;


//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

    public String getOldpassword() {
        return oldpassword;
    }

    public void setOldpassword(String oldpassword) {
        this.oldpassword = oldpassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
}
