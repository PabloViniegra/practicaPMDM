
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
import android.webkit.WebView;
import android.webkit.WebViewClient;
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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    public static ArrayList<Pool> favourites = new ArrayList<>();
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
        Log.d(TAG, "Inicio de la app: contenido de favoritos: " + favourites.toString());
        setToolbar();
        
        drawerLayout = findViewById(R.id.drawer_layout);
        Log.d(TAG, "Contenido de favoritos: " + favourites.toString());

        NavigationView navigationView = findViewById(R.id.navview);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }

        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(InitHomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(InitHomeActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            Log.d(TAG, "Empezando el servicio");
            startService();
        }


        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, new IntentFilter(INTENT_LOCALIZATION_ACTION));
        Intent getIntent = getIntent();
        double latitudeReceive = getIntent.getDoubleExtra(LATITUDE, 0);
        double longitudeReceive = getIntent.getDoubleExtra(LONGITUDE, 0);
        String nameReceive = getIntent.getStringExtra(NAME);


        Log.d(TAG, "Latitude " + String.valueOf(latitudeReceive));
        Log.d(TAG, "Longitude " + String.valueOf(longitudeReceive));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "Array de favoritos en el onResume: " + favourites.toString());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "Array de favoritos en el onRestart: " + favourites.toString());
    }

    private void raiseFavoriteFileIntoArray() {
        fileControllers = new FileController();
        favourites = fileControllers.fileFavReader();
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
                Intent intentInterestLink=new Intent(InitHomeActivity.this, WebViewActivity.class );
                startActivity(intentInterestLink);
                break;
            case R.id.nav_item_five:
                Intent goFavourites = new Intent(InitHomeActivity.this,FavouritesActivity.class);
                startActivity(goFavourites);
                break;
            default:
                Log.d(TAG, "No he entrado por el onNavigationGetItemSelected");
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
        Log.d(TAG, "Parametros de entrada: " + latitude + " " + longitude + " " + Constants.DISTANCE);
        mApi.getPools(latitude, longitude, Constants.DISTANCE).enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                if (response != null && response.body() != null) {
                    mPools = (ArrayList<Pool>) response.body().results;
                    Log.d(TAG, "array de solo piscinas. Inicio");
                    for (Pool mPool : mPools) {
                        Log.d(TAG, mPool.getName() == null ? "" : mPool.getName()); //e.getLocalizedMessage() == null ? "" : e.getLocalizedMessage()
                        Log.d(TAG, String.valueOf(mPool.getLocation().getLatitude() == 0 ? "" : mPool.getLocation().getLatitude()));
                        Log.d(TAG, String.valueOf(mPool.getLocation().getLongitude() == 0 ? "" : mPool.getLocation().getLongitude()));
                    }
                    Log.d(TAG, "Parametros de salida: " + latitude + " " + longitude + " " + Constants.DISTANCE);
                    Log.d(TAG, "array de solo piscinas. Fin");
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
                Log.d(TAG, "Antes de entrar en el arraylist de sports: " + response.body().results.toString());
                Log.d(TAG, "tamaño " + String.valueOf(mSport.size()));
                if (response != null && response.body() != null) {
                    Log.d(TAG, "tamaño del array de piscinas: " + mPools.size());
                    mSport = (ArrayList<Pool>) response.body().results;
                    Log.d(TAG, "tamaño de msports: " + mSport.size());

                    for (int i = 0; i < mSport.size(); i++) {
                        if (!mPools.contains(mSport.get(i))) {
                            temporal.add(mSport.get(i));
                        }
                    }

                    if (temporal.size() == 0) temporal.add(new Pool("Undefined",new Location(0,0)));
                    Log.d(TAG, "el tamaño del array temporal es: " + temporal.size());
                    mSport = temporal;
                    Intent sendSports = new Intent(getApplicationContext(),ActivityViewAdapter.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("LIST",mPools);
                    bundle.putParcelableArrayList("LIST2", mSport);
                    sendSports.putExtras(bundle);
                    Log.d(TAG, "tamaño de mPools antes de ser enviado a ActivityViewerADAPTER: " + mPools.size());
                    startActivity(sendSports);
                    Log.d(TAG, "array de centros deportivos. Inicio");
                    for (Pool mPool : mSport) {
                        Log.d(TAG, mPool.getName() == null ? "" : mPool.getName()); //e.getLocalizedMessage() == null ? "" : e.getLocalizedMessage()
                        Log.d(TAG, String.valueOf(mPool.getLocation().getLatitude() == 0 ? "" : mPool.getLocation().getLatitude()));
                        Log.d(TAG, String.valueOf(mPool.getLocation().getLongitude() == 0 ? "" : mPool.getLocation().getLongitude()));
                    }
                    Log.d(TAG, "Parametros de salida: " + latitude + " " + longitude + " " + Constants.DISTANCE);
                    Log.d(TAG, "array de centros deportivos. Fin");
                } else {
                    Log.d(TAG, "el response o el response body viene vacio bro");
                }
            }

            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {

            }
        });
    }

}