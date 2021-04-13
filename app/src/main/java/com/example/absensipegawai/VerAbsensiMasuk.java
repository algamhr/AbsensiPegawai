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
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class VerAbsensiMasuk extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    //Untuk panggil Repository
    private ApiRepository apiRepository;

    SharedPreferences sharedPreferences;

    String userNip, user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_absensi_masuk);

        apiRepository  = new ApiRepository();

        //
        userNip = getIntent().getStringExtra("nip");
        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String level = sharedPreferences.getString("level", null);
        user_id = sharedPreferences.getString("user_id", null);

        drawerLayout = findViewById(R.id.drawer_layout4);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        //buat possisi arah nav item
        navigationView.setCheckedItem(R.id.nav_absenmasuk);

        // Hide Menu untuk user pegawai
        navigationView.setCheckedItem(R.id.nav_absenmasuk);
        if (level.equals("pegawai")) {
            hideMenuItemAtasan();
        }
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

    private void hideMenuItemAtasan(){
        Menu navigationMenu = navigationView.getMenu();
        navigationMenu.findItem(R.id.nav_listpegawai).setVisible(false);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_absenmasuk:
                break;
            case R.id.nav_absenkerja:
                Intent intent = new Intent(VerAbsensiMasuk.this, AbsensiKerja.class);
                startActivity(intent);
                break;
            case R.id.nav_absenpulang:
                Intent intent2 = new Intent(VerAbsensiMasuk.this, AbsensiPulang.class);
                startActivity(intent2);
                break;
            case R.id.nav_listpegawai:
                Intent intent3 = new Intent(VerAbsensiMasuk.this, ListPegawai.class);
                startActivity(intent3);
                break;
            case R.id.nav_profile:
                Intent intent4 = new Intent(VerAbsensiMasuk.this, ProfileActivity.class);
                startActivity(intent4);
                break;
            case R.id.nav_setting:
                Intent intent5 = new Intent(VerAbsensiMasuk.this, ChangePasswordActivity.class);
                startActivity(intent5);
                break;
            case R.id.nav_off:
                doLogout();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void doLogout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Intent intent6 = new Intent(getBaseContext(), LoginActivity.class);
        String keluar = "Anda telah keluar";
        intent6.putExtra("user_logout", keluar);
        startActivity(intent6);
        Toast.makeText(VerAbsensiMasuk.this, "Anda telah keluar", Toast.LENGTH_SHORT).show();
    }
}