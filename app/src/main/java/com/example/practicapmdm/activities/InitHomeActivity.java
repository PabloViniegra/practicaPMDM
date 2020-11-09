package com.example.practicapmdm.activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.practicapmdm.R;
import com.example.practicapmdm.services.GpsService;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.practicapmdm.constants.Constants.INTENT_LOCALIZATION_ACTION;
import static com.example.practicapmdm.constants.Constants.LATITUDE;
import static com.example.practicapmdm.constants.Constants.LONGITUDE;
import static com.example.practicapmdm.constants.Constants.NAME;

public class InitHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    public final String TAG = getClass().getName();
    public static final String TITLE_KEY = "TITLE_KEY";
    public static final String TITLE = "My location";
    public static final String DESCRIPTION_KEY = "DESCRIPTION_KEY";
    public static final String DESCRIPTION = "This is my location";
    Double latitude;
    Double longitude;
    public String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_home);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.btn_moreinfo);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);

        MenuItem menuItem = navigationView.getMenu().getItem(0);
        menuItem.setChecked(true);

        //drawerLayout.addDrawerListener((DrawerLayout.DrawerListener) this);

        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(InitHomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(InitHomeActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            startService();
        }

        Intent getIntent = getIntent();
        final double latitudeReceive = getIntent.getDoubleExtra(LATITUDE, 0);
        final double longitudeReceive = getIntent.getDoubleExtra(LONGITUDE, 0);
        String nameReceive = getIntent.getStringExtra(NAME);

        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, new IntentFilter(INTENT_LOCALIZATION_ACTION));

    }

    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            latitude = intent.getDoubleExtra(LATITUDE, 0);
            longitude = intent.getDoubleExtra(LONGITUDE, 0);
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_item_one:
                Intent intentMaps = new Intent(InitHomeActivity.this, MapsActivity.class);
                intentMaps.putExtra(LATITUDE, latitude);
                intentMaps.putExtra(LONGITUDE, longitude);
                intentMaps.putExtra(NAME, name);
                startActivity(intentMaps);
                break;
            case R.id.nav_item_two:
                break;
            case R.id.nav_item_three:
                break;
            case R.id.nav_item_four:
                break;
            case R.id.nav_item_five:
                break;
            default:
                Log.d(TAG, "No he entrado por el onNavigationGetItemSelected");
                break;

        }
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void loading() {
        int i = 100;
        final int[] n = {0};
        Random r = new Random();
        int random = r.nextInt(1);
        final TextView percent = null;
        final int[] opcion_0 = {1, 20, 40, 60, 89, 100};
        int[] opcion_1 = {1, 30, 60, 90, 99, 100};
        ExecutorService executor = Executors.newSingleThreadExecutor();

        switch (random) {
            case 0:
                // con el métod submit() se envía la tarea que se hará en el nuevo hilo
                // hará una pausa de un segundo ( al hilo secundario ) y actualizará
                // el valor del elemento de la UI
                executor.submit(new Runnable() {
                    @Override
                    public void run() {

                        for (int k = 0; k <= 5; k++) {
                            String temp = String.valueOf(opcion_0[n[0]]);
                            assert percent != null;
                            percent.setText(temp);
                            n[0]++;

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                break;

        }
    }

    public void startService () {
        Intent intentService = new Intent(getApplicationContext(), GpsService.class);
        startService(intentService);
    }
}