package com.example.absensipegawai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.developer.kalert.KAlertDialog;
import com.google.android.material.navigation.NavigationView;

public class AbsensiKerja extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    //Untuk panggil Repository
    private ApiRepository apiRepository;

    SharedPreferences sharedPreferences;
    Location gps_loc, network_loc, final_loc;
    double longitude;
    double latitude;

    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;

    private Notification notification;

    private Button button;
    String userNip, user_id, tokenAbsen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absensi_kerja);

        apiRepository = new ApiRepository();

        //
        userNip = getIntent().getStringExtra("nip");
        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String level = sharedPreferences.getString("level", null);
        user_id = sharedPreferences.getString("user_id", null);
        tokenAbsen = sharedPreferences.getString("token_absen", null);

        drawerLayout = findViewById(R.id.drawer_layout2);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        //buat possisi arah nav item
        navigationView.setCheckedItem(R.id.nav_absenkerja);

        // Hide Menu untuk user pegawai
        navigationView.setCheckedItem(R.id.nav_absenmasuk);
        if (level.equals("pegawai")) {
            hideMenuItemAtasan();
        }

        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        button = findViewById(R.id.btnkerja);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(AbsensiKerja.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(AbsensiKerja.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(AbsensiKerja.this, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED) {
                    try {
                        gps_loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        network_loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (gps_loc != null) {
                        final_loc = gps_loc;
                        latitude = final_loc.getLatitude();
                        longitude = final_loc.getLongitude();
                    }
                    else if (network_loc != null) {
                        final_loc = network_loc;
                        latitude = final_loc.getLatitude();
                        longitude = final_loc.getLongitude();
                    }
                    else {
                        latitude = 0.0;
                        longitude = 0.0;
                        Toast.makeText(AbsensiKerja.this, "Koneksi Anda tidak terhubung atau GPS anda Mati. " + latitude + " " + longitude, Toast.LENGTH_SHORT).show();
                    }
                    if (latitude != 0.0 && longitude != 0.0){
                        Toast.makeText(AbsensiKerja.this, "Cek lokasi berhasil \n" + latitude + " " + longitude, Toast.LENGTH_SHORT).show();
                        dokerja(user_id, latitude, longitude, tokenAbsen);
                    }
                    return;

                } else {
                    ActivityCompat.requestPermissions(AbsensiKerja.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE}, 1);
                }

                //        button.setText(userNip);
            }
        });
    }

    public void AbsensiPulang(){
        Intent intent = new Intent(this, AbsensiPulang.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
                Intent intent = new Intent(AbsensiKerja.this, VerAbsensiMasuk.class);
                startActivity(intent);
                break;
            case R.id.nav_absenkerja:
                break;
            case R.id.nav_absenpulang:
                Intent intent2 = new Intent(AbsensiKerja.this, AbsensiPulang.class);
                startActivity(intent2);
                break;
            case R.id.nav_listpegawai:
                Intent intent3 = new Intent(AbsensiKerja.this, ListPegawai.class);
                startActivity(intent3);
                break;
            case R.id.nav_profile:
                Intent intent4 = new Intent(AbsensiKerja.this, ProfileActivity.class);
                startActivity(intent4);
                break;
            case R.id.nav_setting:
                Intent intent5 = new Intent(AbsensiKerja.this, ChangePasswordActivity.class);
                startActivity(intent5);
                break;
            case R.id.nav_off:
                doLogout();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void dokerja (String username, double latitude, double longitude, String tokenAbsen){
        apiRepository.kerja(username, latitude, longitude, tokenAbsen, new ApiRepositoryCallBack<PostResponse>() {
            @Override
            public void onGetResponse(PostResponse response) {
                if (response.getStatus() == true) {
                    VerAbsensiKerja();
                    new KAlertDialog(AbsensiKerja.this, KAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Berhasil!")
                            .setContentText(response.getMessage())
                            .show();
                } else {
                    Toast.makeText(AbsensiKerja.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onGetError() {
                new KAlertDialog(AbsensiKerja.this, KAlertDialog.ERROR_TYPE)
                        .setTitleText("Gagal!")
                        .setContentText("Tidak Terhubung ke server.")
                        .show();
            }
        });
    }

    public void VerAbsensiKerja(){
        Intent intent = new Intent(this, VerAbsensiKerja.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void doLogout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Intent intent6 = new Intent(getBaseContext(), LoginActivity.class);
        String keluar = "Anda telah keluar";
        intent6.putExtra("user_logout", keluar);
        startActivity(intent6);
        Toast.makeText(AbsensiKerja.this, "Anda telah keluar", Toast.LENGTH_SHORT).show();
    }
}