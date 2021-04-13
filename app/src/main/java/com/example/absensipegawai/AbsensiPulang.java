package com.example.absensipegawai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
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

import com.google.android.gms.location.LocationRequest;
import com.google.android.material.navigation.NavigationView;

public class AbsensiPulang extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    private Button button;

    //Untuk panggil Repository
    private ApiRepository apiRepository;
    SharedPreferences sharedPreferences;

    //Untuk lokasi
    Location gps_loc, network_loc, final_loc;
    double longitude;
    double latitude;

    //Untuk string yang dibutuhkan
    String userNip;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absensi_pulang);

        apiRepository  = new ApiRepository();

        userNip = getIntent().getStringExtra("nip");
        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String level = sharedPreferences.getString("level", null);
        user_id = sharedPreferences.getString("user_id", null);

        drawerLayout = findViewById(R.id.drawer_layout3);
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

        //buat possisi arah nav item
        navigationView.setCheckedItem(R.id.nav_absenpulang);


        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        button = findViewById(R.id.btnpulang);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(AbsensiPulang.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(AbsensiPulang.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(AbsensiPulang.this, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED) {
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
                        Toast.makeText(AbsensiPulang.this, "Koneksi Anda tidak terhubung. \n" + latitude + " " + longitude, Toast.LENGTH_SHORT).show();
                    }
                    if (latitude != 0.0 && longitude != 0.0){
                        Toast.makeText(AbsensiPulang.this, "Cek lokasi berhasil." + latitude + " " + longitude, Toast.LENGTH_SHORT).show();
                        dopulang(user_id, latitude, longitude);
                    }
                    return;

                } else {
                    ActivityCompat.requestPermissions(AbsensiPulang.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE}, 1);
                }
            }
        });
    }

    private void hideMenuItemAtasan(){
        Menu navigationMenu = navigationView.getMenu();
        navigationMenu.findItem(R.id.nav_listpegawai).setVisible(false);
    }

    public void dopulang (String username, double latitude, double longitude){
        apiRepository.pulang(username, latitude, longitude, new ApiRepositoryCallBack<PostResponse>() {
            @Override
            public void onGetResponse(PostResponse response) {
                if (response.getStatus() == true) {
                    Toast.makeText(AbsensiPulang.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    VerAbsensiPulang();
                } else {
                    Toast.makeText(AbsensiPulang.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onGetError() {
                Toast.makeText(AbsensiPulang.this, "Tidak Terhubung ke Server.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void VerAbsensiPulang(){
        Intent intent = new Intent(this, VerAbsensiPulang.class);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_absenmasuk:
                Intent intent = new Intent(AbsensiPulang.this, HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_absenkerja:
                Intent intent2 = new Intent(AbsensiPulang.this, VerAbsensiKerja.class);
                startActivity(intent2);
                break;
            case R.id.nav_absenpulang:
                break;
            case R.id.nav_listpegawai:
                Intent intent3 = new Intent(AbsensiPulang.this, ListPegawai.class);
                startActivity(intent3);
                break;
            case R.id.nav_profile:
                Intent intent4 = new Intent(AbsensiPulang.this, ProfileActivity.class);
                startActivity(intent4);
                break;
            case R.id.nav_setting:
                Intent intent5 = new Intent(AbsensiPulang.this, ChangePasswordActivity.class);
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

        String keluar = "Anda telah Keluar";
        intent6.putExtra("user_logout", keluar);
        startActivity(intent6);
        Toast.makeText(AbsensiPulang.this, "Anda telah Keluar", Toast.LENGTH_SHORT).show();
    }
}