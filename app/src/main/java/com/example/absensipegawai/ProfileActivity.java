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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.developer.kalert.KAlertDialog;
import com.example.absensipegawai.pojo.ProfileResponse;
import com.example.absensipegawai.pojo.UpdateUserResponse;
import com.example.absensipegawai.webservice.ApiRepository;
import com.example.absensipegawai.webservice.ApiRepositoryCallBack;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Untuk panggil Repository
    private ApiRepository apiRepository;
    SharedPreferences sharedPreferences;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    String userNip, user_id;
    private Button button;

    TextInputLayout nipUser, nidnUser, namaUser, emailUser, jurusanUser, fakultasUser, no_hpUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        apiRepository  = new ApiRepository();

        userNip = getIntent().getStringExtra("nip");
        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String level = sharedPreferences.getString("level", null);
        user_id = sharedPreferences.getString("user_id", null);

        apiRepository.profile(user_id, new ApiRepositoryCallBack<ProfileResponse>() {
            @Override
            public void onGetResponse(ProfileResponse response) {
                Log.d("Profiles", String.valueOf(response.getStatus()));
                if (response.getStatus() == true) {
                    nipUser = findViewById(R.id.niptextinput);
                    nipUser.getEditText().setText(response.getNip());
                    nidnUser = findViewById(R.id.nidntextinput);
                    nidnUser.getEditText().setText(response.getNidn());
                    namaUser = findViewById(R.id.namatexinput);
                    namaUser.getEditText().setText(response.getNama());
                    emailUser = findViewById(R.id.emailtexinput);
                    emailUser.getEditText().setText(response.getEmail());
                    jurusanUser = findViewById(R.id.jurusantexinput);
                    jurusanUser.getEditText().setText(response.getJurusan());
                    fakultasUser = findViewById(R.id.fakultastexinput);
                    fakultasUser.getEditText().setText(response.getFakultas());
                    no_hpUser = findViewById(R.id.no_hptextinput);
                    no_hpUser.getEditText().setText(response.getNo_hp());
                } else {
                    Toast.makeText(ProfileActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onGetError() {
                Toast.makeText(ProfileActivity.this, "Tidak Terhubung ke Server.", Toast.LENGTH_SHORT).show();
            }
        });

        drawerLayout = findViewById(R.id.drawer_layout7);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        // Hide Menu untuk user pegawai
        navigationView.setCheckedItem(R.id.nav_absenpulang);
        if (level.equals("pegawai")) {
            hideMenuItemAtasan();
        }

        navigationView.setCheckedItem(R.id.nav_profile);

        button = findViewById(R.id.btnupdateuser);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doupdateuser(user_id,
                        nipUser.getEditText().getText().toString(),
                        nidnUser.getEditText().getText().toString(),
                        namaUser.getEditText().getText().toString(),
                        emailUser.getEditText().getText().toString(),
                        fakultasUser.getEditText().getText().toString(),
                        jurusanUser.getEditText().getText().toString(),
                        no_hpUser.getEditText().getText().toString());
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
                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_absenkerja:
                Intent intent2 = new Intent(ProfileActivity.this, AbsensiKerjaActivity.class);
                startActivity(intent2);
                break;
            case R.id.nav_absenpulang:
                Intent intent3 = new Intent(ProfileActivity.this, AbsensiPulangActivity.class);
                startActivity(intent3);
                break;
            case R.id.nav_listpegawai:
                Intent intent4 = new Intent(ProfileActivity.this, ListPegawaiActivity.class);
                startActivity(intent4);
                break;
            case R.id.nav_profile:
                break;
            case R.id.nav_setting:
                Intent intent5 = new Intent(ProfileActivity.this, ChangePasswordActivity.class);
                startActivity(intent5);
                break;
            case R.id.nav_off:
                doLogout();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void doupdateuser(String id, final String nip, String nidn, String nama, String email, String fakultas, String jurusan, String no_hp){
        apiRepository.updateuser(id, nip, nidn, nama, email, fakultas, jurusan, no_hp, new ApiRepositoryCallBack<UpdateUserResponse>() {
            @Override
            public void onGetResponse(UpdateUserResponse response) {
                if (response.getStatus() == true) {
                    new KAlertDialog(ProfileActivity.this, KAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Berhasil!")
                            .setContentText("Data Profil Anda telah diubah.")
                            .show();
                } else {
                    Toast.makeText(ProfileActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onGetError() {
                Toast.makeText(ProfileActivity.this, "Tidak Terhubung ke Server.", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(ProfileActivity.this, "Anda telah Keluar", Toast.LENGTH_SHORT).show();
    }
}