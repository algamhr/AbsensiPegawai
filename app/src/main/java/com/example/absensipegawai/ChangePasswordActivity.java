package com.example.absensipegawai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.developer.kalert.KAlertDialog;
import com.example.absensipegawai.pojo.UpdatePasswordResponse;
import com.example.absensipegawai.webservice.ApiRepository;
import com.example.absensipegawai.webservice.ApiRepositoryCallBack;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

public class ChangePasswordActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Untuk panggil Repository
    private ApiRepository apiRepository;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    SharedPreferences sharedPreferences;

    TextInputLayout oldpasswordUser, passwordUser, confirmUser;
    private Button button;

    String userNip;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        apiRepository  = new ApiRepository();

        userNip = getIntent().getStringExtra("nip");
        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String level = sharedPreferences.getString("level", null);
        user_id = sharedPreferences.getString("user_id", null);

        oldpasswordUser = findViewById(R.id.oldpasswordtextinput);
        passwordUser = findViewById(R.id.passwordtextinput);
        confirmUser = findViewById(R.id.confirmtextinput);

        drawerLayout = findViewById(R.id.drawer_layout8);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        // Hide Menu untuk user pegawai
        navigationView.setCheckedItem(R.id.nav_setting);
        if (level.equals("pegawai")) {
            hideMenuItemAtasan();
        }

        navigationView.setCheckedItem(R.id.nav_setting);

        button = findViewById(R.id.btnupdatepassword);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passwordUser.equals("")){
                    new KAlertDialog(ChangePasswordActivity.this, KAlertDialog.ERROR_TYPE)
                            .setTitleText("Gagal!")
                            .setContentText("Silahkan isi Password Baru Anda.")
                            .show();
                } else if(confirmUser.equals("")){
                    new KAlertDialog(ChangePasswordActivity.this, KAlertDialog.ERROR_TYPE)
                            .setTitleText("Gagal!")
                            .setContentText("Silahkan isi Password Konfirmasi.")
                            .show();
                } else if(passwordUser.equals("") && confirmUser.equals("")){
                    new KAlertDialog(ChangePasswordActivity.this, KAlertDialog.ERROR_TYPE)
                            .setTitleText("Gagal!")
                            .setContentText("Silahkan isi Password & Konfirmasi Password.")
                            .show();
                } else if(passwordUser.getEditText().getText().toString().equals(confirmUser.getEditText().getText().toString())){
                    doupdatepassword(user_id,
                            oldpasswordUser.getEditText().getText().toString(),
                            passwordUser.getEditText().getText().toString());
                } else {
                    new KAlertDialog(ChangePasswordActivity.this, KAlertDialog.ERROR_TYPE)
                            .setTitleText("Gagal!")
                            .setContentText("Password baru dan konfirmasi password berbeda, silahkan ulangi kembali.")
                            .show();
                }
            }
        });
    }

    private void hideMenuItemAtasan(){
        Menu navigationMenu = navigationView.getMenu();
        navigationMenu.findItem(R.id.nav_listpegawai).setVisible(false);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_absenmasuk:
                Intent intent = new Intent(ChangePasswordActivity.this, HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_absenkerja:
                Intent intent2 = new Intent(ChangePasswordActivity.this, AbsensiKerjaActivity.class);
                startActivity(intent2);
                break;
            case R.id.nav_absenpulang:
                Intent intent3 = new Intent(ChangePasswordActivity.this, AbsensiPulangActivity.class);
                startActivity(intent3);
                break;
            case R.id.nav_listpegawai:
                Intent intent4 = new Intent(ChangePasswordActivity.this, ListPegawaiActivity.class);
                startActivity(intent4);
                break;
            case R.id.nav_profile:
                Intent intent5 = new Intent(ChangePasswordActivity.this, ProfileActivity.class);
                startActivity(intent5);
                break;
            case R.id.nav_setting:
                break;
            case R.id.nav_off:
                doLogout();
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void doupdatepassword(String id, String oldpassword, String password){
        apiRepository.updatepassword(id, oldpassword, password, new ApiRepositoryCallBack<UpdatePasswordResponse>() {
            @Override
            public void onGetResponse(UpdatePasswordResponse response) {
                if (response.getStatus() == true) {
                    new KAlertDialog(ChangePasswordActivity.this, KAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Berhasil!")
                            .setContentText("Password telah diubah.")
                            .show();
                } else {
                    new KAlertDialog(ChangePasswordActivity.this, KAlertDialog.ERROR_TYPE)
                            .setTitleText("Gagal!")
                            .setContentText(response.getMessage())
                            .show();
                    //Toast.makeText(ChangePasswordActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onGetError() {
                new KAlertDialog(ChangePasswordActivity.this, KAlertDialog.ERROR_TYPE)
                        .setTitleText("Gagal!")
                        .setContentText("Ganti password gagal.")
                        .show();
            }
        });
    }

    public void doLogout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Intent intent6 = new Intent(getBaseContext(), LoginActivity.class);

        String keluar = "Anda telah Keluar";
        intent6.putExtra("user_logout", keluar);
        startActivity(intent6);
        Toast.makeText(ChangePasswordActivity.this, "Anda telah Keluar", Toast.LENGTH_SHORT).show();
    }
}