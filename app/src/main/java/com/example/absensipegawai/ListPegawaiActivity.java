package com.example.absensipegawai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.developer.kalert.KAlertDialog;
import com.example.absensipegawai.adapter.CustomAdapter;
import com.example.absensipegawai.pojo.DataListPegawai;
import com.example.absensipegawai.pojo.ListPegawaiResponse;
import com.example.absensipegawai.webservice.ApiRepository;
import com.example.absensipegawai.webservice.ApiRepositoryCallBack;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class ListPegawaiActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //buat list pegawai
    RecyclerView recyclerView;
    CustomAdapter customAdapter;
    ArrayList<String> items;

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
        setContentView(R.layout.activity_list_pegawai);

        apiRepository  = new ApiRepository();

        //
        userNip = getIntent().getStringExtra("nip");
        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String level = sharedPreferences.getString("level", null);
        user_id = sharedPreferences.getString("user_id", null);

        drawerLayout = findViewById(R.id.drawer_layout9);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        //ubah icon toolbar
        toolbar.setNavigationIcon(R.drawable.menu_icon);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        navigationView.setNavigationItemSelectedListener(this);

        // Hide Menu untuk user pegawai
        navigationView.setCheckedItem(R.id.nav_listpegawai);
        if (level.equals("pegawai")) {
            hideMenuItemAtasan();
        }
        customAdapter = new CustomAdapter(this);
        getListPegawai();
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

    public void getListPegawai (){
        apiRepository.listpegawai(new ApiRepositoryCallBack<ListPegawaiResponse>() {
            @Override
            public void onGetResponse(ListPegawaiResponse response) {
                if (response.getStatus() == true) {
                    ArrayList<DataListPegawai> dataListPegawai = response.getData();
                    Log.d("ListPegawai", String.valueOf(dataListPegawai.size()));
                    customAdapter.setData(dataListPegawai);
                    recyclerView.setAdapter(customAdapter);
                } else {
                    new KAlertDialog(ListPegawaiActivity.this, KAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Gagal!")
                            .setContentText(response.getMessage())
                            .show();
                }
            }

            @Override
            public void onGetError() {
                new KAlertDialog(ListPegawaiActivity.this, KAlertDialog.ERROR_TYPE)
                        .setTitleText("Gagal!")
                        .setContentText("Tidak Terhubung ke server.")
                        .show();
            }
        });
    }

    private void hideMenuItemAtasan(){
        Menu navigationMenu = navigationView.getMenu();
        navigationMenu.findItem(R.id.nav_listpegawai).setVisible(false);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_absenmasuk:
                Intent intent = new Intent(ListPegawaiActivity.this, HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_absenkerja:
                Intent intent2 = new Intent(ListPegawaiActivity.this, AbsensiKerjaActivity.class);
                startActivity(intent2);
                break;
            case R.id.nav_absenpulang:
                Intent intent3 = new Intent(ListPegawaiActivity.this, AbsensiPulangActivity.class);
                startActivity(intent3);
                break;
            case R.id.nav_listpegawai:
                break;
            case R.id.nav_profile:
                Intent intent4 = new Intent(ListPegawaiActivity.this, ProfileActivity.class);
                startActivity(intent4);
                break;
            case R.id.nav_setting:
                Intent intent5 = new Intent(ListPegawaiActivity.this, ChangePasswordActivity.class);
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
        Toast.makeText(ListPegawaiActivity.this, "Anda telah keluar", Toast.LENGTH_SHORT).show();
    }
}