package com.example.absensipegawai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

public class LoginActivity extends AppCompatActivity {

    private ApiRepository apiRepository;

    public static final String MyPREFERENCES = "Prefs AbsensiPegawai";
    SharedPreferences sharedpreferences;
    private String fcmToken;

    public void dologin (String username, String password, String fcmToken){
        apiRepository.login(username, password, fcmToken, new ApiRepositoryCallBack<LoginResponse>() {
            @Override
            public void onGetResponse(LoginResponse response) {
                if (response.getStatus() == true) {
                    onLoginSuccess(response);
                } else {
                    Toast.makeText(LoginActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onGetError() {
                Toast.makeText(LoginActivity.this, "Tidak Terhubung ke Server.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onLoginSuccess(LoginResponse response) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("user_id", response.getUser_id());
        editor.putString("nip", response.getNip());
        editor.putString("nidn", response.getNidn());
        editor.putString("level", response.getLevel());
        editor.apply();
        this.finish();
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.putExtra("nip", response.getNip());
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private EditText nipusr, passusr;
    private Button btnlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        apiRepository  = new ApiRepository();

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        fcmToken = sharedpreferences.getString("fcm_token", null);

        nipusr = findViewById(R.id.loginusername);
        passusr = findViewById(R.id.loginpassword);
        btnlogin = findViewById(R.id.btnlogin);



        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nip = nipusr.getText().toString();
                String password = passusr.getText().toString();

                if (nip.equals("")){
                    Toast.makeText(LoginActivity.this, "Silahkan isi NIP/NIDN Anda.", Toast.LENGTH_SHORT).show();
                } else if (password.equals("")){
                    Toast.makeText(LoginActivity.this, "Silahkan isi Password Anda.", Toast.LENGTH_SHORT).show();
                } else {
                    dologin(nip, password, fcmToken);
                }
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
