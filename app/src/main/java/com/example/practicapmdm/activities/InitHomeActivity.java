
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.practicapmdm.R;
import com.example.practicapmdm.apiRest.ApiLocationMadridData;
import com.example.practicapmdm.constants.Constants;
import com.example.practicapmdm.controllers.FileController;
import com.example.practicapmdm.domain.JsonResponse;
import com.example.practicapmdm.models.Location;
import com.example.practicapmdm.models.Pool;
import com.example.practicapmdm.services.GpsService;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    public static Double latitude;
    public static Double longitude;
    public String name;
    FileController fileControllers;
    private Pool pool;
    private ArrayList<Pool> mPools = new ArrayList<>();
    private ArrayList<Pool> mSport = new ArrayList<>();
    private ArrayList<Pool> temporal;
    public Double latitudReceive = null;
    public Double longitudReceive = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_main);
        raiseFavoriteFileIntoArray();
        Log.d(TAG, "Inicio de la app: contenido de favoritos: " + Constants.favourites.toString());
        setToolbar();
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navview);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }

        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(InitHomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(InitHomeActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            startService();
        }

        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, new IntentFilter(INTENT_LOCALIZATION_ACTION));
        Intent getIntent = getIntent();
        double latitudeReceive = getIntent.getDoubleExtra(LATITUDE, 0);
        double longitudeReceive = getIntent.getDoubleExtra(LONGITUDE, 0);
        String nameReceive = getIntent.getStringExtra(NAME);
    }


    private void raiseFavoriteFileIntoArray() {
        fileControllers = new FileController();
        Constants.favourites = fileControllers.fileFavReader(getApplicationContext());
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.btn_moreinfo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            latitude = intent.getDoubleExtra(LATITUDE, 0);
            longitude = intent.getDoubleExtra(LONGITUDE, 0);
        }
    };


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), R.string.gps_granted, Toast.LENGTH_SHORT).show();
                startService();
            } else {
                Toast.makeText(getApplicationContext(), R.string.gps_denied, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_item_one:
                Toast.makeText(this, "¡Ubicación actualizada!", Toast.LENGTH_SHORT).show();
                Intent intentMaps = new Intent(InitHomeActivity.this, MapsActivity.class);
                intentMaps.putExtra(LATITUDE, latitude);
                intentMaps.putExtra(LONGITUDE, longitude);
                intentMaps.putExtra(NAME, name);
                startActivity(intentMaps);
                break;
            case R.id.nav_item_three:
                getPoolsNear();
                getSportsNear();
                break;
            case R.id.nav_item_four:
                Intent intentInterestLink = new Intent(InitHomeActivity.this, WebViewActivity.class);
                startActivity(intentInterestLink);
                break;
            case R.id.nav_item_five:
                Intent goFavourites = new Intent(InitHomeActivity.this, FavouritesActivity.class);
                startActivity(goFavourites);
                break;
            default:
                break;

        }
        return false;
    }


    public void startService() {
        Intent intentService = new Intent(getApplicationContext(), GpsService.class);
        startService(intentService);
    }

    public void getPoolsNear() {

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(Constants.HEADER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiLocationMadridData mApi = retrofit.create(ApiLocationMadridData.class);
        mApi.getPools(latitude, longitude, Constants.DISTANCE).enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                if (response != null && response.body() != null) {
                    mPools = (ArrayList<Pool>) response.body().results;
                }

            }

            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {

            }
        });


    }

    public void getSportsNear() {
        temporal = new ArrayList<>();
        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(Constants.HEADER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiLocationMadridData mApi = retrofit1.create(ApiLocationMadridData.class);
        mApi.getSports(latitude, longitude, Constants.DISTANCE).enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                if (response != null && response.body() != null) {
                    mSport = (ArrayList<Pool>) response.body().results;

                    //Para eliminar repetidos y no exponer información redundante
                    for (int i = 0; i < mSport.size(); i++) {
                        if (!mPools.contains(mSport.get(i))) {
                            temporal.add(mSport.get(i));
                        }
                    }
                    //Para evitar un NullPointerException creamos un indefinido en caso de que esté vacío
                    if (temporal.size() == 0)
                        temporal.add(new Pool(getString(R.string.undefined), new Location(0, 0)));
                    mSport = temporal;
                    Intent sendSports = new Intent(getApplicationContext(), ActivityViewAdapter.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("LIST", mPools);
                    bundle.putParcelableArrayList("LIST2", mSport);
                    sendSports.putExtras(bundle);
                    startActivity(sendSports);
                }
            }

            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {

            }
        });
    }
}